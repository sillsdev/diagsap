/**
 * Copyright (c) 2024-2025 SIL Global
 * This software is licensed under the LGPL, version 2.1 or later
 * (http://www.gnu.org/licenses/lgpl-2.1.html)
 */

package org.sil.diagsap.service;

import org.sil.diagsap.model.NodeType;

/**
 * @author Andy Black
 *
 */
public class NodeTypeDeterminer {

	public static NodeType determineNodeTypeFrom(String portion) {
		portion = portion.trim();
		NodeType ntype = NodeType.Syntagmeme;
		int iOpenParen = portion.lastIndexOf("(");
		if (iOpenParen < 0) {
			return NodeType.Syntagmeme;
		}
		while (iOpenParen > 0 && portion.substring(Math.max(0, iOpenParen-1)).startsWith("\\")) {
			iOpenParen = portion.substring(0, iOpenParen).lastIndexOf("(");
		}
		int iOpenEnd = (iOpenParen == -1) ? portion.length() : iOpenParen;
		String sThisNodeBeginning = portion.substring(iOpenEnd);
		int iCloseParen = sThisNodeBeginning.lastIndexOf(")");
		while (iCloseParen > 0 && sThisNodeBeginning.substring(0,iCloseParen).endsWith("\\")) {
			iCloseParen = portion.substring(0, iCloseParen).lastIndexOf(")");
		}
		if (iCloseParen > -1) {
			return NodeType.Syntagmeme;
		}
		int iCloseEnd = (iCloseParen == -1) ? sThisNodeBeginning.length() : iCloseParen;
		String sNode = sThisNodeBeginning.substring(0,iCloseEnd);
		if (sNode.endsWith(")") && !sNode.endsWith("\\)")) {
			return NodeType.Syntagmeme;
		}
		if (sNode.startsWith("(")) {
			ntype = NodeType.Lex;
		}
		return ntype;
	}
}
