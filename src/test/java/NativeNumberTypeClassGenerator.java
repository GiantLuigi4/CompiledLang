import java.io.FileOutputStream;
import java.io.IOException;

public class NativeNumberTypeClassGenerator {
	public static void main(String[] args) throws IOException {
		String output = "";
		String type = "double";
		String clazz = "Double";
		output += (
				"package tfc.lang.natives;\n" +
						"\n" +
						"import tfc.lang.LangClass;\n" +
						"\n" +
						"public class Lang" + clazz + " extends LangClass {\n" +
						"\tpublic Lang" + clazz + "() {\n" +
						"\t\tsuper(\"ÿ" + type + "\".getBytes());\n" +
						"\t}\n" +
						"\t\n" +
						"\tpublic boolean isInstance(Object o) {\n" +
						"\t\treturn o instanceof " + clazz + ";\n" +
						"\t}"
		);
		
		String operator = "+";
		String name = "add";
		output += template.replace("%type%", type).replace("%operator%", operator).replace("%name%", name);
		operator = "-";
		name = "subtract";
		output += template.replace("%type%", type).replace("%operator%", operator).replace("%name%", name);
		operator = "*";
		name = "multiply";
		output += template.replace("%type%", type).replace("%operator%", operator).replace("%name%", name);
		operator = "/";
		name = "divide";
		output += template.replace("%type%", type).replace("%operator%", operator).replace("%name%", name);
		operator = "%";
		name = "modulus";
		output += template.replace("%type%", type).replace("%operator%", operator).replace("%name%", name);
		
		operator = "<";
		name = "lessThan";
		output += template.replace("%type%", type).replace("%operator%", operator).replace("%name%", name);
		operator = ">";
		name = "greaterThan";
		output += template.replace("%type%", type).replace("%operator%", operator).replace("%name%", name);
		operator = "==";
		name = "equalTo";
		output += template.replace("%type%", type).replace("%operator%", operator).replace("%name%", name);
		operator = "!=";
		name = "notEqualTo";
		output += template.replace("%type%", type).replace("%operator%", operator).replace("%name%", name);
		operator = ">=";
		name = "greaterThanOrEqual";
		output += template.replace("%type%", type).replace("%operator%", operator).replace("%name%", name);
		operator = "<=";
		name = "lessThanOrEqual";
		output += template.replace("%type%", type).replace("%operator%", operator).replace("%name%", name);
		
		output += "\n}";
		
		FileOutputStream outputStream = new FileOutputStream("generated_type.java");
		outputStream.write(output.getBytes());
		outputStream.close();
		outputStream.flush();
	}
	
	private static final String template = "\n\n" +
			"\t@Override\n" +
			"\tpublic Object %name%(Object self, Object other) {\n" +
			"\t\tif (other instanceof Long) return (%type%) self %operator% (long) other;\n" +
			"\t\tif (other instanceof Short) return (%type%) self %operator% (short) other;\n" +
			"\t\tif (other instanceof Integer) return (%type%) self %operator% (int) other;\n" +
			"\t\tif (other instanceof Byte) return (%type%) self %operator% (byte) other;\n" +
			"\t\tif (other instanceof Float) return (%type%) self %operator% (float) other;\n" +
			"\t\tif (other instanceof Double) return (%type%) self %operator% (double) other;\n" +
			"\t\treturn null;\n" +
			"\t}";
}
