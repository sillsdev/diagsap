/**
 * Copyright (c) 2021 SIL International
 * This software is licensed under the LGPL, version 2.1 or later
 * (http://www.gnu.org/licenses/lgpl-2.1.html)
 */

package org.sil.diagsap.service;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.geometry.NodeOrientation;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

import org.sil.diagsap.model.BranchItem;
import org.sil.diagsap.model.ContentBranch;
import org.sil.diagsap.model.DiagSapNode;
import org.sil.diagsap.model.DiagSapTree;
import org.sil.diagsap.model.InfixIndexBranch;
import org.sil.diagsap.model.InfixedBaseBranch;
import org.sil.diagsap.Constants;
import org.sil.lingtree.model.FontInfo;
import org.sil.lingtree.model.LexFontInfo;
import org.sil.utility.StringUtilities;

/**
 * @author Andy Black
 *
 */
public class TreeDrawer {
	DiagSapTree dsTree;
	List<ContentBranch> contentBranches = new ArrayList<ContentBranch>();
	List<InfixedBaseBranch> infixedBaseBranches = new ArrayList<InfixedBaseBranch>();

	private double dUnderlineYCoordinate = 3;

	public TreeDrawer(DiagSapTree dsTree) {
		super();
		this.dsTree = dsTree;
	}

	public void draw(Pane pane) {
		DiagSapNode node = dsTree.getRootNode();
		contentBranches.clear();
		infixedBaseBranches.clear();
		collectContentAndInfixedBaseBranchItems(node);
		FontInfo fontInfo = LexFontInfo.getInstance();
		double dXCoordinate = dsTree.getInitialXCoordinate();
		pane.getChildren().clear();
		dXCoordinate = drawContentBoxesWithUnderline(pane, fontInfo, dXCoordinate);
		calculateX1AndX2OfNode(node);
		drawNodes(node, pane);
		calculateTreeHeightAndWidth(node);
		if (dsTree.isUseRightToLeftOrientation()) {
			adjustForRightToLeftBasedOnPane(pane);
		}
		pane.setStyle("-fx-background-color:"
				+ StringUtilities.toRGBCode(dsTree.getBackgroundColor()) + ";");
	}

	public void calculateTreeHeightAndWidth(DiagSapNode node) {
		double xTreeWidth = calculateTreeWidth();
		dsTree.setXSize(xTreeWidth);
		double yTreeHeight = dUnderlineYCoordinate + node.getLevel() * dsTree.getVerticalGap();
		dsTree.setYSize(yTreeHeight);
	}

	protected double calculateTreeWidth() {
		ContentBranch lastContent = contentBranches.get(contentBranches.size()-1);
		Text lastMorphTextBox = lastContent.getContentTextBox();
		double xEnd = lastMorphTextBox.getX() + lastMorphTextBox.getBoundsInLocal().getWidth();
		return xEnd;
	}

	protected double drawContentBoxesWithUnderline(Pane pane, FontInfo fontInfo, double dXCoordinate) {
		for (ContentBranch content: contentBranches) {
			Text contentTextBox = drawContentTextBox(fontInfo, dXCoordinate, content);
			pane.getChildren().add(contentTextBox);
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
		Line line = createLine(dXCoordinate, lineY, dXCoordinate + width, lineY);
		return line;
	}

	protected Line createLine(double x1, double y1, double x2, double y2) {
		Line line = new Line(x1, y1, x2, y2);
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

	protected void collectContentAndInfixedBaseBranchItems(DiagSapNode node) {
		collectContentBranchItems(node.getLeftBranch().getItem());
		collectContentBranchItems(node.getRightBranch().getItem());
	}

	protected void collectContentBranchItems(BranchItem item) {
		if (item instanceof DiagSapNode) {
			collectContentAndInfixedBaseBranchItems((DiagSapNode)item);
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
		Line horizontalLine = createLine(node.getX1Coordinate(), yCoordinate, node.getX2Coordinate(),
				yCoordinate);
		pane.getChildren().add(horizontalLine);
		BranchItem branchItem = node.getLeftBranch().getItem();
		drawNodeVerticalLine(node, pane, yCoordinate, branchItem, true);
		branchItem = node.getRightBranch().getItem();
		drawNodeVerticalLine(node, pane, yCoordinate, branchItem, false);
	}

	protected void drawNodeVerticalLine(DiagSapNode node, Pane pane, double yCoordinate,
			BranchItem branchItem, boolean isLeft) {
		double finalY = yCoordinate - dsTree.getVerticalGap();
		double x = isLeft ? node.getX1Coordinate(): node.getX2Coordinate();
		Line verticalLine;
		if (branchItem instanceof DiagSapNode) {
			drawNodes((DiagSapNode) branchItem, pane);
			verticalLine = createLine(x, yCoordinate, x, finalY);
			pane.getChildren().add(verticalLine);
		} else if (branchItem instanceof ContentBranch) {
			verticalLine = createLine(x, yCoordinate, x, dUnderlineYCoordinate);
			pane.getChildren().add(verticalLine);
		} else if (branchItem instanceof InfixedBaseBranch) {
			InfixedBaseBranch base = (InfixedBaseBranch)branchItem;
			if (base.getContentAfter() != null) {
				// need a horizontal line, too.
				double infixYCoordinate = dUnderlineYCoordinate + (base.getLevel() * dsTree.getVerticalGap());
				double widthBefore = base.getContentBefore().getContentTextBox().getBoundsInLocal().getWidth();
				double x1 = base.getContentBefore().getContentTextBox().getX() + widthBefore/2;
				double widthAfter = base.getContentAfter().getContentTextBox().getBoundsInLocal().getWidth();
				double x2 = base.getContentAfter().getContentTextBox().getX() + widthAfter/2;
				Line horizontalLine = createLine(x1, infixYCoordinate, x2, infixYCoordinate);
				horizontalLine = makeLineBeDashed(horizontalLine);
				pane.getChildren().add(horizontalLine);
				verticalLine = createLine(x1, infixYCoordinate, x1, infixYCoordinate - dsTree.getVerticalGap());
				verticalLine = makeLineBeDashed(verticalLine);
				pane.getChildren().add(verticalLine);
				Line verticalLine2 = createLine(x2, infixYCoordinate, x2, infixYCoordinate - dsTree.getVerticalGap());
				verticalLine2 = makeLineBeDashed(verticalLine2);
				pane.getChildren().add(verticalLine2);

				double xMid = calculateXMidOfInfixedBase(base);
				Line verticalMid = createLine(xMid, infixYCoordinate, xMid, yCoordinate);
				pane.getChildren().add(verticalMid);
			} else {
				verticalLine = createLine(x, yCoordinate, x, dUnderlineYCoordinate);
				pane.getChildren().add(verticalLine);
			}
		} else if (branchItem instanceof InfixIndexBranch) {
			InfixIndexBranch ifxIndex = (InfixIndexBranch)branchItem;
			x = calculateXMidOfInfixIndex(ifxIndex);
			verticalLine = createLine(x, yCoordinate, x, dUnderlineYCoordinate);
			pane.getChildren().add(verticalLine);
		}
	}

	private Line makeLineBeDashed(Line line) {
		if (dsTree.isUseDashedLinesForSplitInfixedBase()) {
			line.getStrokeDashArray().clear();
			line.getStrokeDashArray().add(2.5 + (dsTree.getLineWidth() - 1));
		}
		return line;
	}

	public StringBuilder drawAsSVG(Pane pane, double treeWidth, double treeHeight) {
		StringBuilder sb = new StringBuilder();
		// Trying to convert from pixels to mm does not come out right.
		// So we're going with pixels
		// final String sMM = "mm";
		// sb.append(Constants.SVG_HEADER.replace("{0}",
		// pixelsToMM(dsTree.getXSize()) + sMM).replace(
		// "{1}", pixelsToMM(dsTree.getYSize()) + sMM));s=d
		sb.append(Constants.SVG_HEADER.replace("{0}", String.valueOf(treeWidth + 10)).replace(
				"{1}", String.valueOf(treeHeight + 10)));
		sb.append(Constants.SVG_BACKGROUND_COLOR.replace("{0}",
				StringUtilities.toRGBCode(dsTree.getBackgroundColor())));
		drawAsSVGBasedOnPane(pane, sb);
		sb.append(Constants.SVG_END_ELEMENT);
		return sb;
	}

	private void drawAsSVGBasedOnPane(Pane pane, StringBuilder sb) {
		for (Node node : pane.getChildrenUnmodifiable()) {
			if (node instanceof Text) {
				Text morphTextBox = (Text)node;
				createTextAsSVG(morphTextBox, dsTree.getLexicalFontInfo(), sb);
			} else if (node instanceof Line) {
				Line line = (Line)node;
				createLineAsSVG(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY(), line.getStrokeDashArray(), sb);
			}
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
			if (isX1) {
				node.setX1Coordinate(x);
			} else {
				node.setX2Coordinate(x);
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
		x = calculateXMidOfInfix(base);
		return x;
	}

	protected double calculateXMidOfInfix(InfixedBaseBranch base) {
		double x;
		Text infixTextBox = base.getInfixContent().getContentTextBox();
		double width = infixTextBox.getBoundsInLocal().getWidth();
		double x1 = infixTextBox.getX();
		x = x1 + (width/2);
		return x;
	}

	protected double calculateXMidOfInfixedBase(InfixedBaseBranch base) {
		double xInfixMid = calculateXMidOfInfix(base);
		Text leftmostTextBox = base.getContentBefore().getContentTextBox();
		double xInitial = leftmostTextBox.getX() + (leftmostTextBox.getBoundsInLocal().getWidth()/2);
		Text rightmostTextBox = (base.getContentAfter() != null) ? base.getContentAfter()
				.getContentTextBox() : base.getContentBefore().getContentTextBox();
		double xFinal = rightmostTextBox.getX()
				+ (rightmostTextBox.getBoundsInLocal().getWidth())/2;
		double xMid = (xInitial + xFinal) / 2;
		if (base.getContentAfter() != null) {
			double diff = xMid - xInfixMid;
			double minimalGap = dsTree.getMinimalGapBetweenVerticalLines();
			if (Math.abs(diff) < minimalGap) {
				if (diff < 0) {
					xMid = xMid - dsTree.getLineWidth() - minimalGap;
				} else {
					xMid = xMid + dsTree.getLineWidth() + minimalGap;
				}
			}
		}
		return xMid;
	}

	private double calculateXCoordinateOfContentMid(ContentBranch contentBranch) {
		double x = contentBranch.getContentTextBox().getX();
		double width = contentBranch.getContentTextBox().getBoundsInLocal().getWidth();
		return x + (width/2);
	}

	private void adjustForRightToLeftBasedOnPane(Pane pane) {
		double adjust = dsTree.getXSize() + dsTree.getInitialXCoordinate();
		for (Node node : pane.getChildrenUnmodifiable()) {
			if (node instanceof Text) {
				Text morphTextBox = (Text)node;
				morphTextBox = adustTextBoxForRightToLeft(adjust, morphTextBox);
			} else if (node instanceof Line) {
				Line line = (Line)node;
				line = adjustLineForRightToLeft(adjust, line);
			}
		}
	}

	protected Line adjustLineForRightToLeft(double adjust, Line line) {
		line.setStartX(adjust - line.getStartX());
		line.setEndX(adjust - line.getEndX());
		return line;
	}

	protected Text adustTextBoxForRightToLeft(double adjust, Text textBox) {
		textBox.setX(adjust - (textBox.getX() + textBox.getBoundsInLocal().getWidth()));
		textBox.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
		return textBox;
	}

	private void createLineAsSVG(double x1, double y1, double x2, double y2, ObservableList<Double> dasharray, StringBuilder sb) {
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
		int dashSize = dasharray.size();
		if (dsTree.isUseDashedLinesForSplitInfixedBase() && dashSize > 0) {
			sb.append("\" stroke-dasharray=\"");
			for (int i = 0; i < dashSize; i++) {
				sb.append(dasharray.get(i));
				if (i < (dashSize-1)) {
					sb.append(",");
				}
			}
		}
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
}
