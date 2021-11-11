/**
 * Copyright (c) 2021 SIL International
 * This software is licensed under the LGPL, version 2.1 or later
 * (http://www.gnu.org/licenses/lgpl-2.1.html)
 */

package org.sil.diagsap.model;

import org.sil.lingtree.model.FontInfo;
import org.sil.lingtree.model.LexFontInfo;

import javafx.scene.text.Text;

public class DiagSapNode extends BranchItem {

	// TODO: should DiagapNode have UI values or should these be in some other class that is used to draw what is here?
    Branch leftBranch;
    Branch rightBranch;

    private int iLevel; // level (or depth) of the node within the tree

	// mother of this node in the tree
	private DiagSapNode mother;

	private double dHeight; // height of the node
	private double dWidth; // width of the node

	// left horizontal position of the node
	private double dXCoordinate;
	// mid horizontal position of the node
	private double dXMid;
	// upper vertical position of the node
	private double dYCoordinate;
	// lower mid position of the node for drawing a line below the node
	private double dYLowerMid;
	// upper mid position of the node for drawing a line above the node
	private double dYUpperMid;

	public Branch getLeftBranch() {
		return leftBranch;
	}

	public void setLeftBranch(Branch leftBranch) {
		this.leftBranch = leftBranch;
	}

	public Branch getRightBranch() {
		return rightBranch;
	}

	public void setRightBranch(Branch rightBranch) {
		this.rightBranch = rightBranch;
	}

	public DiagSapNode getMother() {
		return mother;
	}

	public void setMother(DiagSapNode mother) {
		this.mother = mother;
	}

	public int getLevel() {
		return iLevel;
	}

	public void setLevel(int iLevel) {
		this.iLevel = iLevel;
	}

	public boolean hasMother() {
		return (mother == null) ? false : true;
	}

	public double getHeight() {
//		dHeight = content1TextBox.getBoundsInLocal().getHeight();
		return dHeight;
	}

	public double getWidth() {
//		dWidth = content1TextBox.getBoundsInLocal().getWidth();
		return dWidth;
	}

	private void changeFontInfo() {
		FontInfo fontInfo = LexFontInfo.getInstance();;
//		content1TextBox.setFont(fontInfo.getFont());
//		content1TextBox.setFill(fontInfo.getColor());
	}
	public double getXCoordinate() {
		return dXCoordinate;
	}

	public void setXCoordinate(double dXCoordinate) {
		this.dXCoordinate = dXCoordinate;
//		content1TextBox.setX(dXCoordinate);
	}

	public double getXMid() {
		return dXMid;
	}

	public void setXMid(double dXMid) {
		this.dXMid = dXMid;
	}

	public double getYCoordinate() {
		return dYCoordinate;
	}

	public void setYCoordinate(double dYCoordinate) {
		// the baseline y coordinate
		this.dYCoordinate = dYCoordinate;
//		content1TextBox.setY(dYCoordinate);
//		// set y upper mid and lower mid (top of box and bottom of box)
//		dYUpperMid = content1TextBox.getLayoutBounds().getMinY();
//		dYLowerMid = content1TextBox.getLayoutBounds().getMaxY();
	}

	public double getYLowerMid() {
		return dYLowerMid;
	}

	public void setYLowerMid(double dYLowerMid) {
		this.dYLowerMid = dYLowerMid;
	}

	public double getYUpperMid() {
		return dYUpperMid;
	}

	public void setYUpperMid(double dYUpperMid) {
		this.dYUpperMid = dYUpperMid;
	}

	@Override
	public String reconstructDescription() {
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		BranchItem item = leftBranch.getItem();
		sb.append(item.reconstructDescription());
		item = rightBranch.getItem();
		sb.append(item.reconstructDescription());
		sb.append(")");
		return sb.toString();
	}
}
