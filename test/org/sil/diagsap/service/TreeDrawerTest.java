/**
 * Copyright (c) 2021 SIL International
 * This software is licensed under the LGPL, version 2.1 or later
 * (http://www.gnu.org/licenses/lgpl-2.1.html)
 */

package org.sil.diagsap.service;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sil.diagsap.model.DiagSapTree;
import org.sil.lingtree.model.LexFontInfo;

/**
 * @author Andy Black
 *
 */
public class TreeDrawerTest extends ServiceBaseTest {

	Pane drawingArea = new Pane();
	LexFontInfo lexicalFontInfo;
	/* (non-Javadoc)
	 * @see org.sil.diagsap.service.ServiceBaseTest#setUp()
	 */
	@Before
	public void setUp() throws Exception {
		super.setUp();
		lexicalFontInfo = LexFontInfo.getInstance();
		lexicalFontInfo.setColor(Color.BLACK);
		lexicalFontInfo.setFontFamily("Arial");
	}

	/* (non-Javadoc)
	 * @see org.sil.diagsap.service.ServiceBaseTest#tearDown()
	 */
	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}

	@Test
	public void drawAndproduceSVGTest() {
		checkDrawSVG("((beauti) ((ful) (ly)))", "beauti-fully", false, false);
		checkDrawSVG("(((beauti) (ful)) (ly))", "beautiful-ly", false, false);
		checkDrawSVG("((\\1) ((g<in>) ((pí-)((\\2) ((p<in>a-)((m-)(ulod)))))))", "ginpipinamulod", false, false);
		checkDrawSVG("((\\1) ((g<in>m) ((pí-)((\\2) ((p<in>a-)((\\3) ((m-)(ul<on>od))))))))", "ginmpipinamulonod", false, false);
		checkDrawSVG("((\\1)((i) ((g<in>) (luto))))", "iginluto", false, false);
		checkDrawSVG("((institut) ((ion) ((al) ( ly))))", "institutionally", false, false);
		checkDrawSVG("((\\1) ((m-)(ul<on>od)))", "mulonod", false, false);
		checkDrawSVG("((\\1)(((p<in>ag) (–arál)) (an)))", "pinagaralan", false, false);
		checkDrawSVG("(((un) (lock))(able)  )", "unlock-able", false, false);
		checkDrawSVG("((un) ((lock)(able) ) )", "un-lockable", false, false);
		checkDrawSVG("((((un) (lock))(able))(ness )  )", "unlock-able-ness", false, false);
		checkDrawSVG("((un) (((lock)(able) ) (ness)))", "un-lockable-ness", false, false);
		checkDrawSVG("((\\1) ((g<in>m) ((pí-)((\\2) ((p<in>a-)((\\3) ((m-)(ul<on>od))))))))", "ginmpipinamulonodDashed", true, false);
		checkDrawSVG("(((אַי)((\\1)(((עַנפִ<נ>אַג) (אַרַל)) (א))))(בִת))", "hebrew", false, true);
	}

	protected void checkDrawSVG(String sDescription, String sTestFilePath, boolean useDashedLines, boolean useRightToLeft) {
		DiagSapTree origTree = new DiagSapTree();
		origTree.setUseDashedLinesForSplitInfixedBase(useDashedLines);
		origTree.setUseRightToLeftOrientation(useRightToLeft);
		DiagSapTree dsTree = TreeBuilder.parseAString(sDescription, origTree);
		TreeDrawer drawer = new TreeDrawer(dsTree);
		drawingArea.getChildren().clear();
		drawer.draw(drawingArea);
		StringBuilder sb = drawer.drawAsSVG(drawingArea, dsTree.getXSize(), dsTree.getYSize());
		
		File file = new File("test/org/sil/diagsap/testData/" + sTestFilePath + ".svg");
		try {
			Stream<String> contents = Files.lines(file.toPath());
			String scontents = contents.collect(Collectors.joining("\n"));
			contents.close();
			assertEquals(scontents, sb.toString());
			} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
