// ConsoleApplication1.cpp : This file contains the 'main' function. Program execution begins and ends there.
//

#include "pch.h"
#include <iostream>
#include <fstream>
#include <sys/stat.h>
#include <list>
#include <algorithm>
#include <string>

using namespace std;

string read(string filename) {
	string line;
	string contents;
	// https://www.cplusplus.com/doc/tutorial/files/
	ifstream myfile("TestClass1.langclass");
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

int main() {
	std::cout << "TupulVM V0.0\n";
	std::cout << "WARNING: TVM is very early dev, and will likely not work at all.\n";

	string contents = read("TestClass1.langclass");

	ofstream myfile1;
	myfile1.open("example.txt");
	myfile1 << contents;
	myfile1.close();

	return 0;
}

// Run program: Ctrl + F5 or Debug > Start Without Debugging menu
// Debug program: F5 or Debug > Start Debugging menu

// Tips for Getting Started: 
//   1. Use the Solution Explorer window to add/manage files
//   2. Use the Team Explorer window to connect to source control
//   3. Use the Output window to see build output and other messages
//   4. Use the Error List window to view errors
//   5. Go to Project > Add New Item to create new code files, or Project > Add Existing Item to add existing code files to the project
//   6. In the future, to open this project again, go to File > Open > Project and select the .sln file
