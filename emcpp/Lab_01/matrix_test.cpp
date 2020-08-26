#include <iostream>
#include <algorithm>
#include "Matrix.h"
#include "MatrixWithLabel.h"

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

    cout << "Inheritance \n";
    MatrixWithLabel l0("B", 3, 4);
    MatrixWithLabel l1({{1, 2},
                        {4, 5}});
    l1.setLabel("A");
    MatrixWithLabel l2 = l1;
    MatrixWithLabel l3 = std::move(l1);
    cout << l2.getLabel() << " " << l3.getLabel() << endl;
//    cout << l1.getLabel() << endl;

    return 0;
}