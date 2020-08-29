#include <iostream>
#include <type_traits>
#include <vector>

// From ex 1

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

// End from ex 1

namespace v1 {
    template<typename T>
    typename std::enable_if_t<!(hasSize<T>::value && hasValueType<T>::value), std::size_t>
    getSize(const T& x) {
        return sizeof(x);
    }


    template<typename T>
    typename std::enable_if_t<(hasSize<T>::value && hasValueType<T>::value), std::size_t>
    getSize(const T& x) {
        return x.size() * sizeof(typename T::value_type);
    }
}

namespace v2 {
    template<typename T>
    std::size_t getSize(const T& x) {
        if constexpr (hasSize<T>::value && hasValueType<T>::value) {
            return x.size() * sizeof(typename T::value_type);
        } else {
            return sizeof(x);
        }
    }
}

int main() {
    std::cout << std::boolalpha;
    std::vector<int> v{1, 2, 3, 4, 5};
    std::cout << v1::getSize(5) << std::endl; // 4
    std::cout << v1::getSize(v) << std::endl; // 20
    std::cout << v2::getSize(5) << std::endl; // 4
    std::cout << v2::getSize(v) << std::endl; // 20
}
