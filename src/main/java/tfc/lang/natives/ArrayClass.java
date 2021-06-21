package tfc.lang.natives;

import tfc.lang.Executor;
import tfc.lang.LangClass;

public class ArrayClass extends LangClass {
	@SuppressWarnings("FieldCanBeLocal")
	private final LangClass heldClass;
	
	public ArrayClass(LangClass heldClass, Executor executor) {
		super(("ÿ[" + heldClass.getName()).getBytes(), executor);
		this.heldClass = heldClass;
	}
	
	@Override
	public boolean isInstance(Object o) {
		return o instanceof Object[];
	}
}
