/**
 * Copyright (c) 2021 SIL International
 * This software is licensed under the LGPL, version 2.1 or later
 * (http://www.gnu.org/licenses/lgpl-2.1.html)
 */

package org.sil.diasap.model;

import static org.junit.Assert.*;

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
		DiagSapNode node = new DiagSapNode();
//		node.setContent1("node");
		assertEquals(25.98046875, node.getWidth(), 0.0);
		assertEquals(19.62890625, node.getHeight(), 0.0);

//		node.setContent1("p<in>ag");
		assertEquals(53.5546875, node.getWidth(), 0.0);
		assertEquals(19.62890625, node.getHeight(), 0.0);
}

}
