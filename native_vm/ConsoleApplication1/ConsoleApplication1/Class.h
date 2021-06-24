#pragma once

#include <string>
#include <vector>
#include <map>
#include "Method.h"
#include "Object.h"

using namespace std;

class Executor;

class Class {
	public: Class();
	public: ~Class();
	public: void load(string text);
	public: vector<Method> methods;
	public: map<string, Object> staticFields;
	public: map<string, Object> instanceFields;
	public: Executor* executor;
	public: string name;
	public: string nativeName;
	public: Class* asPointer();
	public: Object runMethod(string, string, vector<Object>);
	public: Object add(Object self, Object other);
	public: Object subtract(Object self, Object other);
};
