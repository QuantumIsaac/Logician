package logician.parser.ast;

public class ASTProcedure implements ASTExpression{
	private ASTPrototype proto;
	private ASTExpression code;
	public ASTProcedure(ASTPrototype proto, ASTExpression code){
		this.proto = proto;
		this.code = code;
	}
	public ASTPrototype getPrototype(){
		return proto;
	}
	public ASTExpression getCode(){
		return code;
	}
}
