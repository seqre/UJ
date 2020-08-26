//
// Created by kapela on 08.04.2020.
// Reduced by seqre
//

#ifndef GAUSS_VECTALG_H
#define GAUSS_VECTALG_H

#include <memory>
#include <iostream>
#include <cassert>
#include <vector>
#include <cmath>


class Vector {
private:
    size_t n;
    std::unique_ptr<double[]> data = nullptr;
public:

    Vector(size_t n = 0) : n(n) {
        data.reset(new double[n]);
    }

    Vector(const Vector& m) : n(m.n), data(new double[m.n]) {
        std::copy(m.data.get(), m.data.get() + n, data.get());
    }

    Vector& operator=(const Vector& m) {
        if (this != &m) {
            if (n != m.n) {
                data.reset(new double[m.n]);
                n = m.n;
            }
            std::copy(m.data.get(), m.data.get() + n, data.get());
        }
        return *this;
    }

    size_t size() const {
        return n;
    }

    double operator[](size_t index) const {
        assert(index < n);
        return data[index];
    }

    double& operator[](size_t index) {
        assert(index < n);
        return data[index];
    }

    typedef double* iterator;

    iterator begin() {
        return data.get();
    }

    iterator end() {
        return data.get() + n;
    }

    typedef const double* const_iterator;

    const_iterator begin() const {
        return data.get();
    }

    const_iterator end() const {
        return data.get() + n;
    }
};

inline std::ostream& operator<<(std::ostream& out, const Vector& m) {
    for (auto x : m) {
        out << x << "\t";
    }
    return out;
}

inline std::istream& operator>>(std::istream& in, Vector& m) {
    for (auto& x : m) {
        in >> x;
    }
    return in;
}

#endif //GAUSS_VECTALG_H
