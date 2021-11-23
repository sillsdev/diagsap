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
             | '(' node ')' '(' {notifyErrorListeners("contentAfterCompletedTree");} EOF
             | '(' node ')' ')' {notifyErrorListeners("contentAfterCompletedTree");} EOF
             | '(' node ')' content {notifyErrorListeners("contentAfterCompletedTree");} EOF
             | '(' EOF {notifyErrorListeners("missingContentAndClosingParen");}
             | '(' ')' EOF {notifyErrorListeners("missingContent");}
             | '(' content EOF {notifyErrorListeners("missingClosingParen");}
             | content {notifyErrorListeners("missingOpeningParen");} node* ')' EOF
             | infixindex {notifyErrorListeners("missingOpeningParen");} node* ')' EOF
             | EOF {notifyErrorListeners("missingOpeningParen");}
             | '(' content ')' EOF {notifyErrorListeners("missingConstituent");}
             | '(' content {notifyErrorListeners("missingClosingParen");} content+ ')' EOF
             | '(' content {notifyErrorListeners("missingClosingParen");} infixindex+ ')' EOF
             | '(' content {notifyErrorListeners("missingClosingParen");} infixindex+ content*
             | '(' content {notifyErrorListeners("missingClosingParen");} '(' infixindex+ content*
             | '(' '(' content {notifyErrorListeners("missingClosingParen");} '(' infixindex+ content*
             | '(' content {notifyErrorListeners("missingClosingParen");} '(' infixindex+ ')' EOF
             | '(' content {notifyErrorListeners("missingClosingParen");} node ')' EOF
             ;

node : leftbranch rightbranch
     | leftbranch {notifyErrorListeners("missingRightBranch");}
     | leftbranch rightbranch ')' {notifyErrorListeners("tooManyCloseParens");}
     | leftbranch rightbranch {notifyErrorListeners("missingClosingParen");} branch
     | leftbranch rightbranch {notifyErrorListeners("missingClosingParen");} '('
     ;

leftbranch : branch;
rightbranch : branch
            | branch {notifyErrorListeners("missingClosingParen");} branch+ ')'*
            ;
branch : '(' content ')'
       | '(' node ')'
       | '(' infixindex ')'
       | '(' infixedbase ')'
       | '(' content {notifyErrorListeners("missingClosingParen");} content+ ')'
       | '(' content {notifyErrorListeners("missingClosingParen");}
       | content {notifyErrorListeners("missingClosingParen");} node
       | {notifyErrorListeners("missingOpeningWedge");} content '>' content*
       ;

content : //(TEXT | BACKSLASH)+
          TEXT
        ;
infixedbase : content infix content?;

infix : '<' content '>'
      | '<' content {notifyErrorListeners("missingClosingWedge");}
      ;

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
     | '\\'
     | '|' 
     )+  ;

// allow backslash for non-keyword items (\O, \T, \G, \L)
// BACKSLASH : '\\' ~[123456789];

WS : [ \t\r\n]+ -> skip ; // skip tabs, newlines, but leave spaces (for inside of node text)
