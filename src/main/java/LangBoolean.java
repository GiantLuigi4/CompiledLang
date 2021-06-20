public class LangBoolean extends LangClass {
	public LangBoolean() {
		super("ÿboolean".getBytes());
	}
	
	public boolean isInstance(Object o) {
		return o instanceof Boolean;
	}
}
