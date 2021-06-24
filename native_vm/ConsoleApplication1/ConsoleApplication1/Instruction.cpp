#include "pch.h"
#include "Instruction.h"


Instruction::Instruction() {
}


Instruction::~Instruction() {
}

void Instruction::init() {
	if (isInt(ainfo0)) ainfo0I = stoi(ainfo0);
	if (isInt(ainfo1)) ainfo1I = stoi(ainfo1);
}
