#include "pch.h"
#include "Method.h"
#include <string>
#include <vector>
#include "Instruction.h"
#include "Utils.h"
using namespace std;

Method::Method() {
}

Method::~Method() {
}

void Method::load(string name, string desc, string text, bool isPublic, bool isStatic) {
	this->name = name;
	this->descriptor = descriptor;
	string stream = "";
	string temp = "";
	int tempB = 0;
	bool isFirst = false;
	int size = (int)text.length();
	for (int index = 0; index < size; index++) {
		int b = text[index];
		if (tempB != 0) {
			if (opcodeExists(b)) {
				if (b == -3) {
					temp = stream;
					isFirst = false;
					stream = "";
				} else {
					Instruction insn = Instruction();
					insn.id = tempB;
					if (isFirst) insn.ainfo0 = stream;
					else {
						insn.ainfo0 = temp;
						insn.ainfo1 = stream;
					}
					insn.init();
					insns.push_back(insn);
					stream = "";
					tempB = 0;
				}
			} else {
				stream += (char)b;
				cout << stream;
				cout << "\n";
				continue;
			}
		}
		if (b == -4 || b == -7 || b == -8 || b == -14 || b == -15 || b == -16 || b == -17 || b == -18 || b == -19 || b == -20 || b == -21 || b == -22 || b == -26 || b == -27 || b == -28 || b == -30) {
			tempB = b;
			isFirst = true;
			//			} else if (b == -5 || b == -6 || b == -9 || b == -10 || b == -11 || b == -12 || b == -13) {
		} else if (b != -3) {
			Instruction insn = Instruction();
			insn.id = b;
			insns.push_back(insn);
		}
	}
	this->isStatic = isStatic;
	this->isPublic = isPublic;
	string str = "";
	for (Instruction i : insns) {
		str += (char)i.id;
		cout << (int) i.id;
		if (i.ainfo0.length() > 0) str += i.ainfo0;
		if (i.ainfo1.length() > 0) {
			str += (char) -3;
			str += i.ainfo1;
		}
	}
	cout << str + "\n";

	ofstream myfile1;
	myfile1.open("method.txt");
	myfile1 << str;
	myfile1.close();

//	instructions = insns.toArray(new Instruction[0]);
}