#include <iostream>
#include "Vector.h"

using namespace std;

int main() {
    Vector<double, 4> a{};
    for (int i = 0; i < a.size(); i++) {
        a.set(i, 2.0 - i);
    }
    cout << "a   = " << a << endl;
    cout << "5*a = " << 5 * a << endl;

    Vector<int, 5> b{1, 2, 5, 6, 8};
    cout << "b   = " << b << endl;
    cout << "2*b = " << 2 * b << endl;

    Vector<string, 4> c{"curiosity", "killed", "the", "cat"};
    cout << "c   = " << c << endl;
    cout << "2*c = " << 2 * c << endl;

    return 0;
}
/**
 * Expected output:
a   = 2 1 0 -1
5*a = 10 5 0 -5
b   = 1 2 5 6 8
2*b = 2 4 10 12 16
c   = curiosity killed the cat
2*c = curiositycuriosity killedkilled thethe catcat

 */