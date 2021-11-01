/**
 * Copyright (c) 2021 SIL International
 * This software is licensed under the LGPL, version 2.1 or later
 * (http://www.gnu.org/licenses/lgpl-2.1.html)
 */

package org.sil.diasap.descriptionparser;

import static org.junit.Assert.*;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sil.diagsap.descriptionparser.DescriptionErrorListener;
import org.sil.diagsap.descriptionparser.DescriptionErrorListener.VerboseListener;
import org.sil.diagsap.descriptionparser.antlr4generated.DescriptionLexer;
import org.sil.diagsap.descriptionparser.antlr4generated.DescriptionParser;
import org.sil.diagsap.descriptionparser.DescriptionConstants;
import org.sil.diagsap.descriptionparser.DescriptionErrorInfo;

/**
 * @author Andy Black
 *
 */
public class DescriptionRecognizerTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void validDescriptionsTest() {
		// following from original Help Topics documentation
		checkValidDescription(
				"((beauti) ((ful) (ly)))",
				"(description ( (node ( (content beauti) ) ( (node ( (content ful) ) ( (content ly) )) )) ) <EOF>)");
		checkValidDescription(
				"(((beauti) (ful)) (ly))",
				"(description ( (node ( (node ( (content beauti) ) ( (content ful) )) ) ( (content ly) )) ) <EOF>)");
		checkValidDescription(
				"((\\1)(((p<in>ag) (–arál)) (an)))",
				"(description ( (node ( (content (infixindex \\1)) ) ( (node ( (node ( (content p (infix (openWedge <) in (closeWedge >)) ag) ) ( (content –arál) )) ) ( (content an) )) )) ) <EOF>)");
		checkValidDescription(
				"((\\1)((i) ((g<in>) (luto))))",
				"(description ( (node ( (content (infixindex \\1)) ) ( (node ( (content i) ) ( (node ( (content g (infix (openWedge <) in (closeWedge >))) ) ( (content luto) )) )) )) ) <EOF>)");
		checkValidDescription(
				"((\\1) ((g<in>) ((pí-)((\\2) ((p<in>a-)((m-)(ulod)))))))",
				"(description ( (node ( (content (infixindex \\1)) ) ( (node ( (content g (infix (openWedge <) in (closeWedge >))) ) ( (node ( (content pí-) ) ( (node ( (content (infixindex \\2)) ) ( (node ( (content p (infix (openWedge <) in (closeWedge >)) a-) ) ( (node ( (content m-) ) ( (content ulod) )) )) )) )) )) )) ) <EOF>)");
	}

	private void checkValidDescription(String sDescription, String sANTLRTree) {
		DescriptionParser parser = parseAString(sDescription);
		int numErrors = parser.getNumberOfSyntaxErrors();
		assertEquals(0, numErrors);
		ParseTree tree = parser.description();
		assertEquals(sANTLRTree, tree.toStringTree(parser));
	}

	private DescriptionParser parseAString(String sInput) {
		CharStream input = CharStreams.fromString(sInput);
		DescriptionLexer lexer = new DescriptionLexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		DescriptionParser parser = new DescriptionParser(tokens);
		return parser;
	}

	private DescriptionParser parseAStringExpectFailure(String sInput) {
		CharStream input = CharStreams.fromString(sInput);
		DescriptionLexer lexer = new DescriptionLexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		DescriptionParser parser = new DescriptionParser(tokens);
		parser.removeErrorListeners();
		VerboseListener errListener = new DescriptionErrorListener.VerboseListener();
		errListener.clearErrorMessageList();
		parser.addErrorListener(errListener);
		// begin parsing at rule 'description'
		ParseTree tree = parser.description();
		// uncomment the next two lines to see what parsed
//		String sTree = tree.toStringTree(parser);
//		System.out.println(sTree);
		return parser;
	}

	@Test
	public void invalidDescriptionsTest() {
		checkInvalidDescription("", DescriptionConstants.MISSING_OPENING_PAREN, 0, 1);
		checkInvalidDescription("(", "no viable alternative at input '('", 1, 1);
		checkInvalidDescription("()", "no viable alternative at input '()'", 1, 1);
		checkInvalidDescription("(a", "no viable alternative at input '(a'", 2, 1);
		checkInvalidDescription("a)", DescriptionConstants.MISSING_OPENING_PAREN, 2, 3);
		checkInvalidDescription("(a)", DescriptionConstants.MISSING_CLOSING_PAREN, 2, 2);
		checkInvalidDescription("((a))b", DescriptionConstants.CONTENT_AFTER_COMPLETED_TREE, 5, 1);
		checkInvalidDescription("((a))(b", DescriptionConstants.CONTENT_AFTER_COMPLETED_TREE, 5, 2);
		checkInvalidDescription("((a)))", DescriptionConstants.TOO_MANY_CLOSING_PARENS, 6, 1);
		checkInvalidDescription("(a b) (c))", "no viable alternative at input '(ab'", 3, 1);
		checkInvalidDescription("a (b (c))", "mismatched input '(' expecting ')'", 2, 2);
		checkInvalidDescription("\\1 (am (ci))", "mismatched input '(' expecting ')'", 3, 2);
		checkInvalidDescription("(t \\1 (am (ci))", "no viable alternative at input '(t\\1'", 3, 1);
		checkInvalidDescription("(t (am (ci))", "no viable alternative at input '(t('", 3, 1);
		checkInvalidDescription("((t (am (ci))", "no viable alternative at input '((t(am('", 8, 2);
		checkInvalidDescription("(t (am (ci)", "no viable alternative at input '(t('", 3, 1);
		checkInvalidDescription("(a (\\1\\2noun))", "no viable alternative at input '(a('", 3, 1);
		checkInvalidDescription(
				"((\\1)(\\2)(\\3)(\\4)(\\5)(\\6)(\\7)(\\8)(\\9)(((p<in>ag) (–arál)) (an)))",
				DescriptionConstants.MISSING_CLOSING_PAREN, 4, 2);
	}

	private void checkInvalidDescription(String sDescription, String sFailedPortion, int iPos,
			int iNumErrors) {
		DescriptionParser parser = parseAStringExpectFailure(sDescription);
		assertEquals(iNumErrors, parser.getNumberOfSyntaxErrors());
		VerboseListener errListener = (VerboseListener) parser.getErrorListeners().get(0);
		assertNotNull(errListener);
		DescriptionErrorInfo info = errListener.getErrorMessages().get(0);
		assertNotNull(info);
		assertEquals(sFailedPortion, info.getMsg());
		assertEquals(iPos, info.getCharPositionInLine());
	}
}
