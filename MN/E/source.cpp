//Marek Grzelak

#include <iostream>
#include <memory>

#include "vectalg.h"

Vector SOR(
        const std::unique_ptr<Vector[]>& ribbons,
        int matrix_size,
        int ribbons_num,
        const Vector& b,
        const Vector& _x0,
        int iterations,
        double omega
) {
    Vector x0 = _x0;
    const int range = (ribbons_num - 1) / 2;

    auto get_val = [&ribbons, ribbons_num, range](int r, int c) -> double {
        const int row = r - c + range;
        const int col = c + (r - c < 0 ? r - c : 0);

        return ribbons[row][col];
    };

    for (int iteration = 0; iteration < iterations; iteration++) {
        for (int i = 0; i < matrix_size; i++) {
            auto s = b[i];
            for (int m = std::max(0, i - range); m < std::min(i + range + 1, matrix_size); m++) {
                if (m != i) {
                    s -= get_val(i, m) * x0[m];
                }
            }
            x0[i] = (1 - omega) * x0[i] + omega * s / get_val(i, i);
        }
    }
    return x0;
}

int main() {
    int matrix_size;
    int ribbons_num;
    double omega;
    int iterations;

    std::cin >> matrix_size >> ribbons_num;

    int range = (ribbons_num - 1) / 2;

    std::unique_ptr<Vector[]> ribbons(new Vector[ribbons_num]);
    Vector y(matrix_size);
    Vector x0(matrix_size);

    for (int i = 0; i < ribbons_num; ++i) {
        int ribbon_size = matrix_size - range + (i < (ribbons_num + 1) / 2 ? i : ribbons_num - i - 1);
        ribbons[i] = Vector(ribbon_size);
        std::cin >> ribbons[i];
    }

    std::cin >> y >> x0 >> omega >> iterations;

    std::cout.setf(std::ios::scientific);
    std::cout.precision(16);
    std::cout << SOR(ribbons, matrix_size, ribbons_num, y, x0, iterations, omega) << std::endl;

    return 0;
}