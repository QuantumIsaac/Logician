package logician.parser.ast;

public class ASTNumber implements ASTExpression{
	private double value;
	public ASTNumber( double val ){
		value = val;
	}
	public double getValue(){
		return value;
	}
}
