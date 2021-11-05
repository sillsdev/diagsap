/**
 * Copyright (c) 2021 SIL International
 * This software is licensed under the LGPL, version 2.1 or later
 * (http://www.gnu.org/licenses/lgpl-2.1.html)
 */

package org.sil.diagsap.model;

import org.sil.lingtree.model.FontInfo;
import org.sil.lingtree.model.LexFontInfo;

import javafx.scene.text.Text;

public class DiagSapNode {

	// content of the node
	Text content1TextBox = new Text(0, 0, "");
	Text content2TextBox = new Text(0, 0, "");
    DiagSapNode node1 = null;
    DiagSapNode node2 = null;

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

	public String getContent1() {
		return content1TextBox.getText();
	}

	public void setContent1(String content) {
		content1TextBox.setText(content);
		changeFontInfo();
	}

	public Text getContent1TextBox() {
		return content1TextBox;
	}

	public String getContent2() {
		return content2TextBox.getText();
	}

	public void setContent2(String content) {
		content2TextBox.setText(content);
		changeFontInfo();
	}

	public Text getContent2TextBox() {
		return content2TextBox;
	}

	public DiagSapNode getNode1() {
		return node1;
	}

	public void setNode1(DiagSapNode node1) {
		this.node1 = node1;
	}

	public DiagSapNode getNode2() {
		return node2;
	}

	public void setNode2(DiagSapNode node2) {
		this.node2 = node2;
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

	public void setiLevel(int iLevel) {
		this.iLevel = iLevel;
	}

	public boolean hasMother() {
		return (mother == null) ? false : true;
	}

	public double getHeight() {
		dHeight = content1TextBox.getBoundsInLocal().getHeight();
		return dHeight;
	}

	public double getWidth() {
		dWidth = content1TextBox.getBoundsInLocal().getWidth();
		return dWidth;
	}

	private void changeFontInfo() {
		FontInfo fontInfo = LexFontInfo.getInstance();;
		content1TextBox.setFont(fontInfo.getFont());
		content1TextBox.setFill(fontInfo.getColor());
	}
	public double getXCoordinate() {
		return dXCoordinate;
	}

	public void setXCoordinate(double dXCoordinate) {
		this.dXCoordinate = dXCoordinate;
		content1TextBox.setX(dXCoordinate);
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
		content1TextBox.setY(dYCoordinate);
		// set y upper mid and lower mid (top of box and bottom of box)
		dYUpperMid = content1TextBox.getLayoutBounds().getMinY();
		dYLowerMid = content1TextBox.getLayoutBounds().getMaxY();
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
}
