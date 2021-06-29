#include "pch.h"
#include <string>
#include <iostream>
#include <fstream>
#include "Utils.h"
using namespace std;

typedef int byte;

int opcodeBytes[30] = {
		-1, -2, -3, -4, -5, -6, -7, -8, -9, -10, -11,
		-12, -13, -14, -15, -16, -17, -18, -19, -20,
		-21, -22, -23, -24, -25, -26, -27, -28, -29,
		-30
};

// https://www.cplusplus.com/doc/tutorial/files/
string read(string filename) {
	string line;
	string contents;
	// TODO: learn string manipulation so I can make this replace all instances of "." except the last one with "/"
	ifstream myfile(filename);
	if (myfile.is_open()) {
		while (getline(myfile, line)) {
			cout << line << '\n';
			contents += line + '\n';
		}
		myfile.close();
	} else {
		cout << "Unable to open file\n" + filename + "\n";
		return "ÿTestClass1þtestMethodýPS()Iöü0ýIûù53ø0úò0óöþmainýPS()Iöü0ýIûù8ø0úûù64ñ0ý+úò0óöþtestýPS(I)Iöûù32ø0úò0óöþinvokeStaticTestýPS()Iöü0ýIûù45ðtestý(I)Iø0úò0óö";
	}
	return contents;
}

bool startsWith(string src, string prefix) {
	// if the prefix is longer then the string, then it is clearly not at the start of the string
	if (prefix.length() > src.length()) return false;
	int length = (int)prefix.length();
	// iterate over all characters in both strings up until the length of val, if any of the characters in the target string do not match the provided prefix, then return false
	for (int i = 0; i < length; i++) if (prefix.at(i) != src.at(i)) return false;
	return true;
}

bool equals(string src, string targ) {
	// if the prefix is longer then the string, then it is clearly not at the start of the string
/*	cout << targ.length();
	cout << ", ";
	cout << src.length();
	cout << "\n";*/
	if (targ.length() != src.length()) return false;
	int length = (int)targ.length();
	// iterate over all characters in both strings up until the length of val, if any of the characters in the target string do not match the provided prefix, then return false
	for (int i = 0; i < length; i++) {
		if (targ.at(i) != src.at(i)) {
/*			cout << "false, ";
			cout << "\n";
			cout << targ.at(i);
			cout << " != ";
			cout << src.at(i);
			cout << "\n";*/
			return false;
		}
	}
/*	cout << "true";
	cout << "\n";*/
	return true;
}

string substring(string src, int start) {
	int length = (int) src.length();
	return substring(src, start, length);
}

string substring(string src, int start, int end) {
	string out = "";
	for (int i = start; i < end; i++) out += src.at(i);
	return out;
}

// https://www.tutorialspoint.com/how-to-check-if-a-c-cplusplus-string-is-an-int#:~:text=There%20are%20several%20methods%20to%20check%20that%20string,the%20string%20is%20present%20in%20main%20%28%29%20function.
bool isInt(string str) {
	if (str.length() == 0) return false;
	for (int i = 0; i < str.length(); i++) if (!isdigit(str[i])) return false;
	return true;
}

bool opcodeExists(int op) {
	// TODO: figure out how to unhardcode this
	for (int i = 0; i < 30; i++) if (opcodeBytes[i] == op) return true;
	return false;
}
