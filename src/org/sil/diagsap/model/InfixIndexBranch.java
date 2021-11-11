/**
 * Copyright (c) 2021 SIL International
 * This software is licensed under the LGPL, version 2.1 or later
 * (http://www.gnu.org/licenses/lgpl-2.1.html)
 */

package org.sil.diagsap.model;

/**
 * @author Andy Black
 *
 */
public class InfixIndexBranch extends BranchItem {

	int index;

	public InfixIndexBranch(int index) {
		super();
		this.index = index;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	@Override
	public String reconstructDescription() {
		StringBuilder sb = new StringBuilder();
		sb.append("(\\");
		sb.append(index);
		sb.append(")");
		return sb.toString();
	}
}
