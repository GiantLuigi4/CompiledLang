package tfc.lang;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class LangClass {
	protected final String name;
	protected final LangMethod[] methods;
	protected Executor executor;
	protected HashMap<String, Object> staticFields = new HashMap<>();
	protected HashMap<String, Object> instanceFields = new HashMap<>();
	protected ArrayList<LangClass> inheritance = new ArrayList<>();
	
	public LangClass(byte[] bytes, Executor executor) {
		this.executor = executor;
		String name = null;
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		String temp = "";
		byte tempB = 0;
		boolean isFirst = false;
		ArrayList<LangMethod> methods = new ArrayList<>();
		String tempName = null;
		String methodDesc = null;
		String fieldDesc = null;
		for (byte b : bytes) {
			if (tempB != 0) {
				if (b == -1 || b == -2 || b == -3 || b == -10 || b == -17 || b == -29) {
					if (b == -3) {
						temp = new String(stream.toByteArray());
						isFirst = false;
						stream.reset();
					} else {
						if (isFirst) {
							if (tempB == -1) name = stream.toString();
							else if (tempB == -29) {
								String className = stream.toString();
								if (!className.equals("null")) inheritance.add(executor.getOrLoad(className));
								else inheritance.add(null);
							}
						} else {
							if (tempB == -2) {
								tempName = temp;
								methodDesc = stream.toString();
							} else if (tempB == -17) {
								tempName = temp;
								fieldDesc = stream.toString();
							}
						}
						stream.reset();
						tempB = 0;
					}
				} else {
					stream.write(b);
					continue;
				}
			} else if (methodDesc != null) {
				if (b == -10) {
					boolean isPublic = methodDesc.startsWith("P");
					if (isPublic) methodDesc = methodDesc.substring(1);
					boolean isStatic = methodDesc.startsWith("S");
					if (isStatic) methodDesc = methodDesc.substring(1);
					LangMethod method = new LangMethod(tempName, methodDesc, stream.toByteArray(), isPublic, isStatic);
					methods.add(method);
					method.clazz = this;
					methodDesc = null;
				}
				stream.write(b);
				continue;
			}
			if (b == -1 || b == -2 || b == -17 || b == -29) {
				tempB = b;
				isFirst = true;
				stream.reset();
				continue;
			}
			if (fieldDesc != null) {
				if (b == -10) {
					boolean isPublic = fieldDesc.startsWith("P");
					if (isPublic) fieldDesc = fieldDesc.substring(1);
					boolean isStatic = fieldDesc.startsWith("S");
					if (isStatic) fieldDesc = fieldDesc.substring(1);
					fieldDesc = fieldDesc.substring(1);
					if (isStatic) staticFields.put(tempName, null);
					else instanceFields.put(tempName, null);
					fieldDesc = null;
				}
				continue;
			}
		}
		if (inheritance.isEmpty()) {
			try {
				inheritance.add(executor.getOrLoad("tupul.lang.Object"));
			} catch (Throwable ignored) {
			}
		}
		this.name = name;
		this.methods = methods.toArray(new LangMethod[0]);
		for (LangMethod method : methods) {
			if (method.getName().equals("<sinit>"))
				method.run(new LocalCapture());
		}
	}
	
	public void populateFields(HashMap<String, Object> fields, boolean instance) {
		if (instance) instanceFields.forEach(fields::put);
		else staticFields.forEach(fields::put);
		for (LangClass langClass : inheritance) langClass.populateFields(fields, instance);
	}
	
	public Object getStatic(String name) {
		if (staticFields.containsKey(name)) return staticFields.get(name);
		else for (LangClass langClass : inheritance)
			if (langClass.hasStatic(name))
				return langClass.getStatic(name);
		throw new RuntimeException("The requested field does not exist");
	}
	
	public void setStatic(String name, Object object) {
		if (staticFields.containsKey(name)) {
			staticFields.replace(name, object);
			return;
		}
		else
			for (LangClass langClass : inheritance)
				if (langClass.hasStatic(name)) {
					langClass.setStatic(name, object);
					return;
				}
		throw new RuntimeException("The requested field does not exist");
	}
	
	public boolean hasStatic(String name) {
		if (staticFields.containsKey(name)) return true;
		for (LangClass langClass : inheritance) if (langClass.hasStatic(name)) return true;
		return false;
	}
	
	public LangObject newInstance(String desc, Object... args) {
		LangObject object = new LangObject(this);
		populateFields(object.instanceFields, true);
		for (LangMethod method : methods) {
			if (method.getName().equals("<init>") && method.getDescriptor().equals(desc)) {
				LocalCapture capture = new LocalCapture();
				int i;
				for (i = 0; i < args.length; i++) {
					if (args[i] instanceof Integer)
						capture.addLocal(executor.get("int"));
					else if (args[i] instanceof Float)
						capture.addLocal(executor.get("float"));
					else if (args[i] instanceof Double)
						capture.addLocal(executor.get("double"));
					else if (args[i] instanceof Long)
						capture.addLocal(executor.get("long"));
					else if (args[i] instanceof Boolean)
						capture.addLocal(executor.get("boolean"));
					else capture.addLocal(executor.getClassFor(args[i]));
					capture.setLocal(i, args[i]);
				}
				capture.addLocal(this);
				capture.setLocal(i, object);
				capture.contextThis = object;
				return (LangObject) method.run(capture);
			}
		}
		throw new RuntimeException("Could not find an init method matching " + desc);
	}
	
	public boolean hasMethod(String name, String descriptor) {
		for (LangMethod method : methods) {
			if (method.getName().equals(name) && method.getDescriptor().equals(descriptor))
				return true;
		}
		for (LangClass langClass : inheritance) {
			if (langClass.hasMethod(name, descriptor))
				return true;
		}
		return false;
	}
	
	public Object runMethod(String name, String descriptor) {
		for (LangMethod method : methods) {
			if (method.getName().equals(name) && method.getDescriptor().equals(descriptor)) {
				return method.run(new LocalCapture());
			}
		}
		for (LangClass langClass : inheritance) {
			if (langClass.hasMethod(name, descriptor))
				langClass.runMethod(name, descriptor);
		}
		throw new RuntimeException("Method " + name + descriptor + " not found");
	}
	
	public Object runMethod(String name, String descriptor, Object... args) {
		for (LangMethod method : methods) {
			if (method.getName().equals(name) && method.getDescriptor().equals(descriptor)) {
				LocalCapture capture = new LocalCapture();
				for (int i = 0; i < args.length; i++) {
					if (args[i] instanceof Integer)
						capture.addLocal(executor.get("int"));
					else if (args[i] instanceof Float)
						capture.addLocal(executor.get("float"));
					else if (args[i] instanceof Double)
						capture.addLocal(executor.get("double"));
					else if (args[i] instanceof Long)
						capture.addLocal(executor.get("long"));
					else if (args[i] instanceof Boolean)
						capture.addLocal(executor.get("boolean"));
					else capture.addLocal(executor.getClassFor(args[i]));
					capture.setLocal(i, args[i]);
				}
				return method.run(capture);
			}
		}
		for (LangClass langClass : inheritance) {
			if (langClass.hasMethod(name, descriptor))
				return langClass.runMethod(name, descriptor, args);
		}
		throw new RuntimeException("Method " + name + descriptor + " not found");
	}
	
	public boolean isInstance(Object o) {
		if (o instanceof LangObject) {
			return ((LangObject) o).clazz.inheritsFrom(this);
		}
		return false;
	}
	
	private boolean inheritsFrom(LangClass langClass) {
		if (this.equals(langClass)) return true;
		for (LangClass aClass : inheritance)
			if (aClass.inheritsFrom(langClass))
				return true;
		return false;
	}
	
	public Object add(Object self, Object other) {
		// TODO
		return null;
	}
	
	public Object subtract(Object self, Object other) {
		// TODO
		return null;
	}
	
	public Object multiply(Object self, Object other) {
		// TODO
		return null;
	}
	
	public Object divide(Object self, Object other) {
		// TODO
		return null;
	}
	
	public Object modulus(Object self, Object other) {
		// TODO
		return null;
	}
	
	public Object lessThan(Object self, Object other) {
		// TODO
		return null;
	}
	
	public Object greaterThan(Object self, Object other) {
		// TODO
		return null;
	}
	
	public Object equalTo(Object self, Object other) {
		// TODO
		return null;
	}
	
	public Object notEqualTo(Object self, Object other) {
		// TODO
		return null;
	}
	
	public Object greaterThanOrEqual(Object self, Object other) {
		// TODO
		return null;
	}
	
	public Object lessThanOrEqual(Object self, Object other) {
		// TODO
		return null;
	}
	
	public String getName() {
		return name;
	}
}
