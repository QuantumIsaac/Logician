package logician.lexer;

public class Token{
	private Tokens tk;
	public Token( Tokens tk){
		this.tk = tk;
	}
	public void setToken(Tokens tk){
		this.tk = tk;
	}
	public Tokens getToken(){
		return tk;
	}
}
