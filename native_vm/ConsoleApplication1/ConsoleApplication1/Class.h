#pragma once
#include <string>
#include <list>
#include <map>
#include "Method.h"
#include "Object.h"
using namespace std;

class Executor;

class Class {
	public: Class();
	public: ~Class();
	public: void load(string text);
	public: list<Method> methods;
	public: map<string, Object> staticFields;
	public: map<string, Object> instanceFields;
	public: Executor* executor;
	public: string name;
};
