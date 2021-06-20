package compiling.tokens;

public class LiteralToken implements IToken {
	private final String text;
	
	public LiteralToken(String text) {
		this.text = text;
	}
	
	@Override
	public String getText() {
		return text;
	}
	
	@Override
	public String toString() {
		return getText();
	}
}
