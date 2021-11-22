/**
 * Copyright (c) 2021 SIL International
 * This software is licensed under the LGPL, version 2.1 or later
 * (http://www.gnu.org/licenses/lgpl-2.1.html)
 */
package org.sil.diagsap.service;

import java.util.HashMap;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.sil.diagsap.descriptionparser.antlr4generated.DescriptionBaseListener;
import org.sil.diagsap.descriptionparser.antlr4generated.DescriptionParser;
import org.sil.diagsap.descriptionparser.antlr4generated.DescriptionParser.BranchContext;
import org.sil.diagsap.descriptionparser.antlr4generated.DescriptionParser.InfixContext;
import org.sil.diagsap.descriptionparser.antlr4generated.DescriptionParser.InfixedbaseContext;
import org.sil.diagsap.descriptionparser.antlr4generated.DescriptionParser.NodeContext;
import org.sil.diagsap.model.Branch;
import org.sil.diagsap.model.BranchItem;
import org.sil.diagsap.model.ContentBranch;
import org.sil.diagsap.model.DiagSapNode;
import org.sil.diagsap.model.DiagSapTree;
import org.sil.diagsap.model.InfixIndexBranch;
import org.sil.diagsap.model.InfixedBaseBranch;
import org.sil.utility.StringUtilities;

/**
 * @author Andy Black build a tree from the parsed description
 */
public class BuildTreeFromDescriptionListener extends DescriptionBaseListener {
	protected DescriptionParser parser;

	private DiagSapTree tree;
	private HashMap<Integer, DiagSapNode> nodeMap = new HashMap<Integer, DiagSapNode>();
	private HashMap<Integer, BranchItem> branchItemMap = new HashMap<Integer, BranchItem>();
	private HashMap<Integer, Branch> branchMap = new HashMap<Integer, Branch>();
	private HashMap<Integer, InfixedBaseBranch> infixedBaseBranchMap = new HashMap<Integer, InfixedBaseBranch>();
	private int maxLevelFound = 0;

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
			maxLevelFound = 1;
		} else {
			ParserRuleContext parent = ctx.getParent();
			branchItemMap.put(ctx.hashCode(), node);
			if (parent != null) {  // find first node above this one; should be two levels up
				parent = parent.getParent();
				if (parent != null) {
					DescriptionParser.NodeContext nodeCtx = (DescriptionParser.NodeContext)parent.getParent();
					DiagSapNode mother = nodeMap.get(nodeCtx.hashCode());
					node.setLevel(mother.getLevel() + 1);
					maxLevelFound = Math.max(mother.getLevel()+1, maxLevelFound);
					node.setMother(mother);
				}
			}
		}
	}

	@Override
	public void enterBranch(DescriptionParser.BranchContext ctx) {
		Branch branch = new Branch();
		branchMap.put(ctx.hashCode(), branch);
	}

	@Override
	public void enterInfixedbase(DescriptionParser.InfixedbaseContext ctx) {
		InfixedBaseBranch base = new InfixedBaseBranch();
		infixedBaseBranchMap.put(ctx.hashCode(), base);
	}

	@Override
	public void exitContent(DescriptionParser.ContentContext ctx) {
		ParserRuleContext parent = ctx.getParent();
		String sContent = ctx.getText().trim();
		// Note: in regular expressions, to quote a single backslash we need
		// \\\\ and to quote a paren we need \\(
		sContent = sContent.replaceAll("\\\\\\(", "(").replaceAll("\\\\\\)", ")");
		ContentBranch content = new ContentBranch(sContent);
		String sParentClass = parent.getClass().getSimpleName();
		int iStart = sParentClass.indexOf("$");
		switch (sParentClass.substring(iStart +1)) {
		case "BranchContext":
			BranchContext branchContext = (BranchContext) parent;
			Branch branch = branchMap.get(branchContext.hashCode());
			branch.setItem(content);
			branchMap.replace(branchContext.hashCode(), branch);
			branchItemMap.put(branchContext.hashCode(), content);
			break;
		case "InfixContext":
			InfixContext infixContext = (InfixContext) parent;
			branchItemMap.put(infixContext.hashCode(), content);
			break;
		case "InfixedbaseContext":
			InfixedbaseContext infixedBaseContext = (InfixedbaseContext) parent;
			InfixedBaseBranch ifxBaseBranch = infixedBaseBranchMap.get(infixedBaseContext.hashCode());
			if (ifxBaseBranch.getContentBefore() == null) {
				ifxBaseBranch.setContentBefore(content);
			} else {
				ifxBaseBranch.setContentAfter(content);
			}
			break;
		}
	}

	@Override
	public void exitInfix(DescriptionParser.InfixContext ctx) {
		InfixedbaseContext baseContext = (InfixedbaseContext)ctx.getParent();
		InfixedBaseBranch base = infixedBaseBranchMap.get(baseContext.hashCode());
		ContentBranch content = (ContentBranch) branchItemMap.get(ctx.hashCode());
		base.setInfixContent(content);
	}

	@Override
	public void exitInfixedbase(DescriptionParser.InfixedbaseContext ctx) {
		InfixedBaseBranch base = infixedBaseBranchMap.get(ctx.hashCode());
		BranchContext branchContext = (BranchContext)ctx.getParent();
		if (branchContext != null) {
			if (base.getContentAfter() != null) {
				ParserRuleContext branchParent = branchContext.getParent();
				DescriptionParser.NodeContext nodeCtx = (DescriptionParser.NodeContext) branchParent
						.getParent();
				DiagSapNode mother = nodeMap.get(nodeCtx.hashCode());
				base.setLevel(mother.getLevel() + 1);
				maxLevelFound = Math.max(mother.getLevel() + 1, maxLevelFound);
			}
		}
		Branch branch = branchMap.get(branchContext.hashCode());
		branch.setItem(base);
	}

	@Override
	public void exitInfixindex(DescriptionParser.InfixindexContext ctx) {
		String sIndex = ctx.getText();
		int index = Integer.parseInt(sIndex.substring(1));
		InfixIndexBranch ifxIndex = new InfixIndexBranch(index);
		BranchContext branchContext = (BranchContext)ctx.getParent();
		Branch branch = branchMap.get(branchContext.hashCode());
		branch.setItem(ifxIndex);
	}

	@Override
	public void exitLeftbranch(DescriptionParser.LeftbranchContext ctx) {
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
		BranchContext branchContext = (BranchContext) ctx.getChild(0);
		DiagSapNode node = getNodeFromContext(ctx);
		node.setRightBranch(branchMap.get(branchContext.hashCode()));
	}

	@Override
	public void exitNode(DescriptionParser.NodeContext ctx) {
		DiagSapNode node = nodeMap.get(ctx.hashCode());
		ParserRuleContext parent = ctx.getParent();
		if (parent instanceof BranchContext) {
			BranchContext branchContext = (BranchContext)parent;
			Branch branch = branchMap.get(branchContext.hashCode());
			branch.setItem(node);
			branchMap.replace(branchContext.hashCode(), branch);
		}
	}

	@Override
	public void exitDescription(DescriptionParser.DescriptionContext ctx) {
		// need to invert level numbers now that we're at the top
		DescriptionParser.NodeContext nodeContext = (NodeContext) ctx.children.get(1);
		DiagSapNode rootNode = nodeMap.get(nodeContext.hashCode());
		rootNode.setLevel(maxLevelFound);
		adjustLevelsInTree(rootNode);
	}

	private void adjustLevelsInTree(DiagSapNode node) {
		BranchItem leftItem = node.getLeftBranch().getItem();
		adjustLevelOfBranchItem(node, leftItem);
		BranchItem rightItem  = node.getRightBranch().getItem();
		adjustLevelOfBranchItem(node, rightItem);
	}

	protected void adjustLevelOfBranchItem(DiagSapNode node, BranchItem branchItem) {
		if (branchItem instanceof DiagSapNode) {
			DiagSapNode node1 = (DiagSapNode)branchItem;
			int adjustedLevel = (maxLevelFound - node1.getLevel()) + 1;
			node1.setLevel(adjustedLevel);
			adjustLevelsInTree((DiagSapNode)branchItem);
		} else if (branchItem instanceof InfixedBaseBranch) {
			InfixedBaseBranch base = (InfixedBaseBranch)branchItem;
			base.setLevel(1);
		} else if (branchItem instanceof ContentBranch) {
			// nothing to do
		}
	}
}
