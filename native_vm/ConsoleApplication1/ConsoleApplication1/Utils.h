#pragma once

#include <string>
#include <iostream>
#include <fstream>

extern int opcodeBytes[];
std::string read(std::string);
bool startsWith(std::string, std::string);
bool endsWith(std::string, std::string);
bool equals(std::string, std::string);
bool contains(std::string, char);
int lastIndexOf(std::string, char);
std::string substring(std::string, int);
std::string substring(std::string, int, int);
bool isInt(std::string);
bool opcodeExists(int);