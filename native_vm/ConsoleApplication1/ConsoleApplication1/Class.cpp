#include "pch.h"
#include "Class.h"
#include <iostream>
#include <string>
#include "Method.h"
#include "Utils.h"
#include <vector>
using namespace std;

void Class::load(string text) {
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
	cout << this->name + "\n";
}

Class::Class() {
	pointer = this;
}

Class::~Class() {
}
