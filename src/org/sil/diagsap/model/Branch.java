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
public class Branch {

	BranchItem item;

	public Branch() {
		super();
	}

	public Branch(BranchItem item) {
		super();
		this.item = item;
	}

	public BranchItem getItem() {
		return item;
	}

	public void setItem(BranchItem item) {
		this.item = item;
	}
}
