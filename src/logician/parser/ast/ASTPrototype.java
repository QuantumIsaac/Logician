package logician.parser.ast;

import java.util.ArrayList;

public class ASTPrototype implements ASTExpression { //Pretty much the same as ASTCall, but needs to be separate.
	private String func;
	private ArrayList<String> args;
	public ASTPrototype(String func, ArrayList<String> args){
		this.func = func;
		this.args = args;
	}
	public String getFunction(){
		return func;
	}
	public ArrayList<String> getArgs(){
		return args;
	}
}
