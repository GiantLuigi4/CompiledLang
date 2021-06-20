package compiling;

import compiling.tokens.*;

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
		for (IToken iToken : tokenize(stripComments(str))) {
			for (String access_keyword : access_keywords) {
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
			for (String type_keyword : type_keywords) {
				if (type_keyword.equals(iToken.getText())) {
					System.out.println(iToken.getText());
					continue tokenLoop;
				}
			}
		}
		System.out.println(Arrays.toString(tokenize(stripComments(str))));
		System.out.println(out);
//		System.out.println(Arrays.toString(tokenize(str)));
	}
	
	private static final String[] access_keywords = {
			"public", "protected", "private",
			"static", "instance",
			"final", "modifiable",
	};
	
	private static final String[] type_keywords = {
			"class",
			"int", "long", "short", "byte",
			"float", "double",
			"void"
	};
	
	public static String stripComments(String src) {
		StringBuilder out = new StringBuilder();
		boolean bulkComment = false;
		for (String s : src.split("\n")) {
			boolean isEscaped = false;
			boolean isAsterisk = false;
			boolean isInStr = false;
			for (char c : s.toCharArray()) {
				if (c == '\"') isInStr = !isInStr;
				if (c == '*') {
					if (isEscaped) bulkComment = true;
					isAsterisk = true;
					continue;
				}
				if (c == '/') {
					if (isEscaped) break;
					if (isAsterisk) bulkComment = false;
					if (!isInStr) {
						isEscaped = true;
						continue;
					}
				}
				if (bulkComment) continue;
				if (isEscaped) {
					out.append('/');
					isEscaped = false;
					continue;
				}
				if (isAsterisk) {
					out.append('*');
					isAsterisk = false;
					continue;
				}
				out.append(c);
			}
			if (!bulkComment) out.append("\n");
		}
		System.out.println(out);
		return out.toString();
	}
	
	public static IToken[] tokenize(String str) {
		str = str.replace("\n", "").replace("\r", "");
		String literalBuilder = "";
		ArrayList<IToken> tokens = new ArrayList<>();
		boolean inString = false;
		boolean isEscaped = false;
		charLoop:
		for (char c : str.toCharArray()) {
			if (Character.isDigit(c) || Character.isAlphabetic(c)) {
				literalBuilder += c;
			} else {
				if (c == '\\') {
					isEscaped = true;
					literalBuilder += c;
					continue;
				}
				if (!isEscaped && (c == '\"' || c == '\'')) {
					inString = !inString;
					literalBuilder += c;
					if (!inString) {
						tokens.add(new LiteralToken(literalBuilder));
						literalBuilder = "";
					}
					continue;
				}
				if (inString) {
					literalBuilder += c;
					continue;
				}
				if (!literalBuilder.equals("")) {
					for (String access_keyword : access_keywords) {
						if (access_keyword.equals(literalBuilder)) {
							tokens.add(new AccessToken(access_keyword));
							if (c != ' ') tokens.add(new SpecialCharacterToken(c));
							literalBuilder = "";
							continue charLoop;
						}
					}
					for (String keyword : type_keywords) {
						if (keyword.equals(literalBuilder)) {
							tokens.add(new KeywordToken(keyword));
							if (c != ' ') tokens.add(new SpecialCharacterToken(c));
							literalBuilder = "";
							continue charLoop;
						}
					}
					tokens.add(new LiteralToken(literalBuilder));
					if (c != ' ') tokens.add(new SpecialCharacterToken(c));
					literalBuilder = "";
				} else {
					if (c != ' ') tokens.add(new SpecialCharacterToken(c));
				}
			}
		}
		return tokens.toArray(new IToken[0]);
	}
}
