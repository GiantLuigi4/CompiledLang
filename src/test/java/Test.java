import java.io.FileInputStream;
import java.io.IOException;

public class Test {
	public static void main(String[] args) throws IOException {
		Executor executor = new Executor(255);
		executor.classPath = "test/out/";
//		{
//			byte[] bytes;
//			{
//				FileInputStream stream = new FileInputStream("test/out/yes." + Executor.langExtension);
//				bytes = new byte[stream.available()];
//				stream.read(bytes);
//				stream.close();
//			}
//			LangMethod method = new LangMethod("test", "()I", bytes);
//			System.out.println(method.toString());
//			System.out.println(method.run(new LocalCapture()));
//		}
//		System.out.println();
		{
			LangClass clazz = executor.load("TestClass");
//			System.out.println(clazz.runMethod("testMethod", "()I"));
//			System.out.println(clazz.runMethod("test", "(I)I", 5));
//			System.out.println(clazz.runMethod("invokeStaticTest", "()I"));
			clazz = executor.load("FieldTest");
//			System.out.println(clazz.runMethod("test", "()I"));
			clazz = executor.load("Conditions");
			System.out.println(clazz.runMethod("test", "()I"));
			clazz = executor.load("Loops");
			System.out.println(clazz.runMethod("test", "()I"));
			int i = 0;
			if (i < 5) i+=1;
			if (i < 5) i+=1;
			if (i < 5) i+=1;
			if (i < 5) i+=1;
			if (i < 5) i+=1;
			if (i < 5) i+=1;
			if (i < 5) i+=1;
			if (i < 5) i+=1;
			System.out.println(i);
		}
	}
}
