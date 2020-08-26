#include "vectalg.h"

int main() {
    Vector a({1,2,3,4});
    Vector b(a);

    std::transform(a.begin(), a.end(), b.begin(), a.begin(), std::plus<double>());

    std::cout << a << std::endl << b << std::endl;
}