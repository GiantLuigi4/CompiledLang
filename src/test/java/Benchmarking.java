import tfc.lang.Executor;
import tfc.lang.LangClass;

public class Benchmarking {
	public static void main(String[] args) {
		{
			long avgTime = 0;
			for (int i = 0; i < 25600; i++) {
				long timens = System.nanoTime();
				method();
				avgTime += System.nanoTime() - timens;
				avgTime /= 2;
			}
			System.out.println(avgTime);
		}
		{
			long avgTime = 0;
			Executor executor = new Executor(255);
			executor.classPath = "test/out/";
//			tfc.lang.LangClass clazz;
//			{
//				byte[] bytes;
//				{
//					FileInputStream stream = new FileInputStream("test/out/Test2.langclass");
//					bytes = new byte[stream.available()];
//					stream.read(bytes);
//					stream.close();
//				}
//				clazz = new tfc.lang.LangClass(bytes);
//			}
			LangClass clazz = executor.load("Test2");
			for (int i = 0; i < 25600; i++) {
				long timens = System.nanoTime();
				clazz.runMethod("method", "()I");
				avgTime += System.nanoTime() - timens;
				avgTime /= 2;
			}
			System.out.println(avgTime);
		}
	}
	
	@SuppressWarnings("UnusedReturnValue")
	private static int method() {
		int i = 0;
		i += 5;
		i += 64;
		i += 32;
		i += 12;
		i += 16;
		i += 64;
		i += 923;
		i += 203;
		i += 22;
		return i;
	}
}
