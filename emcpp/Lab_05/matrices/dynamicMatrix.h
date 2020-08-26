#ifndef MEC_DYNAMICMATRIX_H
#define MEC_DYNAMICMATRIX_H

#include "staticMatrix.h"
#include "MatrixException.h"

template<typename T>
class Matrix<T, 0, 0> {
private:
    int row{0};
    int column{0};
    std::unique_ptr<T[]> data;

public:
    Matrix() = default;

    Matrix(int n, int m) : row(n), column(m), data(std::make_unique<T[]>(row * column)) {
        std::cout << "\tconstructor of dynamic " << row << "x" << column << " matrix"
                  << std::endl;
    };

    Matrix(std::initializer_list<std::initializer_list<T>> list) : row(list.size()) {
        const auto max_length = std::max(list, [](const auto& a, const auto& b) {
            return a.size() - b.size();
        }).size();

        column = max_length;
        data = std::make_unique<T[]>(row * column);

        std::cout << "\tconstructor of dynamic " << row << "x" << column << " matrix from initializer_list"
                  << std::endl;

        auto m_it = data.get();

        for (auto& l : list) {
            m_it = std::copy(l.begin(), l.end(), m_it);
            m_it = std::fill_n(m_it, max_length - l.size(), 0.0);
        }
    }

    Matrix(const Matrix& m) : row(m.row), column(m.column), data(std::make_unique<T[]>(row * column)) {
        std::cout << "\tcopy constructor of dynamic matrix" << std::endl;
        std::copy(m.data.get(), m.data.get() + column * row, data.get());
    }

    template<int ROW, int COL>
    explicit operator Matrix<T, ROW, COL>() const {
        std::cout << "\tconversion from static to dynamic" << std::endl;

        Matrix<T, ROW, COL> m{};
        std::copy(data.get(), data.get() + ROW * COL, m.getData());

        return m;
    }


    const T* getData() const {
        return data.get();
    }

    T* getData() {
        return data.get();
    }

    Matrix(Matrix&& m) noexcept = default;

    Matrix& operator=(Matrix&& m) noexcept = default;

    Matrix& operator=(const Matrix& m) {
        std::cout << "\tcopy assignment operator" << std::endl;
        if (this == &m) {
            return *this;
        }

        if (m.row != row && m.column != column) {
            data = std::make_unique<T[]>(m.row * m.column);
            row = m.row;
            column = m.column;
        }
        std::copy(m.data.get(), m.data.get() + column * row, data.get());

        return *this;
    }


    Matrix<T, 0, 0> operator+=(const Matrix<T, 0, 0>& rhs) {
        if (column != rhs.numberOfColumns() || row != rhs.numberOfRows()) {
            throw MatrixException("Incompatible dimensions in operator +");
        }

        int i = 0;
        std::for_each(data.get(), data.get() + row * column, [&](T& t) {
            t += rhs.getData()[i++];
        });
        return *this;
    }

    int numberOfRows() const {
        return row;
    }

    int numberOfColumns() const {
        return column;
    }

    T operator()(int r, int c) const {
        return data[(r - 1) * column + (c - 1)];
    }

    T& operator()(int r, int c) {
        return data[(r - 1) * column + (c - 1)];
    }

    virtual ~Matrix() = default;
};

template<typename T>
inline Matrix<T, 0, 0> operator+(Matrix<T, 0, 0> lhs, const Matrix<T, 0, 0>& rhs) {
    lhs += rhs;
    return lhs;
}

template<typename T>
inline Matrix<T, 0, 0> operator*(const Matrix<T, 0, 0>& lhs, const Matrix<T, 0, 0>& rhs) {
    if (lhs.numberOfColumns() != rhs.numberOfRows()) {
        throw MatrixException("Incompatible dimensions in operator *");
    }

    const int R = lhs.numberOfRows();
    const int C = rhs.numberOfColumns();
    const int J = lhs.numberOfColumns();

    Matrix<T, 0, 0> result(R, C);

    for (int c = 1; c <= C; ++c) {
        for (int r = 1; r <= R; ++r) {
            for (int m = 1; m <= J; ++m) {
                result(r, c) += lhs(r, m) * rhs(m, c);
            }
        }
    }

    return result;
}

template<typename T, int ROW, int COL>
inline Matrix<T, ROW, COL> operator+(const Matrix<T, 0, 0>& lhs, const Matrix<T, ROW, COL>& rhs) {
    return (rhs + lhs);
}

template<typename T, int ROW, int COL>
inline Matrix<T, ROW, COL> operator+(Matrix<T, ROW, COL> lhs, const Matrix<T, 0, 0>& rhs) {
    lhs += Matrix<T, ROW, COL>(rhs);
    return lhs;
}

template<typename T, int ROW, int COL>
inline Matrix<T, 0, 0> operator*(const Matrix<T, 0, 0>& lhs, const Matrix<T, ROW, COL>& rh) {
    auto rhs = Matrix<T, 0, 0>(rh);

    if (lhs.numberOfColumns() != rhs.numberOfRows()) {
        throw MatrixException("Incompatible dimensions in operator *");
    }

    const int R = lhs.numberOfRows();
    const int C = rhs.numberOfColumns();
    const int J = lhs.numberOfColumns();

    Matrix<T, 0, 0> result(R, C);

    for (int c = 1; c <= C; ++c) {
        for (int r = 1; r <= R; ++r) {
            for (int m = 1; m <= J; ++m) {
                result(r, c) += lhs(r, m) * rhs(m, c);
            }
        }
    }

    return result;
}

template<typename T, int ROW, int COL>
inline Matrix<T, 0, 0> operator*(const Matrix<T, ROW, COL>& lh, const Matrix<T, 0, 0>& rhs) {
    auto lhs = Matrix<T, 0, 0>(lh);

    if (lhs.numberOfColumns() != rhs.numberOfRows()) {
        throw MatrixException("Incompatible dimensions in operator *");
    }

    const int R = lhs.numberOfRows();
    const int C = rhs.numberOfColumns();
    const int J = lhs.numberOfColumns();

    Matrix<T, 0, 0> result(R, C);

    for (int c = 1; c <= C; ++c) {
        for (int r = 1; r <= R; ++r) {
            for (int m = 1; m <= J; ++m) {
                result(r, c) += lhs(r, m) * rhs(m, c);
            }
        }
    }

    return result;
}

#endif //MEC_DYNAMICMATRIX_H
