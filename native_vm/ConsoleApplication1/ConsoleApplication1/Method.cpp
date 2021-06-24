#include "pch.h"
#include "Method.h"
#include <string>
#include <vector>
#include "Instruction.h"
#include "Utils.h"
#include "Class.h"
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
	this->descriptor = desc;
	cout << this->name;
	cout << this->descriptor;
	cout << "\n";
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
//				cout << stream;
//				cout << "\n";
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
	cout << "Running " + name + "\n";
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
		Object lastStackField;
		Object local;
		Object out;
		Class* type;
		int localId;
		string methodName;
		cout << (int)instruction.id;
		cout << "\n";
		switch ((int) instruction.id) {
			case -4:
				cout << "AAAAAAAAAAAAAAAAAAAAAAAAAA";
				if (startsWith(instruction.ainfo1, "T")) {
					ainfo1 = substring(instruction.ainfo1, 1, instruction.ainfo1.length() - 1);
					name = instruction.ainfo1;
					name += ".langclass";
					locals.addLocal(clazz->executor->getOrLoad((char*)name.c_str()));
					break;
				}
				cout << "AAAAAAAAAAAAAAAAAAAAAAAAAA";
				name = instruction.ainfo1;
				locals.addLocal(clazz->executor->getOrLoad((char*)name.c_str()));
				cout << locals.getType(locals.types.size() - 1)->name;
				cout << " l";
				cout << instruction.ainfo0;
				cout << ";\n";
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
					Class c = clazz->executor->getOrLoad((char*)"I");
					o.clazz = c.asPointer(); // really c++?
					stack.push_back(o);
					break;
				} else {
					switch (ainfo1.at(0)) {
						case 'I': {
							Object o = Object();
							o.intVal = (int*) instruction.ainfo0I;
							stack.push_back(o);
							Class c = clazz->executor->getOrLoad((char*)"I");
							o.clazz = c.asPointer(); // really c++?
							break;
						} default: break; // TODO
						// TODO: other cases
					}
				}
				break;
			case -8:
				cout << instruction.ainfo0;
				cout << " = ";
				cout << (int) stack[stack.size() - 1].intVal;
				cout << ";\n";
				locals.setLocal(instruction.ainfo0I, stack[stack.size() - 1]);
				break;
			case -13:
				cout << "Returning ";
				cout << (int) stack[stack.size() - 1].intVal;
				cout << "\n";
				return stack[stack.size() - 1];
			case -14:
				stack.push_back(locals.getLocal(instruction.ainfo0I));
				break;
			case -15:
				lastStackField = stack[stack.size() - 1];
				localId = instruction.ainfo0I;
				local = locals.getLocal(localId);
				type = locals.getType(localId);

				cout << "l" + instruction.ainfo0 + " = ";
				cout << (int)lastStackField.intVal;
				cout << " " + instruction.ainfo1 + " ";
				cout << (int)local.intVal;
				cout << "; // = ";

				switch (instruction.ainfo1.at(0)) {
					case '+':
						out = type->add(local, lastStackField);
						cout << (int) out.intVal;
						break;
					case '-':
						out = type->subtract(local, lastStackField);
						break;
					case '/':
//						out = type->divide(local, lastStackField);
						break;
					case '*':
//						out = type->multiply(local, lastStackField);
						break;
						// TODO: modulus
					default:
						throw new runtime_error("Invalid or no operator provided");
				}
				cout << "\n";
				locals.setLocal(localId, out);
				break;
			case -16:
				type = clazz;
				methodName = instruction.ainfo0;
				if (contains(methodName, '.')) {
					string className = substring(methodName, 0, lastIndexOf(methodName, '.'));
					methodName = substring(methodName, lastIndexOf(methodName, '.') + 1);
					type = clazz->executor->getOrLoad((char*) className.c_str()).asPointer();
				}
				if (endsWith(instruction.ainfo1, "V"))
					clazz->runMethod(methodName, instruction.ainfo1, stack);
				else stack.push_back(clazz->runMethod(methodName, instruction.ainfo1, stack));
				break;
			case -10:
				cout << "Method ended with no return statement\n";
				throw new runtime_error("Method ended with no return statement");
			default:
				cout << "Invalid Opcode ";
				cout << (int) instruction.id;
				cout << "\n";
				throw new runtime_error("Invalid Opcode " + instruction.id);
		}
	}
	cout << "Method ended with no return statement\n";
	throw new runtime_error("Method ended with no return statement");
}

Method* Method::asPointer() {
	return this;
}