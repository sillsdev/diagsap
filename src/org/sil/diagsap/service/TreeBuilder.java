/**
 * Copyright (c) 2021 SIL International
 * This software is licensed under the LGPL, version 2.1 or later
 * (http://www.gnu.org/licenses/lgpl-2.1.html)
 */

package org.sil.diagsap.service;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import org.antlr.v4.runtime.BailErrorStrategy;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.NoViableAltException;
import org.antlr.v4.runtime.atn.PredictionMode;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.sil.diagsap.model.DiagSapTree;
import org.sil.diagsap.descriptionparser.DescriptionConstants;
import org.sil.diagsap.descriptionparser.DescriptionErrorInfo;
import org.sil.diagsap.descriptionparser.DescriptionErrorListener;
import org.sil.diagsap.descriptionparser.DescriptionErrorListener.VerboseListener;
import org.sil.diagsap.descriptionparser.antlr4generated.DescriptionLexer;
import org.sil.diagsap.descriptionparser.antlr4generated.DescriptionParser;

/**
 * @author Andy Black
 *
 */
public class TreeBuilder {

	static int numberOfErrors;
	static int characterPositionInLineOfError;
	static int lineNumberOfError;
	static String errorMessage = "";
	static String sDescription;
	static TreeSemanticChecker semanticChecker;

	public static int getNumberOfErrors() {
		return numberOfErrors;
	}

	public static int getLineNumberOfError() {
		return lineNumberOfError;
	}

	public static int getCharacterPositionInLineOfError() {
		return characterPositionInLineOfError;
	}

	public static String getErrorMessage() {
		return errorMessage;
	}

	public static String getMarkedDescription(String sMark) {
		StringBuilder sb = new StringBuilder();
		int iCharPosOfMark = getPositionOfMark();
		sb.append(sDescription.substring(0, iCharPosOfMark));
		sb.append(sMark);
		sb.append(sDescription.substring(iCharPosOfMark));
		return sb.toString();
	}

	public static String getDescriptionBeforeMark() {
		int iCharPosOfMark = getPositionOfMark();
		//System.out.println("Before: iCharPosOfMark=" + iCharPosOfMark);
		return sDescription.substring(0, iCharPosOfMark);
	}

	public static String getDescriptionAfterMark() {
		int iCharPosOfMark = getPositionOfMark();
		//System.out.println("After:  iCharPosOfMark=" + iCharPosOfMark);
		return sDescription.substring(iCharPosOfMark);
	}

	private static int getPositionOfMark() {
		int iCharPosOfMark = 0;
		if (lineNumberOfError == 1) {
			iCharPosOfMark = characterPositionInLineOfError;
		} else {
			int iCurrentLineNum = 2;
			int iCharPosOfNL = sDescription.indexOf("\n");
			while (iCharPosOfNL > -1 && iCurrentLineNum <= lineNumberOfError) {
				//System.out.println("iCharPosOfMark=" + iCharPosOfMark + " iCharPosOfNL=" + iCharPosOfNL);
				iCharPosOfMark += (iCharPosOfNL + 1);
				String sRest = sDescription.substring(iCharPosOfMark);
				//System.out.println("\tiCharPosOfMark=" + iCharPosOfMark + " sRest='" + sRest + "'");
				iCharPosOfNL = sRest.indexOf("\n");
				iCurrentLineNum++;
			}
			iCharPosOfMark = iCharPosOfMark + characterPositionInLineOfError;
		}
		return iCharPosOfMark;
	}

	public static DiagSapTree parseAString(String sInput, DiagSapTree origTree) {
		semanticChecker = TreeSemanticChecker.getInstance();
		semanticChecker.initialize();

		sDescription = sInput;
		CharStream input = CharStreams.fromString(sInput);
		DescriptionLexer lexer = new DescriptionLexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		DescriptionParser parser = new DescriptionParser(tokens);

		// try with simpler/faster SLL(*)
		parser.getInterpreter().setPredictionMode(PredictionMode.SLL);
		// add error listener
		parser.removeErrorListeners();
		VerboseListener errListener = new DescriptionErrorListener.VerboseListener();
		errListener.clearErrorMessageList();
		parser.addErrorListener(errListener);
		parser.setErrorHandler(new BailErrorStrategy());

		ParseTree parseTree = null;
		try {
		// begin parsing at rule 'description'
		parseTree = parser.description();
		// if we get here, there was no syntax error and SLL(*) was enough;
		// there is no need to try full LL(*)
		}
		catch (ParseCancellationException ex) {// thrown by BailErrorStrategy
			tokens.seek(0); // rewind input stream
			parser.reset();
			parser.removeErrorListeners();
			errListener = new DescriptionErrorListener.VerboseListener();
			errListener.clearErrorMessageList();
			parser.addErrorListener(errListener);
			// full now with full LL(*)
			parser.getInterpreter().setPredictionMode(PredictionMode.LL);
			try {
			parseTree = parser.description();
			}
			catch (ParseCancellationException | NoViableAltException ex2) {
				// do nothing
			}
		}
		if (parseTree == null) {
			numberOfErrors = 1;
			return origTree;
		}
		numberOfErrors = parser.getNumberOfSyntaxErrors();
		if (numberOfErrors > 0) {
			errListener = (VerboseListener) parser.getErrorListeners().get(0);
			DescriptionErrorInfo info = errListener.getErrorMessages().get(0);
			errorMessage = info.getMsg();
			lineNumberOfError = info.getLine();
			characterPositionInLineOfError = info.getCharPositionInLine();
			return origTree;
		}
		ParseTreeWalker walker = new ParseTreeWalker(); // create standard
														// walker
		BuildTreeFromDescriptionListener validator = new BuildTreeFromDescriptionListener(parser);
		walker.walk(validator, parseTree); // initiate walk of tree with
											// listener
		DiagSapTree dsTree = validator.getTree();
		semanticChecker.checkTree(dsTree);
		if (semanticChecker.getNumberOfErrors() > 0) {
			numberOfErrors += semanticChecker.getNumberOfErrors();
		}
		restoreTreeParameters(origTree, dsTree);
		return dsTree;
	}

	private static void restoreTreeParameters(DiagSapTree origTree, DiagSapTree dsTree) {
		dsTree.setBackgroundColor(origTree.getBackgroundColor());
		dsTree.setHorizontalGap(origTree.getHorizontalGap());
		dsTree.setInitialXCoordinate(origTree.getInitialXCoordinate());
		dsTree.setInitialYCoordinate(origTree.getInitialYCoordinate());
		dsTree.setLexicalFontInfo(origTree.getLexicalFontInfo());
		dsTree.setLineColor(origTree.getLineColor());
		dsTree.setLineWidth(origTree.getLineWidth());
		dsTree.setSaveAsPng(origTree.isSaveAsPng());
		dsTree.setSaveAsSVG(origTree.isSaveAsSVG());
		dsTree.setVerticalGap(origTree.getVerticalGap());
		dsTree.setTextUnderlineGap(origTree.getTextUnderlineGap());
		dsTree.setMinimalGapBetweenVerticalLines(origTree.getMinimalGapBetweenVerticalLines());
		dsTree.setUseDashedLinesForSplitInfixedBase(origTree.isUseDashedLinesForSplitInfixedBase());
		dsTree.setUseRightToLeftOrientation(origTree.isUseRightToLeftOrientation());
	}

	public static String buildErrorMessage(ResourceBundle bundle) {
		String sSyntaxErrorMessage = bundle.getString("descriptionsyntaxerror.unknown");

		switch (TreeBuilder.getErrorMessage()) {
		case DescriptionConstants.CONTENT_AFTER_COMPLETED_TREE:
			sSyntaxErrorMessage = bundle
					.getString("descriptionsyntaxerror.content_after_completed_tree");
			break;

		case DescriptionConstants.MISSING_CLOSING_PAREN:
			sSyntaxErrorMessage = bundle.getString("descriptionsyntaxerror.missing_closing_paren");
			break;

		case DescriptionConstants.MISSING_CLOSING_WEDGE:
			sSyntaxErrorMessage = bundle.getString("descriptionsyntaxerror.missing_closing_wedge");
			break;

		case DescriptionConstants.MISSING_CONSTITUENT:
			sSyntaxErrorMessage = bundle
					.getString("descriptionsyntaxerror.missing_constituent");
			break;

		case DescriptionConstants.MISSING_CONTENT:
			sSyntaxErrorMessage = bundle
					.getString("descriptionsyntaxerror.missing_content");
			break;

		case DescriptionConstants.MISSING_CONTENT_AND_CLOSING_PAREN:
			sSyntaxErrorMessage = bundle
					.getString("descriptionsyntaxerror.missing_content_and_closing_paren");
			break;

		case DescriptionConstants.MISSING_OPENING_PAREN:
			sSyntaxErrorMessage = bundle.getString("descriptionsyntaxerror.missing_opening_paren");
			break;

		case DescriptionConstants.MISSING_OPENING_WEDGE:
			sSyntaxErrorMessage = bundle.getString("descriptionsyntaxerror.missing_opening_wedge");
			break;

		case DescriptionConstants.MISSING_RIGHT_BRANCH:
			sSyntaxErrorMessage = bundle.getString("descriptionsyntaxerror.missing_right_branch");
			break;

		case DescriptionConstants.TOO_MANY_CLOSING_PARENS:
			sSyntaxErrorMessage = bundle.getString("descriptionsyntaxerror.too_many_close_parens");
			break;

		default:
			System.out.println("error was: " + TreeBuilder.getErrorMessage());
			System.out.println("number of errors was: " + TreeBuilder.getNumberOfErrors());
			System.out.println("line number was: " + TreeBuilder.getLineNumberOfError());
			System.out.println("character position was: "
					+ TreeBuilder.getCharacterPositionInLineOfError());
			break;
		}
		return sSyntaxErrorMessage;
	}

	public static String buildErrorMessagePart1(String sSyntaxErrorMessage, ResourceBundle bundle) {
		StringBuilder sb = new StringBuilder();
		sb.append(bundle.getString("descriptionsyntaxerror.errorindescription"));
		sb.append(sSyntaxErrorMessage);
		String sMsgDetectedAt = bundle.getString("descriptionsyntaxerror.detectedat");
		int iLine = TreeBuilder.getLineNumberOfError();
		int iPos = TreeBuilder.getCharacterPositionInLineOfError();
		String sMessage = sMsgDetectedAt.replace("{0}", String.valueOf(iLine)).replace("{1}",
				String.valueOf(iPos));
		sb.append(sMessage);
		sb.append("\n\n");
		return sb.toString();
	}

	public static String buildSemanticErrorMessage(ResourceBundle bundle) {
		StringBuilder sb = new StringBuilder();
		for (SemanticErrorMessage semError : semanticChecker.getTwoConsecutiveNodes()) {
			String result = formatSemanticErrorMessagePortion(bundle, semError);
			sb.append(result);
		}
		for (SemanticErrorMessage semError : semanticChecker.getInfixRelatedErrors()) {
			String result = formatSemanticErrorMessagePortion(bundle, semError);
			sb.append(result);
		}
		return sb.toString();
	}

	protected static String formatSemanticErrorMessagePortion(ResourceBundle bundle,
			SemanticErrorMessage semError) {
		String message = bundle.getString(semError.getMessage());
		Object[] args = semError.getArgs();
		MessageFormat msgFormatter = new MessageFormat("");
		msgFormatter.setLocale(bundle.getLocale());
		msgFormatter.applyPattern(message);
		String result = msgFormatter.format(args);
		return result;
	}
}
