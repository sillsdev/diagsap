/**
 * Copyright (c) 2021 SIL International
 * This software is licensed under the LGPL, version 2.1 or later
 * (http://www.gnu.org/licenses/lgpl-2.1.html)
 */

package org.sil.diagsap.service;

/**
 * @author Andy Black
 *
 */
public class SemanticErrorMessage {

	String message;
	Object[] args;
	
	public SemanticErrorMessage(String message, Object[] args) {
		super();
		this.message = message;
		this.args = args;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object[] getArgs() {
		return args;
	}

	public void setArgs(Object[] args) {
		this.args = args;
	}
}
