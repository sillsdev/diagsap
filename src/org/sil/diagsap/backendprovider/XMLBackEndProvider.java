// Copyright (c) 2021 SIL International 
// This software is licensed under the LGPL, version 2.1 or later 
// (http://www.gnu.org/licenses/lgpl-2.1.html) 
/**
 * 
 */
package org.sil.diagsap.backendprovider;

import java.io.File;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.sil.diagsap.model.DiagSapTree;
import org.sil.diagsap.Constants;
import org.sil.diagsap.MainApp;
import org.sil.utility.HandleExceptionMessage;

/**
 * @author Andy Black
 *
 */
public class XMLBackEndProvider extends BackEndProvider {

	DiagSapTree lingTree;
	String sFileError;
	String sFileErrorLoadHeader;
	String sFileErrorLoadContent;
	String sFileErrorSaveHeader;
	String sFileErrorSaveContent;

	/**
	 * @param lingTree
	 */
	public XMLBackEndProvider(DiagSapTree lingTree, Locale locale) {
		this.lingTree = lingTree;
		setResourceStrings(locale);
	}

	private void setResourceStrings(Locale locale) {
		ResourceBundle bundle = ResourceBundle.getBundle(Constants.RESOURCE_LOCATION, locale);
		sFileError = bundle.getString("file.error");
		sFileErrorLoadHeader = bundle.getString("file.error.load.header");
		sFileErrorLoadContent = bundle.getString("file.error.load.content");
		sFileErrorSaveHeader = bundle.getString("file.error.save.header");
		sFileErrorSaveContent = bundle.getString("file.error.save.content");
	}

	public DiagSapTree getLingTree() {
		return lingTree;
	}

	public void setLingTree(DiagSapTree lingTree) {
		this.lingTree = lingTree;
	}

	final boolean useXMLClasses = false;

	/**
	 * Loads tree data from the specified file. The current tree data
	 * will be replaced.
	 * 
	 * @param file
	 */
	@Override
	public void loadTreeDataFromFile(File file) {
		try {
			JAXBContext context = JAXBContext.newInstance(DiagSapTree.class);
			Unmarshaller um = context.createUnmarshaller();
			// Reading XML from the file and unmarshalling.
			lingTree = (DiagSapTree) um.unmarshal(file);
			lingTree.clear();
			lingTree.load(lingTree);
		} catch (Exception e) { // catches ANY exception
			e.printStackTrace();
			MainApp.reportException(e, null);
		}
	}

	/**
	 * Saves the current tree data to the specified file.
	 * 
	 * @param file
	 */
	@Override
	public void saveTreeDataToFile(File file) {
		try {
			JAXBContext context = JAXBContext.newInstance(DiagSapTree.class);
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			// Marshalling and saving XML to the file.
			m.marshal(lingTree, file);
		} catch (Exception e) { // catches ANY exception
			e.printStackTrace();
			MainApp.reportException(e, null);
		}
	}
}
