package tfc.lang;

import tfc.lang.natives.*;

import java.io.FileInputStream;
import java.util.HashMap;

public class Executor {
	/**
	 * -1 == define class
	 * -2 == define method
	 * -3 == descriptor
	 * -4 == define local (local)
	 * -5 == push
	 * -6 == pop
	 * -7 == load constant (loadc)
	 * -8 == set local (setl)
	 * -9 == return void (returnV)
	 * -10 == end method
	 * -11 == push locals (pushl)
	 * -12 == pop locals (popl)
	 * -13 == return
	 * -14 == load local onto stack (loadl)
	 * -15 == add
	 * -16 == invoke static (istatic)
	 * -17 == define field (field)
	 * -17 == load static field (loadsf)
	 * -18 == set static field (setsf)
	 * -19 == operator
	 * -20 == offset if (boffset)
	 * -21 == mark label
	 * -22 == goto label or instruction under condition (bgoto)
	 * <p>
	 * bgoto [label name] true
	 * bgoto [instruction number] false
	 * instruction number is a lot more of a pain to use and implement
	 * TODO: instruction number goto
	 * <p>
	 * -23 == make array with a size equal to the last stack element (marray)
	 * -24 == sets the element of index equal to the second to last element of the stack of the array in the third to last element of the stack to the value in the last element of the stack (aset)
	 * <p>
	 * stack[stack.length - 3][stack[stack.length - 2] = stack[stack.length - 1]
	 * <p>
	 * -25 == gets the length of an array or the element of an array with an index equal to the top of the stack (aget)
	 * <p>
	 * if stack[stack.length - 2] == -1, add stack[stack.length - 2].length to the top of the stack
	 * else add stack[stack.length - 2][stack[stack.length - 1]] to the top of the stack
	 * InsnToBytes class also offers alen which allows you to represent the means of getting the length of an array with two lines
	 * <p>
	 * -26 == load field (loadf)
	 * -27 == instance
	 * -28 == set field (setf)
	 * -29 == extends
	 * -30 == invoke
	 * <p>
	 * no, the doubled -17 is not a typo
	 * yes, I thought it was a typo myself
	 */
	protected static final byte[] opcodeBytes = new byte[]{
			-1, -2, -3, -4, -5, -6, -7, -8, -9, -10, -11,
			-12, -13, -14, -15, -16, -17, -18, -19, -20,
			-21, -22, -23, -24, -25, -26, -27, -28, -29,
			-30
	};
	
	public static final String langExtension = "langclass";
	
	private final HashMap<String, LangClass> classes = new HashMap<>();
	
	public String classPath = "";
	
	public Executor(int stackSize) {
		classes.put("long", new LangLong(this));
		classes.put("byte", new LangByte(this));
		classes.put("short", new LangShort(this));
		classes.put("int", new LangInteger(this));
		classes.put("float", new LangFloat(this));
		classes.put("double", new LangDouble(this));
		classes.put("boolean", new LangBoolean(this));
		for (LangClass value : classes.values()) value.executor = this;
	}
	
	public LangClass load(byte[] bytes) {
		LangClass clazz = new LangClass(bytes, this);
		if (classes.containsKey(clazz.getName()))
			throw new RuntimeException("Cannot load a class twice without unloading it first");
		classes.put(clazz.getName(), clazz);
		clazz.executor = this;
		return clazz;
	}
	
	public static boolean opcodeExists(byte b) {
		for (byte opcodeByte : opcodeBytes) if (opcodeByte == b) return true;
		return false;
	}
	
	public LangClass getOrLoad(String className) {
		if (className.startsWith("[")) {
			ArrayClass arrayClass = new ArrayClass(getOrLoad(className.substring(1)), this);
			arrayClass.executor = this;
			return arrayClass;
		}
		switch (className) {
			case "I":
				return get("int");
			case "D":
				return get("double");
			case "L":
				return get("long");
			case "B":
				return get("boolean");
			case "K":
				return get("byte");
			case "F":
				return get("float");
			case "S":
				return get("short");
			default:
				if (classes.containsKey(className)) return get(className);
				return load(className);
		}
	}
	
	public LangClass getClassFor(Object o) {
		if (o instanceof Integer) return get("int");
		else if (o instanceof Double) return get("double");
		else if (o instanceof Long) return get("long");
		else if (o instanceof Float) return get("float");
		else if (o instanceof Boolean) return get("boolean");
		else if (o instanceof Short) return get("short");
		else if (o instanceof Byte) return get("byte");
		else if (o instanceof LangObject) return ((LangObject) o).clazz;
		return null; // TODO
	}
	
	public LangClass load(String className) {
		String path = classPath + className;
		try {
			byte[] bytes;
			{
				FileInputStream stream = new FileInputStream(path + "." + langExtension);
				bytes = new byte[stream.available()];
				stream.read(bytes);
				stream.close();
			}
			return load(bytes);
		} catch (Throwable err) {
			if (err instanceof RuntimeException) throw (RuntimeException) err;
			else throw new RuntimeException(err);
		}
	}
	
	public LangClass get(String className) {
		if (className.startsWith("[")) {
			ArrayClass arrayClass = new ArrayClass(get(className.substring(1)), this);
			arrayClass.executor = this;
			return arrayClass;
		}
		switch (className) {
			case "I":
				return get("int");
			case "D":
				return get("double");
			case "L":
				return get("long");
			case "B":
				return get("boolean");
			case "K":
				return get("byte");
			case "F":
				return get("float");
			case "S":
				return get("short");
			default:
				return classes.get(className);
		}
	}
}
