// Copyright (c) 2021 SIL International 
// This software is licensed under the LGPL, version 2.1 or later 
// (http://www.gnu.org/licenses/lgpl-2.1.html) 
/**
 * 
 */
package org.sil.diagsap.view;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

import org.sil.diagsap.MainApp;
import org.sil.diagsap.model.DiagSapTree;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;

/**
 * @author Andy Black
 *
 */
public class TreeSpacingParametersController implements Initializable {

	@FXML
	private Label prompt;
	@FXML
	private TextField initialXCoordinate;
	@FXML
	private TextField initialYCoordinate;
	@FXML
	private TextField horizontalGap;
	@FXML
	private TextField verticalGap;
	@FXML
	private TextField textUnderlineGap;

	Stage dialogStage;
	private boolean okClicked = false;
	private MainApp mainApp;
	private DiagSapTree dsTree;
	private UnaryOperator<TextFormatter.Change> filter;

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	public void initialize(URL location, ResourceBundle resources) {
		filter = new UnaryOperator<TextFormatter.Change>() {
			@Override
			public TextFormatter.Change apply(TextFormatter.Change change) {
				String text = change.getText();
				for (int i = 0; i < text.length(); i++) {
					if (!Character.isDigit(text.charAt(i)) && text.charAt(i) != '.' && text.charAt(i) != '-')
						return null;
				}
				return change;
			}
		};

		initialXCoordinate.setTextFormatter(new TextFormatter<String>(filter));
		initialYCoordinate.setTextFormatter(new TextFormatter<String>(filter));
		horizontalGap.setTextFormatter(new TextFormatter<String>(filter));
		verticalGap.setTextFormatter(new TextFormatter<String>(filter));
		textUnderlineGap.setTextFormatter(new TextFormatter<String>(filter));
	}

	/**
	 * Sets the stage of this dialog.
	 * 
	 * @param dialogStage
	 */
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	public void setData(DiagSapTree dsTree) {
		this.dsTree = dsTree;
		initialXCoordinate.setText(String.valueOf(dsTree.getInitialXCoordinate()));
		initialYCoordinate.setText(String.valueOf(dsTree.getInitialYCoordinate()));
		horizontalGap.setText(String.valueOf(dsTree.getHorizontalGap()));
		verticalGap.setText(String.valueOf(dsTree.getVerticalGap()));
		textUnderlineGap.setText(String.valueOf(dsTree.getTextUnderlineGap()));
	}

	public DiagSapTree getTree() {
		return dsTree;
	}

	/**
	 * Returns true if the user clicked OK, false otherwise.
	 * 
	 * @return
	 */
	public boolean isOkClicked() {
		return okClicked;
	}

	/**
	 * Called when the user clicks OK.
	 */
	@FXML
	private void handleOk() {
		if (initialXCoordinate.getText().length() > 0) {
			dsTree.setInitialXCoordinate(Double.valueOf(initialXCoordinate.getText()));
		}
		if (initialYCoordinate.getText().length() > 0) {
			dsTree.setInitialYCoordinate(Double.valueOf(initialYCoordinate.getText()));
		}
		if (horizontalGap.getText().length() > 0) {
			dsTree.setHorizontalGap(Double.valueOf(horizontalGap.getText()));
		}
		if (verticalGap.getText().length() > 0) {
			dsTree.setVerticalGap(Double.valueOf(verticalGap.getText()));
		}
		if (textUnderlineGap.getText().length() > 0) {
			dsTree.setTextUnderlineGap(Double.valueOf(textUnderlineGap.getText()));
		}
		okClicked = true;
		dialogStage.close();
	}

	/**
	 * Called when the user clicks cancel.
	 */
	@FXML
	private void handleCancel() {
		dialogStage.close();
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	/**
	 * Called when the user clicks help.
	 */
	@FXML
	private void handleHelp() {
		// TODO: write custom (English) documentation for this, showing examples
		//mainApp.showNotImplementedYet();
	}

}
