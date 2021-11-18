/**
 * Copyright (c) 2021 SIL International
 * This software is licensed under the LGPL, version 2.1 or later
 * (http://www.gnu.org/licenses/lgpl-2.1.html)
 */

package org.sil.diagsap.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javafx.geometry.Bounds;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Screen;

import org.sil.diagsap.model.BranchItem;
import org.sil.diagsap.model.ContentBranch;
import org.sil.diagsap.model.DiagSapNode;
import org.sil.diagsap.model.DiagSapTree;
import org.sil.diagsap.model.InfixIndexBranch;
import org.sil.diagsap.model.InfixedBaseBranch;
import org.sil.lingtree.Constants;
import org.sil.lingtree.model.FontInfo;
import org.sil.lingtree.model.LexFontInfo;
import org.sil.lingtree.model.NodeType;
import org.sil.utility.StringUtilities;

/**
 * @author Andy Black
 *
 */
public class TreeDrawer {
	DiagSapTree dsTree;
	HashMap<Integer, Double> maxHeightPerLevel = new HashMap<>();
	List<ContentBranch> contentBranches = new ArrayList<ContentBranch>();
	List<InfixedBaseBranch> infixedBaseBranches = new ArrayList<InfixedBaseBranch>();

	private static final double dYCoordAdjustment = 3; // adjustment value
	private double dUnderlineYCoordinate = 3;

	public TreeDrawer(DiagSapTree dsTree) {
		super();
		this.dsTree = dsTree;
	}

	public HashMap<Integer, Double> getMaxHeightPerLevel() {
		return maxHeightPerLevel;
	}

	public void calculateMaxHeightPerLevel() {
		DiagSapNode node = dsTree.getRootNode();
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
		DiagSapNode node = dsTree.getRootNode();
		calculateYCoordinateOfANode(node, dsTree.getInitialYCoordinate());
	}

	// Determine Y-axis coordinates for this node
	private void calculateYCoordinateOfANode(DiagSapNode node, double dVerticalOffset) {
		node.setYCoordinate(dVerticalOffset);
		node.setYLowerMid(node.getYLowerMid() + dYCoordAdjustment);
		node.setYUpperMid(node.getYUpperMid() - dYCoordAdjustment);
		if (node.getYLowerMid() > dsTree.getYSize()) {
			// Keep track of total height for scrolling
			dsTree.setYSize(node.getYLowerMid());
		}
//		if (node.getNodeType() == NodeType.Lex || node.getNodeType() == NodeType.EmptyElement) {
//			// keep track of lowest for "flat" view
//			if (node.getYCoordinate() > dsTree.getLexBottomYCoordinate()) {
//				dsTree.setLexBottomYCoordinate(node.getYCoordinate());
//			}
//			if (node.getYUpperMid() > dsTree.getLexBottomYUpperMid()) {
//				dsTree.setLexBottomYUpperMid(node.getYUpperMid());
//			}
//		}
//		if (node.getNodeType() == NodeType.Gloss) {
//			// keep track of lowest for "flat" view
//			if (node.getYCoordinate() > dsTree.getGlossBottomYCoordinate()) {
//				dsTree.setGlossBottomYCoordinate(node.getYCoordinate());
//			}
//		}
//		// Determine Y-axis coordinate for any daughters
//		for (DiagSapNode daughterNode : node.getDaughters()) {
//			double dDaughterYCoordinate = node.getYCoordinate()
//					+ maxHeightPerLevel.get(node.getLevel());
//			if (daughterNode.getNodeType() != NodeType.Gloss) {
//				dDaughterYCoordinate += dsTree.getVerticalGap();
//			} else {
//				dDaughterYCoordinate += dsTree.getLexGlossGapAdjustment();
//			}
//			calculateYCoordinateOfANode(daughterNode, dDaughterYCoordinate);
//		}
	}

	public void calculateXCoordinateOfEveryNode() {
		dsTree.setHorizontalOffset(dsTree.getInitialXCoordinate());
		DiagSapNode node = dsTree.getRootNode();
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
			node.setXMid(dsTree.getHorizontalOffset() + dMaxColumnWidth / 2);
			// Update the current horizontal offset to be that mid point plus
			// half of the widest node in the column plus the gap between leaf
			// nodes.
			dsTree.setHorizontalOffset(node.getXMid() + dsTree.getHorizontalGap() + dMaxColumnWidth
					/ 2);
//		}
		node.setX1Coordinate(node.getXMid() - node.getWidth() / 2); // adjust for
																	// width of
																	// this node
		double dEnd = node.getX1Coordinate() + node.getWidth();
		if (dEnd > dsTree.getXSize()) {
			dsTree.setXSize(dEnd); // Keep track of total width for scrolling
		}
		// System.out.printf(
		// "%1$s\tXSize = %2$s,\tWidth = %3$s,\tXCoord = %4$s,\tYCoord = %5$s, \tXMid = %5$s"
		// + "\r\n", node.getContent(), dsTree.getXSize(), node.getWidth(),
		// node.getXCoordinate(), node.getYCoordinate(), node.getXMid());
		// System.out.printf("\tYUpperMid = %1$s, \tYLowerMid = %2$s\r\n",
		// node.getYUpperMid(),
		// node.getYLowerMid());
		return node.getXMid();
	}

	private void adjustXValues(DiagSapNode node, double dAdjust) {
		// adjust this node
		node.setX1Coordinate(node.getX1Coordinate() + dAdjust);
		node.setXMid(node.getXMid() + dAdjust);
		dsTree.setHorizontalOffset(dsTree.getHorizontalOffset() + dAdjust);
		// adjust any daughter nodes
//		for (DiagSapNode daughterNode : node.getDaughters()) {
//			adjustXValues(daughterNode, dAdjust);
//		}
	}

	public void draw(Pane pane) {
//		recalculateValues();
		DiagSapNode node = dsTree.getRootNode();

		contentBranches.clear();
		infixedBaseBranches.clear();
		collectContentBranchItems(node);
		FontInfo fontInfo = LexFontInfo.getInstance();
		double dXCoordinate = dsTree.getInitialXCoordinate();
		dXCoordinate = drawContentBoxesWithUnderline(pane, fontInfo, dXCoordinate);
		calculateX1AndX2OfNode(node);
		drawNodes(node, pane);
		pane.setStyle("-fx-background-color:"
				+ StringUtilities.toRGBCode(dsTree.getBackgroundColor()) + ";");
	}

	protected double drawContentBoxesWithUnderline(Pane pane, FontInfo fontInfo, double dXCoordinate) {
		for (ContentBranch content: contentBranches) {
			Text contentTextBox = drawContentTextBox(fontInfo, dXCoordinate, content);
			pane.getChildren().add(content.getContentTextBox());
			Line line = drawUnderline(dXCoordinate, contentTextBox);
			pane.getChildren().add(line);
			dXCoordinate = updateXCoordinate(dXCoordinate, contentTextBox);
		}
		return dXCoordinate;
	}

	protected Line drawUnderline(double dXCoordinate, Text contentTextBox) {
		Bounds tbBounds = contentTextBox.getBoundsInLocal();
		double width = tbBounds.getWidth();
		double lineY = dUnderlineYCoordinate = tbBounds.getMinY() + tbBounds.getHeight() + dsTree.getTextUnderlineGap();
		Line line = new Line(dXCoordinate, lineY, dXCoordinate + width, lineY);
		line.setStroke(dsTree.getLineColor());
		line.setStrokeWidth(dsTree.getLineWidth());
		return line;
	}

	protected double updateXCoordinate(double dXCoordinate, Text contentTextBox) {
		double width = contentTextBox.getBoundsInLocal().getWidth();
		dXCoordinate += width + dsTree.getHorizontalGap();
		return dXCoordinate;
	}

	protected Text drawContentTextBox(FontInfo fontInfo, double dXCoordinate, ContentBranch content) {
		Text contentTextBox = content.getContentTextBox();
		contentTextBox.setX(dXCoordinate);
		contentTextBox.setY(dsTree.getInitialYCoordinate());
		contentTextBox.setFont(fontInfo.getFont());
		contentTextBox.setFill(fontInfo.getColor());
		return contentTextBox;
	}

	protected void collectContentBranchItems(DiagSapNode node) {
		collectContentBranchItems(node.getLeftBranch().getItem());
		collectContentBranchItems(node.getRightBranch().getItem());
	}

	protected void collectContentBranchItems(BranchItem item) {
		if (item instanceof DiagSapNode) {
			collectContentBranchItems((DiagSapNode)item);
		} else if (item instanceof ContentBranch) {
			contentBranches.add((ContentBranch)item);
		} else if (item instanceof InfixedBaseBranch) {
			InfixedBaseBranch infix = (InfixedBaseBranch)item;
			contentBranches.add(infix.getContentBefore());
			contentBranches.add(infix.getInfixContent());
			if (infix.getContentAfter() != null) {
				contentBranches.add(infix.getContentAfter());
			}
			infixedBaseBranches.add(infix);
		}
	}

	private void drawNodes(DiagSapNode node, Pane pane) {
		double yCoordinate = dUnderlineYCoordinate + (node.getLevel() * dsTree.getVerticalGap());
		System.out.println("drawNodes: node=" + node + "; level=" + node.getLevel() + "; left=" + node.getLeftBranch().getItem() + "; right=" + node.getRightBranch().getItem());
		System.out.println("\tx1=" + node.getX1Coordinate() + "; x2=" + node.getX2Coordinate());
		Line horizontalLine = new Line(node.getX1Coordinate(), yCoordinate, node.getX2Coordinate(),
				yCoordinate);
		pane.getChildren().add(horizontalLine);
		BranchItem branchItem = node.getLeftBranch().getItem();
//		if (branchItem instanceof InfixedBaseBranch) {
//			InfixedBaseBranch infix = (InfixedBaseBranch)branchItem;
//			Line infixHorizontalLine = new Line(infix.getX1Coordinate(), yCoordinate, node.getX2Coordinate(),
//					yCoordinate);
//		}
		drawNodeVerticalLine(node, pane, yCoordinate, branchItem, true);
		branchItem = node.getRightBranch().getItem();
		drawNodeVerticalLine(node, pane, yCoordinate, branchItem, false);
	}

	protected void drawNodeVerticalLine(DiagSapNode node, Pane pane, double yCoordinate,
			BranchItem branchItem, boolean isLeft) {
		System.out.println("vl: level=" + node.getLevel() + "; branch=" + branchItem);
		double finalY = yCoordinate - dsTree.getVerticalGap();
		double x = isLeft ? node.getX1Coordinate(): node.getX2Coordinate();
		Line verticalLine;
		if (branchItem instanceof DiagSapNode) {
			drawNodes((DiagSapNode) branchItem, pane);
			verticalLine = new Line(x, yCoordinate, x, finalY);
			pane.getChildren().add(verticalLine);
			System.out.println("vertical line: level=" + node.getLevel() + "; yc=" + yCoordinate);
			System.out.println("\tnode: x=" + x);
		} else if (branchItem instanceof ContentBranch) {
			verticalLine = new Line(x, yCoordinate, x, dUnderlineYCoordinate);
			pane.getChildren().add(verticalLine);
			System.out.println("vertical line: level=" + node.getLevel() + "; yc=" + yCoordinate);
			System.out.println("\tcontent: x=" + x + "; content='" +((ContentBranch)branchItem).getContent());
		} else if (branchItem instanceof InfixedBaseBranch) {
			InfixedBaseBranch base = (InfixedBaseBranch)branchItem;
			if (base.getContentAfter() != null) {
				System.out.println("base: level=" + base.getLevel() + "; yc=" + yCoordinate);
				// need a horizontal line, too.
				double infixYCoordinate = dUnderlineYCoordinate + (base.getLevel() * dsTree.getVerticalGap());
				double widthBefore = base.getContentBefore().getContentTextBox().getBoundsInLocal().getWidth();
				double x1 = base.getContentBefore().getContentTextBox().getX() + widthBefore/2;
				double widthAfter = base.getContentAfter().getContentTextBox().getBoundsInLocal().getWidth();
				double x2 = base.getContentAfter().getContentTextBox().getX() + widthAfter/2;
				Line horizontalLine = new Line(x1, infixYCoordinate, x2, infixYCoordinate);
				pane.getChildren().add(horizontalLine);
				verticalLine = new Line(x1, infixYCoordinate, x1, infixYCoordinate - dsTree.getVerticalGap());
				pane.getChildren().add(verticalLine);
				System.out.println("\tinfix: x1=" + x1 + "; yinfix=" + infixYCoordinate + " to " + (infixYCoordinate - dsTree.getVerticalGap()));
				Line verticalLine2 = new Line(x2, infixYCoordinate, x2, infixYCoordinate - dsTree.getVerticalGap());
				pane.getChildren().add(verticalLine2);
				System.out.println("\tinfix: x2=" + x2);

				double xMid = calculateXMidOfInfixedBase(base);
				Line verticalMid = new Line(xMid, infixYCoordinate, xMid, yCoordinate);
				pane.getChildren().add(verticalMid);
			} else {
				verticalLine = new Line(x, yCoordinate, x, dUnderlineYCoordinate);
				pane.getChildren().add(verticalLine);
			}
		} else if (branchItem instanceof InfixIndexBranch) {
			InfixIndexBranch ifxIndex = (InfixIndexBranch)branchItem;
			x = calculateXMidOfInfixIndex(ifxIndex);
			verticalLine = new Line(x, yCoordinate, x, dUnderlineYCoordinate);
			pane.getChildren().add(verticalLine);
		}
	}

	public StringBuilder drawAsSVG() {
		recalculateValues();
		DiagSapNode node = dsTree.getRootNode();
		StringBuilder sb = new StringBuilder();
		// Trying to convert from pixels to mm does not come out right.
		// So we're going with pixels
		// final String sMM = "mm";
		// sb.append(Constants.SVG_HEADER.replace("{0}",
		// pixelsToMM(dsTree.getXSize()) + sMM).replace(
		// "{1}", pixelsToMM(dsTree.getYSize()) + sMM));
		sb.append(Constants.SVG_HEADER.replace("{0}", String.valueOf(dsTree.getXSize() + 10)).replace(
				"{1}", String.valueOf(dsTree.getYSize())));
		sb.append(Constants.SVG_BACKGROUND_COLOR.replace("{0}",
				StringUtilities.toRGBCode(dsTree.getBackgroundColor())));
		drawNodesAsSVG(node, sb);
		sb.append(Constants.SVG_END_ELEMENT);
		return sb;
	}

	private void recalculateValues() {
		calculateMaxHeightPerLevel();
		calculateYCoordinateOfEveryNode();
		calculateXCoordinateOfEveryNode();
		if (dsTree.isUseRightToLeftOrientation()) {
			adjustForRightToLeftOrientation();
		}
	}

	private void calculateX1AndX2OfNode(DiagSapNode node) {
		BranchItem leftItem = node.getLeftBranch().getItem();
		calculateXCoordinateOfItem(node, leftItem, true);
		BranchItem rightItem = node.getRightBranch().getItem();
		calculateXCoordinateOfItem(node, rightItem, false);
	}

	protected void calculateXCoordinateOfItem(DiagSapNode node, BranchItem branchItem, boolean isX1) {
		double x;
		if (branchItem instanceof DiagSapNode) {
			DiagSapNode itemNode = (DiagSapNode) branchItem;
			calculateX1AndX2OfNode(itemNode);
			x = itemNode.getX1Coordinate()
					+ (itemNode.getX2Coordinate() - itemNode.getX1Coordinate()) / 2;
			if (isX1) {
				node.setX1Coordinate(x);
			} else {
				node.setX2Coordinate(x);
			}
		} else if (branchItem instanceof ContentBranch) {
			x = calculateXCoordinateOfContentMid((ContentBranch) branchItem);
			if (isX1) {
				node.setX1Coordinate(x);
			} else {
				node.setX2Coordinate(x);
			}
		} else if (branchItem instanceof InfixedBaseBranch) {
			InfixedBaseBranch infix = (InfixedBaseBranch)branchItem;
			x = calculateXMidOfInfixedBase(infix);
			node.setX1Coordinate(x);
			if (infix.getContentAfter() != null) {
				x = calculateXCoordinateOfContentMid(infix.getContentAfter());
				node.setX2Coordinate(x);
			} else {
				System.out.println("infix with no content after");
			}
		} else if (branchItem instanceof InfixIndexBranch) {
			InfixIndexBranch ifxIndex = (InfixIndexBranch)branchItem;
			x = calculateXMidOfInfixIndex(ifxIndex);
			if (isX1) {
				node.setX1Coordinate(x);
			} else {
				node.setX2Coordinate(x);
			}
		}
	}

	protected double calculateXMidOfInfixIndex(InfixIndexBranch ifxIndex) {
		double x;
		int index = ifxIndex.getIndex();
		InfixedBaseBranch base = infixedBaseBranches.get(index - 1);
		Text infixTextBox = base.getInfixContent().getContentTextBox();
		double width = infixTextBox.getBoundsInLocal().getWidth();
		double x1 = infixTextBox.getX();
		x = x1 + (width/2);
		return x;
	}

	protected double calculateXMidOfInfixedBase(InfixedBaseBranch infix) {
		double x;
		double xInitial = infix.getContentBefore().getContentTextBox().getX();
		Text rightmostTextBox = (infix.getContentAfter() != null) ? infix.getContentAfter()
				.getContentTextBox() : infix.getContentBefore().getContentTextBox();
		double xFinal = rightmostTextBox.getX()
				+ rightmostTextBox.getBoundsInLocal().getWidth();
		x = (xInitial + xFinal) / 2;
		return x;
	}

	private double calculateXCoordinateOfContentMid(ContentBranch contentBranch) {
		double x = contentBranch.getContentTextBox().getX();
		double width = contentBranch.getContentTextBox().getBoundsInLocal().getWidth();
		return x + (width/2);
	}

	public void adjustForRightToLeftOrientation()
	{
		double adjust = dsTree.getXSize() + dsTree.getInitialXCoordinate();
		DiagSapNode node = dsTree.getRootNode();
		adjustForRightToLeftOrientation(node, adjust);
	}

	private void adjustForRightToLeftOrientation(DiagSapNode node, double adjust) {
		node.setX1Coordinate((adjust - node.getWidth()) - node.getX1Coordinate());
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
		sb.append(StringUtilities.toRGBCode(dsTree.getLineColor()));
		sb.append("\" stroke-width=\"");
		sb.append(dsTree.getLineWidth());
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
		// sb.append(StringUtilities.toRGBCode(dsTree.getLineColor()));
		// sb.append("\" stroke-width=\"");
		// sb.append(pixelsToMM(dsTree.getLineWidth()));
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
