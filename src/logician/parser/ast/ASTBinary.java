package logician.parser.ast;

public class ASTBinary implements ASTExpression{
	private char op;
	private ASTExpression left;
	private ASTExpression right;
	public ASTBinary(char op, ASTExpression left, ASTExpression right){
		this.op = op;
		this.left = left;
		this.right = right;
	}
	public char getOp(){
		return op;
	}
	public ASTExpression getLeftExpression(){
		return left;
	}
	public ASTExpression getRightExpression(){
		return right;
	}
}
