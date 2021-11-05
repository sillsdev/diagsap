/**
 * Copyright (c) 2021 SIL International
 * This software is licensed under the LGPL, version 2.1 or later
 * (http://www.gnu.org/licenses/lgpl-2.1.html)
 */
package org.sil.diasap.service;

import static org.junit.Assert.*;

import java.util.List;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import org.junit.Rule;
import org.junit.Test;
import org.sil.diagsap.model.DiagSapNode;
import org.sil.diagsap.model.DiagSapTree;
import org.sil.diagsap.service.TreeBuilder;
import org.sil.diagsap.descriptionparser.DescriptionConstants;
import org.sil.utility.view.JavaFXThreadingRule;

/**
 * @author Andy Black
 *
 */
public class BuildTreeFromDescriptionListenerTest extends ServiceBaseTest {
	@Rule
	public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();

	@Test
	public void fontInfoTextTest() {
		// Non-terminal, lex, and gloss and empty element nodes
		DiagSapTree origTree = new DiagSapTree();
		DiagSapTree dsTree = TreeBuilder.parseAString("((beauti) ((ful) (ly)))", origTree);
		DiagSapNode node = dsTree.getRootNode();
		checkFontInfo(node, node.getContent1TextBox(), "Charis SIL", 12.0, "Regular", Color.BLUE);
		DiagSapNode node1 = node.getNode1();
		checkFontInfo(node1, node1.getContent1TextBox(), "Charis SIL", 12.0, "Regular", Color.BLUE);
		DiagSapNode node2 = node.getNode2();
		assertNull(node2);

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
		checkErrorValues(origTree, dsTree, 1, 1, 5, DescriptionConstants.MISSING_CONTENT_AFTER_SUBSCRIPT);

		dsTree = TreeBuilder.parseAString("(NP/S)", origTree);
		checkErrorValues(origTree, dsTree, 1, 1, 5, DescriptionConstants.MISSING_CONTENT_AFTER_SUPERSCRIPT);

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
		// Basic example
		DiagSapTree origTree = new DiagSapTree();
		DiagSapTree dsTree = TreeBuilder.parseAString("((beauti) ((ful) (ly)))", origTree);
		DiagSapNode rootNode = dsTree.getRootNode();
		assertNotNull(rootNode);
		assertNotNull(rootNode.getNode2());
		checkNodeResult(rootNode, "beauti", "", null, rootNode.getNode2(), 1);
		assertNull(rootNode.getMother());
		DiagSapNode node1 = rootNode.getNode1();
		assertNull(node1);
		DiagSapNode node2 = rootNode.getNode2();
		checkNodeResult(node2, "ful", "ly", null, null, 2);
		checkNodeResult(node2.getMother(), "beauti", "", null, node2, 1);

		// lex/gloss example
		dsTree = TreeBuilder.parseAString("(((beauti) (ful)) (ly))", origTree);
		// root node
		rootNode = dsTree.getRootNode();
		node1 = rootNode.getNode1();
		assertNotNull(node1);
		checkNodeResult(rootNode, "", "ly", node1, null, 1);
		assertNull(rootNode.getMother());
		node1 = rootNode.getNode1();
		assertNotNull(node1);
		checkNodeResult(node1, "beauti", "ful", null, null, 2);
		checkNodeResult(node1.getMother(), "", "ly", node1, null, 1);
	}

	private void checkNodeResult(DiagSapNode node, String sContent1, String sContent2,
			DiagSapNode node1, DiagSapNode node2, int iLevel) {
		assertNotNull(node);
		assertEquals(iLevel, node.getLevel());
		assertEquals(sContent1, node.getContent1());
		assertEquals(sContent2, node.getContent2());
		assertEquals(node1, node.getNode1());
		assertEquals(node2, node.getNode2());
	}

}
