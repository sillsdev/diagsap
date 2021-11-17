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

	ContentBranch contentBefore;
	ContentBranch infixContent;
	ContentBranch contentAfter;
	int level;

	public InfixedBaseBranch() {
	}

	public InfixedBaseBranch(ContentBranch contentBefore, ContentBranch infixContent, ContentBranch contentAfter) {
		super();
		this.contentBefore = contentBefore;
		this.infixContent = infixContent;
		this.contentAfter = contentAfter;
	}

	public ContentBranch getContentBefore() {
		return contentBefore;
	}

	public void setContentBefore(ContentBranch contentBefore) {
		this.contentBefore = contentBefore;
	}

	public ContentBranch getInfixContent() {
		return infixContent;
	}

	public void setInfixContent(ContentBranch infixContent) {
		this.infixContent = infixContent;
	}

	public ContentBranch getContentAfter() {
		return contentAfter;
	}

	public void setContentAfter(ContentBranch contentAfter) {
		this.contentAfter = contentAfter;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	@Override
	public String reconstructDescription() {
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		sb.append(contentBefore.getContent());
		sb.append("<");
		sb.append(infixContent.getContent());
		sb.append(">");
		if (contentAfter != null) {
			sb.append(contentAfter.getContent());
		}
		sb.append(")");
		return sb.toString();
	}
}
