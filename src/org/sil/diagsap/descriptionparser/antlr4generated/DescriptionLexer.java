// Generated from Description.g4 by ANTLR 4.7

	package org.sil.diagsap.descriptionparser.antlr4generated;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class DescriptionLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.7", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, TEXT=14, WS=15;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
		"T__9", "T__10", "T__11", "T__12", "TEXT", "WS"
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


	public DescriptionLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Description.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\21Y\b\1\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\3\2\3\2\3\3\3\3\3\4"+
		"\3\4\3\5\3\5\3\6\3\6\3\6\3\7\3\7\3\7\3\b\3\b\3\b\3\t\3\t\3\t\3\n\3\n\3"+
		"\n\3\13\3\13\3\13\3\f\3\f\3\f\3\r\3\r\3\r\3\16\3\16\3\16\3\17\3\17\3\17"+
		"\3\17\3\17\3\17\3\17\3\17\3\17\3\17\6\17O\n\17\r\17\16\17P\3\20\6\20T"+
		"\n\20\r\20\16\20U\3\20\3\20\2\2\21\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n"+
		"\23\13\25\f\27\r\31\16\33\17\35\20\37\21\3\2\5\13\2#),\60\62=??A]_ac}"+
		"\177\177\u0082\1\6\2^^bb~~\u0080\u0080\5\2\13\f\17\17\"\"\2_\2\3\3\2\2"+
		"\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3"+
		"\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2"+
		"\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\3!\3\2\2\2\5#\3\2\2\2\7%\3\2"+
		"\2\2\t\'\3\2\2\2\13)\3\2\2\2\r,\3\2\2\2\17/\3\2\2\2\21\62\3\2\2\2\23\65"+
		"\3\2\2\2\258\3\2\2\2\27;\3\2\2\2\31>\3\2\2\2\33A\3\2\2\2\35N\3\2\2\2\37"+
		"S\3\2\2\2!\"\7*\2\2\"\4\3\2\2\2#$\7+\2\2$\6\3\2\2\2%&\7@\2\2&\b\3\2\2"+
		"\2\'(\7>\2\2(\n\3\2\2\2)*\7^\2\2*+\7\63\2\2+\f\3\2\2\2,-\7^\2\2-.\7\64"+
		"\2\2.\16\3\2\2\2/\60\7^\2\2\60\61\7\65\2\2\61\20\3\2\2\2\62\63\7^\2\2"+
		"\63\64\7\66\2\2\64\22\3\2\2\2\65\66\7^\2\2\66\67\7\67\2\2\67\24\3\2\2"+
		"\289\7^\2\29:\78\2\2:\26\3\2\2\2;<\7^\2\2<=\79\2\2=\30\3\2\2\2>?\7^\2"+
		"\2?@\7:\2\2@\32\3\2\2\2AB\7^\2\2BC\7;\2\2C\34\3\2\2\2DO\t\2\2\2EF\7^\2"+
		"\2FO\7*\2\2GH\7^\2\2HO\7+\2\2IJ\7^\2\2JO\7>\2\2KL\7^\2\2LO\7@\2\2MO\t"+
		"\3\2\2ND\3\2\2\2NE\3\2\2\2NG\3\2\2\2NI\3\2\2\2NK\3\2\2\2NM\3\2\2\2OP\3"+
		"\2\2\2PN\3\2\2\2PQ\3\2\2\2Q\36\3\2\2\2RT\t\4\2\2SR\3\2\2\2TU\3\2\2\2U"+
		"S\3\2\2\2UV\3\2\2\2VW\3\2\2\2WX\b\20\2\2X \3\2\2\2\6\2NPU\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}