#ifndef MEC_RANGE_H
#define MEC_RANGE_H

#include <utility>

template<typename T>
class Range {
private:
    T start;
    T stop;
    T step;

public:
    explicit Range(T stop) : Range(0, stop, 1) {}

    Range(T start, T stop, T step = 1) : start(start), stop(stop), step(step) {}

    class Iterator {
    private:
        T index;
        const Range& range;

    public:
        Iterator(const Range& range, T index = 0) : index(index), range(range) {}

        Iterator& operator++() {
            index = std::min(index + range.step, range.stop);
            return *this;
        }

        Iterator operator++(int) {
            Iterator tmp{*this};
            operator++();
            return tmp;
        }

        T operator*() {
            return index;
        }

        friend inline bool operator==(const Iterator& lhs, const Iterator& rhs) { return lhs.index == rhs.index; }

        friend inline bool operator!=(const Iterator& lhs, const Iterator& rhs) { return !operator==(lhs, rhs); }

        friend inline bool operator<(const Iterator& lhs, const Iterator& rhs) { return lhs.index < rhs.index; }

        friend inline bool operator>(const Iterator& lhs, const Iterator& rhs) { return operator<(rhs, lhs); }

        friend inline bool operator<=(const Iterator& lhs, const Iterator& rhs) { return !operator>(lhs, rhs); }

        friend inline bool operator>=(const Iterator& lhs, const Iterator& rhs) { return !operator<(lhs, rhs); }
    };

    Iterator begin() {
        return Iterator(*this, start);
    }

    Iterator end() {
        return Iterator(*this, stop);
    }
};

template<typename T>
Range<T> make_range(T&& stop) {
    return Range<T>(0, std::forward<T>(stop), 1);
}

template<typename T>
Range<T> make_range(T&& start, T&& stop, T&& step = 1) {
    return Range<T>(std::forward<T>(start), std::forward<T>(stop), std::forward<T>(step));
}

#endif //MEC_RANGE_H
