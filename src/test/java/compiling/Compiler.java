package compiling;

import compiling.tokens.TokenNames;
import compiling.tokens.Tokenizer;
import compiling.tokens.types.IToken;
import compiling.tokens.types.LiteralToken;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Compiler {
	public static void main(String[] args) throws IOException {
		String str;
		{
			FileInputStream src = new FileInputStream("test/src/FieldTest.langsrc");
			byte[] bytes = new byte[src.available()];
			src.read(bytes);
			src.close();
			str = new String(bytes);
		}
		System.out.println(str);
		str = str.replace("\r", "");
		StringBuilder out = new StringBuilder();
		ArrayList<String> access = new ArrayList<>();
		// TODO: figure this out
		tokenLoop:
		for (IToken iToken : Tokenizer.tokenize(Cleaner.stripComments(str))) {
			for (String access_keyword : TokenNames.access_keywords) {
				if (access_keyword.equals(iToken.getText())) {
					access.add(iToken.getText());
					continue tokenLoop;
				}
			}
			if (iToken.getText().equals("class")) {
				out.append("class ");
				continue;
			} else if (iToken instanceof LiteralToken && out.toString().endsWith("class ")) {
				out.append(iToken.getText());
				if (access.size() != 0) {
					out.append(" ");
					if (access.contains("public")) out.append("P");
					if (access.contains("protected")) out.append("O");
					if (access.contains("private")) out.append("I");
					if (access.contains("final")) out.append("F");
					if (access.contains("static")) out.append("S");
				}
				out.append("\n");
				access.clear();
				continue;
			}
			for (String type_keyword : TokenNames.type_keywords) {
				if (type_keyword.equals(iToken.getText())) {
					System.out.println(iToken.getText());
					continue tokenLoop;
				}
			}
		}
		System.out.println(Arrays.toString(Tokenizer.tokenize(Cleaner.stripComments(str))));
		System.out.println(out);
//		System.out.println(Arrays.toString(tokenize(str)));
	}
}
