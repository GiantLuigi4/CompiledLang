#pragma once

#include <list>

using namespace std;

class Map {
	public: Map();
	public: ~Map();
	public: list<int> keys;
	public: list<int> values;
};
