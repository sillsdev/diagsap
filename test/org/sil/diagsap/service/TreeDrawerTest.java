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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sil.diagsap.model.DiagSapTree;

/**
 * @author Andy Black
 *
 */
public class TreeDrawerTest extends ServiceBaseTest {

	Pane drawingArea = new Pane();
	/* (non-Javadoc)
	 * @see org.sil.diagsap.service.ServiceBaseTest#setUp()
	 */
	@Before
	public void setUp() throws Exception {
		super.setUp();
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
		checkDrawSVG("((beauti) ((ful) (ly)))", "beauti-fully");
		checkDrawSVG("(((beauti) (ful)) (ly))", "beautiful-ly");
		checkDrawSVG("((\\1) ((g<in>) ((pí-)((\\2) ((p<in>a-)((m-)(ulod)))))))", "ginpipinamulod");
		checkDrawSVG("((\\1)((i) ((g<in>) (luto))))", "iginluto");
		checkDrawSVG("((institut) ((ion) ((al) ( ly))))", "institutionally");
		checkDrawSVG("((\\1)(((p<in>ag) (–arál)) (an)))", "pinagaralan");
		checkDrawSVG("(((un) (lock))(able)  )", "unlock-able");
		checkDrawSVG("((un) ((lock)(able) ) )", "un-lockable");
		checkDrawSVG("((((un) (lock))(able))(ness )  )", "unlock-able-ness");
		checkDrawSVG("((un) (((lock)(able) ) (ness)))", "un-lockable-ness");
	}

	protected void checkDrawSVG(String sDescription, String sTestFilePath) {
		DiagSapTree origTree = new DiagSapTree();
		DiagSapTree dsTree = TreeBuilder.parseAString(sDescription, origTree);
		TreeDrawer drawer = new TreeDrawer(dsTree);
		drawingArea.getChildren().clear();
		drawer.draw(drawingArea);
		drawer.calculateTreeHeightAndWidth(dsTree.getRootNode());
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
