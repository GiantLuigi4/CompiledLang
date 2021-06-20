package tfc.lang.natives;

import tfc.lang.LangClass;

public class LangBoolean extends LangClass {
	public LangBoolean() {
		super("ÿboolean".getBytes());
	}
	
	public boolean isInstance(Object o) {
		return o instanceof Boolean;
	}
}
