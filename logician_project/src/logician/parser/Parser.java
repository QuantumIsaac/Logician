package logician.parser;

import logician.parser.ast.*;
import logician.lexer.*;
import static logician.lexer.Tokens.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;

public class Parser{
	private Lexer lex;
	private Token current;
	
	public static final Map<Tokens,Integer> binop = new HashMap<>();
	public static final Map<Tokens,Character> binop_char = new HashMap<>();
	static{ //Initilialize maps
		binop.put(TK_EQUALS, 10); //Lowest precedence completed last, highest completed first
		binop.put(TK_GT,10);
		binop.put(TK_LT, 10);
		binop.put(TK_PLUS,20);
		binop.put(TK_MINUS,20);
		binop.put(TK_ASTERISK,40); //Remember: asterisk = * = multiply
		binop.put(TK_SLASH, 40);       //Remember: slash = / = divide
	
		binop_char.put(TK_EQUALS,'=');
		binop_char.put(TK_GT,'>');
		binop_char.put(TK_LT,'<');
		binop_char.put(TK_PLUS,'+');
		binop_char.put(TK_MINUS,'-');
		binop_char.put(TK_ASTERISK,'*');
		binop_char.put(TK_SLASH,'/');
	}

	public Parser(String code){
		lex = new Lexer(code);
	}
	private Token nextToken(){
		current = lex.getToken();
		return current;
	}
	public ASTExpression parseNumExp(){
		if(current instanceof LexerNumber){
			ASTNumber num = new ASTNumber( ((LexerNumber)current).getNumVal() );
			nextToken();
			return num;
		}else{
			Errors.Error("Number expression expected.");
			return null;
		}
	}
	public ASTExpression parseParenExp(){
		if( current.getToken() != TK_OPEN_PAREN){
			Errors.Error("'(' expected.");
			return null;
		}
		nextToken();
		ASTExpression V = parseExpression();
		if(V == null) return null;
		
		if( current.getToken() != TK_CLOSED_PAREN){
			Errors.Error("')' expected.");
			return null;
		}
		nextToken();
		return V;
	}
	public ASTExpression parseIdentifierExp(){
		if(  current instanceof Identifier  ){
			String ident = ((Identifier)current).getIdentifier();
			nextToken();
			Tokens cur = current.getToken();
			if( cur != TK_OPEN_PAREN )
				return new ASTVariable(ident);
			else{
				nextToken();
				ArrayList<ASTExpression> args = new ArrayList<>();
				if(current.getToken() != TK_CLOSED_PAREN){
					while(true){
						ASTExpression arg = parseExpression();
						args.add(arg);
						
						if(current.getToken() == TK_CLOSED_PAREN) break;
						if(current.getToken() != TK_COMMA){
							Errors.Error("')' or ',' expected in arguments list.");
							return null;
						}
						nextToken();
					}
				}
				return new ASTCall(ident,args);
			}
			
		}else{
			Errors.Error("Identifier expected.");
			return null;
		}
	}

	public ASTExpression parsePrimary(){
		switch(current.getToken()){
			case TK_IDENTIFIER:
				return parseIdentifierExp();
			case TK_NUMBER:
				return parseNumExp();
			case TK_OPEN_PAREN:
				return parseParenExp();
			default:
				Errors.Error("Unknown token when expecting an expression");
				return null;
		}
	}

	public int getOpPrecedence(){
		int prec = binop.get(current.getToken());
		if(prec <= 0) return 0;
		return prec;
	}

	public ASTExpression parseExpression(){
		ASTExpression left = parsePrimary();
		if( left == null ) return null;

		return parseBinRight(0,left);
	}
	public ASTExpression parseBinRight(int prec, ASTExpression left){
		while(true){
			int tokprec = getOpPrecedence();

			if(tokprec < prec)
				return left;
			Tokens BinOp = current.getToken();
			if( ! ( binop_char.containsKey(BinOp) ) ){
				Errors.Error("Invalid operator: "+BinOp.toString());
				return null;
			}
			nextToken();
			
			ASTExpression right = parsePrimary();
			if(right == null) return null;

			int nextprec = getOpPrecedence();
			if(tokprec < nextprec){
				right = parseBinRight(tokprec + 1, right);
				if(right == null) return null;
			}
			left = new ASTBinary(binop_char.get(BinOp), left, right);
		}
	}

	public ASTPrototype parsePrototype(){
		if(current instanceof Identifier){
			String name = ( (Identifier)current ).getIdentifier();
			nextToken();

			if(current.getToken() != TK_OPEN_PAREN){
				Errors.ErrorProto("Expected '(' in prototype");
				return null;
			}
			
			ArrayList<String> args = new ArrayList<>();
			while(nextToken() instanceof Identifier){
				args.add( ( (Identifier)current ).getIdentifier() );
			}
			if(current.getToken() != TK_CLOSED_PAREN){
				Errors.ErrorProto("')' expected in prototype");
				return null;
			}
			nextToken();

			return new ASTPrototype(name, args);
		}else{
			Errors.ErrorProto("Expected identifier as procedure name in prototype");
			return null;
		}
	}
	public ASTProcedure parseProcedure(){
		nextToken();
		ASTPrototype proto = parsePrototype();
		if(proto == null) return null;
		ASTExpression e = parseExpression();
		if(e != null) return new ASTProcedure(proto, e);
		return null;
	}
	public ASTPrototype parseExtern(){
		nextToken();
		return parsePrototype();
	}
	public ASTProcedure parseTopLevel(){
		ASTExpression exp = parseExpression();
		if(exp != null){
			ASTPrototype blank = new ASTPrototype("", new ArrayList<String>());
			return new ASTProcedure(blank, exp);
		}
		return null;
	}
	
	private ParseHandler ph = new ParseHandler(){
		@Override
		public void handleProcedure(){
			System.out.println("Procedure parsed.");
		}
		public void handleExtern(){
			System.out.println("Extern parsed.");
		}
		public void handleTopLevel(){
			System.out.println("Top-level parsed.");
		}
	};
	public ParseHandler getParseHandler(){
		return ph;
	}

	public void parse(){
		nextToken(); //Start with a token.
		switch(current.getToken()){
			case TK_PROCEDURE: ph.handleProcedure(); break;
			case TK_EXTERN: ph.handleExtern(); break;
			default: ph.handleTopLevel(); break;
		}
	}

	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		String line;
		while(true){
			System.out.print("Ready> ");
			line = sc.nextLine();
			if(line.equals("exit")) break;
			else{
				Parser parser = new Parser(line);
				parser.parse();
			}
		}
	}
}
