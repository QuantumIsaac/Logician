package logician.parser.ast;

import java.util.ArrayList;

public class ASTCall implements ASTExpression {
	private String func;
	private ArrayList<ASTExpression> args;
	public ASTCall(String func, ArrayList<ASTExpression> args){
		this.func = func;
		this.args = args;
	}
	public String getFunction(){
		return func;
	}
	public ArrayList<ASTExpression> getArgs(){
		return args;
	}
}
