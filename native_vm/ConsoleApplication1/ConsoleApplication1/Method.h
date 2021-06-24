#pragma once

#include <string>
#include <vector>
#include "Object.h"
#include "Instruction.h"

using namespace std;

class Class;
class LocalCapture;

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
	public: Object run(LocalCapture);
	public: Method* asPointer();
};
