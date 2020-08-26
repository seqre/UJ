// Marek Grzelak

#include <algorithm>
#include <functional>
#include <numeric>

#include "vectalg.h"

inline Vector get_scale(const Matrix& m) {
    const unsigned int N = m.size();
    Vector result(N);

    for (int i = 0; i < N; ++i) {
        result[i] = std::accumulate(m.begin() + i * N, m.begin() + (i + 1) * N, 0.0,
                                    [](const double a, const double b) -> double { return std::max(a, std::abs(b)); });
    }

    return result;
}

inline int which_row(const Matrix& m, const Vector& scales, const std::vector<int>& perm, const int& k) {
    const unsigned int N = m.size();
    Vector result(N - k);
    int row = k;
    double val = 0;
    double temp;

    for (int r = k; r < N; ++r) {
        temp = std::abs(m(perm[r], k) / scales[perm[r]]);
        if (temp > val) {
            row = r;
            val = temp;
        }
    }

    return row;
}

Vector solveEquations(
        const Matrix& A,   // Macierz
        const Vector& b,   // Wektor
        double eps         // dopuszczalny błąd
) {
    const int N = A.size();
    Matrix mA(A);
    Vector mB(b);
    Vector x(N);

    Vector scales = get_scale(mA);
    std::vector<int> perm(N);
    std::iota(perm.begin(), perm.end(), 0);

    int designed_row;
    double scale;
    for (int k = 0; k < N - 1; ++k) {
        designed_row = which_row(mA, scales, perm, k);
        std::swap(perm[k], perm[designed_row]);

        for (int r = k + 1; r < N; ++r) {
            scale = mA(perm[r], k) / mA(perm[k], k);
            for (int val = k; val < N; ++val) {
                mA(perm[r], val) -= mA(perm[k], val) * scale;
            }
//            mA(perm[r], k) = scale;             // For LU
            mB[perm[r]] -= mB[perm[k]] * scale;
        }
    }

    double temp;
    for (int i = N - 1; i >= 0; --i) {
        temp = 0;
        for (int j = i + 1; j < N; ++j) {
            temp += mA(perm[i], j) * x[j];
        }
        x[i] = (mB[perm[i]] - temp) / mA(perm[i], i);
    }

    Vector res = residual_vector(A, b, x);
    if (res.max_norm() >= eps) {
        Vector correction = solveEquations(A, res, eps);
        std::transform(x.begin(), x.end(), correction.begin(), x.begin(), std::plus<double>());
        res = residual_vector(A, res, x);
    }

    return x;
}