package compiling.tokens.types;

public class AccessToken implements IToken {
	private final String name;
	
	public AccessToken(String name) {
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
