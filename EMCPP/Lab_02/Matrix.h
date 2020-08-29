#ifndef LAB_01_MATRIX_H
#define LAB_01_MATRIX_H


#include <ostream>
#include <vector>
#include <iostream>


class Matrix {
private:
    int row{0};
    int column{0};
    std::unique_ptr<double[]> data;

public:
    Matrix() = default;

    Matrix(int n, int m) : row(n), column(m), data(std::make_unique<double[]>(row * column)) {
        std::cout << "\tconstructor of " << this->row << "x" << this->column << " matrix"
                  << std::endl;
    };

    Matrix(std::initializer_list<std::initializer_list<double>> list);

    Matrix(const Matrix& m);

    Matrix& operator=(const Matrix& m);

    Matrix(Matrix&& m) noexcept = default;

    Matrix& operator=(Matrix&& m) noexcept = default;

    Matrix operator-();

    double operator()(int r, int c) const;

    double& operator()(int r, int c);

    friend std::ostream& operator<<(std::ostream& os, const Matrix& obj);

    virtual ~Matrix() = default;
};


#endif
