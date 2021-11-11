/**
 * Copyright (c) 2021 SIL International
 * This software is licensed under the LGPL, version 2.1 or later
 * (http://www.gnu.org/licenses/lgpl-2.1.html)
 */

package org.sil.diasap.service;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sil.diagsap.descriptionparser.DescriptionConstants;
import org.sil.diagsap.model.DiagSapTree;
import org.sil.diagsap.service.TreeBuilder;
import org.sil.diagsap.service.TreeSemanticChecker;

/**
 * @author Andy Black
 *
 */
public class TreeSemanticCheckerTest extends ServiceBaseTest {

	DiagSapTree dsTree;
	String sDescriptionWithErrorLocationMarked;
	String result;
	TreeSemanticChecker semanticChecker;
	
	/* (non-Javadoc)
	 * @see org.sil.diasap.service.ServiceBaseTest#setUp()
	 */
	@Before
	public void setUp() throws Exception {
		super.setUp();
		semanticChecker = TreeSemanticChecker.getInstance();
	}

	/* (non-Javadoc)
	 * @see org.sil.diasap.service.ServiceBaseTest#tearDown()
	 */
	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}

	@Test
	public void twoNodesWitinANodeTest() {
		dsTree = TreeBuilder.parseAString("(((a) (b))((c)(d)))", origTree);
		result = semanticChecker.checkTree(dsTree);
		assertEquals("Two consecutive nodes found between \"((a)(b))\" and \"((c)(d))\".  This is not allowed; change one to a value, an infix index, or an infix base.\n", result);
		dsTree = TreeBuilder.parseAString("((a)((b)(((c) (d))((e)(f)))))", origTree);
		result = semanticChecker.checkTree(dsTree);
		assertEquals("Two consecutive nodes found between \"((c)(d))\" and \"((e)(f))\".  This is not allowed; change one to a value, an infix index, or an infix base.\n", result);
	}

	@Test
	public void mismatchedIndexIndexAndInfixTest() {
		// infix index number wrong
		dsTree = TreeBuilder.parseAString("((\\1) ((g<in>) ((pí-)((\\3) ((p<in>a-)((m-)(ulod)))))))", origTree);
		result = semanticChecker.checkTree(dsTree);
		String expected = "The infix index \"\\3\" has no matching infix.  This is not allowed; change its number or add an infix base.\n"
				+ "The infix \"(p<in>a-)\" has no matching index.\n";
		assertEquals(expected, result);

		// missing matching infix
		dsTree = TreeBuilder.parseAString("((\\1) ((g<in>) ((pí-)((\\2) ((pa-)((m-)(ulod)))))))", origTree);
		result = semanticChecker.checkTree(dsTree);
		expected = "The infix index \"\\2\" has no matching infix.  This is not allowed; change its number or add an infix base.\n";
		assertEquals(expected, result);

		// missing matching infix index
		dsTree = TreeBuilder.parseAString("((\\1) ((g<in>) ((pí-)((m-) ((p<in>a-)((m-)(ulod)))))))", origTree);
		result = semanticChecker.checkTree(dsTree);
		expected = "The infix \"(p<in>a-)\" has no matching index.\n";
		assertEquals(expected, result);

		// duplicate index
		dsTree = TreeBuilder.parseAString("((\\1) ((g<in>) ((pí-)((\\1) ((p<in>a-)((m-)(ulod)))))))", origTree);
		result = semanticChecker.checkTree(dsTree);
		expected = "The infix index \"\\1\" appears more than once.  It must only be there one time.\n"
				+ "The infix \"(p<in>a-)\" has no matching index.\n";
		assertEquals(expected, result);
		// three duplicated indexes
		dsTree = TreeBuilder.parseAString("((\\1) ((g<in>) ((pí-)((\\1) ((p<in>a-)((\\1) ((m-)(ul<on>od))))))))", origTree);
		result = semanticChecker.checkTree(dsTree);
		expected = "The infix index \"\\1\" appears more than once.  It must only be there one time.\n"
				+ "The infix \"(p<in>a-)\" has no matching index.\n"
				+ "The infix \"(ul<on>od)\" has no matching index.\n";
		assertEquals(expected, result);
	}
}
