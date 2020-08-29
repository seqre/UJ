#include "MatrixWithLabel.h"

#include <utility>

const std::string& MatrixWithLabel::getLabel() const {
    return label;
}

void MatrixWithLabel::setLabel(const std::string& label) {
    MatrixWithLabel::label = label;
}

MatrixWithLabel::MatrixWithLabel(std::string label, int n, int m) : Matrix(n, m), label(std::move(label)) {}

MatrixWithLabel::MatrixWithLabel(std::string label,
                                 const std::initializer_list<std::initializer_list<double>>& list) : Matrix(list),
                                                                                                     label(std::move(
                                                                                                             label)) {}

MatrixWithLabel::MatrixWithLabel(const MatrixWithLabel& m) : Matrix(m), label(m.label) {
    std::cout << "\tlabel copy constructor" << std::endl;
}
