#ifndef VECTALG_H
#define VECTALG_H

#include <memory>
#include <iostream>
#include <cassert>
#include <vector>
#include <cmath>
#include <limits>

#include "VectorTraits.h"
#include "VectorPolicies.h"

template<typename T, size_t N, typename Policy = SafePolicy>
class Vector {
    T data[N];
public:
    typedef std::size_t size_type;
    typedef T* pointer;
    typedef T& reference;
    typedef const T& const_reference;
    typedef typename VectorTraits<T>::Type ValueType;
    typedef typename VectorTraits<T>::ParamType ParamType;
    typedef typename VectorTraits<T>::ScalarType ScalarType;
    typedef typename VectorTraits<T>::ReturnType ReturnType;

    Vector() {
        Policy::init(data, N);
    }

    Vector(const Vector& v) = default;

    Vector& operator=(const Vector& m) = default;

    Vector(const std::initializer_list<T>& list) {
        Policy::init(data, list, N);
    }

    size_type size() const {
        return N;
    }

    ReturnType get(size_type index) const {
        Policy::check(index, N);
        return data[index];
    }

    void set(size_type index, ParamType value) {
        Policy::check(index, N);
        data[index] = value;
    }

    friend Vector operator*(ScalarType x, const Vector& v) {
        Vector result;
        for (int i = 0; i < v.size(); ++i) {
            result.set(i, VectorTraits<T>::mult(x, v.get(i)));
        }
        return result;
    }

    friend std::ostream& operator<<(std::ostream& out, const Vector& v) {
        for (int i = 0; i < v.size(); ++i) {
            out << v.get(i) << " ";
        }
        return out;
    }

    friend std::istream& operator>>(std::istream& in, Vector& v) {
        Vector::ValueType value;
        for (int i = 0; i < v.size(); ++i) {
            in >> value;
            if (in)
                v.set(i, value);
        }
        return in;
    }

};

#endif //GAUSS_VECTALG_H