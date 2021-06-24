#include "pch.h"
#include "Class.h"
#include <iostream>
#include <string>
#include "Method.h"
#include "Utils.h"
#include <vector>
#include "Object.h"
#include "LocalCapture.h"
using namespace std;

void Class::load(string text) {
	cout << "loaded by ";
	cout << executor;
	cout << "\n";
	string name = "";
	string stream = "";
	string temp = "";
	string tempName = "";
	string methodDesc = "";
	string fieldDesc = "";
	bool isFirst = false;
	int tempB = 0;
	int size = (int)text.length();
	for (int index = 0; index < size; index++) {
		int b = (int) text.at(index);
		// TODO
//		cout << (int)text.at(index);
//		cout << "\n";
		if (tempB != 0) {
			if (b == -1 || b == -2 || b == -3 || b == -10 || b == -17 || b == -29) {
//				cout << stream;
//				cout << "\n";
				if (b == -3) {
					temp = stream;
					isFirst = false;
					stream = "";
				} else {
					if (isFirst) {
						if (tempB == -1) name = stream;
						else if (tempB == -29) {
							string className = stream;
//							if (className != "null") inheritance.add(executor.getOrLoad(className));
//							else inheritance.add(null);
							// TODO
						}
					} else {
						if (tempB == -2) {
							tempName = temp;
							methodDesc = stream;
						} else if (tempB == -17) {
							tempName = temp;
							fieldDesc = stream;
						}
					}
					stream = "";
					tempB = 0;
				}
			} else {
				stream += (char) b;
				continue;
			}
		} else if (methodDesc != "") {
			if (b == -10) {
				bool isPublic = startsWith(methodDesc, "P");
				if (isPublic) methodDesc = substring(methodDesc, 1);
				bool isStatic = startsWith(methodDesc, "S");
				if (isStatic) methodDesc = substring(methodDesc, 1);
				Method method = Method();
				method.clazz = this;
				method.load(tempName, methodDesc, stream, isPublic, isStatic);
				methods.push_back(method);
				methodDesc = "";
			}
			stream += (char) b;
			continue;
		}
		if (b == -1 || b == -2 || b == -17 || b == -29) {
			tempB = b;
			isFirst = true;
			stream = "";
			continue;
		}
		// TODO: test fields
		if (fieldDesc != "") {
			cout << tempName;
			cout << fieldDesc;
			cout << "\n";
			if (b == -10) {
				cout << tempName;
				cout << fieldDesc;
				cout << "\n";
				bool isPublic = startsWith(fieldDesc, "P");
				if (isPublic) fieldDesc = substring(fieldDesc, 1);
				bool isStatic = startsWith(fieldDesc, "S");
				if (isStatic) fieldDesc = substring(fieldDesc, 1);
				fieldDesc = substring(fieldDesc, 1);
				if (isStatic) staticFields.insert({tempName, Object()});
				else instanceFields.insert({tempName, Object()});
				fieldDesc = "";
			}
			continue;
		}
	}
	// TODO: inheritance defaulting
	// TODO: call static init
	this->name = name;
//	cout << this->name + "\n";
}

Class::Class() {
}

Class::~Class() {
}

Object Class::add(Object self, Object other) {
/*	switch (nativeName.at(0)) {
		case 'I':
			// TODO: check other object's native name
			Object out = Object();
			out.intVal = (int*)((int)self.intVal + (int)other.intVal);
			out.clazz = this;
			return out;
			break;
	}*/
	return self;
	throw new runtime_error("Operator Overloads are NYI");
}

Object Class::subtract(Object self, Object other) {
	switch (nativeName.at(0)) {
		case 'I':
			// TODO: check other object's native name
			Object out = Object();
			out.intVal = (int*)((int)self.intVal - (int)other.intVal);
			out.clazz = this;
			return out;
			break;
	}
	throw new runtime_error("Operator Overloads are NYI");
}

Class* Class::asPointer() {
	return this;
}

Object Class::runMethod(string name, string descriptor, vector<Object> args) {
	cout << this->name + "\n";
	cout << methods.size();
	cout << "\n";
	cout << this->methods.at(0).name;
	cout << "Iterating methods\n";
	for (int iter = 0; iter < methods.size(); iter++) {
		cout << "H\n";
		Method* method = methods.at(iter).asPointer();
//		myfile1 << method.name;
//		myfile1 << method.descriptor;
//		myfile1 << "\n";
//		cout << name + descriptor + "\n";
//		cout << method->descriptor + "\n";
//		cout << method->name + method->descriptor + "\n";
		
		if (method->name == name && method->descriptor == descriptor) {
			cout << "Method " + method->name + method->descriptor + " passed\n";
			LocalCapture capture = LocalCapture();
			int i = 0;
			for (Object arg : args) {
//				cout << arg.clazz->name;
				capture.addLocal(arg.clazz);
				capture.setLocal(i, arg);
				cout << "Filling local ";
				cout << i;
				cout << "\n";
				cout << "Local Type " + arg.clazz->name + "\n";
				i++;
			}
/*			for (int i = 0; i < args.size(); i++) {
				if (args[i].clazz.name == "int")
					capture.addLocal(executor.get("int"));
				else if (args[i] instanceof Float)
					capture.addLocal(executor.get("float"));
				else if (args[i] instanceof Double)
					capture.addLocal(executor.get("double"));
				else if (args[i] instanceof Long)
					capture.addLocal(executor.get("long"));
				else if (args[i] instanceof Boolean)
					capture.addLocal(executor.get("boolean"));
				else capture.addLocal(executor.getClassFor(args[i]));
				capture.setLocal(i, args[i]);
			}*/
			return method->run(capture);
		}
	}
//	for (Class langClass: inheritance) {
//		if (langClass.hasMethod(name, descriptor))
//			return langClass.runMethod(name, descriptor, args);
//	}
	throw new runtime_error("Method " + name + descriptor + " not found");
}