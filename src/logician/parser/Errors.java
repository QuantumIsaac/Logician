package logician.parser;

public class Errors {
	public static void Error(String msg){
		System.err.println("Error: "+msg);
	}
	public static void ErrorProto(String msg){
		Error(msg);
	}
	public static void ErrorFunction(String msg){
		Error(msg);
	}
}
