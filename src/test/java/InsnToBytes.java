import tfc.lang.Executor;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class InsnToBytes {
	// class TestClass
	// method testMethod ()V
	// local test I			// defines a local named test of type I
	// push					// pushes the stack
	// loadi 53				// loads the integer 53 into the stack
	// setl test			// sets the defined local to the last value on the stack
	// pop					// pops the local stack
	// returnV				// returns void
	// end 					// marks the end of the method
	
	// -1 == class name
	// -2 == method name
	// -3 == descriptor
	// -4 == define local (local)
	// -5 == push
	// -6 == pop
	// -7 == load integer (loadi)
	// -8 == set local (setl)
	// -8 == return void (returnV)
	// -10 == end method
	public static void main(String[] args) throws IOException {
		File file = new File("test/in");
		ArrayList<File> files = list(file);
		
		for (File file1 : files) {
			String pathOut = file1.toString().substring("test/in".length());
			pathOut = "test/out" + pathOut;
			
			String input;
			{
				FileInputStream stream = new FileInputStream(file1);
				byte[] bytes = new byte[stream.available()];
				stream.read(bytes);
				stream.close();
				input = new String(bytes);
			}
			
			File f = new File(pathOut);
			if (!f.exists()) {
				f.getParentFile().mkdirs();
			}
			FileOutputStream stream = new FileOutputStream(pathOut.replace(".txt", "." + Executor.langExtension));
			stream.write(convert(input));
			stream.close();
			stream.flush();
		}
	}
	
	public static byte[] convert(String input) throws IOException {
		String[] strings = input.split("\n");
		
		ByteArrayOutputStream output = new ByteArrayOutputStream();

//		output.write("0".getBytes()); // language version 0
		
		// TODO: max local count calculation per method
		HashMap<String, Integer> localCounts = new HashMap<>();
		int localNum = 0;
		int labelNum = 0;
		HashMap<String, Integer> locals = new HashMap<>();
		HashMap<String, Integer> labels = new HashMap<>();
		for (String string : strings) {
			string = string.replace("\r", "").trim();
			if (string.equals("")) continue;
			
			if (string.startsWith("class")) {
				output.write(-1);
				String name = string.split(" ")[1];
				output.write(name.getBytes());
			} else if (string.startsWith("method")) {
				output.write(-2);
				String name = string.split(" ")[1];
				String desc = string.split(" ")[2];
				output.write(name.getBytes());
				output.write(-3);
				output.write(desc.getBytes());
				if (desc.equals("SV")) {
					output.write(-10);
					continue;
				}
				String descr = desc.substring(desc.indexOf("(") + 1);
				boolean isComplexType = false;
				for (char c : descr.toCharArray()) {
					if (isComplexType) {
						if (c == ';') isComplexType = false;
					} else if (c == 'T') isComplexType = true;
					else if (c == ')') break;
					else locals.put("!" + localNum++, localNum - 1);
				}
				output.write(-10);
			} else if (string.startsWith("field")) {
				output.write(-17);
				String name = string.split(" ")[1];
				String desc = string.split(" ")[2];
				output.write(name.getBytes());
				output.write(-3);
				output.write(desc.getBytes());
				output.write(-10);
			}
			
			else if (string.startsWith("local")) {
				String name = string.split(" ")[1];
				String desc = string.split(" ")[2];
				locals.put(name, localNum);
				output.write(-4);
				output.write(("" + localNum).getBytes());
				output.write(-3);
				output.write(desc.getBytes());
				localNum++;
			}
			
			else if (string.startsWith("pushl")) {
				output.write(-11);
			} else if (string.startsWith("popl")) {
				output.write(-12);
			} else if (string.startsWith("push")) {
				output.write(-5);
			} else if (string.startsWith("pop")) {
				output.write(-6);
			}
			
			else if (string.startsWith("loadc")) {
				String value = string.split(" ")[1];
				output.write(-7);
				output.write(value.getBytes());
				if (string.split(" ").length > 2) {
					String type = string.split(" ")[2];
					output.write(-3);
					output.write(type.getBytes());
				}
			} else if (string.startsWith("loadsf")) {
				String value = string.split(" ")[1];
				output.write(-17);
				output.write(value.getBytes());
			}
			
			else if (string.startsWith("setl")) {
				String name = string.split(" ")[1];
				output.write(-8);
				output.write(("" + locals.get(name)).getBytes());
			} else if (string.startsWith("setsf")) {
				String name = string.split(" ")[1];
				output.write(-18);
				output.write(name.getBytes());
			}
			
			else if (string.startsWith("returnV")) {
				output.write(-9);
			} else if (string.startsWith("return")) {
				output.write(-13);
			}
			
			else if (string.startsWith("end")) {
				output.write(-10);
				locals.clear();
				localNum = 0;
			}
			
			else if (string.startsWith("loadl")) {
				String name = string.split(" ")[1];
				output.write(-14);
				output.write(("" + locals.get(name)).getBytes());
			}
			
			else if (string.startsWith("add")) {
				String name = string.split(" ")[1];
				output.write(-15);
				output.write(("" + locals.get(name)).getBytes());
			}
			
			else if (string.startsWith("istatic")) {
				String name = string.split(" ")[1];
				String desc = string.split(" ")[2];
				output.write(-16);
				output.write(name.getBytes());
				output.write(-3);
				output.write(desc.getBytes());
			}
			
			else if (string.startsWith("operator")) {
				String name = string.split(" ")[1];
				output.write(-19);
				switch (name) {
					case "<=":
						output.write("0".getBytes());
						break;
					case "<":
						output.write("1".getBytes());
						break;
					case "==":
						output.write("2".getBytes());
						break;
					case ">":
						output.write("3".getBytes());
						break;
					case ">=":
						output.write("4".getBytes());
						break;
				}
			}
			
			else if (string.startsWith("boffset"))  {
				String name = string.split(" ")[1];
				output.write(-20);
				output.write(name.getBytes());
			}
			
			
			else if (string.startsWith("bgoto"))  {
				String name = string.split(" ")[1];
				output.write(-22);
				output.write(("" + labels.get(name)).getBytes());
			} else if (string.startsWith("label")) {
				String name = string.split(" ")[1];
				output.write(-21);
				labels.put(name, labelNum);
				output.write(("" + labelNum++).getBytes());
			}
			
			else if (string.startsWith("marray")) {
				output.write(-23);
			} else if (string.startsWith("aset")) {
				output.write(-24);
			} else if (string.startsWith("aget")) {
				output.write(-25);
			} else if (string.startsWith("alen")) {
				String name = "-1";
				output.write(-7);
				output.write((name).getBytes());
				output.write(-25);
			}
			
			else {
				System.out.println("unrecognized instruction: " +string);
			}
		}
		byte[] bytes = output.toByteArray();
		output.close();
		output.flush();
		return bytes;
	}
	
	private static ArrayList<File> list(File f) {
		ArrayList<File> files = new ArrayList<>();
		for (File file : f.listFiles()) {
			if (file.isDirectory()) files.addAll(list(file));
			else files.add(file);
		}
		return files;
	}
}
