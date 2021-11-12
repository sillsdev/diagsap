// Copyright (c) 2021 SIL International
// This software is licensed under the LGPL, version 2.1 or later
// (http://www.gnu.org/licenses/lgpl-2.1.html)
package org.sil.diagsap;

import java.io.File;
import java.util.prefs.Preferences;

import org.sil.diagsap.model.DiagSapTree;
import org.sil.lingtree.model.ColorXmlAdaptor;
import org.sil.lingtree.model.FontInfo;
import org.sil.utility.*;

import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ApplicationPreferences extends ApplicationPreferencesUtilities {

	static final String LAST_OPENED_FILE_PATH = "lastOpenedFilePath";
	static final String LAST_OPENED_DIRECTORY_PATH = "lastOpenedDirectoryPath";
	static final String LAST_LOCALE_LANGUAGE = "lastLocaleLanguage";
	static final String DRAW_AS_TYPE = "drawastype";
	static final String SHOW_MATCHING_PAREN_DELAY = "showmatchingparendelay";
	static final String SHOW_MATCHING_PAREN_WITH_ARROW_KEYS = "showmatchingparenwitharrowkeys";
	static final String TREE_DESCRIPTION_FONT_SIZE = "treedescriptionfontsize";
	// Not trying to be anglo-centric, but we have to start with something...
	static final String DEFAULT_LOCALE_LANGUAGE = "en";

	// Window parameters to remember
	static final String POSITION_X = "PositionX";
	static final String POSITION_Y = "PositionY";
	static final String WIDTH = "Width";
	static final String HEIGHT = "Height";
	static final String MAXIMIZED = "Maximized";
	// Window parameters for main window and various dialogs
	public static final String LAST_WINDOW = "lastWindow";
	public static final String LAST_SPLIT_PANE_POSITION = "lastSplitPanePosition";

	// Tree parameters to remember
	static final String BACKGROUND_COLOR = "backgroundColor";
	static final String HORIZONTAL_GAP = "horizontalGap";
	static final String HORIZONTAL_OFFSET = "horizontalOffset";
	static final String INITIAL_X_COORDINATE = "initialXCoordinate";
	static final String INITIAL_Y_COORDINATE = "initialYCoordinate";
	public static final String LAST_QUICK_REFERENCE_GUIDE = "lastQuickReferenceGuide";
	static final String LEXICAL_FONT_COLOR = "lexicalFontColor";
	static final String LEXICAL_FONT_FAMILY = "lexicalFontFamily";
	static final String LEXICAL_FONT_SIZE = "lexicalFontSize";
	static final String LEXICAL_FONT_TYPE = "lexicalFontType";
	static final String LINE_COLOR = "lineColor";
	static final String LINE_WIDTH = "lineWidth";
	static final String SAVE_AS_PNG = "saveAsPng";
	static final String SAVE_AS_SVG = "saveAsSVG";
	static final String TEXT_UNDERLINE_GAP = "textUnderlineGap";
	static final String VERTICAL_GAP = "verticalGap";

	Preferences prefs;
	ColorXmlAdaptor adaptor;

	public ApplicationPreferences(Object app) {
		prefs = Preferences.userNodeForPackage(app.getClass());
		adaptor = new ColorXmlAdaptor();
	}

	public String getLastOpenedFilePath() {
		return prefs.get(LAST_OPENED_FILE_PATH, null);
	}

	public void setLastOpenedFilePath(String lastOpenedFile) {
		setPreferencesKey(LAST_OPENED_FILE_PATH, lastOpenedFile);
	}

	public String getLastLocaleLanguage() {
		return prefs.get(LAST_LOCALE_LANGUAGE, DEFAULT_LOCALE_LANGUAGE);
	}

	public void setLastLocaleLanguage(String lastLocaleLanguage) {
		setPreferencesKey(LAST_LOCALE_LANGUAGE, lastLocaleLanguage);
	}

	public File getLastOpenedFile() {
		String filePath = prefs.get(LAST_OPENED_FILE_PATH, null);
		if (filePath != null) {
			return new File(filePath);
		} else {
			return null;
		}
	}

	public void setLastOpenedFilePath(File file) {
		if (file != null) {
			setPreferencesKey(LAST_OPENED_FILE_PATH, file.getPath());

		} else {
			prefs.remove(LAST_OPENED_FILE_PATH);
		}
	}

	@Override
	public String getLastOpenedDirectoryPath() {
		return prefs.get(LAST_OPENED_DIRECTORY_PATH, "");
	}

	@Override
	public void setLastOpenedDirectoryPath(String directoryPath) {
		setPreferencesKey(LAST_OPENED_DIRECTORY_PATH, directoryPath);
	}

	public boolean getDrawAsType() {
		return prefs.getBoolean(DRAW_AS_TYPE, false);
	}

	public void setDrawAsType(boolean fDrawAsType) {
		setPreferencesKey(DRAW_AS_TYPE, fDrawAsType);
	}

	public double getShowMatchingParenDelay() {
		return prefs.getDouble(SHOW_MATCHING_PAREN_DELAY, 750.0);
	}

	public void setShowMatchingParenDelay(double dSize) {
		setPreferencesKey(SHOW_MATCHING_PAREN_DELAY, dSize);
	}

	public boolean getShowMatchingParenWithArrowKeys() {
		return prefs.getBoolean(SHOW_MATCHING_PAREN_WITH_ARROW_KEYS, false);
	}

	public void setShowMatchingParenWithArrowKeys(boolean fShowMatchingParenWithArrowKeys) {
		setPreferencesKey(SHOW_MATCHING_PAREN_WITH_ARROW_KEYS, fShowMatchingParenWithArrowKeys);
	}

	public double getTreeDescriptionFontSize() {
		return prefs.getDouble(TREE_DESCRIPTION_FONT_SIZE, 12.0);
	}

	public void setTreeDescriptionFontSize(double dSize) {
		setPreferencesKey(TREE_DESCRIPTION_FONT_SIZE, dSize);
	}

	public Stage getLastWindowParameters(String sWindow, Stage stage, Double defaultHeight,
			Double defaultWidth) {
		Double value = prefs.getDouble(sWindow + HEIGHT, defaultHeight);
		stage.setHeight(value);
		value = prefs.getDouble(sWindow + WIDTH, defaultWidth);
		stage.setWidth(value);
		value = prefs.getDouble(sWindow + POSITION_X, 10);
		stage.setX(value);
		value = prefs.getDouble(sWindow + POSITION_Y, 10);
		stage.setY(value);
		boolean fValue = prefs.getBoolean(sWindow + MAXIMIZED, false);
		stage.setMaximized(fValue);
		return stage;
	}

	public void setLastWindowParameters(String sWindow, Stage stage) {
		boolean isMaximized = stage.isMaximized();
		if (!isMaximized) {
			setPreferencesKey(sWindow + HEIGHT, stage.getHeight());
			setPreferencesKey(sWindow + WIDTH, stage.getWidth());
			setPreferencesKey(sWindow + POSITION_X, stage.getX());
			setPreferencesKey(sWindow + POSITION_Y, stage.getY());
		}
		setPreferencesKey(sWindow + MAXIMIZED, stage.isMaximized());
	}

	public double getLastSplitPaneDividerPosition() {
		return prefs.getDouble(LAST_SPLIT_PANE_POSITION, 0.5);
	}

	public void setLastSplitPaneDividerPosition(double position) {
		setPreferencesKey(LAST_SPLIT_PANE_POSITION, position);
	}

	public void getSavedTreeParameters(DiagSapTree ltTree) {
		ltTree.setHorizontalGap(prefs.getDouble(HORIZONTAL_GAP, 20));
		ltTree.setHorizontalOffset(prefs.getDouble(HORIZONTAL_OFFSET, 225));
		ltTree.setInitialXCoordinate(prefs.getDouble(INITIAL_X_COORDINATE, 10));
		ltTree.setInitialYCoordinate(prefs.getDouble(INITIAL_Y_COORDINATE, 20));
		ltTree.setLineWidth(prefs.getDouble(LINE_WIDTH, 1));
		ltTree.setSaveAsPng(prefs.getBoolean(SAVE_AS_PNG, false));
		ltTree.setSaveAsSVG(prefs.getBoolean(SAVE_AS_SVG, true));
		ltTree.setTextUnderlineGap(prefs.getDouble(TEXT_UNDERLINE_GAP, 0));
		ltTree.setVerticalGap(prefs.getDouble(VERTICAL_GAP, 20));

		ltTree.setBackgroundColor(Color.web(prefs.get(BACKGROUND_COLOR, "#ffffff")));
		ltTree.setLineColor(Color.web(prefs.get(LINE_COLOR, "#000000")));

		final String sDefaultFamily = "Arial";
		final String sDefaultType = "Regular";
		final String sDefaultColor = "#000000";
		FontInfo fontInfo = new FontInfo(prefs.get(LEXICAL_FONT_FAMILY, sDefaultFamily), prefs.getDouble(
				LEXICAL_FONT_SIZE, 12), prefs.get(LEXICAL_FONT_TYPE, sDefaultType));
		fontInfo.setColor(Color.web(prefs.get(LEXICAL_FONT_COLOR, sDefaultColor)));
		ltTree.setLexicalFontInfo(fontInfo);
	}

	public void setSavedTreeParameters(DiagSapTree ltTree) throws Exception {
		setPreferencesKey(HORIZONTAL_GAP, ltTree.getHorizontalGap());
		setPreferencesKey(HORIZONTAL_OFFSET, ltTree.getHorizontalOffset());
		setPreferencesKey(INITIAL_X_COORDINATE, ltTree.getInitialXCoordinate());
		setPreferencesKey(INITIAL_Y_COORDINATE, ltTree.getInitialYCoordinate());
		setPreferencesKey(LINE_WIDTH, ltTree.getLineWidth());
		setPreferencesKey(SAVE_AS_PNG, ltTree.isSaveAsPng());
		setPreferencesKey(SAVE_AS_SVG, ltTree.isSaveAsSVG());
		setPreferencesKey(TEXT_UNDERLINE_GAP, ltTree.getTextUnderlineGap());
		setPreferencesKey(VERTICAL_GAP, ltTree.getVerticalGap());

		setPreferencesKey(BACKGROUND_COLOR, ltTree.getBackgroundColor());
		setPreferencesKey(LINE_COLOR, ltTree.getLineColor());
		FontInfo fontInfo = ltTree.getLexicalFontInfo();
		setPreferencesKey(LEXICAL_FONT_COLOR, fontInfo.getColor());
		setPreferencesKey(LEXICAL_FONT_FAMILY, fontInfo.getFontFamily());
		setPreferencesKey(LEXICAL_FONT_SIZE, fontInfo.getFontSize());
		setPreferencesKey(LEXICAL_FONT_TYPE, fontInfo.getFontType());
	}

	private void setPreferencesKey(String key, boolean value) {
		if (!StringUtilities.isNullOrEmpty(key)) {
			if (key != null) {
				prefs.putBoolean(key, value);

			} else {
				prefs.remove(key);
			}
		}
	}

	private void setPreferencesKey(String key, Color color) throws Exception {
		if (!StringUtilities.isNullOrEmpty(key)) {
			if (key != null) {
				String value = adaptor.marshal(color);
				prefs.put(key, value);

			} else {
				prefs.remove(key);
			}
		}
	}

	private void setPreferencesKey(String key, Double value) {
		if (!StringUtilities.isNullOrEmpty(key)) {
			if (key != null && value != null) {
				prefs.putDouble(key, value);

			} else {
				prefs.remove(key);
			}
		}
	}

	private void setPreferencesKey(String key, String value) {
		if (!StringUtilities.isNullOrEmpty(key) && !StringUtilities.isNullOrEmpty(value)) {
			prefs.put(key, value);

		} else {
			prefs.remove(key);
		}
	}
}
