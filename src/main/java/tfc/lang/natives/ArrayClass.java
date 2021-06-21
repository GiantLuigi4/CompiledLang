package tfc.lang.natives;

import tfc.lang.LangClass;

public class ArrayClass extends LangClass {
	@SuppressWarnings("FieldCanBeLocal")
	private final LangClass heldClass;
	
	public ArrayClass(LangClass heldClass) {
		super(("ÿ[" + heldClass.getName()).getBytes());
		staticFields.put("array", new Object[0]);
		this.heldClass = heldClass;
	}
	
	@Override
	public boolean isInstance(Object o) {
		return o instanceof Object[];
	}
}
