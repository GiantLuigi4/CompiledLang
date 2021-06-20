import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class LangClass {
	private final String name;
	private final LangMethod[] methods;
	protected Executor executor;
	protected HashMap<String, Object> staticFields = new HashMap<>();
	
	public LangClass(byte[] bytes) {
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
				if (b == -1 || b == -2 || b == -3 || b == -10 || b == -17) {
					if (b == -3) {
						temp = new String(stream.toByteArray());
						isFirst = false;
						stream.reset();
					} else {
						if (isFirst) {
							if (tempB == -1) name = stream.toString();
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
			if (b == -1 || b == -2 || b == -17) {
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
					staticFields.put(tempName, null);
					fieldDesc = null;
				}
				continue;
			}
		}
		this.name = name;
		this.methods = methods.toArray(new LangMethod[0]);
		for (LangMethod method : methods) {
			if (method.getName().equals("<sinit>"))
				method.run(new LocalCapture());
		}
	}
	
	public Object runMethod(String name, String descriptor) {
		for (LangMethod method : methods) {
			if (method.getName().equals(name) && method.getDescriptor().equals(descriptor)) {
				return method.run(new LocalCapture());
			}
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
					else capture.addLocal(null);
					capture.setLocal(i, args[i]);
				}
				return method.run(capture);
			}
		}
		throw new RuntimeException("Method " + name + descriptor + " not found");
	}
	
	public boolean isInstance(Object o) {
		// TODO
		return false;
	}
	
	public String getName() {
		return name;
	}
}
