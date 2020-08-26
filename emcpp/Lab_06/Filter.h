#ifndef MEC_FILTER_H
#define MEC_FILTER_H

#include <iostream>
#include <utility>
#include <algorithm>

template<class Container, typename Predicate>
class Filter {
private:
    Container cont;
    const Predicate& pred;

public:
    Filter(Container&& cont, const Predicate& pred) : cont(std::forward<Container>(cont)), pred(pred) {}

    class Iterator;

    using iterator = Iterator;

    class Iterator {
    private:
        using citerator = typename std::decay_t<Container>::iterator;

        Filter& filter;
        citerator iterator;

        using cvalue = decltype(*iterator);

    public:
        Iterator(Filter& filter, citerator iterator) : filter(filter), iterator(iterator) {}

        Iterator& operator++() {
            ++iterator;
            return *this;
        }

        Iterator operator++(int) {
            Iterator tmp{*this};
            operator++();
            return tmp;
        }

        cvalue& operator*() {
            while (iterator != filter.cont.end() && !filter.pred(*iterator)) {
                operator++();
            }
            return *iterator;
        }

        friend inline bool operator==(const Iterator& lhs, const Iterator& rhs) { return lhs.iterator == rhs.iterator; }

        friend inline bool operator!=(const Iterator& lhs, const Iterator& rhs) { return !operator==(lhs, rhs); }

        friend inline bool operator<(const Iterator& lhs, const Iterator& rhs) { return lhs.iterator < rhs.iterator; }

        friend inline bool operator>(const Iterator& lhs, const Iterator& rhs) { return operator<(rhs, lhs); }

        friend inline bool operator<=(const Iterator& lhs, const Iterator& rhs) { return !operator>(lhs, rhs); }

        friend inline bool operator>=(const Iterator& lhs, const Iterator& rhs) { return !operator<(lhs, rhs); }
    };

    Iterator begin() {
        return Iterator(*this, cont.begin());
    }

    Iterator end() {
        return Iterator(*this, cont.end());
    }
};

template<class Container, typename Predicate>
auto make_filter(Container&& c, Predicate p) {
    return Filter<Container, Predicate>(std::forward<Container>(c), p);
}

#endif //MEC_FILTER_H
