// Generated from Description.g4 by ANTLR 4.7

	package org.sil.diagsap.descriptionparser.antlr4generated;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class DescriptionParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, TEXT=14, WS=15;
	public static final int
		RULE_description = 0, RULE_node = 1, RULE_leftbranch = 2, RULE_rightbranch = 3, 
		RULE_branch = 4, RULE_content = 5, RULE_infixedbase = 6, RULE_infix = 7, 
		RULE_infixindex = 8;
	public static final String[] ruleNames = {
		"description", "node", "leftbranch", "rightbranch", "branch", "content", 
		"infixedbase", "infix", "infixindex"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'('", "')'", "'>'", "'<'", "'\\1'", "'\\2'", "'\\3'", "'\\4'", 
		"'\\5'", "'\\6'", "'\\7'", "'\\8'", "'\\9'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, "TEXT", "WS"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "Description.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public DescriptionParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class DescriptionContext extends ParserRuleContext {
		public List<NodeContext> node() {
			return getRuleContexts(NodeContext.class);
		}
		public NodeContext node(int i) {
			return getRuleContext(NodeContext.class,i);
		}
		public TerminalNode EOF() { return getToken(DescriptionParser.EOF, 0); }
		public List<ContentContext> content() {
			return getRuleContexts(ContentContext.class);
		}
		public ContentContext content(int i) {
			return getRuleContext(ContentContext.class,i);
		}
		public List<InfixindexContext> infixindex() {
			return getRuleContexts(InfixindexContext.class);
		}
		public InfixindexContext infixindex(int i) {
			return getRuleContext(InfixindexContext.class,i);
		}
		public DescriptionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_description; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DescriptionListener ) ((DescriptionListener)listener).enterDescription(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DescriptionListener ) ((DescriptionListener)listener).exitDescription(this);
		}
	}

	public final DescriptionContext description() throws RecognitionException {
		DescriptionContext _localctx = new DescriptionContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_description);
		int _la;
		try {
			setState(172);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,11,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(18);
				match(T__0);
				setState(19);
				node();
				setState(20);
				match(T__1);
				setState(21);
				match(EOF);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(23);
				match(T__0);
				setState(24);
				node();
				setState(25);
				match(T__1);
				setState(26);
				match(T__0);
				notifyErrorListeners("contentAfterCompletedTree");
				setState(28);
				match(EOF);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(30);
				match(T__0);
				setState(31);
				node();
				setState(32);
				match(T__1);
				setState(33);
				match(T__1);
				notifyErrorListeners("contentAfterCompletedTree");
				setState(35);
				match(EOF);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(37);
				match(T__0);
				setState(38);
				node();
				setState(39);
				match(T__1);
				setState(40);
				content();
				notifyErrorListeners("contentAfterCompletedTree");
				setState(42);
				match(EOF);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(44);
				match(T__0);
				setState(45);
				match(EOF);
				notifyErrorListeners("missingContentAndClosingParen");
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(47);
				match(T__0);
				setState(48);
				match(T__1);
				setState(49);
				match(EOF);
				notifyErrorListeners("missingContent");
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(51);
				match(T__0);
				setState(52);
				content();
				setState(53);
				match(EOF);
				notifyErrorListeners("missingClosingParen");
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(56);
				content();
				notifyErrorListeners("missingOpeningParen");
				setState(61);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__0 || _la==TEXT) {
					{
					{
					setState(58);
					node();
					}
					}
					setState(63);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(64);
				match(T__1);
				setState(65);
				match(EOF);
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(67);
				infixindex();
				notifyErrorListeners("missingOpeningParen");
				setState(72);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__0 || _la==TEXT) {
					{
					{
					setState(69);
					node();
					}
					}
					setState(74);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(75);
				match(T__1);
				setState(76);
				match(EOF);
				}
				break;
			case 10:
				enterOuterAlt(_localctx, 10);
				{
				setState(78);
				match(EOF);
				notifyErrorListeners("missingOpeningParen");
				}
				break;
			case 11:
				enterOuterAlt(_localctx, 11);
				{
				setState(80);
				match(T__0);
				setState(81);
				content();
				setState(82);
				match(T__1);
				setState(83);
				match(EOF);
				notifyErrorListeners("missingConstituent");
				}
				break;
			case 12:
				enterOuterAlt(_localctx, 12);
				{
				setState(86);
				match(T__0);
				setState(87);
				content();
				notifyErrorListeners("missingClosingParen");
				setState(90); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(89);
					content();
					}
					}
					setState(92); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==TEXT );
				setState(94);
				match(T__1);
				setState(95);
				match(EOF);
				}
				break;
			case 13:
				enterOuterAlt(_localctx, 13);
				{
				setState(97);
				match(T__0);
				setState(98);
				content();
				notifyErrorListeners("missingClosingParen");
				setState(101); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(100);
					infixindex();
					}
					}
					setState(103); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__4) | (1L << T__5) | (1L << T__6) | (1L << T__7) | (1L << T__8) | (1L << T__9) | (1L << T__10) | (1L << T__11) | (1L << T__12))) != 0) );
				setState(105);
				match(T__1);
				setState(106);
				match(EOF);
				}
				break;
			case 14:
				enterOuterAlt(_localctx, 14);
				{
				setState(108);
				match(T__0);
				setState(109);
				content();
				notifyErrorListeners("missingClosingParen");
				setState(112); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(111);
					infixindex();
					}
					}
					setState(114); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__4) | (1L << T__5) | (1L << T__6) | (1L << T__7) | (1L << T__8) | (1L << T__9) | (1L << T__10) | (1L << T__11) | (1L << T__12))) != 0) );
				setState(119);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==TEXT) {
					{
					{
					setState(116);
					content();
					}
					}
					setState(121);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			case 15:
				enterOuterAlt(_localctx, 15);
				{
				setState(122);
				match(T__0);
				setState(123);
				content();
				notifyErrorListeners("missingClosingParen");
				setState(125);
				match(T__0);
				setState(127); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(126);
					infixindex();
					}
					}
					setState(129); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__4) | (1L << T__5) | (1L << T__6) | (1L << T__7) | (1L << T__8) | (1L << T__9) | (1L << T__10) | (1L << T__11) | (1L << T__12))) != 0) );
				setState(134);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==TEXT) {
					{
					{
					setState(131);
					content();
					}
					}
					setState(136);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			case 16:
				enterOuterAlt(_localctx, 16);
				{
				setState(137);
				match(T__0);
				setState(138);
				match(T__0);
				setState(139);
				content();
				notifyErrorListeners("missingClosingParen");
				setState(141);
				match(T__0);
				setState(143); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(142);
					infixindex();
					}
					}
					setState(145); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__4) | (1L << T__5) | (1L << T__6) | (1L << T__7) | (1L << T__8) | (1L << T__9) | (1L << T__10) | (1L << T__11) | (1L << T__12))) != 0) );
				setState(150);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==TEXT) {
					{
					{
					setState(147);
					content();
					}
					}
					setState(152);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			case 17:
				enterOuterAlt(_localctx, 17);
				{
				setState(153);
				match(T__0);
				setState(154);
				content();
				notifyErrorListeners("missingClosingParen");
				setState(156);
				match(T__0);
				setState(158); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(157);
					infixindex();
					}
					}
					setState(160); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__4) | (1L << T__5) | (1L << T__6) | (1L << T__7) | (1L << T__8) | (1L << T__9) | (1L << T__10) | (1L << T__11) | (1L << T__12))) != 0) );
				setState(162);
				match(T__1);
				setState(163);
				match(EOF);
				}
				break;
			case 18:
				enterOuterAlt(_localctx, 18);
				{
				setState(165);
				match(T__0);
				setState(166);
				content();
				notifyErrorListeners("missingClosingParen");
				setState(168);
				node();
				setState(169);
				match(T__1);
				setState(170);
				match(EOF);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NodeContext extends ParserRuleContext {
		public LeftbranchContext leftbranch() {
			return getRuleContext(LeftbranchContext.class,0);
		}
		public RightbranchContext rightbranch() {
			return getRuleContext(RightbranchContext.class,0);
		}
		public BranchContext branch() {
			return getRuleContext(BranchContext.class,0);
		}
		public NodeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_node; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DescriptionListener ) ((DescriptionListener)listener).enterNode(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DescriptionListener ) ((DescriptionListener)listener).exitNode(this);
		}
	}

	public final NodeContext node() throws RecognitionException {
		NodeContext _localctx = new NodeContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_node);
		try {
			setState(195);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,12,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(174);
				leftbranch();
				setState(175);
				rightbranch();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(177);
				leftbranch();
				notifyErrorListeners("missingRightBranch");
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(180);
				leftbranch();
				setState(181);
				rightbranch();
				setState(182);
				match(T__1);
				notifyErrorListeners("tooManyCloseParens");
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(185);
				leftbranch();
				setState(186);
				rightbranch();
				notifyErrorListeners("missingClosingParen");
				setState(188);
				branch();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(190);
				leftbranch();
				setState(191);
				rightbranch();
				notifyErrorListeners("missingClosingParen");
				setState(193);
				match(T__0);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LeftbranchContext extends ParserRuleContext {
		public BranchContext branch() {
			return getRuleContext(BranchContext.class,0);
		}
		public LeftbranchContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_leftbranch; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DescriptionListener ) ((DescriptionListener)listener).enterLeftbranch(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DescriptionListener ) ((DescriptionListener)listener).exitLeftbranch(this);
		}
	}

	public final LeftbranchContext leftbranch() throws RecognitionException {
		LeftbranchContext _localctx = new LeftbranchContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_leftbranch);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(197);
			branch();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RightbranchContext extends ParserRuleContext {
		public List<BranchContext> branch() {
			return getRuleContexts(BranchContext.class);
		}
		public BranchContext branch(int i) {
			return getRuleContext(BranchContext.class,i);
		}
		public RightbranchContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_rightbranch; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DescriptionListener ) ((DescriptionListener)listener).enterRightbranch(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DescriptionListener ) ((DescriptionListener)listener).exitRightbranch(this);
		}
	}

	public final RightbranchContext rightbranch() throws RecognitionException {
		RightbranchContext _localctx = new RightbranchContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_rightbranch);
		try {
			int _alt;
			setState(213);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,15,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(199);
				branch();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(200);
				branch();
				notifyErrorListeners("missingClosingParen");
				setState(203); 
				_errHandler.sync(this);
				_alt = 1;
				do {
					switch (_alt) {
					case 1:
						{
						{
						setState(202);
						branch();
						}
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(205); 
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,13,_ctx);
				} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
				setState(210);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,14,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(207);
						match(T__1);
						}
						} 
					}
					setState(212);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,14,_ctx);
				}
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BranchContext extends ParserRuleContext {
		public List<ContentContext> content() {
			return getRuleContexts(ContentContext.class);
		}
		public ContentContext content(int i) {
			return getRuleContext(ContentContext.class,i);
		}
		public NodeContext node() {
			return getRuleContext(NodeContext.class,0);
		}
		public InfixindexContext infixindex() {
			return getRuleContext(InfixindexContext.class,0);
		}
		public InfixedbaseContext infixedbase() {
			return getRuleContext(InfixedbaseContext.class,0);
		}
		public BranchContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_branch; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DescriptionListener ) ((DescriptionListener)listener).enterBranch(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DescriptionListener ) ((DescriptionListener)listener).exitBranch(this);
		}
	}

	public final BranchContext branch() throws RecognitionException {
		BranchContext _localctx = new BranchContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_branch);
		int _la;
		try {
			int _alt;
			setState(258);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,18,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(215);
				match(T__0);
				setState(216);
				content();
				setState(217);
				match(T__1);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(219);
				match(T__0);
				setState(220);
				node();
				setState(221);
				match(T__1);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(223);
				match(T__0);
				setState(224);
				infixindex();
				setState(225);
				match(T__1);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(227);
				match(T__0);
				setState(228);
				infixedbase();
				setState(229);
				match(T__1);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(231);
				match(T__0);
				setState(232);
				content();
				notifyErrorListeners("missingClosingParen");
				setState(235); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(234);
					content();
					}
					}
					setState(237); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==TEXT );
				setState(239);
				match(T__1);
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(241);
				match(T__0);
				setState(242);
				content();
				notifyErrorListeners("missingClosingParen");
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(245);
				content();
				notifyErrorListeners("missingClosingParen");
				setState(247);
				node();
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				notifyErrorListeners("missingOpeningWedge");
				setState(250);
				content();
				setState(251);
				match(T__2);
				setState(255);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,17,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(252);
						content();
						}
						} 
					}
					setState(257);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,17,_ctx);
				}
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ContentContext extends ParserRuleContext {
		public TerminalNode TEXT() { return getToken(DescriptionParser.TEXT, 0); }
		public ContentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_content; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DescriptionListener ) ((DescriptionListener)listener).enterContent(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DescriptionListener ) ((DescriptionListener)listener).exitContent(this);
		}
	}

	public final ContentContext content() throws RecognitionException {
		ContentContext _localctx = new ContentContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_content);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(260);
			match(TEXT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class InfixedbaseContext extends ParserRuleContext {
		public List<ContentContext> content() {
			return getRuleContexts(ContentContext.class);
		}
		public ContentContext content(int i) {
			return getRuleContext(ContentContext.class,i);
		}
		public InfixContext infix() {
			return getRuleContext(InfixContext.class,0);
		}
		public InfixedbaseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_infixedbase; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DescriptionListener ) ((DescriptionListener)listener).enterInfixedbase(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DescriptionListener ) ((DescriptionListener)listener).exitInfixedbase(this);
		}
	}

	public final InfixedbaseContext infixedbase() throws RecognitionException {
		InfixedbaseContext _localctx = new InfixedbaseContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_infixedbase);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(262);
			content();
			setState(263);
			infix();
			setState(265);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==TEXT) {
				{
				setState(264);
				content();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class InfixContext extends ParserRuleContext {
		public ContentContext content() {
			return getRuleContext(ContentContext.class,0);
		}
		public InfixContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_infix; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DescriptionListener ) ((DescriptionListener)listener).enterInfix(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DescriptionListener ) ((DescriptionListener)listener).exitInfix(this);
		}
	}

	public final InfixContext infix() throws RecognitionException {
		InfixContext _localctx = new InfixContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_infix);
		try {
			setState(275);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,20,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(267);
				match(T__3);
				setState(268);
				content();
				setState(269);
				match(T__2);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(271);
				match(T__3);
				setState(272);
				content();
				notifyErrorListeners("missingClosingWedge");
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class InfixindexContext extends ParserRuleContext {
		public InfixindexContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_infixindex; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DescriptionListener ) ((DescriptionListener)listener).enterInfixindex(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DescriptionListener ) ((DescriptionListener)listener).exitInfixindex(this);
		}
	}

	public final InfixindexContext infixindex() throws RecognitionException {
		InfixindexContext _localctx = new InfixindexContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_infixindex);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(277);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__4) | (1L << T__5) | (1L << T__6) | (1L << T__7) | (1L << T__8) | (1L << T__9) | (1L << T__10) | (1L << T__11) | (1L << T__12))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\21\u011a\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\3\2\3"+
		"\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2"+
		"\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3"+
		"\2\3\2\3\2\3\2\3\2\7\2>\n\2\f\2\16\2A\13\2\3\2\3\2\3\2\3\2\3\2\3\2\7\2"+
		"I\n\2\f\2\16\2L\13\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3"+
		"\2\3\2\3\2\6\2]\n\2\r\2\16\2^\3\2\3\2\3\2\3\2\3\2\3\2\3\2\6\2h\n\2\r\2"+
		"\16\2i\3\2\3\2\3\2\3\2\3\2\3\2\3\2\6\2s\n\2\r\2\16\2t\3\2\7\2x\n\2\f\2"+
		"\16\2{\13\2\3\2\3\2\3\2\3\2\3\2\6\2\u0082\n\2\r\2\16\2\u0083\3\2\7\2\u0087"+
		"\n\2\f\2\16\2\u008a\13\2\3\2\3\2\3\2\3\2\3\2\3\2\6\2\u0092\n\2\r\2\16"+
		"\2\u0093\3\2\7\2\u0097\n\2\f\2\16\2\u009a\13\2\3\2\3\2\3\2\3\2\3\2\6\2"+
		"\u00a1\n\2\r\2\16\2\u00a2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\5\2"+
		"\u00af\n\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\5\3\u00c6\n\3\3\4\3\4\3\5\3\5\3\5\3\5\6\5\u00ce"+
		"\n\5\r\5\16\5\u00cf\3\5\7\5\u00d3\n\5\f\5\16\5\u00d6\13\5\5\5\u00d8\n"+
		"\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6"+
		"\3\6\3\6\3\6\6\6\u00ee\n\6\r\6\16\6\u00ef\3\6\3\6\3\6\3\6\3\6\3\6\3\6"+
		"\3\6\3\6\3\6\3\6\3\6\3\6\3\6\7\6\u0100\n\6\f\6\16\6\u0103\13\6\5\6\u0105"+
		"\n\6\3\7\3\7\3\b\3\b\3\b\5\b\u010c\n\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t"+
		"\5\t\u0116\n\t\3\n\3\n\3\n\2\2\13\2\4\6\b\n\f\16\20\22\2\3\3\2\7\17\2"+
		"\u013e\2\u00ae\3\2\2\2\4\u00c5\3\2\2\2\6\u00c7\3\2\2\2\b\u00d7\3\2\2\2"+
		"\n\u0104\3\2\2\2\f\u0106\3\2\2\2\16\u0108\3\2\2\2\20\u0115\3\2\2\2\22"+
		"\u0117\3\2\2\2\24\25\7\3\2\2\25\26\5\4\3\2\26\27\7\4\2\2\27\30\7\2\2\3"+
		"\30\u00af\3\2\2\2\31\32\7\3\2\2\32\33\5\4\3\2\33\34\7\4\2\2\34\35\7\3"+
		"\2\2\35\36\b\2\1\2\36\37\7\2\2\3\37\u00af\3\2\2\2 !\7\3\2\2!\"\5\4\3\2"+
		"\"#\7\4\2\2#$\7\4\2\2$%\b\2\1\2%&\7\2\2\3&\u00af\3\2\2\2\'(\7\3\2\2()"+
		"\5\4\3\2)*\7\4\2\2*+\5\f\7\2+,\b\2\1\2,-\7\2\2\3-\u00af\3\2\2\2./\7\3"+
		"\2\2/\60\7\2\2\3\60\u00af\b\2\1\2\61\62\7\3\2\2\62\63\7\4\2\2\63\64\7"+
		"\2\2\3\64\u00af\b\2\1\2\65\66\7\3\2\2\66\67\5\f\7\2\678\7\2\2\389\b\2"+
		"\1\29\u00af\3\2\2\2:;\5\f\7\2;?\b\2\1\2<>\5\4\3\2=<\3\2\2\2>A\3\2\2\2"+
		"?=\3\2\2\2?@\3\2\2\2@B\3\2\2\2A?\3\2\2\2BC\7\4\2\2CD\7\2\2\3D\u00af\3"+
		"\2\2\2EF\5\22\n\2FJ\b\2\1\2GI\5\4\3\2HG\3\2\2\2IL\3\2\2\2JH\3\2\2\2JK"+
		"\3\2\2\2KM\3\2\2\2LJ\3\2\2\2MN\7\4\2\2NO\7\2\2\3O\u00af\3\2\2\2PQ\7\2"+
		"\2\3Q\u00af\b\2\1\2RS\7\3\2\2ST\5\f\7\2TU\7\4\2\2UV\7\2\2\3VW\b\2\1\2"+
		"W\u00af\3\2\2\2XY\7\3\2\2YZ\5\f\7\2Z\\\b\2\1\2[]\5\f\7\2\\[\3\2\2\2]^"+
		"\3\2\2\2^\\\3\2\2\2^_\3\2\2\2_`\3\2\2\2`a\7\4\2\2ab\7\2\2\3b\u00af\3\2"+
		"\2\2cd\7\3\2\2de\5\f\7\2eg\b\2\1\2fh\5\22\n\2gf\3\2\2\2hi\3\2\2\2ig\3"+
		"\2\2\2ij\3\2\2\2jk\3\2\2\2kl\7\4\2\2lm\7\2\2\3m\u00af\3\2\2\2no\7\3\2"+
		"\2op\5\f\7\2pr\b\2\1\2qs\5\22\n\2rq\3\2\2\2st\3\2\2\2tr\3\2\2\2tu\3\2"+
		"\2\2uy\3\2\2\2vx\5\f\7\2wv\3\2\2\2x{\3\2\2\2yw\3\2\2\2yz\3\2\2\2z\u00af"+
		"\3\2\2\2{y\3\2\2\2|}\7\3\2\2}~\5\f\7\2~\177\b\2\1\2\177\u0081\7\3\2\2"+
		"\u0080\u0082\5\22\n\2\u0081\u0080\3\2\2\2\u0082\u0083\3\2\2\2\u0083\u0081"+
		"\3\2\2\2\u0083\u0084\3\2\2\2\u0084\u0088\3\2\2\2\u0085\u0087\5\f\7\2\u0086"+
		"\u0085\3\2\2\2\u0087\u008a\3\2\2\2\u0088\u0086\3\2\2\2\u0088\u0089\3\2"+
		"\2\2\u0089\u00af\3\2\2\2\u008a\u0088\3\2\2\2\u008b\u008c\7\3\2\2\u008c"+
		"\u008d\7\3\2\2\u008d\u008e\5\f\7\2\u008e\u008f\b\2\1\2\u008f\u0091\7\3"+
		"\2\2\u0090\u0092\5\22\n\2\u0091\u0090\3\2\2\2\u0092\u0093\3\2\2\2\u0093"+
		"\u0091\3\2\2\2\u0093\u0094\3\2\2\2\u0094\u0098\3\2\2\2\u0095\u0097\5\f"+
		"\7\2\u0096\u0095\3\2\2\2\u0097\u009a\3\2\2\2\u0098\u0096\3\2\2\2\u0098"+
		"\u0099\3\2\2\2\u0099\u00af\3\2\2\2\u009a\u0098\3\2\2\2\u009b\u009c\7\3"+
		"\2\2\u009c\u009d\5\f\7\2\u009d\u009e\b\2\1\2\u009e\u00a0\7\3\2\2\u009f"+
		"\u00a1\5\22\n\2\u00a0\u009f\3\2\2\2\u00a1\u00a2\3\2\2\2\u00a2\u00a0\3"+
		"\2\2\2\u00a2\u00a3\3\2\2\2\u00a3\u00a4\3\2\2\2\u00a4\u00a5\7\4\2\2\u00a5"+
		"\u00a6\7\2\2\3\u00a6\u00af\3\2\2\2\u00a7\u00a8\7\3\2\2\u00a8\u00a9\5\f"+
		"\7\2\u00a9\u00aa\b\2\1\2\u00aa\u00ab\5\4\3\2\u00ab\u00ac\7\4\2\2\u00ac"+
		"\u00ad\7\2\2\3\u00ad\u00af\3\2\2\2\u00ae\24\3\2\2\2\u00ae\31\3\2\2\2\u00ae"+
		" \3\2\2\2\u00ae\'\3\2\2\2\u00ae.\3\2\2\2\u00ae\61\3\2\2\2\u00ae\65\3\2"+
		"\2\2\u00ae:\3\2\2\2\u00aeE\3\2\2\2\u00aeP\3\2\2\2\u00aeR\3\2\2\2\u00ae"+
		"X\3\2\2\2\u00aec\3\2\2\2\u00aen\3\2\2\2\u00ae|\3\2\2\2\u00ae\u008b\3\2"+
		"\2\2\u00ae\u009b\3\2\2\2\u00ae\u00a7\3\2\2\2\u00af\3\3\2\2\2\u00b0\u00b1"+
		"\5\6\4\2\u00b1\u00b2\5\b\5\2\u00b2\u00c6\3\2\2\2\u00b3\u00b4\5\6\4\2\u00b4"+
		"\u00b5\b\3\1\2\u00b5\u00c6\3\2\2\2\u00b6\u00b7\5\6\4\2\u00b7\u00b8\5\b"+
		"\5\2\u00b8\u00b9\7\4\2\2\u00b9\u00ba\b\3\1\2\u00ba\u00c6\3\2\2\2\u00bb"+
		"\u00bc\5\6\4\2\u00bc\u00bd\5\b\5\2\u00bd\u00be\b\3\1\2\u00be\u00bf\5\n"+
		"\6\2\u00bf\u00c6\3\2\2\2\u00c0\u00c1\5\6\4\2\u00c1\u00c2\5\b\5\2\u00c2"+
		"\u00c3\b\3\1\2\u00c3\u00c4\7\3\2\2\u00c4\u00c6\3\2\2\2\u00c5\u00b0\3\2"+
		"\2\2\u00c5\u00b3\3\2\2\2\u00c5\u00b6\3\2\2\2\u00c5\u00bb\3\2\2\2\u00c5"+
		"\u00c0\3\2\2\2\u00c6\5\3\2\2\2\u00c7\u00c8\5\n\6\2\u00c8\7\3\2\2\2\u00c9"+
		"\u00d8\5\n\6\2\u00ca\u00cb\5\n\6\2\u00cb\u00cd\b\5\1\2\u00cc\u00ce\5\n"+
		"\6\2\u00cd\u00cc\3\2\2\2\u00ce\u00cf\3\2\2\2\u00cf\u00cd\3\2\2\2\u00cf"+
		"\u00d0\3\2\2\2\u00d0\u00d4\3\2\2\2\u00d1\u00d3\7\4\2\2\u00d2\u00d1\3\2"+
		"\2\2\u00d3\u00d6\3\2\2\2\u00d4\u00d2\3\2\2\2\u00d4\u00d5\3\2\2\2\u00d5"+
		"\u00d8\3\2\2\2\u00d6\u00d4\3\2\2\2\u00d7\u00c9\3\2\2\2\u00d7\u00ca\3\2"+
		"\2\2\u00d8\t\3\2\2\2\u00d9\u00da\7\3\2\2\u00da\u00db\5\f\7\2\u00db\u00dc"+
		"\7\4\2\2\u00dc\u0105\3\2\2\2\u00dd\u00de\7\3\2\2\u00de\u00df\5\4\3\2\u00df"+
		"\u00e0\7\4\2\2\u00e0\u0105\3\2\2\2\u00e1\u00e2\7\3\2\2\u00e2\u00e3\5\22"+
		"\n\2\u00e3\u00e4\7\4\2\2\u00e4\u0105\3\2\2\2\u00e5\u00e6\7\3\2\2\u00e6"+
		"\u00e7\5\16\b\2\u00e7\u00e8\7\4\2\2\u00e8\u0105\3\2\2\2\u00e9\u00ea\7"+
		"\3\2\2\u00ea\u00eb\5\f\7\2\u00eb\u00ed\b\6\1\2\u00ec\u00ee\5\f\7\2\u00ed"+
		"\u00ec\3\2\2\2\u00ee\u00ef\3\2\2\2\u00ef\u00ed\3\2\2\2\u00ef\u00f0\3\2"+
		"\2\2\u00f0\u00f1\3\2\2\2\u00f1\u00f2\7\4\2\2\u00f2\u0105\3\2\2\2\u00f3"+
		"\u00f4\7\3\2\2\u00f4\u00f5\5\f\7\2\u00f5\u00f6\b\6\1\2\u00f6\u0105\3\2"+
		"\2\2\u00f7\u00f8\5\f\7\2\u00f8\u00f9\b\6\1\2\u00f9\u00fa\5\4\3\2\u00fa"+
		"\u0105\3\2\2\2\u00fb\u00fc\b\6\1\2\u00fc\u00fd\5\f\7\2\u00fd\u0101\7\5"+
		"\2\2\u00fe\u0100\5\f\7\2\u00ff\u00fe\3\2\2\2\u0100\u0103\3\2\2\2\u0101"+
		"\u00ff\3\2\2\2\u0101\u0102\3\2\2\2\u0102\u0105\3\2\2\2\u0103\u0101\3\2"+
		"\2\2\u0104\u00d9\3\2\2\2\u0104\u00dd\3\2\2\2\u0104\u00e1\3\2\2\2\u0104"+
		"\u00e5\3\2\2\2\u0104\u00e9\3\2\2\2\u0104\u00f3\3\2\2\2\u0104\u00f7\3\2"+
		"\2\2\u0104\u00fb\3\2\2\2\u0105\13\3\2\2\2\u0106\u0107\7\20\2\2\u0107\r"+
		"\3\2\2\2\u0108\u0109\5\f\7\2\u0109\u010b\5\20\t\2\u010a\u010c\5\f\7\2"+
		"\u010b\u010a\3\2\2\2\u010b\u010c\3\2\2\2\u010c\17\3\2\2\2\u010d\u010e"+
		"\7\6\2\2\u010e\u010f\5\f\7\2\u010f\u0110\7\5\2\2\u0110\u0116\3\2\2\2\u0111"+
		"\u0112\7\6\2\2\u0112\u0113\5\f\7\2\u0113\u0114\b\t\1\2\u0114\u0116\3\2"+
		"\2\2\u0115\u010d\3\2\2\2\u0115\u0111\3\2\2\2\u0116\21\3\2\2\2\u0117\u0118"+
		"\t\2\2\2\u0118\23\3\2\2\2\27?J^ity\u0083\u0088\u0093\u0098\u00a2\u00ae"+
		"\u00c5\u00cf\u00d4\u00d7\u00ef\u0101\u0104\u010b\u0115";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}