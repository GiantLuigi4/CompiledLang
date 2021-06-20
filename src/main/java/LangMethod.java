import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class LangMethod {
	private final Instruction[] instructions;
	private final String name, descriptor;
	private boolean isStatic, isPublic;
	protected LangClass clazz;
	
	public LangMethod(String name, String descriptor, byte[] bytes) {
		this.name = name;
		this.descriptor = descriptor;
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		ArrayList<Instruction> insns = new ArrayList<>();
		String temp = "";
		byte tempB = 0;
		boolean isFirst = false;
		for (byte b : bytes) {
			if (tempB != 0) {
				if (Executor.opcodeExists(b)) {
					if (b == -3) {
						temp = new String(stream.toByteArray());
						isFirst = false;
						stream.reset();
					} else {
						if (isFirst) insns.add(new Instruction(tempB, new String(stream.toByteArray()), null));
						else insns.add(new Instruction(tempB, temp, new String(stream.toByteArray())));
						stream.reset();
						tempB = 0;
					}
				} else {
					stream.write(b);
					continue;
				}
			}
			if (b == -4 || b == -7 || b == -8 || b == -14 || b == -15 || b == -16 || b == -17 || b == -18 || b == -19 || b == -20 || b == -21 || b == -22) {
				tempB = b;
				isFirst = true;
//			} else if (b == -5 || b == -6 || b == -9 || b == -10 || b == -11 || b == -12 || b == -13) {
			} else if (b != -3) {
				insns.add(new Instruction(b, null, null));
			}
		}
		try {
			stream.close();
			stream.flush();
		} catch (Throwable ignored) {
		}
		isStatic = true;
		isPublic = true;
		instructions = insns.toArray(new Instruction[0]);
	}
	
	public LangMethod(String tempName, String methodDesc, byte[] toByteArray, boolean isPublic, boolean isStatic) {
		this(tempName, methodDesc, toByteArray);
		this.isStatic = isStatic;
		this.isPublic = isPublic;
	}
	
	public String toString(boolean bl) {
		if (bl) return toString();
		return "LangMethod{" +
				"instructions=" + Arrays.toString(instructions) +
				", name='" + name + '\'' +
				", descriptor='" + descriptor + '\'' +
				'}';
	}
	
	@Override
	public String toString() {
		ByteArrayOutputStream builder = new ByteArrayOutputStream();
		try {
			for (Instruction instruction : instructions) {
				builder.write(instruction.id);
				if (instruction.ainfo0 != null) builder.write(instruction.ainfo0.getBytes());
				if (instruction.ainfo1 != null) builder.write(instruction.ainfo1.getBytes());
			}
		} catch (Throwable ignored) {
		}
		return builder.toString();
	}
	
	public Object run(LocalCapture locals) {
		ArrayList<Object> stack = new ArrayList<>();
		HashMap<Integer, Integer> labelStackStates = new HashMap<>();
		HashMap<Integer, Integer> labelLabelStates = new HashMap<>();
		HashMap<Integer, Integer> labelPoints = new HashMap<>();
		ArrayList<Integer> pushPoints = new ArrayList<>();
		int toSkip = 0;
		for (int i = 0; i < instructions.length; i++) {
			Instruction instruction = instructions[i];
			switch (instruction.id) {
				case -4:
//					System.out.println(instruction.ainfo1 + " l" + instruction.ainfo0);
					switch (instruction.ainfo1) {
						case "I":
							locals.addLocal(clazz.executor.getOrLoad("int"));
							break;
						case "D":
							locals.addLocal(clazz.executor.getOrLoad("double"));
							break;
						case "L":
							locals.addLocal(clazz.executor.getOrLoad("long"));
							break;
						case "F":
							locals.addLocal(clazz.executor.getOrLoad("float"));
							break;
						default:
							locals.addLocal(clazz.executor.getOrLoad(instruction.ainfo1));
							break;
					}
					break;
				case -5:
					pushPoints.add(stack.size());
					break;
				case -6:
					int num = pushPoints.remove(pushPoints.size() - 1);
					while (stack.size() != num) stack.remove(stack.size() - 1);
					break;
				case -11:
					locals.push();
					break;
				case -12:
					locals.pop();
					break;
				case -7:
					stack.add(Integer.parseInt(instruction.ainfo0));
					break;
				case -8:
					locals.setLocal(Integer.parseInt(instruction.ainfo0), stack.get(stack.size() - 1));
					break;
				case -9:
					return null;
				case -13:
					return stack.get(stack.size() - 1);
				case -14:
					stack.add(locals.getLocal(Integer.parseInt(instruction.ainfo0)));
					break;
				case -15:
					Object lastStackField = stack.get(stack.size() - 1);
					int localId = Integer.parseInt(instruction.ainfo0);
					Object local = locals.getLocal(localId);
					Object out = null;
					if (lastStackField instanceof Integer) {
						if (local instanceof Integer) out = (int) local + (int) lastStackField;
						else if (local instanceof Float) out = (float) local + (int) lastStackField;
						else if (local instanceof Double) out = (double) local + (int) lastStackField;
						else if (local instanceof Long) out = (long) local + (int) lastStackField;
						else if (local instanceof Short) out = (short) local + (int) lastStackField;
					}
//					System.out.println("l"+localId  + " = " + local + " + " + lastStackField + " = " + out);
					locals.setLocal(localId, out);
					break;
				case -16:
					LangClass clazz = this.clazz;
					String methodName = instruction.ainfo0;
					if (methodName.contains(".")) {
						String className = methodName.substring(0, methodName.lastIndexOf("."));
						methodName = methodName.substring(methodName.lastIndexOf(".") + 1);
						clazz = clazz.executor.getOrLoad(className);
					}
					if (instruction.ainfo1.endsWith("V"))
						clazz.runMethod(methodName, instruction.ainfo1, stack.toArray(new Object[0]));
					else stack.add(clazz.runMethod(methodName, instruction.ainfo1, stack.toArray(new Object[0])));
					break;
				case -17:
					clazz = this.clazz;
					String fieldName = instruction.ainfo0;
					if (fieldName.contains(".")) {
						String className = fieldName.substring(0, fieldName.lastIndexOf("."));
						fieldName = fieldName.substring(fieldName.lastIndexOf(".") + 1);
						clazz = clazz.executor.getOrLoad(className);
					}
					stack.add(clazz.staticFields.get(fieldName));
					break;
				case -18:
					clazz = this.clazz;
					fieldName = instruction.ainfo0;
					if (fieldName.contains(".")) {
						String className = fieldName.substring(0, fieldName.lastIndexOf("."));
						fieldName = fieldName.substring(fieldName.lastIndexOf(".") + 1);
						clazz = clazz.executor.getOrLoad(className);
					}
					clazz.staticFields.replace(fieldName, stack.get(stack.size() - 1));
					stack.add(clazz.staticFields.get(fieldName));
					break;
				case -19:
					boolean passes = false;
					lastStackField = stack.get(stack.size() - 2);
					local = stack.get(stack.size() - 1);
//					if (local instanceof Integer) local = (int) local - 1;
//					else if (local instanceof Float) local = (float) local - 1;
//					else if (local instanceof Double) local = (double) local - 1;
//					else if (local instanceof Long) local = (long) local - 1;
//					else if (local instanceof Byte) local = (byte) ((byte) local - 1);
//					else if (local instanceof Short) local = (short) ((short) local - 1);
					switch (instruction.ainfo0) {
						case "0":
							if (lastStackField instanceof Integer) {
								if (local instanceof Integer) passes = (int) local <= (int) lastStackField;
								else if (local instanceof Float) passes = (float) local <= (int) lastStackField;
								else if (local instanceof Double) passes = (double) local <= (int) lastStackField;
								else if (local instanceof Long) passes = (long) local <= (int) lastStackField;
								else if (local instanceof Byte) passes = (byte) local <= (int) lastStackField;
								else if (local instanceof Short) passes = (short) local <= (int) lastStackField;
							}
							break;
						case "1":
							if (lastStackField instanceof Integer) {
								if (local instanceof Integer) passes = (int) local < (int) lastStackField;
								else if (local instanceof Float) passes = (float) local < (int) lastStackField;
								else if (local instanceof Double) passes = (double) local < (int) lastStackField;
								else if (local instanceof Long) passes = (long) local < (int) lastStackField;
								else if (local instanceof Byte) passes = (byte) local < (int) lastStackField;
								else if (local instanceof Short) passes = (short) local < (int) lastStackField;
							}
							break;
						case "2":
							if (lastStackField instanceof Integer) {
								if (local instanceof Integer) passes = (int) local == (int) lastStackField;
								else if (local instanceof Float) passes = (float) local == (int) lastStackField;
								else if (local instanceof Double) passes = (double) local == (int) lastStackField;
								else if (local instanceof Long) passes = (long) local == (int) lastStackField;
								else if (local instanceof Byte) passes = (byte) local == (int) lastStackField;
								else if (local instanceof Short) passes = (short) local == (int) lastStackField;
							}
							break;
						case "3":
							if (lastStackField instanceof Integer) {
								if (local instanceof Integer) passes = (int) local > (int) lastStackField;
								else if (local instanceof Float) passes = (float) local > (int) lastStackField;
								else if (local instanceof Double) passes = (double) local > (int) lastStackField;
								else if (local instanceof Long) passes = (long) local > (int) lastStackField;
								else if (local instanceof Byte) passes = (byte) local > (int) lastStackField;
								else if (local instanceof Short) passes = (short) local > (int) lastStackField;
							}
							break;
						case "4":
							if (lastStackField instanceof Integer) {
								if (local instanceof Integer) passes = (int) local >= (int) lastStackField;
								else if (local instanceof Float) passes = (float) local >= (int) lastStackField;
								else if (local instanceof Double) passes = (double) local >= (int) lastStackField;
								else if (local instanceof Long) passes = (long) local >= (int) lastStackField;
								else if (local instanceof Byte) passes = (byte) local >= (int) lastStackField;
								else if (local instanceof Short) passes = (short) local >= (int) lastStackField;
							}
							break;
					}
					stack.add(passes);
					break;
				case -20:
					Object o = stack.get(stack.size() - 1);
					if (o instanceof Boolean) {
						if ((boolean) o) i += Integer.parseInt(instruction.ainfo0);
					} else throw new RuntimeException("Expected boolean ontop of stack but got " + o + " instead");
					break;
				case -21:
					int label = Integer.parseInt(instruction.ainfo0);
					labelLabelStates.put(label, labelLabelStates.size());
					labelStackStates.put(label, pushPoints.size());
					labelPoints.put(label, i);
					// TODO: label local states
					break;
				case -22:
					o = stack.get(stack.size() - 1);
					if (o instanceof Boolean) {
						if (!(boolean) o) break;
					}
					label = Integer.parseInt(instruction.ainfo0);
					int num1 = labelStackStates.get(label);
					int point = labelPoints.get(label);
					num = pushPoints.get(num1);
					while (stack.size() != num) stack.remove(stack.size() - 1);
					while (pushPoints.size() != num1) pushPoints.remove(pushPoints.size() - 1);
					while (labelStackStates.size() > label + 1) labelStackStates.remove(labelStackStates.size() - 1);
					while (labelLabelStates.size() > label + 1) labelLabelStates.remove(labelLabelStates.size() - 1);
					while (labelPoints.size() > label + 1) labelPoints.remove(labelPoints.size() - 1);
					i = point;
					break;
				/*
				 * 0 <=
				 * 1 <
				 * 2 =
				 * 3 >
				 * 4 >=
				 * @param name
				 * @param descriptor
				 * @param bytes
				 */
				case -10:
					throw new RuntimeException("Method ended with no return statement");
				default:
					throw new RuntimeException("Invalid Opcode " + instruction.id);
			}
		}
		throw new RuntimeException("Method ended with no return statement");
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescriptor() {
		return descriptor;
	}
}
