/**
 * Copyright (c) 2021 SIL International
 * This software is licensed under the LGPL, version 2.1 or later
 * (http://www.gnu.org/licenses/lgpl-2.1.html)
 */
package org.sil.diagsap.service;

import java.util.HashMap;

import org.sil.diagsap.descriptionparser.antlr4generated.DescriptionBaseListener;
import org.sil.diagsap.descriptionparser.antlr4generated.DescriptionParser;
import org.sil.diagsap.descriptionparser.antlr4generated.DescriptionParser.NodeContext;
import org.sil.diagsap.model.DiagSapNode;
import org.sil.diagsap.model.DiagSapTree;

/**
 * @author Andy Black build a tree from the parsed description
 */
public class BuildTreeFromDescriptionListener extends DescriptionBaseListener {
	protected DescriptionParser parser;

	private DiagSapTree tree;
	private HashMap<Integer, DiagSapNode> nodeMap = new HashMap<Integer, DiagSapNode>();

	public BuildTreeFromDescriptionListener(DescriptionParser parser) {
		super();
		this.parser = parser;
	}

	public DiagSapTree getTree() {
		return tree;
	}

	public void setTree(DiagSapTree tree) {
		this.tree = tree;
	}

	@Override
	public void enterDescription(DescriptionParser.DescriptionContext ctx) {
		tree = new DiagSapTree();
	}

	@Override
	public void enterNode(DescriptionParser.NodeContext ctx) {
		DiagSapNode node = new DiagSapNode();
		nodeMap.put(ctx.hashCode(), node);
		if (tree.getRootNode() == null) {
			node.setiLevel(1);
			tree.setRootNode(node);
			System.out.println("enterNode: setting rootnode " + node);
		} else {
			DescriptionParser.NodeContext parentCtx = (NodeContext) ctx.getParent();
			DiagSapNode mother = nodeMap.get(parentCtx.hashCode());
			if (mother.getContent1().equals("")) {
				if (mother.getNode1() == null) {
					mother.setNode1(node);
					System.out.println("enterNode: added node " + node + " to mother " + mother + " as node1");
				} else {
				}
			} else {
				mother.setNode2(node);
				System.out.println("enterNode: added node " + node + " to mother " + mother + " as node2");
				
			}
			node.setiLevel(mother.getLevel() + 1);
			node.setMother(mother);
		}
	}

	@Override
	public void exitContent(DescriptionParser.ContentContext ctx) {
		DescriptionParser.NodeContext parentCtx = (NodeContext) ctx.getParent();
		DiagSapNode node = nodeMap.get(parentCtx.hashCode());
		String sContent = ctx.getText().trim();
		// Note: in regular expressions, to quote a single backslash we need
		// \\\\ and to quote a paren we need \\(
		sContent = sContent.replaceAll("\\\\\\(", "(").replaceAll("\\\\\\)", ")");
		int iEndOfText = sContent.length();
		if (node.getNode1() == null) {
			if (node.getContent1().equals("")) {
				node.setContent1(sContent.substring(0, iEndOfText).trim());
				System.out.println("exitContent: node " + node + " content1='" + sContent + "'");			
			} else {
				node.setContent2(sContent.substring(0, iEndOfText).trim());
				System.out.println("exitContent: node " + node + " content2='" + sContent + "'");			
			}
		} else {
			node.setContent2(sContent.substring(0, iEndOfText).trim());
			System.out.println("exitContent: node " + node + " content2='" + sContent + "'");			
		}
	}
}
