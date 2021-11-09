/**
 * Copyright (c) 2021 SIL International
 * This software is licensed under the LGPL, version 2.1 or later
 * (http://www.gnu.org/licenses/lgpl-2.1.html)
 */
package org.sil.diagsap.service;

import java.util.HashMap;

import org.antlr.v4.runtime.ParserRuleContext;
import org.sil.diagsap.descriptionparser.antlr4generated.DescriptionBaseListener;
import org.sil.diagsap.descriptionparser.antlr4generated.DescriptionParser;
import org.sil.diagsap.descriptionparser.antlr4generated.DescriptionParser.BranchContext;
import org.sil.diagsap.descriptionparser.antlr4generated.DescriptionParser.InfixContext;
import org.sil.diagsap.descriptionparser.antlr4generated.DescriptionParser.NodeContext;
import org.sil.diagsap.model.Branch;
import org.sil.diagsap.model.BranchItem;
import org.sil.diagsap.model.ContentBranch;
import org.sil.diagsap.model.DiagSapNode;
import org.sil.diagsap.model.DiagSapTree;

/**
 * @author Andy Black build a tree from the parsed description
 */
public class BuildTreeFromDescriptionListener extends DescriptionBaseListener {
	protected DescriptionParser parser;

	private DiagSapTree tree;
	private HashMap<Integer, DiagSapNode> nodeMap = new HashMap<Integer, DiagSapNode>();
	private HashMap<Integer, BranchItem> branchItemMap = new HashMap<Integer, BranchItem>();
	private HashMap<Integer, Branch> branchMap = new HashMap<Integer, Branch>();

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
			node.setLevel(1);
			tree.setRootNode(node);
			System.out.println("enterNode: setting rootnode " + node);
		} else {
			ParserRuleContext parent = ctx.getParent();
			System.out.println("enterNode: node=" + node + "; ctx=" + ctx + "; parent=" + parent);
			branchItemMap.put(ctx.hashCode(), node);
			if (parent != null) {  // find first node above this one; should be two levels up
				parent = parent.getParent();
				if (parent != null) {
					DescriptionParser.NodeContext nodeCtx = (DescriptionParser.NodeContext)parent.getParent();
					DiagSapNode mother = nodeMap.get(nodeCtx.hashCode());
					node.setLevel(mother.getLevel() + 1);
					node.setMother(mother);
				}
			}
		}
	}

	@Override
	public void enterLeftbranch(DescriptionParser.LeftbranchContext ctx) {
		System.out.println("enterLeftBranch: ctx=" + ctx);
	}

	@Override
	public void enterRightbranch(DescriptionParser.RightbranchContext ctx) {
		System.out.println("enterRightBranch: ctx=" + ctx);
	}

	@Override
	public void enterBranch(DescriptionParser.BranchContext ctx) {
		System.out.println("enterBranch: ctx=" + ctx);
		Branch branch = new Branch();
		branchMap.put(ctx.hashCode(), branch);
	}

	@Override
	public void enterInfixindex(DescriptionParser.InfixindexContext ctx) {
		System.out.println("enterInfixindex: ctx=" + ctx);
	}

	@Override
	public void enterInfixedbase(DescriptionParser.InfixedbaseContext ctx) {
		System.out.println("enterInfixedbase: ctx=" + ctx);
	}

	@Override
	public void enterInfix(DescriptionParser.InfixContext ctx) {
		System.out.println("enterInfix: ctx=" + ctx);
	}

	@Override
	public void enterContent(DescriptionParser.ContentContext ctx) {
		System.out.println("enterContent: ctx=" + ctx);
	}

	@Override
	public void exitContent(DescriptionParser.ContentContext ctx) {
		ParserRuleContext parent = ctx.getParent();
		String sContent = ctx.getText().trim();
		// Note: in regular expressions, to quote a single backslash we need
		// \\\\ and to quote a paren we need \\(
		sContent = sContent.replaceAll("\\\\\\(", "(").replaceAll("\\\\\\)", ")");
		System.out.println("exitContent: str='" + sContent + "'; ctx=" + ctx + "; parent=" + parent + "; p class='" + parent.getClass() + "'");
		ContentBranch content = new ContentBranch(sContent);
//		branchItemMap.put(ctx.hashCode(), content);
		String sParentClass = parent.getClass().getName();
		int iStart = sParentClass.indexOf("$");
		switch (sParentClass.substring(iStart +1)) {
		case "BranchContext":
			System.out.println("\tfound branch");
			BranchContext branchContext = (BranchContext) parent;
			Branch branch = branchMap.get(branchContext.hashCode());
			branch.setItem(content);
			branchMap.replace(branchContext.hashCode(), branch);
			branchItemMap.put(branchContext.hashCode(), content);
			break;
		case "InfixContext":
			System.out.println("\tfound infix");
			InfixContext infixContext = (InfixContext) parent;
			branchItemMap.put(infixContext.hashCode(), content);
			break;
		}
//		DescriptionParser.NodeContext parentCtx = (NodeContext) ctx.getParent();
//		DiagSapNode node = nodeMap.get(parentCtx.hashCode());
//		int iEndOfText = sContent.length();
//		if (node.getNode1() == null) {
//			if (node.getContent1().equals("")) {
//				node.setContent1(sContent.substring(0, iEndOfText).trim());
//				System.out.println("exitContent: node " + node + " content1='" + sContent + "'");
//			} else {
//				node.setContent2(sContent.substring(0, iEndOfText).trim());
//				System.out.println("exitContent: node " + node + " content2='" + sContent + "'");
//			}
//		} else {
//			node.setContent2(sContent.substring(0, iEndOfText).trim());
//			System.out.println("exitContent: node " + node + " content2='" + sContent + "'");
//		}
	}

	@Override
	public void exitInfix(DescriptionParser.InfixContext ctx) {
		System.out.println("exitInfix: ctx=" + ctx);
	}

	@Override
	public void exitInfixedbase(DescriptionParser.InfixedbaseContext ctx) {
		System.out.println("exitInfixedBase: ctx=" + ctx);
	}

	@Override
	public void exitInfixindex(DescriptionParser.InfixindexContext ctx) {
		System.out.println("exitInfixIndent: ctx=" + ctx);
	}

	@Override
	public void exitLeftbranch(DescriptionParser.LeftbranchContext ctx) {
		System.out.println("exitLeftBranch: ctx=" + ctx);
		BranchContext branchContext = (BranchContext) ctx.getChild(0);
		DiagSapNode node = getNodeFromContext(ctx);
		node.setLeftBranch(branchMap.get(branchContext.hashCode()));
	}

	protected DiagSapNode getNodeFromContext(ParserRuleContext ctx) {
		NodeContext parentCtx = (NodeContext)ctx.getParent();
		DiagSapNode node = nodeMap.get(parentCtx.hashCode());
		return node;
	}

	@Override
	public void exitRightbranch(DescriptionParser.RightbranchContext ctx) {
		System.out.println("exitRightBranch: ctx=" + ctx);
		BranchContext branchContext = (BranchContext) ctx.getChild(0);
		System.out.println("\tbranchContext=" + branchContext);
		DiagSapNode node = getNodeFromContext(ctx);
		System.out.println("\tnode=" + node);
		System.out.println("\tset right branch to " + branchMap.get(branchContext.hashCode()));
		node.setRightBranch(branchMap.get(branchContext.hashCode()));
	}

	@Override
	public void exitBranch(DescriptionParser.BranchContext ctx) {
		BranchItem item = branchItemMap.get(ctx.hashCode());
//		String sParentClass = childContext.getClass().getName();
//		int iStart = sParentClass.indexOf("$");
//		switch (sParentClass.substring(iStart +1)) {
//		case "ContentContext":
//			System.out.println("\tfound content");
//			BranchContext branchContext = (BranchContext) parent;
//			branchItemMap.put(branchContext.hashCode(), content);
//			break;
//		case "InfixContext":
//			System.out.println("\tfound infix");
//			InfixContext infixContext = (InfixContext) parent;
//			branchItemMap.put(infixContext.hashCode(), content);
//			break;
//		}
		System.out.println("exitBranch: ctx=" + ctx + "; bi=" + item);
		Branch branch = branchMap.get(ctx.hashCode());
//		branch.setItem(item);
		System.out.println("exitBranch: ctx=" + ctx + "; bi=" + branch.getItem());
	}

	@Override
	public void exitNode(DescriptionParser.NodeContext ctx) {
		System.out.println("exitNode: ctx=" + ctx);
		ParserRuleContext parent = ctx.getParent();
		if (parent instanceof BranchContext) {
			branchMap.values().stream().forEach(n -> System.out.println("branch=" + n + "; bi=" + n.getItem()));

			BranchContext branchContext = (BranchContext)parent;
			Branch branch = branchMap.get(branchContext.hashCode());
			System.out.println("\tbranch=" + branch + "; bi=" + branch.getItem());
			DiagSapNode node = nodeMap.get(ctx.hashCode());
			System.out.println("\tnode=" + node);
			branch.setItem(node);
			System.out.println("\tbranch=" + branch + "; bi=" + branch.getItem());
			branchMap.replace(branchContext.hashCode(), branch);
//			branchMap.remove(branchContext.hashCode());
//			branchMap.put(branchContext.hashCode(), branch);
			branchMap.values().stream().forEach(n -> System.out.println("branch=" + n + "; bi=" + n.getItem()));
//			branchItemMap.values().stream().forEach(n -> System.out.println("bi=" + n));
//			branchItemMap.replace(ctx.hashCode(), node);
//			branchItemMap.values().stream().forEach(n -> System.out.println("bi=" + n));
		}
	}

	@Override
	public void exitDescription(DescriptionParser.DescriptionContext ctx) {
		nodeMap.values().stream().forEach(n -> System.out.println("node=" + n));
		branchItemMap.values().stream().forEach(n -> System.out.println("bi=" + n));
		branchMap.values().stream().forEach(n -> System.out.println("branch=" + n + "; bi=" + n.getItem()));
	}


}