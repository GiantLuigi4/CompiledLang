package compiling.tokens.types;

public class KeywordToken implements IToken {
	public String name;
	
	public KeywordToken(String name) {
		this.name = name;
	}
	
	@Override
	public String getText() {
		return name;
	}
	
	@Override
	public String toString() {
		return getText();
	}
}
