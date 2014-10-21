package logician.lexer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import static logician.lexer.Tokens.*;

public class Lexer{
	private Iterator<Character> it;
	public Lexer(String code){
		char[] asArray = code.toCharArray();
		ArrayList<Character> al = new ArrayList<>();
		for(char c : asArray){
			al.add(new Character(c));
		}
		it = al.iterator();
	}
	public Token getToken(){
		Character lastchar = null;
		while(it.hasNext() && !LexerUtil.isWhitespace( (lastchar = it.next()) ) ){
			if(LexerUtil.isEndLine(lastchar.charValue())){
				return new Token(TK_LINE_END);
			}
			if(LexerUtil.isAlpha(lastchar.charValue()) ){
				String identifier = lastchar.toString();
				while(it.hasNext() && LexerUtil.isAlNum( (lastchar = it.next() ) ) ){
					identifier += lastchar.charValue();
				}
				Token out = getTokenFor(identifier);
				return out;
			}else if(LexerUtil.isNumeric(lastchar.charValue()) ){
				String numstr = lastchar.toString();
				boolean hasDot = false;
				while(it.hasNext() && (LexerUtil.isNumeric(lastchar.charValue()) || lastchar.charValue() == '.') ){
					if(lastchar.charValue() == '.' && hasDot){
						System.err.println("Numerical expression has multiple decimal points!");
						return null;
					}else if(lastchar.charValue() == '.' && !hasDot){
						numstr += '.';
						hasDot = true;
					}else{
						numstr += lastchar.charValue();
					}
				}
			}else{
				switch(lastchar.charValue()){
					case '{':
						return new Token(TK_OPEN_BRACE);
					case '}':
						return new Token(TK_CLOSED_BRACE);
					case '(':
						return new Token(TK_OPEN_PAREN);
					case ')':
						return new Token(TK_CLOSED_PAREN);
					case ';':
						return new Token(TK_SEMICOLON);
					case ',':
						return new Token(TK_COMMA);
					case '#':
						return new Token(TK_HASH);
					case '"':
						return new Token(TK_QUOTE);
					case '\'':
						return new Token(TK_SINGLE_QUOTE);
					case '=':
						return new Token(TK_EQUALS);
					case '+':
						return new Token(TK_PLUS);
					case '-':
						return new Token(TK_MINUS);
					case '*':
						return new Token(TK_ASTERISK);
					case '/':
						return new Token(TK_SLASH);
					case '.':
						return new Token(TK_DOT);
					case '>':
						return new Token(TK_GT);
					case '<':
						return new Token(TK_LT);
					case '&':
						return new Token(TK_AMP);
					default:
						return null;
				}
			}
		}
		if( lastchar != null && LexerUtil.isWhitespace( lastchar.charValue() ) ){
			return new Token(TK_WHITESPACE);
		}else{
			return null;
		}
	}
	private Token getTokenFor(String ident){
		if(ident.equals("Extern"))
			return new Token(TK_EXTERN);
		else if(ident.equals("Given"))
			return new Token(TK_GIVEN);
		else if(ident.equals("Assert"))
			return new Token(TK_ASSERT);
		else if(ident.equals("AssertFailed"))
			return new Token(TK_ASSERTFAILED);
		else if(ident.equals("Procedure"))
			return new Token(TK_PROCEDURE);
		else
			return new Identifier(ident);
	}
	public static void main(String[] args){
		Scanner scan = new Scanner(System.in);
		String total = "";
		String line;
		boolean first = true;
		while( !(line = scan.nextLine() ).equals("EOF") ){
			if(first){
				total = line;
				first = false;
			}else{
				total += '\n'+line;
			}
		}
		Lexer lex = new Lexer(total);
		Token cur;
		while( ( cur = lex.getToken() ) != null ){
			if( cur instanceof Identifier ){
				System.out.println("Identifier: "+( (Identifier) cur).getIdentifier());
			}else{
				System.out.println("Token: "+cur.getToken() );
			}
		}
	}
}
