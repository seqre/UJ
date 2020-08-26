//Marek Grzelak

#include <iostream>

#include "vectalg.h"


long long factorial(int n, const std::vector<long long>& vec) {
    return n < vec.size() ? vec.at(n) : n * factorial(n - 1, vec);
}

long double calculate(const Vector& X, const Vector& T, const double& _point) {
    const auto point = static_cast<long double>(_point);
    auto result = static_cast<long double>(T[T.size() - 1]);

    for (int i = T.size() - 2; i >= 0; --i) {
        result = result * (point - X[i]) + T[i];
    }

    return result;
}

Vector interpolation(const Vector& X, const Vector& Y_, Vector& Y, const Vector& Z, const std::vector<long long>& vec) {
    Vector result(Y_.size());

    result[0] = Y[0];
    for (int i = 1; i < X.size(); ++i) {
        for (int j = 0; j < Y.size() - i; ++j) {
            if (X[j] == X[j + i]) {
                Y[j] = static_cast<double>(Y_[Z[j] + i] / static_cast<long double>(factorial(i, vec)));
            } else {
                Y[j] = (Y[j + 1] - Y[j]) / (X[j + i] - X[j]);
            }
        }
        result[i] = Y[0];
    }

    return result;
}


int main() {
    static std::vector<long long> factorial_data{{1, 1, 2, 6, 24, 120, 720, 5040, 40320, 362880, 3628800, 39916800,
                                                         479001600, 6227020800, 87178291200, 1307674368000,
                                                         20922789888000, 355687428096000, 6402373705728000,
                                                         121645100408832000, 2432902008176640000}};

    int N;
    int M;

    std::cin >> M >> N;

    Vector X(M);
    Vector Y(M);
    Vector Y_prim(M);
    Vector StartIndexes(M);
    Vector PointsToCalculate(N);

    std::cin >> X >> Y >> PointsToCalculate;

    int last = 0;
    StartIndexes[0] = last;
    Y_prim[0] = Y[0];
    for (int i = 1; i < X.size(); ++i) {
        if (X[i - 1] != X[i]) {
            last = i;
        }
        StartIndexes[i] = last;
        Y_prim[i] = Y[StartIndexes[i]];
    }

    Vector polynomial = interpolation(X, Y, Y_prim, StartIndexes, factorial_data);

    std::cout.precision(16);
    std::cout << polynomial << std::endl;

    for (const double& point : PointsToCalculate) {
        std::cout << calculate(X, polynomial, point) << " ";
    }
    std::cout << std::endl;

    return 0;
}