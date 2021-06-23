#include "pch.h"
#include "Class.h"
#include <iostream>
#include <string>
using namespace std;

void Class::load(string text) {
	int size = (int)text.length();
	for (int i = 0; i < size; i++) {
		text.at(0);
		// TODO
		cout << (int)text.at(i);
	}
}

Class::Class() {
}

Class::~Class() {
}
