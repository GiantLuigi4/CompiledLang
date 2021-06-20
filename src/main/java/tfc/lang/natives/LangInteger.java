package tfc.lang.natives;

import tfc.lang.LangClass;

public class LangInteger extends LangClass {
	public LangInteger() {
		super("ÿint".getBytes());
	}
	
	public boolean isInstance(Object o) {
		return o instanceof Integer;
	}
}