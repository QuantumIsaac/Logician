package logician.lexer;

public class LexerNumber extends Token{
	private double numval;	
	public LexerNumber(double numval){
		super(Tokens.TK_NUMBER);
		this.numval = numval;
	}
	public double getNumVal(){
		return numval;
	}
}
