/**
 * Copyright (c) 2021 SIL International
 * This software is licensed under the LGPL, version 2.1 or later
 * (http://www.gnu.org/licenses/lgpl-2.1.html)
 */

package org.sil.diagsap.model;

import static org.junit.Assert.*;
import javafx.scene.text.Text;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.sil.diagsap.model.DiagSapNode;
import org.sil.utility.view.JavaFXThreadingRule;

/**
 * @author Andy Black
 *
 */
public class DiagSapNodeTest {
	@Rule
	public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void textBoxDimensionsTest() {
		ContentBranch content = new ContentBranch("node");
		Text textBox = content.getContentTextBox();
		assertEquals(40.74609375, textBox.getBoundsInLocal().getWidth(), 0.0);
		assertEquals(23.94140625, textBox.getBoundsInLocal().getHeight(), 0.0);

		content.setContent("p<in>ag");
		textBox = content.getContentTextBox();
		assertEquals(69.5126953125, textBox.getBoundsInLocal().getWidth(), 0.0);
		assertEquals(23.94140625, textBox.getBoundsInLocal().getHeight(), 0.0);
}

}
