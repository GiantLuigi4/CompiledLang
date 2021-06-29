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
	clazz.setName("int");
	clazz.executor = this;
	clazz.setNativeName("I");
	classes.push_back(clazz);
	cout << "HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH";
	cout << classes[0].getName();
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
			equals(c.getName() + (string)".langclass", (string)(filename)) || 
			equals(c.getNativeName(), (string)(filename))
			)
		//if (c.name + (string)".langclass" == (string)(filename) || c.nativeName.c_str() == filename)
			return c;
	}
	return load(filename);
}

Class Executor::get(char* filename) {
	for (Class c : classes) {
		if (c.getName().c_str() == filename || c.getNativeName().c_str() == filename)
			return c;
	}
	throw new runtime_error("Class " + (string) filename + " is not present.");
}
