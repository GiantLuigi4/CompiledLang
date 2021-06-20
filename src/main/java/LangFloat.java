public class LangFloat extends LangClass {
	public LangFloat() {
		super("ÿfloat".getBytes());
	}
	
	public boolean isInstance(Object o) {
		return o instanceof Float;
	}
}
