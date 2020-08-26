#ifndef LAB_01_MATRIXWITHLABEL_H
#define LAB_01_MATRIXWITHLABEL_H

#include "Matrix.h"


class MatrixWithLabel : public Matrix {
private:
    std::string label = "A";
public:
    using Matrix::Matrix;

    MatrixWithLabel(std::string label, int n, int m);

    MatrixWithLabel(std::string label, const std::initializer_list<std::initializer_list<double>>& list);

    [[nodiscard]] const std::string& getLabel() const;

    void setLabel(const std::string& label);

    MatrixWithLabel(const MatrixWithLabel& m);

    MatrixWithLabel(MatrixWithLabel&& m) noexcept = default;

    MatrixWithLabel& operator=(MatrixWithLabel&& m) noexcept = default;
};


#endif
