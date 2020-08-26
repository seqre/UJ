#include <iostream>
#include <type_traits>
#include <vector>

template<typename... Ts>
using void_t = void;

template<typename T, typename = void>
struct hasSize : std::false_type {
};

template<typename T>
struct hasSize<T, void_t<decltype(&T::size)>> : std::true_type {
};


template<typename T, typename = void>
struct hasValueType : std::false_type {
};

template<typename T>
struct hasValueType<T, void_t<typename T::value_type>> : std::true_type {
};

int main() {
    std::cout << std::boolalpha;
    std::cout << hasSize<int>::value << std::endl; // false
    std::cout << hasSize<std::vector<int>>::value << std::endl; //true
    std::cout << hasValueType<int>::value << std::endl; // false
    std::cout << hasValueType<std::vector<int> >::value << std::endl; //true
}
