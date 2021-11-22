/**
 * Copyright (c) 2021 SIL International
 * This software is licensed under the LGPL, version 2.1 or later
 * (http://www.gnu.org/licenses/lgpl-2.1.html)
 */

package org.sil.diagsap.descriptionparser;

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
		checkValidDescription(
				"((beauti) ((ful) (ly)))",
				"(description ( (node (leftbranch (branch ( (content beauti) ))) (rightbranch (branch ( (node (leftbranch (branch ( (content ful) ))) (rightbranch (branch ( (content ly) )))) )))) ) <EOF>)");
		checkValidDescription(
				"(((beauti) (ful)) (ly))",
				"(description ( (node (leftbranch (branch ( (node (leftbranch (branch ( (content beauti) ))) (rightbranch (branch ( (content ful) )))) ))) (rightbranch (branch ( (content ly) )))) ) <EOF>)");
		checkValidDescription(
				"((\\1)(((p<in>ag) (–arál)) (an)))",
				"(description ( (node (leftbranch (branch ( (infixindex \\1) ))) (rightbranch (branch ( (node (leftbranch (branch ( (node (leftbranch (branch ( (infixedbase (content p) (infix < (content in) >) (content ag)) ))) (rightbranch (branch ( (content –arál) )))) ))) (rightbranch (branch ( (content an) )))) )))) ) <EOF>)");
		checkValidDescription(
				"((\\1)((i) ((g<in>) (luto))))",
				"(description ( (node (leftbranch (branch ( (infixindex \\1) ))) (rightbranch (branch ( (node (leftbranch (branch ( (content i) ))) (rightbranch (branch ( (node (leftbranch (branch ( (infixedbase (content g) (infix < (content in) >)) ))) (rightbranch (branch ( (content luto) )))) )))) )))) ) <EOF>)");
		checkValidDescription(
				"((\\1) ((g<in>) ((pí-)((\\2) ((p<in>a-)((m-)(ulod)))))))",
				"(description ( (node (leftbranch (branch ( (infixindex \\1) ))) (rightbranch (branch ( (node (leftbranch (branch ( (infixedbase (content g) (infix < (content in) >)) ))) (rightbranch (branch ( (node (leftbranch (branch ( (content pí-) ))) (rightbranch (branch ( (node (leftbranch (branch ( (infixindex \\2) ))) (rightbranch (branch ( (node (leftbranch (branch ( (infixedbase (content p) (infix < (content in) >) (content a-)) ))) (rightbranch (branch ( (node (leftbranch (branch ( (content m-) ))) (rightbranch (branch ( (content ulod) )))) )))) )))) )))) )))) )))) ) <EOF>)");
		checkValidDescription(
				"((\\1) ((m-)(ul<on>od)))",
				"(description ( (node (leftbranch (branch ( (infixindex \\1) ))) (rightbranch (branch ( (node (leftbranch (branch ( (content m-) ))) (rightbranch (branch ( (infixedbase (content ul) (infix < (content on) >) (content od)) )))) )))) ) <EOF>)");
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
		// uncomment the next line to see what the error messages were
//		errListener.getErrorMessages().stream().forEach(err -> System.out.println("error: " + err.getMsg()));
		return parser;
	}

	@Test
	public void invalidDescriptionsTest() {
		checkInvalidDescription("((\\1)(p<in>ag)) (–arál)) (an)))", DescriptionConstants.CONTENT_AFTER_COMPLETED_TREE, 17, 2);
		checkInvalidDescription("", DescriptionConstants.MISSING_OPENING_PAREN, 0, 1);
		checkInvalidDescription("(", DescriptionConstants.MISSING_CONTENT_AND_CLOSING_PAREN, 1, 1);
		checkInvalidDescription("()", DescriptionConstants.MISSING_CONTENT, 2, 1);
		checkInvalidDescription("(a", DescriptionConstants.MISSING_CLOSING_PAREN, 2, 1);
		checkInvalidDescription("a)", DescriptionConstants.MISSING_OPENING_PAREN, 1, 1);
		checkInvalidDescription("(a)", DescriptionConstants.MISSING_CONSTITUENT, 3, 1);
		checkInvalidDescription("((a))b", DescriptionConstants.MISSING_RIGHT_BRANCH, 4, 2);
		checkInvalidDescription("((a))(b", DescriptionConstants.MISSING_RIGHT_BRANCH, 4, 3);
		checkInvalidDescription("((a)))", DescriptionConstants.MISSING_RIGHT_BRANCH, 4, 2);
		checkInvalidDescription("((a)(b)))", DescriptionConstants.TOO_MANY_CLOSING_PARENS, 8, 1);
		checkInvalidDescription("(((a)(b))(c)))", DescriptionConstants.TOO_MANY_CLOSING_PARENS, 13, 1);
		checkInvalidDescription("(((a)(b)))(c))", DescriptionConstants.TOO_MANY_CLOSING_PARENS, 9, 1);
		checkInvalidDescription("(a b)", DescriptionConstants.MISSING_CLOSING_PAREN, 3, 1);
		checkInvalidDescription("((a) (b c))", DescriptionConstants.MISSING_CLOSING_PAREN, 8, 1);
		checkInvalidDescription("(a b) (c))", DescriptionConstants.MISSING_CLOSING_PAREN, 3, 2);
		checkInvalidDescription("a (b (c))", DescriptionConstants.MISSING_OPENING_PAREN, 2, 2);
		checkInvalidDescription("\\1 (am (ci))", DescriptionConstants.MISSING_OPENING_PAREN, 3, 2);
		checkInvalidDescription("(t \\1 (am (ci))", DescriptionConstants.MISSING_CLOSING_PAREN, 3, 1);
		checkInvalidDescription("(t (am (ci))", DescriptionConstants.MISSING_CLOSING_PAREN, 3, 3);
		checkInvalidDescription("((t (am (ci))", DescriptionConstants.MISSING_CLOSING_PAREN, 4, 5);
		checkInvalidDescription("(a (\\1\\2noun))", DescriptionConstants.MISSING_CLOSING_PAREN, 3, 1);
		checkInvalidDescription("((a (\\1\\2noun))", DescriptionConstants.MISSING_CLOSING_PAREN, 4, 1);
		checkInvalidDescription(
				"((\\1)(\\2)(\\3)(\\4)(\\5)(\\6)(\\7)(\\8)(\\9)(((p<in>ag) (–arál)) (an)))",
				DescriptionConstants.MISSING_CLOSING_PAREN, 9, 1);
		checkInvalidDescription("((\\1)((p<in>ag) (–arál)) (an)))",	DescriptionConstants.MISSING_CLOSING_PAREN, 25, 1);
		checkInvalidDescription("((\\1)(p<in>ag) (–arál)) (an)))", DescriptionConstants.MISSING_CLOSING_PAREN, 15, 3);
		checkInvalidDescription("((\\1)(p<inag) (–arál)) (an)))", DescriptionConstants.MISSING_CLOSING_WEDGE, 12, 4);
		checkInvalidDescription("((\\1)(pinag>) (–arál)) (an)))", DescriptionConstants.MISSING_OPENING_WEDGE, 6, 5);
		checkInvalidDescription("((\\1)(((pin>ag) (–arál)) (an)))", DescriptionConstants.MISSING_OPENING_WEDGE, 8, 2);
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
