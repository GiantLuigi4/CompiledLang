// ConsoleApplication1.cpp : This file contains the 'main' function. Program execution begins and ends there.

#include "pch.h"
#include <sys/stat.h>
#include <list>
#include <algorithm>
#include "Executor.h"
#include "Utils.h"
#include <string>
#include <iostream>
#include <fstream>
#include "Method.h"
#include "Class.h"
#include "LocalCapture.h"
#include <list>

#include <windows.h>
#include <filesystem>
using namespace std;

int main() {
	cout << "TupulVM V0.0\n";
	cout << "WARNING: Native TVM is very early dev, and will likely not work at all.\n";

	Executor executor = Executor();

/*	string contents = read("TestClass1.langclass");
	
	ofstream myfile1;
	myfile1.open("example.txt");
	myfile1 << contents;
	myfile1.close();*/

	Class c = executor.load((char*) "TestClass1.langclass");
	c = executor.getOrLoad((char*) "TestClass1.langclass");
	c = executor.getOrLoad((char*) "TestClass1.langclass");
	c = executor.getOrLoad((char*) "TestClass1.langclass");
	Method m = c.methods[0];
	cout << m.name + "\n";
	cout << (int) m.run(LocalCapture()).intVal;
	cout << "\n";
	c.executor = executor.asPointer();
	m = c.methods[1];
	cout << m.name + "\n";
	cout << (int) m.run(LocalCapture()).intVal;
	cout << "\n";
	c.executor = executor.asPointer();
	m = c.methods[3];
	cout << m.name + "\n";
	cout << (int) m.run(LocalCapture()).intVal;
	cout << "\n";
	c.executor = executor.asPointer();

	return 0;
}
