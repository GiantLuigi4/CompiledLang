package tfc.lang.natives;

import tfc.lang.Executor;
import tfc.lang.LangClass;

public class LangBoolean extends LangClass {
	public LangBoolean(Executor executor) {
		super("ÿboolean".getBytes(), executor);
	}
	
	public boolean isInstance(Object o) {
		return o instanceof Boolean;
	}
}
