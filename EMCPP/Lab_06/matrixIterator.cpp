#include <iostream>
#include <algorithm>
#include <numeric>
#include <iomanip>
#include <vector>
#include "MatrixIterator.h"

using namespace std;

int main() {
    using DMatrix = Matrix<double, 3, 4>;
    DMatrix m({{1, 2,  3,  4},  // constructor from
               {5, 6,  7,  8},  // initialization_list
               {9, 10, 11, 12}});
    cout << fixed << setprecision(2);
    cout << "Matrix m\n";
    for (DMatrix::iterator it = m.begin(); it != m.end(); ++it)
        cout << setw(6) << *it << " ";

    cout << endl;
    auto sum = std::accumulate(m.begin(), m.end(), 0.0);
    cout << "sum of all elements = " << sum << endl;

    auto prod = std::inner_product(m.row_begin(1), m.row_end(1), m.row_begin(2), 0.0);
    cout << "inner product of 1st and 2nd row = " << prod << endl;

    for (DMatrix::col_iterator it = m.col_begin(3); it != m.col_end(3); ++it)
        cout << setw(6) << *it << " ";
    cout << endl;

    auto prod2 = std::inner_product(m.col_begin(1), m.col_end(1), m.col_begin(3), 0.0);
    cout << "inner product of 1st and 3rd column = " << prod2 << endl;

    Matrix<std::vector<int>, 2, 3> mv = {
            {{1, 2}, {1, 4, 5},    {1, 2, 3, 4}},
            {{1},    {1, 4, 2, 3}, {5, 4}}
    };
    cout << "sizes of vectors in second row" << endl;
    for (auto it = mv.row_begin(2); it != mv.row_end(2); ++it) {
        cout << it->size() << endl;
    }
    return 0;
}

/**
* Expected output:
Matrix m
  1.00   2.00   3.00   4.00   5.00   6.00   7.00   8.00   9.00  10.00  11.00  12.00
sum of all elements = 78.00
inner product of 1st and 2nd row = 70.00
  3.00   7.00  11.00
inner product of 1st and 3rd column = 137.00
 */