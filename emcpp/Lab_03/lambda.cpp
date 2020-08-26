#include <iostream>
#include <vector>
#include <list>
#include <algorithm>
#include <functional>
#include <cassert>
#include <numeric>
#include <cmath>
#include <type_traits>

using std::cout;
using std::endl;

void print(const std::vector<int>& v) {
    for (auto x  : v) cout << x << " ";
    cout << endl;
}

void printPair(std::pair<unsigned, unsigned> p) {
    cout << "[power of 2: " << p.first << ", remainder: " << p.second << "]" << endl;
}

int main() {
    cout << "Comparing according to center" << endl;
    std::vector<int> v = {256, 64, 16 * 3, 16 * 9, 16, 8 * 7, 9, 3, 1, 6, 2, 5};
    print(v);

    int center = 50;
    std::sort(v.begin(), v.end(), [center](const int& lhs, const int& rhs) -> bool {
        return std::abs(center - lhs) - std::abs(center - rhs) < 0;
    });
    print(v);

    cout << endl << "Sharkovsky" << endl;
    auto sharkovsky = [](unsigned int lhs, unsigned int rhs) -> bool {
        if (lhs == rhs) return false;
        if (lhs == 1) return true;
        if (rhs == 1) return false;

        auto sharkovsky_decomposition = [](unsigned int x) -> std::pair<unsigned, unsigned> {
            int i = 0;
            while (x % 2 == 0 && x != 0) {
                i++;
                x /= 2;
            }
            return {i, x};
        };

        const auto lp = sharkovsky_decomposition(lhs);
        const auto rp = sharkovsky_decomposition(rhs);

        if (lp.second == 1) {
            if (rp.second == 1) {
                return lhs < rhs;
            }
            return true;
        }

        if (rp.second == 1) return false;

        return rp < lp;
    };

    assert(sharkovsky(1, 2));
    assert(sharkovsky(2, 4));
    assert(sharkovsky(64, 256));
    assert(sharkovsky(256, 256 * 3));
    assert(sharkovsky(256 * 9, 256 * 5));
    assert(sharkovsky(256 * 3, 64 * 9));
    assert(!sharkovsky(1, 1));
    assert(!sharkovsky(4, 2));
    assert(!sharkovsky(256, 64));
    assert(!sharkovsky(256 * 3, 256));
    assert(!sharkovsky(256 * 5, 256 * 9));
    assert(!sharkovsky(64 * 9, 256 * 3));

    std::sort(v.begin(), v.end(), sharkovsky);
    print(v);

    cout << endl << "Random integer" << endl;
    std::srand(2019);
    int a = 0, b = 40;
    auto generator = [&a, &b] {
        return std::rand() % (std::abs(a - b) + 1) + a;
    };

    std::generate(v.begin(), v.end(), generator);
    print(v);

    a = 100, b = 200;
    std::generate(v.begin(), v.end(), generator);
    print(v);

    cout << endl << "Random even integer" << endl;
    a = -10;
    b = 10;
    auto evenGenerator = [a, b] {
        int result = std::rand() % (b + 1) + a;
        return (result % 2 ? result + 1 : result);
    };
    std::generate(v.begin(), v.end(), evenGenerator);
    print(v);

    v.resize(20);
    a = 0;
    b = 100;
    std::generate(v.begin(), v.end(), evenGenerator);
    print(v);

    cout << endl << "L1 norm" << endl;
    auto l1_norm = [](auto cont) -> double {
        auto add_abs = [](double init, double x) {
            return init + std::abs(x);
        };
        double result = std::accumulate(cont.begin(), cont.end(), 0.0, add_abs);
        return result;
    };
    cout << l1_norm(v) << endl;

    std::vector<double> m = {1.4, 2.4, -2.4, -4.2, -43.3};
    cout << l1_norm(m) << endl;

    std::list<double> l(m.begin(), m.end());
    cout << l1_norm(l) << endl;

    cout << endl << "Wielomian" << endl;
    auto wielomian = [](double* a, int n) {
        return [a, n](double x) -> double {
            double result = 0;
            for (int i = 0; i <= n; i++) {
                result += a[i] * std::pow(x, i);
            }
            return result;
        };
    };

    double tab[] = {1, 2, 3, 4, 5};
    auto w1 = wielomian(tab, 4);
    cout << w1(1.0) << " " << w1(0.0) << " " << w1(2.0) << endl;

    auto w2 = wielomian(tab, 2);
    cout << w2(1.0) << " " << w2(0.0) << " " << w2(2.0) << endl;

    return 0;
}


/**
Expected output (or similar): 
256 64 48 144 16 56 9 3 1 6 2 5 
48 56 64 16 9 6 5 3 2 1 144 256

1 2 16 64 256 144 48 56 6 9 5 3

11 33 7 4 40 20 36 27 4 38 31 18
142 135 106 164 160 189 152 196 156 102 198 114

0 -2 -6 -8 -2 -10 -2 -4 -4 0 -8 0 
-6 -2 -4 -8 8 -6 0 -4 8 -2 6 8 -8 -8 2 10 10 -6 6 2

114
53.7
53.7

15 1 129
6 1 17
*/
