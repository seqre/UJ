#ifndef MEC_VECTORTRAITS_H
#define MEC_VECTORTRAITS_H


template<typename T>
class VectorTraits {
public:
    typedef T Type;
    typedef Type ScalarType;
    typedef const Type& ReturnType;
    typedef const Type& ParamType;

    static Type mult(ScalarType scalarType, ParamType type) {
        return scalarType * type;
    }

    static Type default_value() {
        return Type{};
    }
};

template<>
class VectorTraits<int> {
public:
    typedef int Type;
    typedef Type ScalarType;
    typedef Type ReturnType;
    typedef Type ParamType;

    static Type mult(ScalarType scalarType, ParamType type) {
        return scalarType * type;
    }

    static Type default_value() {
        return 0;
    }
};

template<>
class VectorTraits<double> {
public:
    typedef double Type;
    typedef Type ScalarType;
    typedef Type ReturnType;
    typedef Type ParamType;

    static Type mult(ScalarType scalarType, ParamType type) {
        return scalarType * type;
    }

    static Type default_value() {
        return 0;
    }
};

template<>
class VectorTraits<std::string> {
public:
    typedef std::string Type;
    typedef int ScalarType;
    typedef const Type& ReturnType;
    typedef const Type& ParamType;

    static Type mult(ScalarType scalarType, ParamType type) {
        std::string temp;
        temp.reserve(scalarType * type.length());

        for (int i = 0; i < scalarType; ++i) {
            temp += type;
        }

        return temp;
    }

    static Type default_value() {
        return "";
    }
};


#endif //MEC_VECTORTRAITS_H
