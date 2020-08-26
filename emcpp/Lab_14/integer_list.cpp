#include <iostream>
#include <algorithm>

template<int...>
class IntegerList {
};

template<int num, int... Args>
class IntegerList<num, Args...> {
private:

public:
    friend std::ostream& operator<<(std::ostream& os, const IntegerList& list) {
        os << num << " ";
        ((os << Args << " "), ...);
        return os;
    }
};

template<typename f, typename s>
class Join {
public:
    using type = IntegerList<>;
};

template<int... f, int... s>
class Join<IntegerList<f...>, IntegerList<s...>> {
public:
    using type = IntegerList<f..., s...>;
};

template<typename f>
class IsSorted {
public:
    static const bool value = false;
};

template<int... f>
class IsSorted<IntegerList<f...>> {
public:
    static const bool value = (... < f);
};

template<typename f>
class Max {
public:
    static const int value = INT32_MIN;
};

template<int... f>
class Max<IntegerList<f...>> {
public:
    static const int value = std::max({f...});
};

int main() {
    using listA = IntegerList<5, -1, 5, 2, 1>;
    using listB = IntegerList<1, 4, 6, 9>;
    std::cout << "List A : " << listA() << std::endl;
    std::cout << "List B : " << listB() << std::endl;

    using listC = Join<listA, listB>::type;
    std::cout << "List C : " << listC() << std::endl;
    std::cout << std::boolalpha;
    std::cout << "Is A sorted? " << IsSorted<listA>::value << std::endl;
    std::cout << "Is B sorted? " << IsSorted<listB>::value << std::endl;
    std::cout << Max<listC>::value << std::endl;
}