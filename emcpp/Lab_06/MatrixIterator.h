#ifndef MEC_MATRIXITERATOR_H
#define MEC_MATRIXITERATOR_H

#include <iostream>
#include "../Lab_05/matrices/MatrixException.h"

template<typename T, int ROW, int COL>
class Matrix {
private:
    T data[ROW * COL];

public:
    Matrix() = default;

    Matrix(int n, int m) {};

    Matrix(std::initializer_list<std::initializer_list<T>> list) {
        auto m_it = data;

        for (auto& l : list) {
            m_it = std::copy(l.begin(), l.end(), m_it);
        }
    }

    Matrix(const Matrix& m) {
        std::copy(m.getData(), m.getData() + COL * ROW, data);
    }

    explicit operator Matrix<T, 0, 0>() const {
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

    class Iterator;

    using iterator = Iterator;
    using row_iterator = Iterator;
    using col_iterator = Iterator;

    class Iterator {
    private:
        using citerator = T*;
        using cvalue = T;

        citerator iterator;
        std::size_t step;

    public:
        Iterator(citerator iterator, size_t step = 1) : iterator(iterator), step(step) {}

        Iterator& operator++() {
            iterator += step;
            return *this;
        }

        Iterator operator++(int) {
            Iterator tmp{*this};
            operator++();
            return tmp;
        }

        cvalue& operator*() {
            return *iterator;
        }

        const cvalue& operator*() const {
            return *iterator;
        }

        friend inline bool operator==(const Iterator& lhs, const Iterator& rhs) { return lhs.iterator == rhs.iterator; }

        friend inline bool operator!=(const Iterator& lhs, const Iterator& rhs) { return !operator==(lhs, rhs); }

        friend inline bool operator<(const Iterator& lhs, const Iterator& rhs) { return lhs.iterator < rhs.iterator; }

        friend inline bool operator>(const Iterator& lhs, const Iterator& rhs) { return operator<(rhs, lhs); }

        friend inline bool operator<=(const Iterator& lhs, const Iterator& rhs) { return !operator>(lhs, rhs); }

        friend inline bool operator>=(const Iterator& lhs, const Iterator& rhs) { return !operator<(lhs, rhs); }


        citerator operator->() {
            return iterator;
        }

        const citerator operator->() const {
            return iterator;
        }
    };

    Iterator begin() {
        return Iterator(data);
    }

    Iterator end() {
        return Iterator(data + ROW * COL);
    }

    Iterator row_begin(int n) {
        return Iterator(data + (n - 1) * COL);
    }

    Iterator row_end(int n) {
        return Iterator(data + n * COL);
    }

    Iterator col_begin(int n) {
        return Iterator(data + (n - 1), COL);
    }

    Iterator col_end(int n) {
        return Iterator(data + ROW * COL + (n - 1));
    }
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

#endif //MEC_MATRIXITERATOR_H
