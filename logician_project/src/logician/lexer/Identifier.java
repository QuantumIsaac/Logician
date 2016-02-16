package logician.lexer;

public class Identifier extends Token{
	private String identifier;
	public Identifier(String ident){
		super(Tokens.TK_IDENTIFIER);
		identifier = ident;
	}
	public String getIdentifier(){
		return identifier;
	}
	public void setIdentifier(String id){
		identifier = id;
	}
}
