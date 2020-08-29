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

template<int N, int M>
using SMatrix = Matrix<double, N, M>;

using DMatrix = Matrix<double, 0, 0>;

int main() {
    cout << fixed << setprecision(2);

    DMatrix m1({{1, 2,  3},  // dynamic matrix from
                {5, 6,  7},  // initialization_list
                {9, 10, 11}});
    printMatrix(m1);

    SMatrix<3, 3> m2 = {{1, -1, 0},  // static matrix
                        {2, -2, 1},
                        {4, -4, 2}};
    printMatrix(m2);

    auto m3 = (SMatrix<3, 3>) (m1);  // conversion from dynamic to static
    printMatrix(m3);

    auto m4 = m1 + m2 + m1;
    printMatrix(m4);

    auto m4d = static_cast<DMatrix>(m4);
    printMatrix(m4d);

    auto m5 = m3 * m1 * m2;
    printMatrix(m5);

    DMatrix m6({{4}});
    try {
        auto mm = m2 * m6; // ERROR: exception
    } catch (MatrixException& e) {
        cout << "Exception : " << e.what() << endl;
    }

    try {
        auto mm = m2 + m6;   // ERROR: exception
    } catch (MatrixException& e) {
        cout << "Exception : " << e.what() << endl;
    }

    return 0;
}

/*
 * Expected output (or similar)
 *
 constructor of dynamic 3x3 matrix from initializer_list
      1.00       2.00       3.00
      5.00       6.00       7.00
      9.00      10.00      11.00
 constructor of static 3x3 matrix from initializer_list
      1.00      -1.00       0.00
      2.00      -2.00       1.00
      4.00      -4.00       2.00
 conversion from dynamic to static matrix
      1.00       2.00       3.00
      5.00       6.00       7.00
      9.00      10.00      11.00
 constructor of static 3x3 matrix
 copy constructor of static matrix
 constructor of static 3x3 matrix
 copy constructor of static matrix
      3.00       3.00       6.00
     12.00      10.00      15.00
     22.00      16.00      24.00
 conversion from static to dynamic matrix
      3.00       3.00       6.00
     12.00      10.00      15.00
     22.00      16.00      24.00
 constructor of uninitialized dynamic 3x3 matrix
 constructor of uninitialized dynamic 3x3 matrix
    326.00    -326.00     144.00
    866.00    -866.00     384.00
   1406.00   -1406.00     624.00
 constructor of dynamic 1x1 matrix from initializer_list
Exception : Incompatible dimensions in operator *
Exception : Incompatible dimensions in operator +
*/