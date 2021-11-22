/**
 * Copyright (c) 2021 SIL International
 * This software is licensed under the LGPL, version 2.1 or later
 * (http://www.gnu.org/licenses/lgpl-2.1.html)
 */

package org.sil.diagsap.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import org.sil.diagsap.model.BranchItem;
import org.sil.diagsap.model.DiagSapNode;
import org.sil.diagsap.model.DiagSapTree;
import org.sil.diagsap.model.InfixIndexBranch;
import org.sil.diagsap.model.InfixedBaseBranch;

/**
 * @author Andy Black
 *
 *         Singleton pattern
 */
public class TreeSemanticChecker {

	private static TreeSemanticChecker instance;

	DiagSapTree origTree;
	List<SemanticErrorMessage> infixRelatedErrors = new ArrayList<SemanticErrorMessage>();
	List<SemanticErrorMessage> twoConsecutiveNodes = new ArrayList<SemanticErrorMessage>();
	Locale locale;
	List<InfixIndexBranch> infixIndexes = new ArrayList<InfixIndexBranch>();
	List<InfixedBaseBranch> infixedBases = new ArrayList<InfixedBaseBranch>();

	private TreeSemanticChecker() {
		origTree = new DiagSapTree();
	}

	public static TreeSemanticChecker getInstance() {
		if (instance == null) {
			instance = new TreeSemanticChecker();
		}
		return instance;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public List<SemanticErrorMessage> getInfixRelatedErrors() {
		return infixRelatedErrors;
	}

	public List<SemanticErrorMessage> getTwoConsecutiveNodes() {
		return twoConsecutiveNodes;
	}

	public int getNumberOfErrors() {
		return twoConsecutiveNodes.size() + infixRelatedErrors.size();
	}

	public void checkTree(DiagSapTree dsTree) {
		origTree = dsTree;
		initialize();
		checkForTwoConsecutiveNodes(dsTree.getRootNode());
		checkForMismatchedInfixIndexAndBase(dsTree.getRootNode());
	}
	
	public void initialize() {
		twoConsecutiveNodes.clear();
		infixRelatedErrors.clear();
		infixedBases.clear();
		infixIndexes.clear();
	}
	
	private void checkForTwoConsecutiveNodes(DiagSapNode node) {
		BranchItem leftItem = node.getLeftBranch().getItem();
		BranchItem rightItem  = node.getRightBranch().getItem();
		if (leftItem instanceof DiagSapNode && rightItem instanceof DiagSapNode) {
			DiagSapNode leftNode = (DiagSapNode)leftItem;
			DiagSapNode rightNode = (DiagSapNode)rightItem;
			String leftLocation = leftNode.reconstructDescription();
			String rightLocation = rightNode.reconstructDescription();
			Object[] args = { leftLocation, rightLocation };
			twoConsecutiveNodes.add(new SemanticErrorMessage("descriptionsemanticerror.two_consecutive_nodes", args));
			}
		if (leftItem instanceof DiagSapNode) {
			checkForTwoConsecutiveNodes((DiagSapNode)leftItem);
		}
		if (rightItem instanceof DiagSapNode) {
			checkForTwoConsecutiveNodes((DiagSapNode)rightItem);
		}
	}

	private void checkForMismatchedInfixIndexAndBase(DiagSapNode node) {
		collectAllInfixIndexesAndBases(node);
		int numberOfInfixes = infixedBases.size();
		infixIndexes.stream().forEach(idx -> {
			int index = idx.getIndex();
			if (index > numberOfInfixes) {
				String sIndex = "\\" + index;
				Object[] args = { sIndex };
				infixRelatedErrors.add(new SemanticErrorMessage("descriptionsemanticerror.infix_index_has_no_matching_infix_base", args));
			}
			List<InfixIndexBranch> indexDuplicates = infixIndexes.stream().filter(idx2 -> idx.getIndex() == idx2.getIndex()).collect(Collectors.toList());
			if (indexDuplicates.size() > 1) {
				// only want to report it for the first duplicate
				InfixIndexBranch first = indexDuplicates.get(0);
				int firstDuplicateIndex = infixIndexes.indexOf(first);
				int currentIndex = infixIndexes.indexOf(idx);
				if (firstDuplicateIndex == currentIndex) {
					String sIndex = "\\" + index;
					Object[] args = { sIndex };
					infixRelatedErrors.add(new SemanticErrorMessage("descriptionsemanticerror.infix_index_duplicated", args));
				}
			}
		});
		int i = 0;
		for (InfixedBaseBranch base : infixedBases) {
			final int count = ++i;
			Optional<InfixIndexBranch> opt = infixIndexes.stream().filter(idx -> idx.getIndex() == count).findFirst();
			if (!opt.isPresent()) {
				Object[] args = { base.reconstructDescription() };
				infixRelatedErrors.add(new SemanticErrorMessage("descriptionsemanticerror.infixed_base_has_no_index", args));
			}
		}		
	}
	
	private void collectAllInfixIndexesAndBases(DiagSapNode node) {
		collectBranchInfixItems(node.getLeftBranch().getItem());
		collectBranchInfixItems(node.getRightBranch().getItem());
	}

	protected void collectBranchInfixItems(BranchItem item) {
		if (item instanceof DiagSapNode) {
			collectAllInfixIndexesAndBases((DiagSapNode)item);
		} else if (item instanceof InfixIndexBranch) {
			infixIndexes.add((InfixIndexBranch)item);
		} else if (item instanceof InfixedBaseBranch) {
			infixedBases.add((InfixedBaseBranch)item);
		}
	}
}
