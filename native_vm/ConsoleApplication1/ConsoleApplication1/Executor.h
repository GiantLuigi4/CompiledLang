#pragma once
#include <map>
#include "Class.h"
#include <string>
using namespace std;

class Executor {
public:
	Executor();
	~Executor();
	std::map<char*, Class> classes;
	Class load(char* filename);
	Class get(char* filename);
	Class getOrLoad(char* filename);
};
