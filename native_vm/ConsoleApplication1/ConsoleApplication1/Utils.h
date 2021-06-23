#pragma once
#include <string>
#include <iostream>
#include <fstream>

extern int opcodeBytes[];
std::string read(std::string);
bool startsWith(std::string, std::string);
std::string substring(std::string, int);
std::string substring(std::string, int, int);
bool isInt(std::string);
bool opcodeExists(int);
