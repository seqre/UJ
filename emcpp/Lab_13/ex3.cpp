#include <iostream>
#include <list>
#include <vector>
#include <type_traits>
#include <iterator>
#include <algorithm>


template<typename Cont>
double median_impl(Cont c, std::random_access_iterator_tag) {
    std::sort(c.begin(), c.end());
    std::size_t size = c.size();
    if (size % 2 != 0) {
        return c[(size - 1) / 2];
    } else {
        return (c[(size - 1) / 2] + c[size / 2]) / 2.0;
    }
}

template<typename Cont>
double median_impl(Cont c, std::forward_iterator_tag) {
    c.sort();
    std::size_t size = c.size();
    auto b = c.begin();
    std::advance(b, (size - 1) / 2);
    auto val = *b;

    if (size % 2 != 0) {
        return val;
    } else {
        std::advance(b, 1);
        return (val + *b) / 2.0;
    }
}


template<typename Cont>
double median(Cont c) {
    return median_impl(c, typename std::iterator_traits<decltype(std::declval<Cont>().begin())>::iterator_category());
}

int main() {
    std::list<int> a{3, 2, 5, 1, 4};
    std::cout << median(a) << std::endl; // 3
    std::vector<int> v{3, 1, 4, 2};
    std::cout << median(v) << std::endl; // 2.5
}