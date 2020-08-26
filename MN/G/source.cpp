// Marek Grzelak

#include <cmath>
#include <algorithm>
#include "source.h"

spline::spline(int n) : n(n) {
    h.reset(new double[n]{0});
    x.reset(new double[n]{0});
    y.reset(new double[n]{0});
    z.reset(new double[n]{0});
}

void spline::set_points(double* x, double* y) {
    std::copy(x, x + n, this->x.get());
    std::copy(y, y + n, this->y.get());
    calculate();
}

double spline::operator()(double z) const {
    int i = find_range(z);
    double x_minus_t = z - x.get()[i];

    double val = y.get()[i] + x_minus_t * (
            getC(i) + x_minus_t * (
                    getB(i) + x_minus_t * getA(i)
            )
    );

    return val;
}

inline int spline::find_range(const double& z) const {
    auto it = std::upper_bound(x.get(), x.get() + n, 0, [z](const double& val, const double& e) {
        return z - e < val;
    });
    return (it == x.get() + n ? 0 : (int) std::distance(x.get(), it) - 1);
}

inline double spline::getA(int i) const {
    return (z.get()[i + 1] - z.get()[i]) / (6 * h.get()[i]);
}

inline double spline::getB(int i) const {
    return z.get()[i] / 2;
}

inline double spline::getC(int i) const {
    return (y.get()[i + 1] - y.get()[i]) / h.get()[i] - (z.get()[i + 1] + 2 * z.get()[i]) * (h.get()[i] / 6);
}

void spline::calculate() {
    std::unique_ptr<double[]> b(new double[n]{0});
    std::unique_ptr<double[]> u(new double[n]{0});
    std::unique_ptr<double[]> v(new double[n]{0});

    for (int i = 0; i <= n - 2; ++i) {
        h.get()[i] = x.get()[i + 1] - x.get()[i];
        b.get()[i] = 6 * (y.get()[i + 1] - y.get()[i]) / h.get()[i];
    }

    u.get()[1] = 2 * (h.get()[0] + h.get()[1]);
    v.get()[1] = b.get()[1] - b.get()[0];

    for (int j = 2; j <= n - 2; ++j) {
        u.get()[j] = 2 * (h.get()[j - 1] + h.get()[j]) - std::pow(h.get()[j - 1], 2) / u.get()[j - 1];
        v.get()[j] = b.get()[j] - b.get()[j - 1] - h.get()[j - 1] * v.get()[j - 1] / u.get()[j - 1];
    }

    z.get()[n - 1] = 0;
    for (int k = n - 2; k > 0; --k) {
        z.get()[k] = (v.get()[k] - h.get()[k] * z.get()[k + 1]) / u.get()[k];
    }
    z.get()[0] = 0;
}