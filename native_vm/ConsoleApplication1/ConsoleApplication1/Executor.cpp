#include "pch.h"
#include "Executor.h"
#include "Class.h"
#include "Utils.h"
#include <iostream>
#include <map>
#include "Utils.h"
using namespace std;

Executor::Executor() {
	Class clazz = Class();
	clazz.name = "int";
	clazz.executor = this;
	clazz.nativeName = "I";
	classes.push_back(clazz);
	cout << classes[0].name;
	cout << "\n";
	cout << clazz.name;
	cout << "\n";
	cout << clazz.nativeName;
	cout << "\n\n\n\n";
}

Executor::~Executor() {
}

Class Executor::load(char* filename) {
	cout << "loading ";
	cout << filename;
	cout << "\n";
	Class clazz = Class();
	clazz.executor = this;
	clazz.load(read(filename));
	classes.push_back(clazz);
	clazz.nativeName = "";
	return clazz;
}

Class Executor::getOrLoad(char* filename) {
	classes[0].name = "int";
	classes[0].nativeName = "I";
	cout << classes[1].nativeName;
	for (Class c : classes) {
		cout << "the native name of the class being checked is ";
		cout << c.nativeName;
		cout << "\n";
		cout << "the pointer is ";
		cout << c.pointer;
		cout << "\n";
/*		cout << c.name;
		cout << "\n";
		cout << c.nativeName;
		cout << "\n";
		cout << filename;
		cout << "\n";
		cout << "\n";*/
		if (
			equals(c.name + (string)".langclass", (string)(filename)) || 
			equals(c.nativeName, (string)(filename))
			)
		//if (c.name + (string)".langclass" == (string)(filename) || c.nativeName.c_str() == filename)
			return c;
	}
	return load(filename);
}

Class Executor::get(char* filename) {
	for (Class c : classes) {
		if (c.name.c_str() == filename || c.nativeName.c_str() == filename)
			return c;
	}
	throw new runtime_error("Class " + (string) filename + " is not present.");
}
