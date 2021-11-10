/**
 * Copyright (c) 2021 SIL International
 * This software is licensed under the LGPL, version 2.1 or later
 * (http://www.gnu.org/licenses/lgpl-2.1.html)
 */
package org.sil.diasap.service;

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
		DiagSapTree origTree = new DiagSapTree();
		DiagSapTree dsTree = TreeBuilder.parseAString("(S (NP) ((VP))", origTree);
		checkErrorValues(origTree, dsTree, 1, 1, 12, DescriptionConstants.MISSING_CLOSING_PAREN);

		dsTree = TreeBuilder.parseAString("(S (NP (VP))", origTree);
		checkErrorValues(origTree, dsTree, 1, 1, 10, DescriptionConstants.MISSING_CLOSING_PAREN);

		dsTree = TreeBuilder.parseAString("(S (NP ((VP))", origTree);
		checkErrorValues(origTree, dsTree, 2, 1, 11, DescriptionConstants.MISSING_CLOSING_PAREN);

		dsTree = TreeBuilder.parseAString("S (NP (VP))", origTree);
		checkErrorValues(origTree, dsTree, 1, 1, 0, DescriptionConstants.MISSING_OPENING_PAREN);

		dsTree = TreeBuilder.parseAString("(S (NP) VP))", origTree);
		checkErrorValues(origTree, dsTree, 2, 1, 8, DescriptionConstants.MISSING_OPENING_PAREN);

		dsTree = TreeBuilder.parseAString("(NP/s)", origTree);
		checkErrorValues(origTree, dsTree, 1, 1, 5, DescriptionConstants.MISSING_CONTENT);

		dsTree = TreeBuilder.parseAString("(NP/S)", origTree);
		checkErrorValues(origTree, dsTree, 1, 1, 5, DescriptionConstants.MISSING_CONTENT);

		dsTree = TreeBuilder.parseAString("(S NP) (V) (VP))", origTree);
		checkErrorValues(origTree, dsTree, 1, 1, 7, DescriptionConstants.CONTENT_AFTER_COMPLETED_TREE);

		String sBad = "(S) (NP (N-bar/S sup' (N(\\L Juan (\\G John)))))\n" +
				"(VP (V/ssub (\\T\\L duerme (\\G sleeps)))))";
		dsTree = TreeBuilder.parseAString(sBad, origTree);
		checkErrorValues(origTree, dsTree, 1, 1, 4, DescriptionConstants.CONTENT_AFTER_COMPLETED_TREE);
		String sDescriptionWithErrorLocationMarked = "(S)  << HERE >> (NP (N-bar/S sup' (N(\\L Juan (\\G John)))))\n" +
				"(VP (V/ssub (\\T\\L duerme (\\G sleeps)))))";
		assertEquals(sDescriptionWithErrorLocationMarked, TreeBuilder.getMarkedDescription(" << HERE >> "));

		sBad = "(S (NP) (N-bar/S sup' (N(\\L Juan (\\G John)))))\n" +
				"(VP (V/ssub (\\T\\L duerme (\\G sleeps)))))";
		dsTree = TreeBuilder.parseAString(sBad, origTree);
		checkErrorValues(origTree, dsTree, 1, 2, 0, DescriptionConstants.CONTENT_AFTER_COMPLETED_TREE);
		sDescriptionWithErrorLocationMarked = "(S (NP) (N-bar/S sup' (N(\\L Juan (\\G John)))))\n" +
				" << HERE >> (VP (V/ssub (\\T\\L duerme (\\G sleeps)))))";
		assertEquals(sDescriptionWithErrorLocationMarked, TreeBuilder.getMarkedDescription(" << HERE >> "));
		sBad = "(S (PP (P' (P (\\L de (\\G from))))(NP (N' (N (\\L aqui (\\G here))))))\n" +
				"(NP (N'/Ssup' (N(\\L Juanita (\\G Juanita)))))\n" +
				"(VP (NP (N' (N (\\LSusana (\\G Susana))))) (V' (V/ssub (\\T\\L duerme (\\G sleeps)))))))";
		dsTree = TreeBuilder.parseAString(sBad, origTree);
		String sErrorMessage = DescriptionConstants.TOO_MANY_CLOSING_PARENS;
		checkErrorValues(origTree, dsTree, 1, 3, 83, sErrorMessage);
		sDescriptionWithErrorLocationMarked = "(S (PP (P' (P (\\L de (\\G from))))(NP (N' (N (\\L aqui (\\G here))))))\n" +
				"(NP (N'/Ssup' (N(\\L Juanita (\\G Juanita)))))\n" +
				"(VP (NP (N' (N (\\LSusana (\\G Susana))))) (V' (V/ssub (\\T\\L duerme (\\G sleeps))))))) << HERE >> ";
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

		dsTree = TreeBuilder.parseAString("((institut) ((tion) ((al) ( ly))))", origTree);
		rootNode = dsTree.getRootNode();
		assertNotNull(rootNode);
		assertEquals(3, rootNode.getLevel());
		branch = rootNode.getLeftBranch();
		checkContentBranch(branch, "institut");
		assertNull(rootNode.getMother());
		branch = rootNode.getRightBranch();
		node = checkNodeBranch(branch, 2);
		branch = node.getLeftBranch();
		checkContentBranch(branch, "tion");
		branch = node.getRightBranch();
		node = checkNodeBranch(branch, 1);
		branch = node.getLeftBranch();
		checkContentBranch(branch, "al");
		branch = node.getRightBranch();
		checkContentBranch(branch, "ly");

		dsTree = TreeBuilder.parseAString("((\\1)(((p<in>ag) (–arál)) (an)))", origTree);
		rootNode = dsTree.getRootNode();
		assertNotNull(rootNode);
		assertEquals(3, rootNode.getLevel());
		branch = rootNode.getLeftBranch();
		checkInfixIndexBranch(branch, 1);
		branch = rootNode.getRightBranch();
		node = checkNodeBranch(branch, 2);
		branch = node.getRightBranch();
		checkContentBranch(branch, "an");
		branch = node.getLeftBranch();
		node = checkNodeBranch(branch, 1);
		branch = node.getRightBranch();
		checkContentBranch(branch, "–arál");
		branch = node.getLeftBranch();
		checkInfixBaseBranch(branch, "p", "in", "ag");

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
		checkInfixBaseBranch(branch, "g", "in", null);

		dsTree = TreeBuilder.parseAString("((\\1) ((g<in>) ((pí-)((\\2) ((p<in>a-)((m-)(ulod)))))))", origTree);
		rootNode = dsTree.getRootNode();
		assertNotNull(rootNode);
		assertEquals(6, rootNode.getLevel());
		branch = rootNode.getLeftBranch();
		checkInfixIndexBranch(branch, 1);
		branch = rootNode.getRightBranch();
		node = checkNodeBranch(branch, 5);
		branch = node.getLeftBranch();
		checkInfixBaseBranch(branch, "g", "in", null);
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
		checkInfixBaseBranch(branch, "p", "in", "a-");
		branch = node.getRightBranch();
		node = checkNodeBranch(branch, 1);
		branch = node.getLeftBranch();
		checkContentBranch(branch, "m-");
		branch = node.getRightBranch();
		checkContentBranch(branch, "ulod");
	}

	protected void checkInfixBaseBranch(Branch branch, String expectedContentBefore,
			String expectedInfixContent, String expectedContentAfter) {
		assertNotNull(branch);
		BranchItem bi = branch.getItem();
		assertNotNull(bi);
		InfixedBaseBranch ifxBase = (InfixedBaseBranch) bi;
		assertEquals(expectedContentBefore, ifxBase.getContentBefore());
		assertEquals(expectedInfixContent, ifxBase.getInfixContent());
		assertEquals(expectedContentAfter, ifxBase.getContentAfter());
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

	protected void checkContentBranch(Branch branch, String sContent) {
		assertNotNull(branch);
		BranchItem bi = branch.getItem();
		assertNotNull(bi);
		ContentBranch content = (ContentBranch) bi;
		assertNotNull(content);
		assertEquals(sContent, content.getContent());
	}

}
