import tfc.lang.Executor;
import tfc.lang.LangClass;

public class Test {
	public static void main(String[] args) {
		Executor executor = new Executor(255);
		executor.classPath = "test/out/";
//		{
//			byte[] bytes;
//			{
//				FileInputStream stream = new FileInputStream("test/out/yes." + tfc.lang.Executor.langExtension);
//				bytes = new byte[stream.available()];
//				stream.read(bytes);
//				stream.close();
//			}
//			tfc.lang.LangMethod method = new tfc.lang.LangMethod("test", "()I", bytes);
//			System.out.println(method.toString());
//			System.out.println(method.run(new tfc.lang.LocalCapture()));
//		}
//		System.out.println();
		{
			LangClass clazz = executor.load("TestClass");
			System.out.println(clazz.runMethod("testMethod", "()I"));
			System.out.println(clazz.runMethod("test", "(I)I", 5));
			System.out.println(clazz.runMethod("invokeStaticTest", "()I"));
			clazz = executor.load("FieldTest");
			System.out.println(clazz.runMethod("test", "()I"));
			clazz = executor.load("Conditions");
			System.out.println(clazz.runMethod("test", "()I"));
			clazz = executor.load("Loops");
			{
				int i = 0;
				if (i < 5) i += 1;
				if (i < 5) i += 1;
				if (i < 5) i += 1;
				if (i < 5) i += 1;
				if (i < 5) i += 1;
				if (i < 5) i += 1;
				if (i < 5) i += 1;
				if (i < 5) i += 1;
				System.out.println(i);
			}
			System.out.println(clazz.runMethod("test", "()I"));
			clazz = executor.load("DoubleTest");
			System.out.println(clazz.runMethod("test", "()D"));
		}
		System.out.println();
		{
			LangClass clazz = executor.load("InstanceTest");
			System.out.println(clazz.runMethod("test", "()I"));
		}
	}
}
