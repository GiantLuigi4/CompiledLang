package tfc.lang;

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
		return "tfc.lang.LangMethod{" +
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
					locals.addLocal(clazz.executor.getOrLoad(instruction.ainfo1));
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
					if (instruction.ainfo1 == null) {
						stack.add(Integer.parseInt(instruction.ainfo0));
					} else {
						switch (instruction.ainfo1) {
							case "I":
								stack.add(Integer.parseInt(instruction.ainfo0));
								break;
							case "S":
								stack.add(Short.parseShort(instruction.ainfo0));
								break;
							case "L":
								stack.add(Long.parseLong(instruction.ainfo0));
								break;
							case "F":
								stack.add(Float.parseFloat(instruction.ainfo0));
								break;
							case "D":
								stack.add(Double.parseDouble(instruction.ainfo0));
								break;
							case "B":
								stack.add(Boolean.parseBoolean(instruction.ainfo0));
								break;
							case "K":
								stack.add(Byte.parseByte(instruction.ainfo0));
								break;
						}
					}
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
					// AAAAAAAAAAAAA
					// TODO: move this to the type class, because holy lord I am not doing this like 10 times
					if (lastStackField instanceof Integer) {
						if (local instanceof Integer) out = (int) local + (int) lastStackField;
						else if (local instanceof Float) out = (float) local + (int) lastStackField;
						else if (local instanceof Double) out = (double) local + (int) lastStackField;
						else if (local instanceof Long) out = (long) local + (int) lastStackField;
						else if (local instanceof Short) out = (short) local + (int) lastStackField;
						else if (local instanceof Byte) out = (byte) local + (int) lastStackField;
					} else if (lastStackField instanceof Double) {
						if (local instanceof Integer) out = (int) local + (double) lastStackField;
						else if (local instanceof Float) out = (float) local + (double) lastStackField;
						else if (local instanceof Double) out = (double) local + (double) lastStackField;
						else if (local instanceof Long) out = (long) local + (double) lastStackField;
						else if (local instanceof Short) out = (short) local + (double) lastStackField;
						else if (local instanceof Byte) out = (byte) local + (double) lastStackField;
					} else if (lastStackField instanceof Float) {
						if (local instanceof Integer) out = (int) local + (float) lastStackField;
						else if (local instanceof Float) out = (float) local + (float) lastStackField;
						else if (local instanceof Double) out = (double) local + (float) lastStackField;
						else if (local instanceof Long) out = (long) local + (float) lastStackField;
						else if (local instanceof Short) out = (short) local + (float) lastStackField;
						else if (local instanceof Byte) out = (byte) local + (float) lastStackField;
					} else if (lastStackField instanceof Long) {
						if (local instanceof Integer) out = (int) local + (long) lastStackField;
						else if (local instanceof Float) out = (float) local + (long) lastStackField;
						else if (local instanceof Double) out = (double) local + (long) lastStackField;
						else if (local instanceof Long) out = (long) local + (long) lastStackField;
						else if (local instanceof Short) out = (short) local + (long) lastStackField;
						else if (local instanceof Byte) out = (byte) local + (long) lastStackField;
					} else if (lastStackField instanceof Byte) {
						if (local instanceof Integer) out = (int) local + (byte) lastStackField;
						else if (local instanceof Float) out = (float) local + (byte) lastStackField;
						else if (local instanceof Double) out = (double) local + (byte) lastStackField;
						else if (local instanceof Long) out = (long) local + (byte) lastStackField;
						else if (local instanceof Short) out = (short) local + (byte) lastStackField;
						else if (local instanceof Byte) out = (byte) local + (byte) lastStackField;
					} else if (lastStackField instanceof Short) {
						if (local instanceof Integer) out = (int) local + (short) lastStackField;
						else if (local instanceof Float) out = (float) local + (short) lastStackField;
						else if (local instanceof Double) out = (double) local + (short) lastStackField;
						else if (local instanceof Long) out = (long) local + (short) lastStackField;
						else if (local instanceof Short) out = (short) local + (short) lastStackField;
						else if (local instanceof Byte) out = (byte) local + (short) lastStackField;
					}
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
					local = stack.get(stack.size() - 2);
					lastStackField = stack.get(stack.size() - 1);
					if (lastStackField instanceof Integer) lastStackField = (int) lastStackField - 1;
					else if (lastStackField instanceof Float) lastStackField = (float) lastStackField - 1;
					else if (lastStackField instanceof Double) lastStackField = (double) lastStackField - 1;
					else if (lastStackField instanceof Long) lastStackField = (long) lastStackField - 1;
					else if (lastStackField instanceof Byte) lastStackField = (byte) ((byte) lastStackField - 1);
					else if (lastStackField instanceof Short) lastStackField = (short) ((short) lastStackField - 1);
					// TODO: get this to work with things that aren't integers
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
				case -23:
					o = stack.get(stack.size() - 1);
					if (!(o instanceof Integer))
						throw new RuntimeException("Expected integer while creating array, but got " + o + " instead");
					num = (int) o;
					stack.add(new Object[num]);
					break;
				case -24:
					o = stack.get(stack.size() - 3);
					if (!(o instanceof Object[])) throw new RuntimeException("Expected array as the third to last element of the stack while setting the value of an array");
					Object[] array = (Object[]) o;
					o = stack.get(stack.size() - 2);
					if (!(o instanceof Integer)) throw new RuntimeException("Expected integer as the second to last element of the stack while setting the value of an array");
					num = (int) o;
					o = stack.get(stack.size() - 1);
					array[num] = o;
					break;
				case -25:
					o = stack.get(stack.size() - 2);
					if (!(o instanceof Object[])) throw new RuntimeException("Expected array as the second to last element of the stack while setting the value of an array");
					array = (Object[]) o;
					o = stack.get(stack.size() - 1);
					if (!(o instanceof Integer)) throw new RuntimeException("Expected integer as the last element of the stack while setting the value of an array");
					num = (int) o;
					if (num == -1) stack.add(array.length);
					else stack.add(array[num]);
					break;
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
