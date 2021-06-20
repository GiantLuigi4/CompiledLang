import tfc.lang.Executor;
import tfc.lang.LangClass;

import java.util.Arrays;

public class Test2 {
	public static void main(String[] args) {
		Executor executor = new Executor(255);
		executor.classPath = "test/out/";
		{
			LangClass clazz = executor.load("Arrays");
			Object o = clazz.runMethod("test", "()[I");
			System.out.println(o);
			if (o instanceof Object[]) System.out.println(Arrays.toString((Object[]) o));
		}
		{
			for (int i = 0; i < 4; i++) System.out.println(i);
			LangClass clazz = executor.load("ArrayIter");
			Object o = clazz.runMethod("test", "()I");
			System.out.println(o);
		}
	}
}
