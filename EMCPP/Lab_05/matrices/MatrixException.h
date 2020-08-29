#ifndef MEC_MATRIXEXCEPTION_H
#define MEC_MATRIXEXCEPTION_H

#include <stdexcept>

class MatrixException : public std::runtime_error {
public:
    MatrixException(const std::string& string) : runtime_error(string) {}

    const char* what() const noexcept override {
        return runtime_error::what();
    }
};


#endif //MEC_MATRIXEXCEPTION_H
