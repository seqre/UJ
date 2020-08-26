#include <iostream>
#include <vector>
#include <list>
#include <type_traits>
#include <cmath>

using std::cout;
using std::endl;

namespace mine {

    template<typename T>
    std::enable_if_t<std::is_arithmetic_v<T>, T> sqr(const T x) {
        return x * x;
    }

    template<typename T>
    std::enable_if_t<!std::is_arithmetic_v<T>, T> sqr(T x);

    template<>
    std::string sqr<std::string>(const std::string x) {
        return x + x;
    }

// #######################################################

    template<int N>
    int mod(int x) {
        return x % N;
    }

    template<>
    int mod<0>(int x) {
        return -x;
    }

// #######################################################

    template<typename Container>
    void print(const Container& c) {
        if (c.begin() != c.end()) {
            std::cout << *c.begin();
            std::for_each(++c.begin(), c.end(), [](auto v) {
                std::cout << " " << v;
            });
            std::cout << std::endl;
        }
    }

// #######################################################

    template<typename Cont, typename Func>
    Cont apply(const Cont& c, Func f) {
        Cont res(c.size());
        std::transform(c.begin(), c.end(), res.begin(), f);
        return res;
    }

}

// #######################################################

int main() {
    // function template  sqr<T>
    cout << mine::sqr(4) << endl;             // 16
    cout << mine::sqr(14.5) << endl;          // 210.25
    cout << mine::sqr(std::string("hey")) << endl; // heyhey

    // function template mod<N>
    cout << mine::mod<5>(131) << endl;        // 1
    cout << mine::mod<7>(131) << endl;        // 5

    // function template print
    std::vector<int> v = {1, 21, 34, 4, 15};
    mine::print(v);                    // 1 21 34 4 15

    std::list<double> l = {1, 2.1, 3.2, 6.3};
    mine::print(l);                    // 1 2.1 3.2 6.3

    // function template apply
    auto w = mine::apply(v, mine::sqr<int>);
    mine::print(w);  // 1 441 1156 16 225

    auto w2 = mine::apply(w, mine::mod<5>);
    mine::print(w2); // 1 1 1 1 0

    auto w3 = mine::apply(w, mine::mod<0>);
    mine::print(w3); // -1 -441 -1156 -16 -225


    auto l2 = mine::apply(l, mine::sqr<double>);
    mine::print(l2); // 1 4.41 10.24 39.69

    auto l3 = mine::apply(l2, mine::mod<5>);
    mine::print(l3); // 1 4 0 4

    // function sin is overloaded, we need to cast it
    auto l4 = mine::apply(l3, static_cast<double (*)(double)>(std::sin));
    mine::print(l4); // 0.841471 -0.756802 0 -0.756802

    return 0;
}