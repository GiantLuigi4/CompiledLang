#include "pch.h"
#include "LocalCapture.h"
#include "Object.h"

LocalCapture::LocalCapture() {
}

LocalCapture::~LocalCapture() {
}

Object LocalCapture::getLocal(int index) {
	return locals[index];
}

Class LocalCapture::getType(int index) {
	return types[index];
}

Object LocalCapture::setLocal(int index, Object o) {
	Object old = locals[index];
	locals[index] = o;
	return old;
}

void LocalCapture::addLocal(Class clazz) {
	types.push_back(clazz);
	locals.push_back(Object());
}

void LocalCapture::push() {
	pushPoints.push_back(locals.capacity());
}

void LocalCapture::pop() {
	int num = pushPoints[pushPoints.capacity() - 1];
	pushPoints.pop_back();
//	System.out.println("Popping " + (locals.size() - num) + " locals");
	while (locals.size() != num) {
		locals.pop_back();
		if (types.size() >= locals.size()) types.pop_back();
	}
}