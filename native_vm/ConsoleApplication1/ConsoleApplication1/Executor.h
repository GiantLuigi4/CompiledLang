#pragma once
#include "Class.h"
#include <string>
#include <vector>
using namespace std;

class Executor {
	public: Executor();
	public: ~Executor();
	public: vector<Class> classes;
	public: Class load(char* filename);
	public: Class get(char* filename);
	public: Class getOrLoad(char* filename);
};
