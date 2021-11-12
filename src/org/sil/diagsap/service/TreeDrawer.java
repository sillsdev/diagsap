/**
 * Copyright (c) 2021 SIL International
 * This software is licensed under the LGPL, version 2.1 or later
 * (http://www.gnu.org/licenses/lgpl-2.1.html)
 */

package org.sil.diagsap.service;

import java.util.HashMap;

import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Screen;

import org.sil.diagsap.model.DiagSapNode;
import org.sil.diagsap.model.DiagSapTree;
import org.sil.lingtree.Constants;
import org.sil.lingtree.model.FontInfo;
import org.sil.lingtree.model.NodeType;
import org.sil.utility.StringUtilities;

/**
 * @author Andy Black
 *
 */
public class TreeDrawer {
	DiagSapTree ltTree;
	HashMap<Integer, Double> maxHeightPerLevel = new HashMap<>();

	private static final double dYCoordAdjustment = 3; // adjustment value
	private static final double dTriangleOffset = 3;

	public TreeDrawer(DiagSapTree ltTree) {
		super();
		this.ltTree = ltTree;
	}

	public HashMap<Integer, Double> getMaxHeightPerLevel() {
		return maxHeightPerLevel;
	}

	public void calculateMaxHeightPerLevel() {
		DiagSapNode node = ltTree.getRootNode();
		calculateMaxHeightPerLevel(node);
	}

	private void calculateMaxHeightPerLevel(DiagSapNode node) {
		int iLevel = node.getLevel();
		double thisHeight = node.getHeight();
		if (maxHeightPerLevel.get(iLevel) == null) {
			maxHeightPerLevel.put(iLevel, thisHeight);
		} else if (thisHeight > maxHeightPerLevel.get(iLevel)) {
			maxHeightPerLevel.put(iLevel, thisHeight);
		}
//		for (DiagSapNode daughterNode : node.getDaughters()) {
//			calculateMaxHeightPerLevel(daughterNode);
//		}
	}

	public void calculateYCoordinateOfEveryNode() {
		DiagSapNode node = ltTree.getRootNode();
		calculateYCoordinateOfANode(node, ltTree.getInitialYCoordinate());
	}

	// Determine Y-axis coordinates for this node
	private void calculateYCoordinateOfANode(DiagSapNode node, double dVerticalOffset) {
		node.setYCoordinate(dVerticalOffset);
		node.setYLowerMid(node.getYLowerMid() + dYCoordAdjustment);
		node.setYUpperMid(node.getYUpperMid() - dYCoordAdjustment);
		if (node.getYLowerMid() > ltTree.getYSize()) {
			// Keep track of total height for scrolling
			ltTree.setYSize(node.getYLowerMid());
		}
//		if (node.getNodeType() == NodeType.Lex || node.getNodeType() == NodeType.EmptyElement) {
//			// keep track of lowest for "flat" view
//			if (node.getYCoordinate() > ltTree.getLexBottomYCoordinate()) {
//				ltTree.setLexBottomYCoordinate(node.getYCoordinate());
//			}
//			if (node.getYUpperMid() > ltTree.getLexBottomYUpperMid()) {
//				ltTree.setLexBottomYUpperMid(node.getYUpperMid());
//			}
//		}
//		if (node.getNodeType() == NodeType.Gloss) {
//			// keep track of lowest for "flat" view
//			if (node.getYCoordinate() > ltTree.getGlossBottomYCoordinate()) {
//				ltTree.setGlossBottomYCoordinate(node.getYCoordinate());
//			}
//		}
//		// Determine Y-axis coordinate for any daughters
//		for (DiagSapNode daughterNode : node.getDaughters()) {
//			double dDaughterYCoordinate = node.getYCoordinate()
//					+ maxHeightPerLevel.get(node.getLevel());
//			if (daughterNode.getNodeType() != NodeType.Gloss) {
//				dDaughterYCoordinate += ltTree.getVerticalGap();
//			} else {
//				dDaughterYCoordinate += ltTree.getLexGlossGapAdjustment();
//			}
//			calculateYCoordinateOfANode(daughterNode, dDaughterYCoordinate);
//		}
	}

	public void calculateXCoordinateOfEveryNode() {
		ltTree.setHorizontalOffset(ltTree.getInitialXCoordinate());
		DiagSapNode node = ltTree.getRootNode();
		calculateXCoordinateOfANode(node, 0);
	}

	// Determine the X-axis coordinate for this node
	// It assumes that the width of this and all other nodes have
	// already been established.
	// It also assumes that higher branching nodes are not wider than the
	// total width of their daughters (which may not always be true...)
	private double calculateXCoordinateOfANode(DiagSapNode node, double dMaxColumnWidth) {
		node.setXMid(0);
		if (dMaxColumnWidth < node.getWidth()) {
			// remember widest node in the column
			dMaxColumnWidth = node.getWidth();
		}

//		if (node.getDaughters().size() > 0) { // is a non-leaf node
//			DiagSapNode firstDaughterNode = node.getDaughters().get(0);
//			DiagSapNode nextDaughter = firstDaughterNode.getRightSister();
//			double dLeftMost = calculateXCoordinateOfANode(firstDaughterNode, dMaxColumnWidth);
//			double dRightMost = dLeftMost;
//			while (nextDaughter != null) { // calculate coordinates for other
//											// daughters
//				dRightMost = calculateXCoordinateOfANode(nextDaughter, dMaxColumnWidth);
//				nextDaughter = nextDaughter.getRightSister();
//			}
//			// take mid point between first & last daughter
//			node.setXMid((dLeftMost + dRightMost) / 2);
//			if (dRightMost > dLeftMost) {
//				if (node.getWidth() > (dRightMost - dLeftMost)) {
//					double dAdjust = (node.getWidth() - (dRightMost - dLeftMost)) / 2;
//					node.setXMid(node.getXMid() + dAdjust);
//
//					nextDaughter = firstDaughterNode;
//					while (nextDaughter != null) {
//						adjustXValues(nextDaughter, dAdjust);
//						nextDaughter = nextDaughter.getRightSister();
//					}
//				}
//			}
//		} else { // is a leaf node
			// half the width of this column - Offset from last terminal
			// node plus
			// half the width of the widest node in this column - gap
			// between terminal nodes plus - have a new offset for next
			// terminal node

			// The mid point of this leaf node is the current horizontal offset
			// plus half of the widest node in the column.
			node.setXMid(ltTree.getHorizontalOffset() + dMaxColumnWidth / 2);
			// Update the current horizontal offset to be that mid point plus
			// half of the widest node in the column plus the gap between leaf
			// nodes.
			ltTree.setHorizontalOffset(node.getXMid() + ltTree.getHorizontalGap() + dMaxColumnWidth
					/ 2);
//		}
		node.setXCoordinate(node.getXMid() - node.getWidth() / 2); // adjust for
																	// width of
																	// this node
		double dEnd = node.getXCoordinate() + node.getWidth();
		if (dEnd > ltTree.getXSize()) {
			ltTree.setXSize(dEnd); // Keep track of total width for scrolling
		}
		// System.out.printf(
		// "%1$s\tXSize = %2$s,\tWidth = %3$s,\tXCoord = %4$s,\tYCoord = %5$s, \tXMid = %5$s"
		// + "\r\n", node.getContent(), ltTree.getXSize(), node.getWidth(),
		// node.getXCoordinate(), node.getYCoordinate(), node.getXMid());
		// System.out.printf("\tYUpperMid = %1$s, \tYLowerMid = %2$s\r\n",
		// node.getYUpperMid(),
		// node.getYLowerMid());
		return node.getXMid();
	}

	private void adjustXValues(DiagSapNode node, double dAdjust) {
		// adjust this node
		node.setXCoordinate(node.getXCoordinate() + dAdjust);
		node.setXMid(node.getXMid() + dAdjust);
		ltTree.setHorizontalOffset(ltTree.getHorizontalOffset() + dAdjust);
		// adjust any daughter nodes
//		for (DiagSapNode daughterNode : node.getDaughters()) {
//			adjustXValues(daughterNode, dAdjust);
//		}
	}

	public void draw(Pane pane) {
		recalculateValues();
		DiagSapNode node = ltTree.getRootNode();
		drawNodes(node, pane);
		pane.setStyle("-fx-background-color:"
				+ StringUtilities.toRGBCode(ltTree.getBackgroundColor()) + ";");
	}

	private void drawNodes(DiagSapNode node, Pane pane) {
//		pane.getChildren().add(node.getContentTextBox());
//		if (node.hasMother() && !node.isOmitLine() && node.getNodeType() != NodeType.Gloss) {
//			DiagSapNode mother = node.getMother();
//			if (!node.isTriangle()) {
//				// need to draw a line between mother and this node
//				Line line = new Line(mother.getXMid(), mother.getYLowerMid(), node.getXMid(),
//						node.getYUpperMid());
//				line.setStroke(ltTree.getLineColor());
//				line.setStrokeWidth(ltTree.getLineWidth());
//				pane.getChildren().add(line);
//			} else if (node.isTriangle()) {
//				drawTriangle(node, pane, mother);
//			}
//		}
//		for (DiagSapNode daughterNode : node.getDaughters()) {
//			drawNodes(daughterNode, pane);
//		}
	}

	public StringBuilder drawAsSVG() {
		recalculateValues();
		DiagSapNode node = ltTree.getRootNode();
		StringBuilder sb = new StringBuilder();
		// Trying to convert from pixels to mm does not come out right.
		// So we're going with pixels
		// final String sMM = "mm";
		// sb.append(Constants.SVG_HEADER.replace("{0}",
		// pixelsToMM(ltTree.getXSize()) + sMM).replace(
		// "{1}", pixelsToMM(ltTree.getYSize()) + sMM));
		sb.append(Constants.SVG_HEADER.replace("{0}", String.valueOf(ltTree.getXSize() + 10)).replace(
				"{1}", String.valueOf(ltTree.getYSize())));
		sb.append(Constants.SVG_BACKGROUND_COLOR.replace("{0}",
				StringUtilities.toRGBCode(ltTree.getBackgroundColor())));
		drawNodesAsSVG(node, sb);
		sb.append(Constants.SVG_END_ELEMENT);
		return sb;
	}

	private void recalculateValues() {
		calculateMaxHeightPerLevel();
		calculateYCoordinateOfEveryNode();
		calculateXCoordinateOfEveryNode();
		if (ltTree.isUseRightToLeftOrientation()) {
			adjustForRightToLeftOrientation();
		}
	}

	public void adjustForRightToLeftOrientation()
	{
		double adjust = ltTree.getXSize() + ltTree.getInitialXCoordinate();
		DiagSapNode node = ltTree.getRootNode();
		adjustForRightToLeftOrientation(node, adjust);
	}

	private void adjustForRightToLeftOrientation(DiagSapNode node, double adjust) {
		node.setXCoordinate((adjust - node.getWidth()) - node.getXCoordinate());
		node.setXMid(adjust - node.getXMid());
//		for (DiagSapNode daughterNode : node.getDaughters()) {
//			adjustForRightToLeftOrientation(daughterNode, adjust);
//		}
	}

	private void drawNodesAsSVG(DiagSapNode node, StringBuilder sb) {
//		createTextAsSVG(node.getContentTextBox(), node.getFontInfoFromNodeType(), sb);
//		if (node.hasMother() && !node.isOmitLine() && node.getNodeType() != NodeType.Gloss) {
//			DiagSapNode mother = node.getMother();
//			if (!node.isTriangle()) {
//				// need to draw a line between mother and this node
//				createLineAsSVG(mother.getXMid(), mother.getYLowerMid(), node.getXMid(),
//						node.getYUpperMid(), sb);
//			} else if (node.isTriangle()) {
//				drawTriangleAsSVG(mother, node, sb);
//			}
//		}
//		for (DiagSapNode daughterNode : node.getDaughters()) {
//			drawNodesAsSVG(daughterNode, sb);
//		}
	}

	private void createLineAsSVG(double x1, double y1, double x2, double y2, StringBuilder sb) {
		sb.append("<line x1=\"");
		sb.append(x1);
		sb.append("\" y1=\"");
		sb.append(y1);
		sb.append("\" x2=\"");
		sb.append(x2);
		sb.append("\" y2=\"");
		sb.append(y2);
		sb.append("\" stroke=\"");
		sb.append(StringUtilities.toRGBCode(ltTree.getLineColor()));
		sb.append("\" stroke-width=\"");
		sb.append(ltTree.getLineWidth());
		sb.append("\"/>\n");
		// Using mm does not work right
		// sb.append(pixelsToMM(x1));
		// sb.append("mm\" y1=\"");
		// sb.append(pixelsToMM(y1));
		// sb.append("mm\" x2=\"");
		// sb.append(pixelsToMM(x2));
		// sb.append("mm\" y2=\"");
		// sb.append(pixelsToMM(y2));
		// sb.append("mm\" stroke=\"");
		// sb.append(StringUtilities.toRGBCode(ltTree.getLineColor()));
		// sb.append("\" stroke-width=\"");
		// sb.append(pixelsToMM(ltTree.getLineWidth()));
		// sb.append("mm\"/>\n");
	}

	private void createTextAsSVG(Text tb, FontInfo fontInfo, StringBuilder sb) {
		sb.append("<text x=\"");
		// Using mm does not work right
		// sb.append(pixelsToMM(tb.getX()));
		// sb.append("mm\" y=\"");
		// sb.append(pixelsToMM(tb.getY()));
		// sb.append("mm\" font-family=\"");
		sb.append(tb.getX());
		sb.append("\" y=\"");
		sb.append(tb.getY());
		sb.append("\" font-family=\"");
		sb.append(fontInfo.getFontFamily());
		sb.append("\" font-size=\"");
		sb.append(fontInfo.getFontSize());
		String sFontType = fontInfo.getFontType();
		if (sFontType.contains("Italic")) {
			sb.append("\" font-style=\"italic");
		}
		if (sFontType.contains("Bold")) {
			sb.append("\" font-weight=\"bold");
		}
		sb.append("\" fill=\"");
		sb.append(StringUtilities.toRGBCode(fontInfo.getColor()));
		sb.append("\">");
		sb.append(tb.getText().replace("<", "&lt;").replace(">", "&gt;"));
		sb.append("</text>\n");
	}

	private void drawTriangleAsSVG(DiagSapNode mother, DiagSapNode node, StringBuilder sb) {
		double dLeftmostX = node.getXCoordinate() + dTriangleOffset;
		double dRightmostX = node.getXCoordinate() + node.getWidth() - dTriangleOffset;
		double dTopX = mother.getXMid();
		double dBottomY = node.getYUpperMid();
		double dTopY = mother.getYLowerMid();
		// right part of line
		createLineAsSVG(dLeftmostX, dBottomY, dTopX, dTopY, sb);
		// left part of line
		createLineAsSVG(dTopX, dTopY, dRightmostX, dBottomY, sb);
		// bottom part of line
		createLineAsSVG(dLeftmostX, dBottomY, dRightmostX, dBottomY, sb);
	}

	private double pixelsToInches(double pixels) {
		double dpi = Screen.getPrimary().getDpi();
		return pixels * dpi;
	}

	private double pixelsToMM(double pixels) {
		double dpi = Screen.getPrimary().getDpi();
		// int res = Toolkit.getDefaultToolkit().getScreenResolution();
		// NB: Toolkit is awt. We want FX
		double inches = pixels / dpi;
		return inches * 25.4; // there are 2.54 cm per inch so 25.4 mm per inch
	}

}
