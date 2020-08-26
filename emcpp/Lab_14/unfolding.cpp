#include <iostream>

template<int N, typename T>
constexpr T DotProduct(T* a, T* b) {
    if constexpr (N == 0) {
        return 0;
    } else {
        return a[N - 1] * b[N - 1] + DotProduct<N - 1>(a, b);
    }
};

/*
 * N - rows
 * M - cols / x length
 */
template<int N, int M, typename T>
constexpr void Multiply(T* A, T* x, T* y) {
    if constexpr (N != 0) {
        y[N - 1] = DotProduct<M>(x, A + (N - 1) * M);
        Multiply<N - 1, M>(A, x, y);
    }
}


int main() {
    double a[] = {1, 2, 3};
    double b[] = {1, 1, 1};
    std::cout << DotProduct<3>(a, b) << std::endl; // 6
    double x[] = {1, 1, 0};
    double A[] = {1, 0, 0,
                  2, -5, 1};
    double y[2];
    Multiply<2, 3>(A, x, y);
    std::cout << y[0] << " " << y[1]; // 1 -3
}