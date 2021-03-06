package logician.lexer;

public class LexerUtil {
	public static final String alpha = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final String numeric = "1234567890";
	public static final String whitespace = " \t";
	public static final String endline = "\n\r";
	public static boolean isAlpha(char c){
		String cAsStr = Character.toString(c);
		if(alpha.contains(cAsStr))
			return true;
		return false;
	}
	public static boolean isNumeric(char c){
		String cAsStr = Character.toString(c);		
		if(numeric.contains(cAsStr))
			return true;
		return false;
	}
	public static boolean isAlNum(char c){
		if(isAlpha(c) || isNumeric(c))
			return true;
		return false;
	}
	public static boolean isWhitespace(char c){
		String cAsStr = Character.toString(c);		
		if(whitespace.contains(cAsStr))
			return true;
		return false;
	}
	public static boolean isEndLine(char c){
		String cAsStr = Character.toString(c);
		if(whitespace.contains(cAsStr))
			return true;
		return false;
	}
}
