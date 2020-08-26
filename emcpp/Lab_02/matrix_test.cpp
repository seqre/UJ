#include <iostream>
#include <algorithm>

#include "Matrix.h"
#include "String.h"
#include "PairOfMatrices.h"

using std::cout;
using std::endl;


int main() {
    Matrix m1;
    Matrix m2(3, 4);
    Matrix m3({{1,  2,   3},
               {32, 23,  22},
               {3,  234, 23, 44}});
    cout << m2(1, 1) << endl;  // 0
    cout << m2 << endl;
    cout << m3(2, 2) << endl;  // 23
    cout << m3 << endl;

    cout << "Copy semantics \n";
    Matrix m4 = m2;
    m4 = m3;

    cout << "Move semantics \n";
    Matrix m7 = std::move(m2);
    m4 = -m3;

    cout << "Copy elision \n";
    Matrix m6 = -m4;
    Matrix* pm = new Matrix(-m4);
    cout << m6(2, 1) << endl; // 32

    String a("hi");
    String b;

    const String c = a;
    String d = a + b;

    a[0] = 'l';
    a[1] = 'l';

    d = c + a;

    cout << c << " " << d << endl;
    cout << c[0] << endl;

    {
        Matrix m1({{1, 2},
                   {4, 4}});
        Matrix m2(4, 5);
        PairOfMatrices p1{m1, std::move(m2)};
//      PairOfMatrices p2 = p1; // ERROR!
        PairOfMatrices p3 = std::move(p1);
        Matrix a = p3.left;
        Matrix b = p3.right;
        PairOfMatrices p4{a, b};
//      p1 = p4; // ERROR!
        p1 = std::move(p4);
    }

    return 0;
}