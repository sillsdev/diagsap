/**
 * Copyright (c) 2021 SIL International
 * This software is licensed under the LGPL, version 2.1 or later
 * (http://www.gnu.org/licenses/lgpl-2.1.html)
 */
package org.sil.diagsap;

/**
 * @author Andy Black
 *
 */
public class Constants {
	public static final String VERSION_NUMBER = "1.2.0";
	public static final int CURRENT_DATABASE_VERSION = 1;

	public static final String RESOURCE_LOCATION = "org.sil.diagsap.resources.DiagSap";

	public static final String DIAGSAP_DATA_FILE_EXTENSION = "diagsap";
	public static final String DIAGSAP_DATA_FILE_EXTENSIONS = "*."
			+ DIAGSAP_DATA_FILE_EXTENSION;
	public static final String DIAGSAP_STARTER_FILE = "resources/starterFile.diagsap";
	public static final String DEFAULT_DIRECTORY_NAME = "My DiagSap";

	public static final String SVG_HEADER="﻿<?xml version='1.0' standalone='no'?>\n" +
			"<svg width='{0}' height='{1}' version='1.1' xmlns='http://www.w3.org/2000/svg' contentScriptType='text/javascript'>\n" +
			"<script  id=\"clientEventHandlersJS\">\n" +
			"function OnClickDiagSapNode(node){}\n" +
			"</script>\n";
	public static final String SVG_BACKGROUND_COLOR="<rect width=\"100%\" height=\"100%\" fill=\"{0}\"/>\n";
	public static final String SVG_END_ELEMENT="</svg>\n";

	public static final String UTF8_ENCODING = "UTF8";
	public static final String MIGRATION_XSLT_FILE_NAME = "resources/DataMigration";
	public static final String RESOURCE_SOURCE_LOCATION = "src/org/sil/diagsap/";

	// Unit Testing constants
	public static final String UNIT_TEST_DATA_FILE_NAME = "test/org/sil/diagsap/testdata/TestData.";
	public static final String UNIT_TEST_DATA_FILE = "test/org/sil/diagsap/testdata/TestData.tre";
	public static final String UNIT_TEST_DATA_FILE_BAD_TREE = "test/org/sil/diagsap/testdata/TestDataBadTree.tre";
	public static final String UNIT_TEST_DATA_FILE_VERSION_000 = "test/org/sil/diagsap/testdata/TestDataVersion000.tre";
	public static final String UNIT_TEST_DATA_FILE_VERSION_001 = "test/org/sil/diagsap/testdata/TestDataVersion001.tre";
	public static final String UNIT_TEST_DATA_FILE_VERSION_002 = "test/org/sil/diagsap/testdata/TestDataVersion002.tre";
	public static final String UNIT_TEST_DATA_FILE_VERSION_2 = "test/org/sil/diagsap/testdata/TestDataVersion2.tre";
	public static final String UNIT_TEST_DATA_FILE_WITH_WEDGES_IN_DESCRIPTION_VERSION_000 = "test/org/sil/diagsap/testdata/TestDataWedgesInDescriptionVersion000.tre";
	public static final String UNIT_TEST_DATA_FILE_WITH_WEDGES_IN_DESCRIPTION_VERSION_001 = "test/org/sil/diagsap/testdata/TestDataWedgesInDescriptionVersion001.tre";
	public static final String UNIT_TEST_DATA_FILE_WITH_WEDGES_IN_DESCRIPTION_VERSION_002 = "test/org/sil/diagsap/testdata/TestDataWedgesInDescriptionVersion002.tre";
}
