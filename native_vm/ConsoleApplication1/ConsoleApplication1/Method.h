#pragma once

#include <string>
#include <vector>
#include "Instruction.h"
using namespace std;

class Class;

class Method {
	public: Method();
	public: ~Method();
	public: Class* clazz;
	public: bool isPublic;
	public: bool isStatic;
	public: string name;
	public: string descriptor;
	public: vector<Instruction> insns;
	public: void load(string name, string desc, string stream, bool isPublic, bool isStatic);
};
