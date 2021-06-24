#pragma once

#include <vector>
#include "Object.h"
#include "Class.h"

class LocalCapture {
	public: LocalCapture();
	public: ~LocalCapture();

	public: vector<Object> locals;
	public: vector<Class*> types;
	public: vector<int> pushPoints;

	public: Object getLocal(int);
	public: Class* getType(int);
	public: Object setLocal(int, Object);
	public: void addLocal(Class);
	public: void addLocal(Class*);
	public: void push();
	public: void pop();
};
