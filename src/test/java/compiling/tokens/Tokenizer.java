package compiling.tokens;

import compiling.tokens.types.*;

import java.util.ArrayList;

public class Tokenizer {
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
					for (String access_keyword : TokenNames.access_keywords) {
						if (access_keyword.equals(literalBuilder)) {
							tokens.add(new AccessToken(access_keyword));
							if (c != ' ') tokens.add(new SpecialCharacterToken(c));
							literalBuilder = "";
							continue charLoop;
						}
					}
					for (String keyword : TokenNames.type_keywords) {
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
