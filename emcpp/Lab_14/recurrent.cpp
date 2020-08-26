#include <iostream>
#include <type_traits>

template<int num, int pow>
struct Power {
    static const int value = num * Power<num, pow - 1>::value;
};

template<int num>
struct Power<num, 1> {
    static const int value = num;
};

// ========================

template<int N, int K>
struct Binomial {
    static constexpr long long value = Binomial<N - 1, K>::value + Binomial<N - 1, K - 1>::value;
};

template<int N>
struct Binomial<N, 0> {
    static constexpr long long value = 1;
};

template<int N>
struct Binomial<N, N> {
    static constexpr long long value = 1;
};

template<>
struct Binomial<0, 0> {
    static constexpr long long value = 1;
};


int main() {
    std::cout << Power<5, 3>::value; // 125
    std::cout << Binomial<4, 2>::value << std::endl; //6
    std::cout << Binomial<100, 0>::value << std::endl; //1
    std::cout << Binomial<100, 1>::value << std::endl; //100
    std::cout << Binomial<100, 7>::value << std::endl; //16007560800
}