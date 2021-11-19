/**
 * Copyright (c) 2021 SIL International
 * This software is licensed under the LGPL, version 2.1 or later
 * (http://www.gnu.org/licenses/lgpl-2.1.html)
 */

package org.sil.diagsap.model;

import javafx.scene.paint.Color;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.sil.diagsap.Constants;
import org.sil.lingtree.model.ColorXmlAdaptor;
import org.sil.lingtree.model.FontInfo;
import org.sil.lingtree.model.LexFontInfo;

@XmlRootElement(name = "diagSapTree")
public class DiagSapTree {
	DiagSapNode rootNode;
	private int version;
	private String description;

	double dInitialXCoordinate; // initial, leftmost X coordinate
	double dInitialYCoordinate; // initial, topmost Y coordinate
	double dXSize; // total width of tree
	double dYSize; // total height of tree
	double dTextUnderlineGap; // extra gap between text and its underline
	double dVerticalGap; // extra gap between levels

	double dHorizontalGap; // extra gap between leaf nodes

	FontInfo lexicalFontInfo;

	Color backgroundColor;
	Color lineColor;
	double lineWidth;

	boolean fSaveAsPng;
	boolean fSaveAsSVG;
	boolean fUseRightToLeftOrientation;

	/**
	 * 
	 */
	public DiagSapTree() {
		dInitialXCoordinate = 10;
		dInitialYCoordinate = 20;
		dVerticalGap = 20;
		dHorizontalGap = 20;
		fUseRightToLeftOrientation = false;
		lexicalFontInfo = LexFontInfo.getInstance();
		lineWidth = 1;
		lineColor = Color.BLACK;
		backgroundColor = Color.WHITE;
		version=Constants.CURRENT_DATABASE_VERSION;
	}

	@XmlTransient
	public DiagSapNode getRootNode() {
		return rootNode;
	}

	public void setRootNode(DiagSapNode rootNode) {
		this.rootNode = rootNode;
	}

	@XmlElement(name = "initialXCoordinate")
	public double getInitialXCoordinate() {
		return dInitialXCoordinate;
	}

	public void setInitialXCoordinate(double dInitialXCoordinate) {
		this.dInitialXCoordinate = dInitialXCoordinate;
	}

	@XmlElement(name = "initialYCoordinate")
	public double getInitialYCoordinate() {
		return dInitialYCoordinate;
	}

	public void setInitialYCoordinate(double dInitialYCoordinate) {
		this.dInitialYCoordinate = dInitialYCoordinate;
	}

	@XmlTransient
	public double getXSize() {
		return dXSize;
	}

	public void setXSize(double dXSize) {
		this.dXSize = dXSize;
	}

	@XmlTransient
	public double getYSize() {
		return dYSize;
	}

	public void setYSize(double dYSize) {
		this.dYSize = dYSize;
	}

	@XmlElement(name = "verticalGap")
	public double getVerticalGap() {
		return dVerticalGap;
	}

	public void setVerticalGap(double dVerticalGap) {
		this.dVerticalGap = dVerticalGap;
	}

	@XmlElement(name = "horizontalGap")
	public double getHorizontalGap() {
		return dHorizontalGap;
	}

	public void setHorizontalGap(double dHorizontalGap) {
		this.dHorizontalGap = dHorizontalGap;
	}

	@XmlElement(name = "saveAsPng")
	public boolean isSaveAsPng() {
		return fSaveAsPng;
	}

	public void setSaveAsPng(boolean fSaveAsPng) {
		this.fSaveAsPng = fSaveAsPng;
	}

	@XmlElement(name = "saveAsSVG")
	public boolean isSaveAsSVG() {
		return fSaveAsSVG;
	}

	public void setSaveAsSVG(boolean fSaveAsSVG) {
		this.fSaveAsSVG = fSaveAsSVG;
	}

	@XmlElement(name = "textUnderlineGap")
	public double getTextUnderlineGap() {
		return dTextUnderlineGap;
	}

	public void setTextUnderlineGap(double dTextUnderlineGap) {
		this.dTextUnderlineGap = dTextUnderlineGap;
	}

	@XmlElement(name = "useRightToLeftOrientation")
	public boolean isUseRightToLeftOrientation() {
		return fUseRightToLeftOrientation;
	}

	public void setUseRightToLeftOrientation(boolean useRightToLeftOrientation) {
		this.fUseRightToLeftOrientation = useRightToLeftOrientation;
	}

	@XmlElement(name = "lexicalFontInfo")
	public FontInfo getLexicalFontInfo() {
		return lexicalFontInfo;
	}

	public void setLexicalFontInfo(FontInfo lexicalFontInfo) {
		this.lexicalFontInfo = lexicalFontInfo;
	}

	@XmlJavaTypeAdapter(ColorXmlAdaptor.class)
	public Color getLineColor() {
		return lineColor;
	}

	public void setLineColor(Color lineColor) {
		this.lineColor = lineColor;
	}

	@XmlElement(name = "lineWidth")
	public double getLineWidth() {
		return lineWidth;
	}

	public void setLineWidth(double lineWidth) {
		this.lineWidth = lineWidth;
	}

	@XmlJavaTypeAdapter(ColorXmlAdaptor.class)
	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public int getVersion() {
		return version;
	}

	@XmlAttribute(name="dbversion")
	public void setVersion(int version) {
		this.version = version;
	}

	/**
	 * @return the description
	 */
	@XmlElement(name = "description")
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Clear out all data in this language project
	 */
	public void clear() {
		rootNode = null;
	}

	/**
	 * @param ltTree
	 */
	public void load(DiagSapTree ltTree) {
		version = ltTree.getVersion();
	}

	public void setFontsAndColors() {
		LexFontInfo.getInstance().setFont(getLexicalFontInfo().getFont());
		LexFontInfo.getInstance().setColor(getLexicalFontInfo().getColor());
	}
}
