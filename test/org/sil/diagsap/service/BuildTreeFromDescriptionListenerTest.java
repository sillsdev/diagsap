/**
 * Copyright (c) 2021 SIL International
 * This software is licensed under the LGPL, version 2.1 or later
 * (http://www.gnu.org/licenses/lgpl-2.1.html)
 */
package org.sil.diagsap.service;

import static org.junit.Assert.*;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import org.junit.Rule;
import org.junit.Test;
import org.sil.diagsap.model.Branch;
import org.sil.diagsap.model.BranchItem;
import org.sil.diagsap.model.ContentBranch;
import org.sil.diagsap.model.DiagSapNode;
import org.sil.diagsap.model.DiagSapTree;
import org.sil.diagsap.model.InfixIndexBranch;
import org.sil.diagsap.model.InfixedBaseBranch;
import org.sil.diagsap.service.TreeBuilder;
import org.sil.diagsap.descriptionparser.DescriptionConstants;
import org.sil.utility.view.JavaFXThreadingRule;

/**
 * @author Andy Black
 *
 */
public class BuildTreeFromDescriptionListenerTest extends ServiceBaseTest {

	final String nodeClassName = "DiagSapNode";

	@Rule
	public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();

	@Test
	public void fontInfoTextTest() {
		// Non-terminal, lex, and gloss and empty element nodes
		DiagSapTree origTree = new DiagSapTree();
		DiagSapTree dsTree = TreeBuilder.parseAString("((beauti) ((ful) (ly)))", origTree);
		DiagSapNode node = dsTree.getRootNode();
//		checkFontInfo(node, node.getContent1TextBox(), "Charis SIL", 12.0, "Regular", Color.BLUE);
//		DiagSapNode node1 = node.getNode1();
//		checkFontInfo(node1, node1.getContent1TextBox(), "Charis SIL", 12.0, "Regular", Color.BLUE);
//		DiagSapNode node2 = node.getNode2();
//		assertNull(node2);

		// right-to-left
		origTree.setUseRightToLeftOrientation(false);
		dsTree = TreeBuilder.parseAString("((beauti) ((ful) (ly)))", origTree);
		assertFalse(dsTree.isUseRightToLeftOrientation());
		origTree.setUseRightToLeftOrientation(true);
		dsTree = TreeBuilder.parseAString("((beauti) ((ful) (ly)))", origTree);
		assertTrue(dsTree.isUseRightToLeftOrientation());
	}

	private void checkFontInfo(DiagSapNode node, Text textBox, String fontFamily, double fontSize,
			String fontType, Color color) {
		Font font = textBox.getFont();
		assertEquals(fontFamily, font.getFamily());
		assertEquals(fontSize, font.getSize(), 0.000001);
		assertEquals(fontType, font.getStyle());
		assertEquals(color, textBox.getFill());
	}

	@Test
	public void buildTreeFailuresTest() {
		DiagSapTree dsTree = TreeBuilder.parseAString("(a)", origTree);
		checkErrorValues(origTree, dsTree, 1, 1, 3, DescriptionConstants.MISSING_CONSTITUENT);

		dsTree = TreeBuilder.parseAString("((a))b", origTree);
		checkErrorValues(origTree, dsTree, 2, 1, 4, DescriptionConstants.MISSING_RIGHT_BRANCH);

		dsTree = TreeBuilder.parseAString("((a))(b", origTree);
		checkErrorValues(origTree, dsTree, 2, 1, 4, DescriptionConstants.MISSING_RIGHT_BRANCH);

		dsTree = TreeBuilder.parseAString("((a)))", origTree);
		checkErrorValues(origTree, dsTree, 2, 1, 4, DescriptionConstants.MISSING_RIGHT_BRANCH);

		dsTree = TreeBuilder.parseAString("((a)(b)))", origTree);
		checkErrorValues(origTree, dsTree, 1, 1, 8, DescriptionConstants.TOO_MANY_CLOSING_PARENS);

		dsTree = TreeBuilder.parseAString("(((a)(b))(c)))", origTree);
		checkErrorValues(origTree, dsTree, 1, 1, 13, DescriptionConstants.TOO_MANY_CLOSING_PARENS);

		dsTree = TreeBuilder.parseAString("(((a)(b)))(c))", origTree);
		checkErrorValues(origTree, dsTree, 1, 1, 9, DescriptionConstants.TOO_MANY_CLOSING_PARENS);

		dsTree = TreeBuilder.parseAString("(a b)", origTree);
		checkErrorValues(origTree, dsTree, 1, 1, 3, DescriptionConstants.MISSING_CLOSING_PAREN);

		dsTree = TreeBuilder.parseAString("((a) (b c))", origTree);
		checkErrorValues(origTree, dsTree, 1, 1, 8, DescriptionConstants.MISSING_CLOSING_PAREN);

		dsTree = TreeBuilder.parseAString("(a b) (c))", origTree);
		checkErrorValues(origTree, dsTree, 1, 1, 3, DescriptionConstants.MISSING_CLOSING_PAREN);

		dsTree = TreeBuilder.parseAString("a (b (c))", origTree);
		checkErrorValues(origTree, dsTree, 2, 1, 2, DescriptionConstants.MISSING_OPENING_PAREN);

		dsTree = TreeBuilder.parseAString("\\1 (am (ci))", origTree);
		checkErrorValues(origTree, dsTree, 2, 1, 3, DescriptionConstants.MISSING_OPENING_PAREN);

		dsTree = TreeBuilder.parseAString("(t \\1 (am (ci))", origTree);
		checkErrorValues(origTree, dsTree, 1, 1, 3, DescriptionConstants.MISSING_CLOSING_PAREN);

		dsTree = TreeBuilder.parseAString("(t (am (ci))", origTree);
		checkErrorValues(origTree, dsTree, 3, 1, 3, DescriptionConstants.MISSING_CLOSING_PAREN);

		dsTree = TreeBuilder.parseAString("((t (am (ci))", origTree);
		checkErrorValues(origTree, dsTree, 5, 1, 4, DescriptionConstants.MISSING_CLOSING_PAREN);

		dsTree = TreeBuilder.parseAString("(a (\\1\\2noun))", origTree);
		checkErrorValues(origTree, dsTree, 1, 1, 3, DescriptionConstants.MISSING_CLOSING_PAREN);

		dsTree = TreeBuilder.parseAString("((a (\\1\\2noun))", origTree);
		checkErrorValues(origTree, dsTree, 1, 1, 4, DescriptionConstants.MISSING_CLOSING_PAREN);

		dsTree = TreeBuilder.parseAString("((\\1)(\\2)(\\3)(\\4)(\\5)(\\6)(\\7)(\\8)(\\9)(((p<in>ag) (–arál)) (an)))", origTree);
		checkErrorValues(origTree, dsTree, 1, 1, 9, DescriptionConstants.MISSING_CLOSING_PAREN);

		dsTree = TreeBuilder.parseAString("((\\1)((p<in>ag) (–arál)) (an)))", origTree);
		checkErrorValues(origTree, dsTree, 1, 1, 25, DescriptionConstants.MISSING_CLOSING_PAREN);

		dsTree = TreeBuilder.parseAString("((\\1)(p<in>ag) (–arál)) (an)))", origTree);
		checkErrorValues(origTree, dsTree, 3, 1, 15, DescriptionConstants.MISSING_CLOSING_PAREN);
		
		String sBad = "((\\1)(p<in>ag)) (–arál)) (an)))";
		dsTree = TreeBuilder.parseAString(sBad, origTree);
		checkErrorValues(origTree, dsTree, 1, 1, 17, DescriptionConstants.CONTENT_AFTER_COMPLETED_TREE);
		String sDescriptionWithErrorLocationMarked = "((\\1)(p<in>ag)) ( << HERE >> –arál)) (an)))";
		assertEquals(sDescriptionWithErrorLocationMarked, TreeBuilder.getMarkedDescription(" << HERE >> "));

		sBad = "((\\1)(p<in>ag) (–arál)) (an)))";
		dsTree = TreeBuilder.parseAString(sBad, origTree);
		checkErrorValues(origTree, dsTree, 3, 1, 15, DescriptionConstants.MISSING_CLOSING_PAREN);
		sDescriptionWithErrorLocationMarked = "((\\1)(p<in>ag)  << HERE >> (–arál)) (an)))";
		assertEquals(sDescriptionWithErrorLocationMarked, TreeBuilder.getMarkedDescription(" << HERE >> "));

		sBad = "((\\1) ((g<in>) ((pí-)((\\2) ((p<in>a-)((m-)(ulod))))))";
		dsTree = TreeBuilder.parseAString(sBad, origTree);
		checkErrorValues(origTree, dsTree, 1, 1, 47, DescriptionConstants.MISSING_CLOSING_PAREN);
		sDescriptionWithErrorLocationMarked = "((\\1) ((g<in>) ((pí-)((\\2) ((p<in>a-)((m-)(ulod << HERE >> ))))))";
		assertEquals(sDescriptionWithErrorLocationMarked, TreeBuilder.getMarkedDescription(" << HERE >> "));
	}

	private void checkErrorValues(DiagSapTree origTree, DiagSapTree dsTree,
			int expectedNumErrors, int expectedLineNumber, int expectedCharPos,
			String sExpectedErrorMessage) {
		int numErrors = TreeBuilder.getNumberOfErrors();
		int iLineNum = TreeBuilder.getLineNumberOfError();
		int iCharPos = TreeBuilder.getCharacterPositionInLineOfError();
		String sErrorMessage = TreeBuilder.getErrorMessage();
		assertEquals(expectedNumErrors, numErrors);
		assertEquals(expectedLineNumber, iLineNum);
		assertEquals(expectedCharPos, iCharPos);
		assertEquals(sExpectedErrorMessage, sErrorMessage);
		assertEquals(dsTree, origTree);
	}

	@Test
	public void buildTreesTest() {
		DiagSapTree origTree = new DiagSapTree();
		DiagSapTree dsTree = TreeBuilder.parseAString("((beauti) ((ful) (ly)))", origTree);
		DiagSapNode rootNode = dsTree.getRootNode();
		assertNotNull(rootNode);
		assertEquals(2, rootNode.getLevel());
		Branch branch = rootNode.getLeftBranch();
		checkContentBranch(branch, "beauti");
		assertNull(rootNode.getMother());
		branch = rootNode.getRightBranch();
		DiagSapNode node = checkNodeBranch(branch, 1);
		branch = node.getLeftBranch();
		checkContentBranch(branch, "ful");
		branch = node.getRightBranch();
		checkContentBranch(branch, "ly");

		dsTree = TreeBuilder.parseAString("(((beauti) (ful)) (ly))", origTree);
		rootNode = dsTree.getRootNode();
		assertNotNull(rootNode);
		assertEquals(2, rootNode.getLevel());
		assertNull(rootNode.getMother());
		branch = rootNode.getLeftBranch();
		node = checkNodeBranch(branch, 1);
		branch = node.getLeftBranch();
		checkContentBranch(branch, "beauti");
		branch = node.getRightBranch();
		checkContentBranch(branch, "ful");
		branch = rootNode.getRightBranch();
		checkContentBranch(branch, "ly");

		dsTree = TreeBuilder.parseAString("((institut) ((ion) ((al) ( ly))))", origTree);
		rootNode = dsTree.getRootNode();
		assertNotNull(rootNode);
		assertEquals(3, rootNode.getLevel());
		branch = rootNode.getLeftBranch();
		checkContentBranch(branch, "institut");
		assertNull(rootNode.getMother());
		branch = rootNode.getRightBranch();
		node = checkNodeBranch(branch, 2);
		branch = node.getLeftBranch();
		checkContentBranch(branch, "ion");
		branch = node.getRightBranch();
		node = checkNodeBranch(branch, 1);
		branch = node.getLeftBranch();
		checkContentBranch(branch, "al");
		branch = node.getRightBranch();
		checkContentBranch(branch, "ly");

		dsTree = TreeBuilder.parseAString("((\\1)(((p<in>ag) (–arál)) (an)))", origTree);
		rootNode = dsTree.getRootNode();
		assertNotNull(rootNode);
		assertEquals(4, rootNode.getLevel());
		branch = rootNode.getLeftBranch();
		checkInfixIndexBranch(branch, 1);
		branch = rootNode.getRightBranch();
		node = checkNodeBranch(branch, 3);
		branch = node.getRightBranch();
		checkContentBranch(branch, "an");
		branch = node.getLeftBranch();
		node = checkNodeBranch(branch, 2);
		branch = node.getRightBranch();
		checkContentBranch(branch, "–arál");
		branch = node.getLeftBranch();
		checkInfixBaseBranch(branch, "p", "in", "ag", 1);

		dsTree = TreeBuilder.parseAString("((\\1)((i) ((g<in>) (luto))))", origTree);
		rootNode = dsTree.getRootNode();
		assertNotNull(rootNode);
		assertEquals(3, rootNode.getLevel());
		branch = rootNode.getLeftBranch();
		checkInfixIndexBranch(branch, 1);
		branch = rootNode.getRightBranch();
		node = checkNodeBranch(branch, 2);
		branch = node.getLeftBranch();
		checkContentBranch(branch, "i");
		branch = node.getRightBranch();
		node = checkNodeBranch(branch, 1);
		branch = node.getRightBranch();
		checkContentBranch(branch, "luto");
		branch = node.getLeftBranch();
		checkInfixBaseBranch(branch, "g", "in", null, 4);

		dsTree = TreeBuilder.parseAString("((\\1) ((g<in>) ((pí-)((\\2) ((p<in>a-)((m-)(ulod)))))))", origTree);
		rootNode = dsTree.getRootNode();
		assertNotNull(rootNode);
		assertEquals(6, rootNode.getLevel());
		branch = rootNode.getLeftBranch();
		checkInfixIndexBranch(branch, 1);
		branch = rootNode.getRightBranch();
		node = checkNodeBranch(branch, 5);
		branch = node.getLeftBranch();
		checkInfixBaseBranch(branch, "g", "in", null, 7);
		branch = node.getRightBranch();
		node = checkNodeBranch(branch, 4);
		branch = node.getLeftBranch();
		checkContentBranch(branch, "pí-");
		branch = node.getRightBranch();
		node = checkNodeBranch(branch, 3);
		branch = node.getLeftBranch();
		checkInfixIndexBranch(branch, 2);
		branch = node.getRightBranch();
		node = checkNodeBranch(branch, 2);
		branch = node.getLeftBranch();
		checkInfixBaseBranch(branch, "p", "in", "a-", 1);
		branch = node.getRightBranch();
		node = checkNodeBranch(branch, 1);
		branch = node.getLeftBranch();
		checkContentBranch(branch, "m-");
		branch = node.getRightBranch();
		checkContentBranch(branch, "ulod");
	}

	protected void checkInfixBaseBranch(Branch branch, String expectedContentBefore,
			String expectedInfixContent, String expectedContentAfter, int expectedLevel) {
		assertNotNull(branch);
		BranchItem bi = branch.getItem();
		assertNotNull(bi);
		InfixedBaseBranch ifxBase = (InfixedBaseBranch) bi;
		assertEquals(expectedContentBefore, ifxBase.getContentBefore().getContent());
		assertEquals(expectedInfixContent, ifxBase.getInfixContent().getContent());
		if (expectedContentAfter == null) {
			assertNull(ifxBase.getContentAfter());
		} else {
			assertEquals(expectedContentAfter, ifxBase.getContentAfter().getContent());
		}
		assertEquals(expectedLevel, ifxBase.getLevel());
	}

	protected DiagSapNode checkNodeBranch(Branch branch, int expectedLevel) {
		assertNotNull(branch);
		assertEquals(nodeClassName, branch.getItem().getClass().getSimpleName());
		DiagSapNode node = (DiagSapNode)branch.getItem();
		assertNotNull(node);
		assertEquals(expectedLevel, node.getLevel());
		return node;
	}

	protected void checkInfixIndexBranch(Branch branch, int expectedIndex) {
		InfixIndexBranch index = (InfixIndexBranch)branch.getItem();
		assertNotNull(index);
		assertEquals(expectedIndex,index.getIndex());
	}

	protected void checkContentBranch(Branch branch, String sExpectedContent) {
		assertNotNull(branch);
		BranchItem bi = branch.getItem();
		assertNotNull(bi);
		ContentBranch content = (ContentBranch) bi;
		assertNotNull(content);
		assertEquals(sExpectedContent, content.getContent());
	}

	@Test
	public void reconstructDescriptionTest() {
		DiagSapTree origTree = new DiagSapTree();
		DiagSapTree dsTree = TreeBuilder.parseAString("((beauti) ((ful) (ly)))", origTree);
		DiagSapNode rootNode = dsTree.getRootNode();
		assertNotNull(rootNode);
		assertEquals("((beauti)((ful)(ly)))", rootNode.reconstructDescription());
		Branch branch = rootNode.getLeftBranch();
		checkReconstructedContentBranch(branch, "(beauti)");
		branch = rootNode.getRightBranch();
		DiagSapNode node = checkNodeBranch(branch, 1);
		assertEquals("((ful)(ly))", node.reconstructDescription());
		branch = node.getLeftBranch();
		checkReconstructedContentBranch(branch, "(ful)");
		branch = node.getRightBranch();
		checkReconstructedContentBranch(branch, "(ly)");

		dsTree = TreeBuilder.parseAString("(((beauti) (ful)) (ly))", origTree);
		rootNode = dsTree.getRootNode();
		assertNotNull(rootNode);
		assertEquals("(((beauti)(ful))(ly))", rootNode.reconstructDescription());
		branch = rootNode.getLeftBranch();
		node = checkReconstructedNodeBranch(branch, "((beauti)(ful))");
		branch = node.getLeftBranch();
		checkReconstructedContentBranch(branch, "(beauti)");
		branch = node.getRightBranch();
		checkReconstructedContentBranch(branch, "(ful)");
		branch = rootNode.getRightBranch();
		checkReconstructedContentBranch(branch, "(ly)");

		dsTree = TreeBuilder.parseAString("((institut) ((ion) ((al) ( ly))))", origTree);
		rootNode = dsTree.getRootNode();
		assertNotNull(rootNode);
		assertEquals("((institut)((ion)((al)(ly))))", rootNode.reconstructDescription());
		branch = rootNode.getLeftBranch();
		checkReconstructedContentBranch(branch, "(institut)");
		branch = rootNode.getRightBranch();
		node = checkReconstructedNodeBranch(branch, "((ion)((al)(ly)))");
		branch = node.getLeftBranch();
		checkReconstructedContentBranch(branch, "(ion)");
		branch = node.getRightBranch();
		node = checkReconstructedNodeBranch(branch, "((al)(ly))");
		branch = node.getLeftBranch();
		checkReconstructedContentBranch(branch, "(al)");
		branch = node.getRightBranch();
		checkReconstructedContentBranch(branch, "(ly)");

		dsTree = TreeBuilder.parseAString("((\\1)(((p<in>ag) (–arál)) (an)))", origTree);
		rootNode = dsTree.getRootNode();
		assertNotNull(rootNode);
		assertEquals(4, rootNode.getLevel());
		branch = rootNode.getLeftBranch();
		checkRecontructedInfixIndexBranch(branch, "(\\1)");
		branch = rootNode.getRightBranch();
		node = checkReconstructedNodeBranch(branch, "(((p<in>ag)(–arál))(an))");
		branch = node.getRightBranch();
		checkReconstructedContentBranch(branch, "(an)");
		branch = node.getLeftBranch();
		node = checkNodeBranch(branch, 2);
		branch = node.getRightBranch();
		checkReconstructedContentBranch(branch, "(–arál)");
		branch = node.getLeftBranch();
		checkReconstructedInfixBaseBranch(branch, "(p<in>ag)");

		dsTree = TreeBuilder.parseAString("((\\1)((i) ((g<in>) (luto))))", origTree);
		rootNode = dsTree.getRootNode();
		assertNotNull(rootNode);
		assertEquals("((\\1)((i)((g<in>)(luto))))", rootNode.reconstructDescription());
		branch = rootNode.getLeftBranch();
		checkRecontructedInfixIndexBranch(branch, "(\\1)");
		branch = rootNode.getRightBranch();
		node = checkReconstructedNodeBranch(branch, "((i)((g<in>)(luto)))");
		branch = node.getLeftBranch();
		checkReconstructedContentBranch(branch, "(i)");
		branch = node.getRightBranch();
		node = checkReconstructedNodeBranch(branch, "((g<in>)(luto))");
		branch = node.getRightBranch();
		checkReconstructedContentBranch(branch, "(luto)");
		branch = node.getLeftBranch();
		checkReconstructedInfixBaseBranch(branch, "(g<in>)");

		dsTree = TreeBuilder.parseAString("((\\1) ((g<in>) ((pí-)((\\2) ((p<in>a-)((m-)(ulod)))))))", origTree);
		rootNode = dsTree.getRootNode();
		assertNotNull(rootNode);
		assertEquals("((\\1)((g<in>)((pí-)((\\2)((p<in>a-)((m-)(ulod)))))))", rootNode.reconstructDescription());
		branch = rootNode.getLeftBranch();
		checkRecontructedInfixIndexBranch(branch, "(\\1)");
		branch = rootNode.getRightBranch();
		node = checkReconstructedNodeBranch(branch, "((g<in>)((pí-)((\\2)((p<in>a-)((m-)(ulod))))))");
		branch = node.getLeftBranch();
		checkReconstructedInfixBaseBranch(branch, "(g<in>)");
		branch = node.getRightBranch();
		node = checkReconstructedNodeBranch(branch, "((pí-)((\\2)((p<in>a-)((m-)(ulod)))))");
		branch = node.getLeftBranch();
		checkReconstructedContentBranch(branch, "(pí-)");
		branch = node.getRightBranch();
		node = checkReconstructedNodeBranch(branch, "((\\2)((p<in>a-)((m-)(ulod))))");
		branch = node.getLeftBranch();
		checkRecontructedInfixIndexBranch(branch, "(\\2)");
		branch = node.getRightBranch();
		node = checkReconstructedNodeBranch(branch, "((p<in>a-)((m-)(ulod)))");
		branch = node.getLeftBranch();
		checkReconstructedInfixBaseBranch(branch, "(p<in>a-)");
		branch = node.getRightBranch();
		node = checkReconstructedNodeBranch(branch, "((m-)(ulod))");
		branch = node.getLeftBranch();
		checkReconstructedContentBranch(branch, "(m-)");
		branch = node.getRightBranch();
		checkReconstructedContentBranch(branch, "(ulod)");
	}

	protected void checkReconstructedContentBranch(Branch branch, String sExpectedReconstruction) {
		assertNotNull(branch);
		BranchItem bi = branch.getItem();
		assertNotNull(bi);
		ContentBranch content = (ContentBranch) bi;
		assertNotNull(content);
		assertEquals(sExpectedReconstruction, content.reconstructDescription());
	}

	protected DiagSapNode checkReconstructedNodeBranch(Branch branch, String sExpected) {
		assertNotNull(branch);
		assertEquals(nodeClassName, branch.getItem().getClass().getSimpleName());
		DiagSapNode node = (DiagSapNode)branch.getItem();
		assertNotNull(node);
		assertEquals(sExpected, node.reconstructDescription());
		return node;
	}

	protected void checkRecontructedInfixIndexBranch(Branch branch, String sExpected) {
		InfixIndexBranch index = (InfixIndexBranch)branch.getItem();
		assertNotNull(index);
		assertEquals(sExpected, index.reconstructDescription());
	}

	protected void checkReconstructedInfixBaseBranch(Branch branch, String sExpected) {
		assertNotNull(branch);
		BranchItem bi = branch.getItem();
		assertNotNull(bi);
		InfixedBaseBranch ifxBase = (InfixedBaseBranch) bi;
		assertEquals(sExpected, ifxBase.reconstructDescription());
	}

}
