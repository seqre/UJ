#include <iostream>
#include <memory>
#include "Matrix.h"

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
    const auto max_length = std::max(list, [](const auto& a, const auto& b) {
        return a.size() - b.size();
    }).size();

    this->column = max_length;
    data = std::make_unique<double[]>(row * column);

    std::cout << "\tconstructor of " << this->row << "x" << this->column << " matrix from initializer_list"
              << std::endl;

    auto m_it = this->data.get();

    for (auto& l : list) {
        m_it = std::copy(l.begin(), l.end(), m_it);
        m_it = std::fill_n(m_it, max_length - l.size(), 0.0);
    }
}

Matrix::Matrix(const Matrix& m) : row(m.row), column(m.column), data(std::make_unique<double[]>(row * column)) {
    std::cout << "\tcopy constructor" << std::endl;
    std::copy(m.data.get(), m.data.get() + this->column * this->row, this->data.get());
}

Matrix& Matrix::operator=(const Matrix& m) {
    std::cout << "\tcopy assignment operator" << std::endl;
    if (this == &m) {
        return *this;
    }

    if (m.row != this->row && m.column != this->column) {
        this->data = std::make_unique<double[]>(m.row * m.column);
        this->row = m.row;
        this->column = m.column;
    }
    std::copy(m.data.get(), m.data.get() + this->column * this->row, this->data.get());

    return *this;
}

double& Matrix::operator()(int x, int y) {
    return this->data[(x - 1) * this->column + (y - 1)];
}

Matrix Matrix::operator-() {
    std::cout << "\tminus operator" << std::endl;
    Matrix temp(this->row, this->column);

    std::transform(this->data.get(), &this->data[this->column * this->row], temp.data.get(),
                   [](double d) { return -d; });

    return temp;
}
