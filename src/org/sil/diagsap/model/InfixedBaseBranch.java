/**
 * Copyright (c) 2021 SIL International
 * This software is licensed under the LGPL, version 2.1 or later
 * (http://www.gnu.org/licenses/lgpl-2.1.html)
 */

package org.sil.diagsap.model;

import org.sil.utility.StringUtilities;

/**
 * @author Andy Black
 *
 */
public class InfixedBaseBranch extends BranchItem {

	String contentBefore;
	String infixContent;
	String contentAfter;

	public InfixedBaseBranch() {
	}

	public InfixedBaseBranch(String contentBefore, String infixContent, String contentAfter) {
		super();
		this.contentBefore = contentBefore;
		this.infixContent = infixContent;
		this.contentAfter = contentAfter;
	}

	public String getContentBefore() {
		return contentBefore;
	}

	public void setContentBefore(String contentBefore) {
		this.contentBefore = contentBefore;
	}

	public String getInfixContent() {
		return infixContent;
	}

	public void setInfixContent(String infixContent) {
		this.infixContent = infixContent;
	}

	public String getContentAfter() {
		return contentAfter;
	}

	public void setContentAfter(String contentAfter) {
		this.contentAfter = contentAfter;
	}

	@Override
	public String reconstructDescription() {
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		sb.append(contentBefore);
		sb.append("<");
		sb.append(infixContent);
		sb.append(">");
		if (!StringUtilities.isNullOrEmpty(contentAfter)) {
			sb.append(contentAfter);
		}
		sb.append(")");
		return sb.toString();
	}
}
