/**
 * Copyright (c) 2021 SIL International
 * This software is licensed under the LGPL, version 2.1 or later
 * (http://www.gnu.org/licenses/lgpl-2.1.html)
 */

package org.sil.diagsap.model;

import javafx.scene.text.Text;

/**
 * @author Andy Black
 *
 */
public class ContentBranch extends BranchItem {

	String content;
	Text contentTextBox = new Text(0, 0, "");

	public ContentBranch(String content) {
		super();
		setContent(content);
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
		contentTextBox.setText(content);
	}

	public Text getContentTextBox() {
		return contentTextBox;
	}

	public void setContentTextBox(Text contentTextBox) {
		this.contentTextBox = contentTextBox;
	}

	@Override
	public String reconstructDescription() {
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		sb.append(content);
		sb.append(")");
		return sb.toString();
	}
}
