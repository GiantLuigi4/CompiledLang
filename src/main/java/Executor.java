import java.io.File;
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
	 * -7 == load integer (loadi)
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
	 * bgoto [label name] true
	 * bgoto [instruction number] false
	 * instruction number is a lot more of a paint to use
	 * <p>
	 * no, the doubled -17 is not a type
	 * yes, I thought it was a typo myself
	 */
	protected static final byte[] opcodeBytes = new byte[]{
			-1, -2, -3, -4, -5, -6, -7, -8, -9, -10, -11, -12, -13, -14, -15, -16, -17, -18, -19, -20, -21, -22
	};
	
	public static final String langExtension = "langclass";
	
	private final HashMap<String, LangClass> classes = new HashMap<>();
	
	public String classPath = "";
	
	public Executor(int stackSize) {
		classes.put("int", new LangInteger());
		classes.put("float", new LangFloat());
		classes.put("boolean", new LangBoolean());
	}
	
	public LangClass load(byte[] bytes) {
		LangClass clazz = new LangClass(bytes);
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
		if (classes.containsKey(className)) return get(className);
		return load(className);
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
		return classes.get(className);
	}
}
