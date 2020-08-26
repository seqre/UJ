#include <iostream>
#include <iomanip>

using namespace std;

#include "../matrices/dynamicMatrix.h"
#include "../matrices/staticMatrix.h"

template<typename T, int ROW, int COL>
Matrix<T, COL, ROW> transpose(const Matrix<T, ROW, COL>& m) {
    const int R = m.numberOfRows();
    const int C = m.numberOfColumns();

    Matrix<T, COL, ROW> matrix(C, R);

    for (int r = 1; r <= R; ++r) {
        for (int c = 1; c <= C; ++c) {
            matrix(c, r) = m(r, c);

        }
    }

    return matrix;
}

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
    cout << "------------------------\n";
}

template<int N, int M>
using SMatrix = Matrix<double, N, M>;

using DMatrix = Matrix<double, 0, 0>;

int main() {
    cout << fixed << setprecision(2);

    DMatrix m1({{1, 2,  3,  4},  // constructor from
                {5, 6,  7,  8},  // initialization_list
                {9, 10, 11, 12}});
    printMatrix(m1);

    DMatrix m2 = transpose(m1);              // copy constructor
    printMatrix(m2);

    SMatrix<4, 2> m3({
                             {1.2, 1},
                             {21,  2},
                             {34,  2},
                             {2,   32}});
    printMatrix(m3);

    SMatrix<2, 4> m4 = transpose(m3);
    printMatrix(m4);

    return 0;
}

/*
 * Expected output (or similar)
 constructor of dynamic 3x4 matrix from initializer_list
      1.00       2.00       3.00       4.00
      5.00       6.00       7.00       8.00
      9.00      10.00      11.00      12.00
------------------------
 constructor of uninitialized dynamic 4x3 matrix
      1.00       5.00       9.00
      2.00       6.00      10.00
      3.00       7.00      11.00
      4.00       8.00      12.00
------------------------
 constructor of static 4x2 matrix from initializer_list
      1.20       1.00
     21.00       2.00
     34.00       2.00
      2.00      32.00
------------------------
 constructor of static uninitialized matrix
      1.20      21.00      34.00       2.00
      1.00       2.00       2.00      32.00
------------------------
*/