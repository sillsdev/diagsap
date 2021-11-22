/**
 * Copyright (c) 2021 SIL International
 * This software is licensed under the LGPL, version 2.1 or later
 * (http://www.gnu.org/licenses/lgpl-2.1.html)
 */

package org.sil.diagsap.service;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
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
	TreeSemanticChecker semanticChecker;
	List<SemanticErrorMessage> infixRelatedErrors = new ArrayList<SemanticErrorMessage>();
	List<SemanticErrorMessage> twoConsecutiveNodes = new ArrayList<SemanticErrorMessage>();
	SemanticErrorMessage errorMessage;
	String message;
	Object[] args;
	
	/* (non-Javadoc)
	 * @see org.sil.diasap.service.ServiceBaseTest#setUp()
	 */
	@Before
	public void setUp() throws Exception {
		super.setUp();
		semanticChecker = TreeSemanticChecker.getInstance();
		infixRelatedErrors.clear();
		twoConsecutiveNodes.clear();
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
		final String twoConsecutiveMessage = "descriptionsemanticerror.two_consecutive_nodes";
		dsTree = TreeBuilder.parseAString("(((a) (b))((c)(d)))", origTree);
		semanticChecker.checkTree(dsTree);
		twoConsecutiveNodes = semanticChecker.getTwoConsecutiveNodes();
		assertEquals(1, twoConsecutiveNodes.size());
		errorMessage = twoConsecutiveNodes.get(0);
		message = errorMessage.getMessage();
		assertEquals(twoConsecutiveMessage, message);
		args = errorMessage.getArgs();
		assertEquals(2, args.length);
		assertEquals("((a)(b))", (String)args[0]);
		assertEquals("((c)(d))", (String)args[1]);

		dsTree = TreeBuilder.parseAString("((a)((b)(((c) (d))((e)(f)))))", origTree);
		semanticChecker.checkTree(dsTree);
		twoConsecutiveNodes = semanticChecker.getTwoConsecutiveNodes();
		assertEquals(1, twoConsecutiveNodes.size());
		errorMessage = twoConsecutiveNodes.get(0);
		message = errorMessage.getMessage();
		assertEquals(twoConsecutiveMessage, message);
		args = errorMessage.getArgs();
		assertEquals(2, args.length);
		assertEquals("((c)(d))", (String)args[0]);
		assertEquals("((e)(f))", (String)args[1]);
	}

	@Test
	public void mismatchedIndexIndexAndInfixTest() {
		// infix index number wrong
		final String indexWrongMessage = "descriptionsemanticerror.infix_index_has_no_matching_infix_base";
		final String indexedBaseWrongMessage = "descriptionsemanticerror.infixed_base_has_no_index";
		final String duplicateIndexMessage = "descriptionsemanticerror.infix_index_duplicated";
		
		dsTree = TreeBuilder.parseAString("((\\1) ((g<in>) ((pí-)((\\3) ((p<in>a-)((m-)(ulod)))))))", origTree);
		semanticChecker.checkTree(dsTree);
		assertEquals(2, semanticChecker.getNumberOfErrors());
		infixRelatedErrors = semanticChecker.getInfixRelatedErrors();
		assertEquals(2, infixRelatedErrors.size());
		errorMessage = infixRelatedErrors.get(0);
		message = errorMessage.getMessage();
		assertEquals(indexWrongMessage, message);
		args = errorMessage.getArgs();
		assertEquals(1, args.length);
		assertEquals("\\3", (String)args[0]);

		errorMessage = infixRelatedErrors.get(1);
		message = errorMessage.getMessage();
		assertEquals(indexedBaseWrongMessage, message);
		args = errorMessage.getArgs();
		assertEquals(1, args.length);
		assertEquals("(p<in>a-)", (String)args[0]);

		// missing matching infix
		dsTree = TreeBuilder.parseAString("((\\1) ((g<in>) ((pí-)((\\2) ((pa-)((m-)(ulod)))))))", origTree);
		semanticChecker.checkTree(dsTree);
		assertEquals(1, semanticChecker.getNumberOfErrors());
		infixRelatedErrors = semanticChecker.getInfixRelatedErrors();
		assertEquals(1, infixRelatedErrors.size());
		errorMessage = infixRelatedErrors.get(0);
		message = errorMessage.getMessage();
		assertEquals(indexWrongMessage, message);
		args = errorMessage.getArgs();
		assertEquals(1, args.length);
		assertEquals("\\2", (String)args[0]);

		// missing matching infix index
		dsTree = TreeBuilder.parseAString("((\\1) ((g<in>) ((pí-)((m-) ((p<in>a-)((m-)(ulod)))))))", origTree);
		semanticChecker.checkTree(dsTree);
		assertEquals(1, semanticChecker.getNumberOfErrors());
		infixRelatedErrors = semanticChecker.getInfixRelatedErrors();
		assertEquals(1, infixRelatedErrors.size());
		errorMessage = infixRelatedErrors.get(0);
		message = errorMessage.getMessage();
		assertEquals(indexedBaseWrongMessage, message);
		args = errorMessage.getArgs();
		assertEquals(1, args.length);
		assertEquals("(p<in>a-)", (String)args[0]);

		// duplicate index
		dsTree = TreeBuilder.parseAString("((\\1) ((g<in>) ((pí-)((\\1) ((p<in>a-)((m-)(ulod)))))))", origTree);
		semanticChecker.checkTree(dsTree);
		assertEquals(2, semanticChecker.getNumberOfErrors());
		infixRelatedErrors = semanticChecker.getInfixRelatedErrors();
		assertEquals(2, infixRelatedErrors.size());
		errorMessage = infixRelatedErrors.get(0);
		message = errorMessage.getMessage();
		assertEquals(duplicateIndexMessage, message);
		args = errorMessage.getArgs();
		assertEquals(1, args.length);
		assertEquals("\\1", (String)args[0]);

		errorMessage = infixRelatedErrors.get(1);
		message = errorMessage.getMessage();
		assertEquals(indexedBaseWrongMessage, message);
		args = errorMessage.getArgs();
		assertEquals(1, args.length);
		assertEquals("(p<in>a-)", (String)args[0]);

		// three duplicated indexes
		dsTree = TreeBuilder.parseAString("((\\1) ((g<in>) ((pí-)((\\1) ((p<in>a-)((\\1) ((m-)(ul<on>od))))))))", origTree);
		semanticChecker.checkTree(dsTree);
		assertEquals(3, semanticChecker.getNumberOfErrors());
		infixRelatedErrors = semanticChecker.getInfixRelatedErrors();
		assertEquals(3, infixRelatedErrors.size());
		errorMessage = infixRelatedErrors.get(0);
		message = errorMessage.getMessage();
		assertEquals(duplicateIndexMessage, message);
		args = errorMessage.getArgs();
		assertEquals(1, args.length);
		assertEquals("\\1", (String)args[0]);

		errorMessage = infixRelatedErrors.get(1);
		message = errorMessage.getMessage();
		assertEquals(indexedBaseWrongMessage, message);
		args = errorMessage.getArgs();
		assertEquals(1, args.length);
		assertEquals("(p<in>a-)", (String)args[0]);

		errorMessage = infixRelatedErrors.get(2);
		message = errorMessage.getMessage();
		assertEquals(indexedBaseWrongMessage, message);
		args = errorMessage.getArgs();
		assertEquals(1, args.length);
		assertEquals("(ul<on>od)", (String)args[0]);
	}
}
