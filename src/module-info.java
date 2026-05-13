// Copyright (c) 2026 SIL International
// This software is licensed under the LGPL, version 2.1 or later
// (http://www.gnu.org/licenses/lgpl-2.1.html)
module org.sil.diagsap {
	// Exports
	exports org.sil.diagsap;
	exports org.sil.diagsap.backendprovider;
	exports org.sil.diagsap.model;
	exports org.sil.diagsap.descriptionparser;
	exports org.sil.diagsap.descriptionparser.antlr4generated;
	exports org.sil.diagsap.service;
	exports org.sil.diagsap.view;

	opens org.sil.diagsap.view to javafx.fxml;

	// Java
	requires java.desktop;
	requires java.prefs;

	// JavaFX
	requires transitive javafx.controls;
	requires transitive javafx.fxml;
	requires javafx.graphics;
	requires javafx.swing;
	requires javafx.web;

	// JAXB
	requires jakarta.xml.bind;
	requires jakarta.activation;
	opens org.sil.diagsap.model;

	// JNA
//	requires com.sun.jna;
//	requires com.sun.jna.platform;

	// LibJavaDev
	requires transitive org.sil.utility;

	// JUnit
//	requires junit;

	// Other modules/libraries
	requires antlr;
	requires transitive org.controlsfx.controls;
	requires javafx.base;
	requires javafx.media;
	requires java.base;
	requires transitive richtextfx.fat;
//	requires org.json;
	requires org.sil.lingtree;
}
