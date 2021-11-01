// --------------------------------------------------------------------------------------------
// Copyright (c) 2021 SIL International
// This software is licensed under the LGPL, version 2.1 or later
// (http://www.gnu.org/licenses/lgpl-2.1.html)
//
// File: Description.g4
// Responsibility: Andy Black
// Last reviewed:
//
// <remarks>
// ANTLR v.4 grammar for DiagSap descriptions
// </remarks>
// --------------------------------------------------------------------------------------------
grammar Description;

@header {
	package org.sil.diagsap.descriptionparser.antlr4generated;
}
description  : '(' node ')' EOF
             | EOF {notifyErrorListeners("missingOpeningParen");}
             | node ')' {notifyErrorListeners("missingOpeningParen");}
             | '(' node ')' {notifyErrorListeners("contentAfterCompletedTree");} content
             | '(' node ')' {notifyErrorListeners("contentAfterCompletedTree");} node
             | '(' node ')' ')' {notifyErrorListeners("tooManyCloseParens");}
             ;

// we allow empty nodes that just have parens (hence, both type and content are optional)
node : '(' node ')' '(' node ')'
     | '(' content ')' '(' node ')'
     | '(' node ')' '(' content ')'
     | '(' content ')' '(' content ')'
     | '(' content ')'
     | node ')' '(' node ')' {notifyErrorListeners("missingOpeningParen");}
     | node ')' '(' content ')' {notifyErrorListeners("missingOpeningParen");}
     | '(' node ')' {notifyErrorListeners("missingOpeningParen");} node ')'
     | '(' node ')' {notifyErrorListeners("missingOpeningParen");} content ')'
     | '(' node '(' node ')' {notifyErrorListeners("missingClosingParen");}
     | '(' node '(' content ')' {notifyErrorListeners("missingClosingParen");}
     | '(' node ')' '(' node  {notifyErrorListeners("missingClosingParen");}
     | '(' node ')' '(' content {notifyErrorListeners("missingClosingParen");}
     | content ')' {notifyErrorListeners("missingOpeningParen");}
     | '(' content {notifyErrorListeners("missingClosingParen");}
     ;

content : //(TEXT | BACKSLASH)+
          TEXT
        | infixindex
        | TEXT infix TEXT*
        |      infix TEXT
        ;

infix : openWedge TEXT closeWedge;

openWedge : '<';

closeWedge : '>';

infixindex : '\\1'
           | '\\2'
           | '\\3'
           | '\\4'
           | '\\5'
           | '\\6'
           | '\\7'
           | '\\8'
           | '\\9'
           ;

// Node text content, with exception of backslash or forward slash sequences.
// Those are handled via BACKSLASH and SLASH
// We need to do it this way because the lexer is a greedy parser and will always
// match the longest sequence (so we'll never see \O, \T, \L, \G, /s, or /S).
TEXT : (
	   [,.;:^!?@#$%&'"a-zA-Z0-9\u0080-\uFFFF+-]
     | [_*=]
     | '['
     | ']'
     | '{'
     | '}'
     | '\\('
     | '\\)'
     | '\\<'
     | '\\>'
//     | '/'
     | '~'
     | '`'
//     | '\\'
     | '|' 
     )+  ;

// allow backslash for non-keyword items (\O, \T, \G, \L)
// BACKSLASH : '\\' ~[123456789];

WS : [ \t\r\n]+ -> skip ; // skip tabs, newlines, but leave spaces (for inside of node text)
