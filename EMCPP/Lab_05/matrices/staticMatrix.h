#ifndef MEC_STATICMATRIX_H
#define MEC_STATICMATRIX_H

#include <iostream>
#include "MatrixException.h"

template<typename T, int ROW, int COL>
class Matrix {
private:
    T data[ROW * COL];

public:
    Matrix() {
        std::cout << "\tconstructor of static " << ROW << "x" << COL << " matrix" << std::endl;
    }

    Matrix(int n, int m) {};

    Matrix(std::initializer_list<std::initializer_list<T>> list) {
        std::cout << "\tconstructor of static " << ROW << "x" << COL << " matrix from initializer_list"
                  << std::endl;

        auto m_it = data;

        for (auto& l : list) {
            m_it = std::copy(l.begin(), l.end(), m_it);
        }
    }

    Matrix(const Matrix& m) {
        std::cout << "\tcopy constructor of static matrix" << std::endl;
        std::copy(m.getData(), m.getData() + COL * ROW, data);
    }

    explicit operator Matrix<T, 0, 0>() const {
        std::cout << "\tconversion from dynamic to static" << std::endl;

        Matrix<T, 0, 0> m(ROW, COL);
        std::copy(data, data + ROW * COL, m.getData());

        return m;
    }

    const T* getData() const {
        return data;
    }

    T* getData() {
        return data;
    }

    Matrix<T, ROW, COL>& operator=(const Matrix& m) = default;

//    Matrix(Matrix &&m) noexcept = default;

//    Matrix &operator=(Matrix &&m) noexcept = default;

    T operator()(int row, int col) const {
        return data[(row - 1) * COL + (col - 1)];
    }

    T& operator()(int row, int col) {
        return data[(row - 1) * COL + (col - 1)];
    }

    Matrix<T, ROW, COL> operator+=(const Matrix<T, ROW, COL>& rhs) {
        int i = 0;
        std::for_each(data, data + ROW * COL, [&](T& t) {
            t += rhs.getData()[i++];
        });
        return *this;
    }

    int numberOfRows() const {
        return ROW;
    }

    int numberOfColumns() const {
        return COL;
    }

    virtual ~Matrix() = default;
};

template<typename T, int ROW, int COL>
inline Matrix<T, ROW, COL> operator+(Matrix<T, ROW, COL> lhs, const Matrix<T, ROW, COL>& rhs) {
    lhs += rhs;
    return lhs;
}

template<typename T, int ROW1, int COL1, int COL2>
inline Matrix<T, ROW1, COL2> operator*(const Matrix<T, ROW1, COL1>& lhs, const Matrix<T, COL1, COL2>& rhs) {
    Matrix<T, ROW1, COL2> result;

    for (int c = 1; c <= COL2; ++c) {
        for (int r = 1; r <= ROW1; ++r) {
            for (int m = 1; m <= COL1; ++m) {
                result(r, c) += lhs(r, m) * rhs(m, c);
            }
        }
    }

    return result;
}

#endif //MEC_STATICMATRIX_H
