#ifndef MEC_VECTORPOLICIES_H
#define MEC_VECTORPOLICIES_H

#include <algorithm>

#include "VectorTraits.h"

class SafePolicy {
public:
    template<typename T>
    static void init(T* data, int N) {
        const T& val = VectorTraits<T>::default_value();
        std::fill(data, data + N, val);
    }

    template<typename T>
    static void init(T* data, const std::initializer_list<T>& list, int N) {
        if (list.size() <= N) {
            std::copy(list.begin(), list.end(), data);
        } else {
//            throw std::exception();
            std::cout << "exception" << std::endl;
        }
    }

    static void check(int index, int N) {
        if (index < 0 || N <= index) {
//            throw std::exception();
            std::cout << "exception" << std::endl;
        }
    }
};

class FastPolicy {
public:
    template<typename T>
    static void init(T& data, int N) {}

    template<typename T>
    static void init(T* data, const std::initializer_list<T>& list, int N) {
        std::copy(list.begin(), std::min(list.end(), list.begin() + N), data);
    }

    static void check(int index, int N) {}
};

class InitFastPolicy {
public:
    template<typename T>
    static void init(T* data, int N) {
        const T& val = VectorTraits<T>::default_value();
        std::fill(data, data + N, val);
    }

    template<typename T>
    static void init(T* data, const std::initializer_list<T>& list, int N) {
        std::copy(list.begin(), std::min(list.end(), list.begin() + N), data);
    }

    static void check(int index, int N) {}
};

#endif //MEC_VECTORPOLICIES_H
