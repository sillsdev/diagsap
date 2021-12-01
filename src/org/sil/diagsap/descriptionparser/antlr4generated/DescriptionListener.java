// Generated from Description.g4 by ANTLR 4.7

	package org.sil.diagsap.descriptionparser.antlr4generated;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link DescriptionParser}.
 */
public interface DescriptionListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link DescriptionParser#description}.
	 * @param ctx the parse tree
	 */
	void enterDescription(DescriptionParser.DescriptionContext ctx);
	/**
	 * Exit a parse tree produced by {@link DescriptionParser#description}.
	 * @param ctx the parse tree
	 */
	void exitDescription(DescriptionParser.DescriptionContext ctx);
	/**
	 * Enter a parse tree produced by {@link DescriptionParser#node}.
	 * @param ctx the parse tree
	 */
	void enterNode(DescriptionParser.NodeContext ctx);
	/**
	 * Exit a parse tree produced by {@link DescriptionParser#node}.
	 * @param ctx the parse tree
	 */
	void exitNode(DescriptionParser.NodeContext ctx);
	/**
	 * Enter a parse tree produced by {@link DescriptionParser#leftbranch}.
	 * @param ctx the parse tree
	 */
	void enterLeftbranch(DescriptionParser.LeftbranchContext ctx);
	/**
	 * Exit a parse tree produced by {@link DescriptionParser#leftbranch}.
	 * @param ctx the parse tree
	 */
	void exitLeftbranch(DescriptionParser.LeftbranchContext ctx);
	/**
	 * Enter a parse tree produced by {@link DescriptionParser#rightbranch}.
	 * @param ctx the parse tree
	 */
	void enterRightbranch(DescriptionParser.RightbranchContext ctx);
	/**
	 * Exit a parse tree produced by {@link DescriptionParser#rightbranch}.
	 * @param ctx the parse tree
	 */
	void exitRightbranch(DescriptionParser.RightbranchContext ctx);
	/**
	 * Enter a parse tree produced by {@link DescriptionParser#branch}.
	 * @param ctx the parse tree
	 */
	void enterBranch(DescriptionParser.BranchContext ctx);
	/**
	 * Exit a parse tree produced by {@link DescriptionParser#branch}.
	 * @param ctx the parse tree
	 */
	void exitBranch(DescriptionParser.BranchContext ctx);
	/**
	 * Enter a parse tree produced by {@link DescriptionParser#content}.
	 * @param ctx the parse tree
	 */
	void enterContent(DescriptionParser.ContentContext ctx);
	/**
	 * Exit a parse tree produced by {@link DescriptionParser#content}.
	 * @param ctx the parse tree
	 */
	void exitContent(DescriptionParser.ContentContext ctx);
	/**
	 * Enter a parse tree produced by {@link DescriptionParser#infixedbase}.
	 * @param ctx the parse tree
	 */
	void enterInfixedbase(DescriptionParser.InfixedbaseContext ctx);
	/**
	 * Exit a parse tree produced by {@link DescriptionParser#infixedbase}.
	 * @param ctx the parse tree
	 */
	void exitInfixedbase(DescriptionParser.InfixedbaseContext ctx);
	/**
	 * Enter a parse tree produced by {@link DescriptionParser#infix}.
	 * @param ctx the parse tree
	 */
	void enterInfix(DescriptionParser.InfixContext ctx);
	/**
	 * Exit a parse tree produced by {@link DescriptionParser#infix}.
	 * @param ctx the parse tree
	 */
	void exitInfix(DescriptionParser.InfixContext ctx);
	/**
	 * Enter a parse tree produced by {@link DescriptionParser#infixindex}.
	 * @param ctx the parse tree
	 */
	void enterInfixindex(DescriptionParser.InfixindexContext ctx);
	/**
	 * Exit a parse tree produced by {@link DescriptionParser#infixindex}.
	 * @param ctx the parse tree
	 */
	void exitInfixindex(DescriptionParser.InfixindexContext ctx);
}