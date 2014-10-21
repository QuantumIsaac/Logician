package logician.lexer;

public enum Tokens {
	TK_EXTERN, TK_GIVEN, TK_ASSERT, TK_ASSERTFAILED, TK_PROCEDURE, //'Procedure' is the keyword for a procedure.
																		       //They are called procedures as this is a logic-based language.

	/* Symbols */
	TK_OPEN_BRACE, TK_CLOSED_BRACE, TK_OPEN_PAREN,
	TK_CLOSED_PAREN, TK_SEMICOLON, TK_COMMA, TK_HASH,
	TK_QUOTE, TK_SINGLE_QUOTE, TK_EQUALS, TK_PLUS, TK_MINUS,
	TK_ASTERISK, TK_SLASH, TK_DOT, TK_GT, TK_LT, TK_AMP,

	TK_IDENTIFIER, TK_NUMBER, TK_WHITESPACE, TK_LINE_END
}
