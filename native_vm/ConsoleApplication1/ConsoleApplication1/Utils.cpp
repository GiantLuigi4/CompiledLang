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
	} else cout << "Unable to open file\n" + filename + "\n";
	return contents;
}

bool startsWith(string src, string prefix) {
	// if the prefix is longer then the string, then it is clearly not at the start of the string
	if (prefix.length() > src.length()) return false;
	int length = (int) prefix.length();
	// iterate over all characters in both strings up until the length of val, if any of the characters in the target string do not match the provided prefix, then return false
	for (int i = 0; i < length; i++) if (prefix.at(i) != src.at(i)) return false;
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