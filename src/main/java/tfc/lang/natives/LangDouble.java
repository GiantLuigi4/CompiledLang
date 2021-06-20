package tfc.lang.natives;

import tfc.lang.LangClass;

public class LangDouble extends LangClass {
	public LangDouble() {
		super("ÿdouble".getBytes());
	}
	
	public boolean isInstance(Object o) {
		return o instanceof Double;
	}
}
