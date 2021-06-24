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
//	cout << "HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH";
//	cout << classes[0].name;
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
	return clazz;
}

Class Executor::getOrLoad(char* filename) {
	for (Class c : classes) {
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
