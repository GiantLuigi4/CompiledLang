package tfc.lang.natives;

import tfc.lang.LangClass;

public class LangFloat extends LangClass {
	public LangFloat() {
		super("ÿfloat".getBytes());
	}
	
	public boolean isInstance(Object o) {
		return o instanceof Float;
	}
}
