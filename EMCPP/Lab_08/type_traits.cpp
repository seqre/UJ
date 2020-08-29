#include <iostream>
#include <limits>

using namespace std;

/**
 * Outputs information about numerical type T:
 * (un)signed, (not) integer, min & max values
*/
template<typename T>
void info(T x) {
    typedef typename std::numeric_limits<T> snl;

    std::cout << (snl::is_signed ? "signed" : "unsigned") << ", "
              << (snl::is_integer ? "integer" : "not integer") << ", "
              << "min : " << snl::min() << " "
              << "max : " << snl::max() << std::endl;
}

int main() {
    info(1);
    info(2.0f);
    info(3U);
    return 0;
}
/**
 * Expected output:
    signed,  integer, min : -2147483648 max : 2147483647
    signed, not integer, min : 1.17549e-38 max : 3.40282e+38
    unsigned,  integer, min : 0 max : 4294967295
 */