//Marek Grzelak

#include <iostream>
#include <cmath>
#include <cstdio>
#include <vector>

#include "funkcja.h"

class Jet {
private:
    const static int SIZE = 6;
    std::vector<double> data;

    // =============================
    // EASIER GETTERS
    // =============================

    double f() const { return data.at(0); }

    double& f() { return data.at(0); }

    double fx() const { return data.at(1); }

    double& fx() { return data.at(1); }

    double fy() const { return data.at(2); }

    double& fy() { return data.at(2); }

    double fxx() const { return data.at(3); }

    double& fxx() { return data.at(3); }

    double fxy() const { return data.at(4); }

    double& fxy() { return data.at(4); }

    double fyy() const { return data.at(5); }

    double& fyy() { return data.at(5); }

public:
    explicit Jet(double v = 0, bool x = true) : data(std::vector<double>(SIZE)) {
        data.at(0) = v;
        if (x) {
            data.at(1) = 1.0;
        } else {
            data.at(2) = 1.0;
        }
    }

    Jet(const Jet& jet) = default;

    Jet& operator=(const Jet& a) = default;

    Jet operator-() const {
        Jet tmp(*this);
        for (int i = 0; i < SIZE; ++i) {
            tmp.data.at(i) *= -1;
        }
        return tmp;
    }

    // =============================
    // JET OPERATORS
    // =============================

    Jet& operator+=(const Jet& rhs) {
        for (int i = 0; i < SIZE; ++i) {
            data.at(i) += rhs.data.at(i);
        }
        return *this;
    }

    friend inline Jet operator+(Jet lhs, const Jet& rhs) {
        lhs += rhs;
        return lhs;
    }

    Jet& operator-=(const Jet& rhs) {
        for (int i = 0; i < SIZE; ++i) {
            data.at(i) -= rhs.data.at(i);
        }
        return *this;
    }

    friend inline Jet operator-(Jet lhs, const Jet& rhs) {
        lhs -= rhs;
        return lhs;
    }

    Jet& operator*=(const Jet& rhs) {
        const Jet tmp(*this);
        f() = tmp.f() * rhs.f();
        fx() = tmp.fx() * rhs.f() + tmp.f() * rhs.fx();
        fy() = tmp.fy() * rhs.f() + tmp.f() * rhs.fy();
        fxx() = 2 * tmp.fx() * rhs.fx() + tmp.fxx() * rhs.f() + tmp.f() * rhs.fxx();
        fxy() = tmp.fx() * rhs.fy() + tmp.fy() * rhs.fx() + tmp.fxy() * rhs.f() + tmp.f() * rhs.fxy();
        fyy() = 2 * tmp.fy() * rhs.fy() + tmp.fyy() * rhs.f() + tmp.f() * rhs.fyy();
        return *this;
    }

    friend inline Jet operator*(Jet lhs, const Jet& rhs) {
        lhs *= rhs;
        return lhs;
    }

    Jet& operator/=(const Jet& rhs) {
        const Jet tmp(*this);
        const double powrf2 = std::pow(rhs.f(), 2);
        const double powrf3 = std::pow(rhs.f(), 3);

        f() = tmp.f() / rhs.f();
        fx() = (tmp.fx() * rhs.f() - tmp.f() * rhs.fx()) / powrf2;
        fy() = (tmp.fy() * rhs.f() - tmp.f() * rhs.fy()) / powrf2;
        fxx() = (-rhs.f() * (2 * tmp.fx() * rhs.fx() + tmp.f() * rhs.fxx()) + tmp.fxx() * powrf2 +
                 2 * tmp.f() * std::pow(rhs.fx(), 2)) / powrf3;
        fxy() = (-rhs.f() * (tmp.fx() * rhs.fy() + tmp.fy() * rhs.fx() + tmp.f() * rhs.fxy()) +
                 tmp.fxy() * powrf2 + 2 * tmp.f() * rhs.fy() * rhs.fx()) / powrf3;
        fyy() = (-rhs.f() * (2 * tmp.fy() * rhs.fy() + tmp.f() * rhs.fyy()) + tmp.fyy() * powrf2 +
                 2 * tmp.f() * std::pow(rhs.fy(), 2)) / powrf3;
        return *this;
    }

    friend inline Jet operator/(Jet lhs, const Jet& rhs) {
        lhs /= rhs;
        return lhs;
    }

    // =============================
    // RIGHT-HAND CONSTANT OPERATORS
    // =============================

    Jet& operator+=(double c) {
        f() += c;
        return *this;
    }

    friend inline Jet operator+(Jet lhs, double c) {
        lhs += c;
        return lhs;
    }

    Jet& operator-=(double c) {
        f() -= c;
        return *this;
    }

    friend inline Jet operator-(Jet lhs, double c) {
        lhs -= c;
        return lhs;
    }

    Jet& operator*=(double c) {
        for (int i = 0; i < SIZE; ++i) {
            data.at(i) *= c;
        }
        return *this;
    }

    friend inline Jet operator*(Jet lhs, double c) {
        lhs *= c;
        return lhs;
    }

    Jet& operator/=(double c) {
        for (int i = 0; i < SIZE; ++i) {
            data.at(i) /= c;
        }
        return *this;
    }

    friend inline Jet operator/(Jet lhs, double c) {
        lhs /= c;
        return lhs;
    }

    // =============================
    // LEFT-HAND CONSTANT OPERATORS
    // =============================

    friend inline Jet operator+(double c, Jet rhs) {
        rhs += c;
        return rhs;
    }

    friend inline Jet operator-(double c, Jet rhs) {
        rhs -= c;
        return -rhs;
    }

    friend inline Jet operator*(double c, Jet rhs) {
        rhs *= c;
        return rhs;
    }

    friend inline Jet operator/(double c, const Jet& rhs) {
        Jet C(c);
        C /= rhs;
        return C;
    }

    // =============================
    // ELEMENTARY FUNCTIONS
    // =============================

    friend inline Jet sin(Jet rhs) {
        const Jet tmp(rhs);
        const double cosf = std::cos(tmp.f());
        const double sinf = std::sin(tmp.f());

        rhs.f() = sinf;
        rhs.fx() = tmp.fx() * cosf;
        rhs.fy() = tmp.fy() * cosf;
        rhs.fxx() = tmp.fxx() * cosf - std::pow(tmp.fx(), 2) * sinf;
        rhs.fxy() = tmp.fxy() * cosf - tmp.fy() * tmp.fx() * sinf;
        rhs.fyy() = tmp.fyy() * cosf - std::pow(tmp.fy(), 2) * sinf;
        return rhs;
    }

    friend inline Jet cos(Jet rhs) {
        const Jet tmp(rhs);
        const double sinf = std::sin(tmp.f());
        const double cosf = std::cos(tmp.f());

        rhs.f() = cosf;
        rhs.fx() = tmp.fx() * (-sinf);
        rhs.fy() = tmp.fy() * (-sinf);
        rhs.fxx() = std::pow(tmp.fx(), 2) * (-cosf) - tmp.fxx() * sinf;
        rhs.fxy() = tmp.fy() * tmp.fx() * (-cosf) - tmp.fxy() * sinf;
        rhs.fyy() = std::pow(tmp.fy(), 2) * (-cosf) - tmp.fyy() * sinf;
        return rhs;
    }

    friend inline Jet exp(Jet rhs) {
        const Jet tmp(rhs);
        const double expf = std::exp(tmp.f());

        rhs.f() = expf;
        rhs.fx() = expf * tmp.fx();
        rhs.fy() = expf * tmp.fy();
        rhs.fxx() = expf * (std::pow(tmp.fx(), 2) + tmp.fxx());
        rhs.fxy() = expf * (tmp.fy() * tmp.fx() + tmp.fxy());
        rhs.fyy() = expf * (std::pow(tmp.fy(), 2) + tmp.fyy());
        return rhs;
    }

    // =============================
    // OTHER
    // =============================

    friend void print(const Jet& obj) {
        for (unsigned i = 0; i < Jet::SIZE; ++i)
            printf("%.15f ", obj.data.at(i));
        printf("\n");
    }
};

int main() {
    int M;
    std::cin >> M;
    std::cout.precision(17);

    double x;
    double y;
    for (int i = 0; i < M; ++i) {
        std::cin >> x >> y;
        Jet X(x);
        Jet Y(y, false);
        print(funkcja(X, Y));
    }
    return 0;
}