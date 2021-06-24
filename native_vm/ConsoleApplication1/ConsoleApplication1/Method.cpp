#include "pch.h"
#include "Method.h"
#include <string>
#include <vector>
#include "Instruction.h"
#include "Utils.h"
#include "Executor.h"
#include "LocalCapture.h"
#include "Map.h"
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
//	string str = "";
//	for (Instruction i : insns) {
//		str += (char)i.id;
//		cout << (int) i.id;
//		if (i.ainfo0.length() > 0) str += i.ainfo0;
//		if (i.ainfo1.length() > 0) {
//			str += (char) -3;
//			str += i.ainfo1;
//		}
//	}
//	cout << str + "\n";
//
//	ofstream myfile1;
//	myfile1.open("method.txt");
//	myfile1 << str;
//	myfile1.close();
}

Object Method::run(LocalCapture locals) {
	vector<Object> stack = vector<Object>();
//	Map labelStackStates = Map();
//	Map labelLabelStates = Map();
//	Map labelPoints = Map();
	vector<int> pushPoints = vector<int>();
	for (int i = 0; i < insns.capacity(); i++) {
		Instruction instruction = insns[i];
		string name;
		string ainfo1 = instruction.ainfo1;
		int num;
		switch ((int) instruction.id) {
			case -4:
				if (startsWith(instruction.ainfo1, "T")) {
					ainfo1 = substring(instruction.ainfo1, 1, instruction.ainfo1.length() - 1);
					name = instruction.ainfo1;
					name += ".langclass";
					locals.addLocal(clazz->executor->getOrLoad((char*)name.c_str()));
					break;
				}
				name = instruction.ainfo1;
				locals.addLocal(clazz->executor->getOrLoad((char*)name.c_str()));
				break;
			case -5:
				pushPoints.push_back(stack.size());
				break;
			case -6:
				num = pushPoints[pushPoints.size() - 1];
				pushPoints.pop_back();
				while (stack.size() != num) stack.pop_back();
				break;
			case -7:
				if (ainfo1 == "") {
					Object o = Object();
					o.intVal = (int*) instruction.ainfo0I;
					stack.push_back(o);
					break;
				} else {
					switch (ainfo1.at(0)) {
						case 'I': {
							Object o = Object();
							o.intVal = (int*) instruction.ainfo0I;
							stack.push_back(o);
							break;
						} default: break; // TODO
						// TODO: other cases
					}
				}
				break;
			case -8:
				locals.setLocal(instruction.ainfo0I, stack[stack.size() - 1]);
				break;
			case -13:
				return stack[stack.size() - 1];
			case -14:
				stack.push_back(locals.getLocal(instruction.ainfo0I));
				break;
			case -10:
				cout << "Method ended with no return statement\n";
				throw new runtime_error("Method ended with no return statement");
			default:
//				cout << "Invalid Opcode " + (int) instruction.id;
				cout << "Invalid Opcode ";
				cout << (int) instruction.id;
				cout << "\n";
				throw new runtime_error("Invalid Opcode " + instruction.id);
		}
	}
	cout << "Method ended with no return statement\n";
	throw new runtime_error("Method ended with no return statement");
}