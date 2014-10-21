package logician.parser.ast;

public class ASTVariable implements ASTExpression {
	private String name;
	public ASTVariable(String name){
		this.name = name;
	}
	public String getName(){
		return name;
	}
}
