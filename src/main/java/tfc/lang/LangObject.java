package tfc.lang;

import java.util.HashMap;

public class LangObject {
	public final HashMap<String, Object> instanceFields = new HashMap<>();
	public final LangClass clazz;
	
	public LangObject(LangClass clazz) {
		this.clazz = clazz;
	}
	
	public Object invoke(String name, String descriptor, Object... args) {
		for (LangMethod method : clazz.methods) {
			if (method.getName().equals(name) && method.getDescriptor().equals(descriptor)) {
				LocalCapture capture = new LocalCapture();
				int i;
				for (i = 0; i < args.length; i++) {
					if (args[i] instanceof Integer)
						capture.addLocal(clazz.executor.get("int"));
					else if (args[i] instanceof Float)
						capture.addLocal(clazz.executor.get("float"));
					else if (args[i] instanceof Double)
						capture.addLocal(clazz.executor.get("double"));
					else if (args[i] instanceof Long)
						capture.addLocal(clazz.executor.get("long"));
					else if (args[i] instanceof Boolean)
						capture.addLocal(clazz.executor.get("boolean"));
					else capture.addLocal(clazz.executor.getClassFor(args[i]));
					capture.setLocal(i, args[i]);
				}
				capture.addLocal(clazz);
				capture.setLocal(i, this);
				capture.contextThis = this;
				return method.run(capture);
			}
		}
		throw new RuntimeException("Method " + name + descriptor + " not found");
	}
}
