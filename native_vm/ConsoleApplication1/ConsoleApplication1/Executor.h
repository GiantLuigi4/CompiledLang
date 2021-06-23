#pragma once
#include <map>
#include "Class.h"
#include <string>
using namespace std;

class Executor {
	public: Executor();
	public: ~Executor();
	public: map<char*, Class> classes;
	public: Class load(char* filename);
	public: Class get(char* filename);
	public: Class getOrLoad(char* filename);
};
