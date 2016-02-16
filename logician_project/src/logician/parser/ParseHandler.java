package logician.parser;

public interface ParseHandler{
	public void handleProcedure();
	public void handleExtern();
	public void handleTopLevel();
}
