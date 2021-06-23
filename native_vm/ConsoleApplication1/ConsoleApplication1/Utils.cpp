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
	}
	else cout << "Unable to open file";
	return contents;
}