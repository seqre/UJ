#ifndef LAB_01_MATRIX_H
#define LAB_01_MATRIX_H


#include <ostream>
#include <vector>
#include <iostream>


class Matrix {
private:
    int row{0};
    int column{0};
    double* data{nullptr};

public:
    Matrix() = default;

    Matrix(int n, int m) : row(n), column(m), data(new double[row * column]{0.0}) {
        std::cout << "\tconstructor of " + std::to_string(this->row) + "x" + std::to_string(this->column) + " matrix"
                  << std::endl;
    };

    Matrix(std::initializer_list<std::initializer_list<double>> list);

    Matrix(const Matrix& m);

    Matrix& operator=(const Matrix& m);

    Matrix(Matrix&& m) noexcept;

    Matrix& operator=(Matrix&& m) noexcept;

    Matrix operator-();

    double operator()(int r, int c) const;

    double& operator()(int r, int c);

    friend std::ostream& operator<<(std::ostream& os, const Matrix& obj);

    virtual ~Matrix();
};


#endif
