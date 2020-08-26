#include <iostream>
#include <iomanip>

using namespace std;

#include "../matrices/dynamicMatrix.h"
#include "../matrices/staticMatrix.h"

template<typename M>
void printMatrix(const M& m, int width = 10) {
    for (int i = 1; i <= m.numberOfRows(); ++i) {
        for (int j = 1; j <= m.numberOfColumns(); ++j) {
            if (j != 1)
                cout << " ";
            cout << setw(width) << m(i, j);
        }
        cout << endl;
    }
}

int main() {
    cout << fixed << setprecision(2);

    using DMatrix = Matrix<double, 0, 0>;
    DMatrix m0(2, 3);
    printMatrix(m0);

    DMatrix m1({{1, 2,  3,  4},  // constructor from
                {5, 6,  7,  8},  // initialization_list
                {9, 10, 11, 12}});
    printMatrix(m1);

    DMatrix m2 = m1;              // copy constructor
    m2(2, 1) = -5;
    m2(3, 2) = -20; // mutable access
    printMatrix(m2);

    DMatrix m3 = m1 + m2;         // matrix sum (with equal sizes)
    printMatrix(m3);

    try {
        auto mm = m1 * m2; // ERROR: exception
    } catch (MatrixException& e) {
        cout << " Exception : " << e.what() << endl;
    }

    DMatrix m4({
                       {1.2, 1},
                       {21,  2},
                       {34,  2},
                       {2,   32}});
    DMatrix m5 = m1 * m4;    // matrix multiplication
    printMatrix(m5);

    try {
        auto mm = m1 + m4;   // ERROR: exception
    } catch (MatrixException& e) {
        cout << " Exception : " << e.what() << endl;
    }

    return 0;
}

/*
 * Expected output (or similar)
 constructor of dynamic 2x3 matrix
      0.00       0.00       0.00
      0.00       0.00       0.00
 constructor of dynamic 3x4 matrix from initializer_list
      1.00       2.00       3.00       4.00
      5.00       6.00       7.00       8.00
      9.00      10.00      11.00      12.00
 copy constructor of dynamic matrix
      1.00       2.00       3.00       4.00
     -5.00       6.00       7.00       8.00
      9.00     -20.00      11.00      12.00
 constructor of dynamic 3x4 matrix
 move constructor of dynamic matrix
      2.00       4.00       6.00       8.00
      0.00      12.00      14.00      16.00
     18.00     -10.00      22.00      24.00
 Exception : Incompatible dimensions in operator *
 constructor of dynamic 4x2 matrix from initializer_list
 constructor of uninitialized dynamic 3x2 matrix
    153.20     139.00
    386.00     287.00
    618.80     435.00
 Exception : Incompatible dimensions in operator +

*/