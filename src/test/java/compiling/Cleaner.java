package compiling;

public class Cleaner {
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
}
