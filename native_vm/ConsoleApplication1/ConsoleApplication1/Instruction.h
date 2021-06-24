#pragma once

#include <string>
#include "Utils.h"

using namespace std;

class Instruction{
	public: Instruction();
	public: ~Instruction();
	public: char id;
	public: string ainfo0 = "";
	public: int ainfo0I;
	public: string ainfo1 = "";
	public: int ainfo1I;
	public: void init();
};
