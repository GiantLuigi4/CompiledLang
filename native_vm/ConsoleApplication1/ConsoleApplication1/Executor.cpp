#include "pch.h"
#include "Executor.h"
#include "Class.h"
#include "Utils.h"
#include <iostream>
#include <map>
using namespace std;

map<char*, Class> classes;

Executor::Executor() {
}


Executor::~Executor() {
}

Class Executor::load(char* filename) {
	Class clazz = Class();
	clazz.load(read(filename));
	classes.insert({filename, clazz});
	return clazz;
}

Class Executor::getOrLoad(char* filename) {
	if (classes.find(filename) == classes.end()) return load(filename);
	return get(filename);
}

Class Executor::get(char* filename) {
	return classes[filename];
}
