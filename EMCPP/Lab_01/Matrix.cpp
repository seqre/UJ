#include <iostream>
#include "Matrix.h"


bool cmp(const std::initializer_list<double>& a, const std::initializer_list<double>& b) {
    return a.size() - b.size();
}

double Matrix::operator()(int x, int y) const {
    if (!this->data) {
        return 0;
    }

    return this->data[(x - 1) * this->column + (y - 1)];
}

std::ostream& operator<<(std::ostream& os, const Matrix& obj) {
    os << "[\n";
    for (int r = 1; r <= obj.row; r++) {
        os << "\t[";
        for (int c = 1; c <= obj.column; c++) {
            os << obj(r, c);
            if (c < obj.column) {
                os << ", ";
            }
        }
        os << "]";
        if (r < obj.row) {
            os << ",";
        }
        os << "\n";
    }
    os << "]";
    return os;
}

Matrix::Matrix(const std::initializer_list<std::initializer_list<double>> list) : row(list.size()) {
    size_t max_length = std::max(list, cmp).size();

    this->column = max_length;
    data = new double[row * column]{0.0};

    std::cout << "\tconstructor of " + std::to_string(this->row) + "x" + std::to_string(this->column) +
                 " matrix from initializer_list"
              << std::endl;

    auto m_it = this->data;

    for (auto& l : list) {
        m_it = std::copy(l.begin(), l.end(), m_it);
        m_it = std::fill_n(m_it, max_length - l.size(), 0.0);
    }
}

Matrix::Matrix(const Matrix& m) : row(m.row), column(m.column), data(new double[row * column]) {
    std::cout << "\tcopy constructor" << std::endl;
    std::copy(m.data, m.data + this->column * this->row, this->data);
}

Matrix& Matrix::operator=(const Matrix& m) {
    std::cout << "\tcopy assignment operator" << std::endl;
    if (this == &m) {
        return *this;
    }

    if (m.row != this->row && m.column != this->column) {
        delete[] this->data;
        this->data = nullptr;
        this->data = new double[m.row * m.column];
        this->row = m.row;
        this->column = m.column;
    }
    std::copy(m.data, m.data + this->column * this->row, this->data);

    return *this;
}

Matrix::~Matrix() {
    if (this->data) {
        delete[] this->data;
    }
}

Matrix::Matrix(Matrix&& m) noexcept: row(m.row), column(m.column), data(m.data) {
    std::cout << "\tmove constructor" << std::endl;
    m.data = nullptr;
}

Matrix& Matrix::operator=(Matrix&& m) noexcept {
    std::cout << "\tmove assignment operator" << std::endl;
    if (this == &m) {
        return *this;
    }

    delete[] this->data;
    this->data = std::exchange(m.data, nullptr);
    this->row = std::exchange(m.row, 0);
    this->column = std::exchange(m.column, 0);

    return *this;
}

double& Matrix::operator()(int x, int y) {
    return this->data[(x - 1) * this->column + (y - 1)];
}

