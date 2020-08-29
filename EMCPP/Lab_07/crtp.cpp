#include <iostream>

using namespace std;

struct Base {
    static int overall_objects;
    static size_t size;

    Base() {
        ++overall_objects;
    }

    Base(const Base&) {
        ++overall_objects;
    }

    virtual ~Base() {
        --overall_objects;
    }
};

int Base::overall_objects{0};
size_t Base::size{0};

template<typename T>
struct BigBase : Base {
    static int objects;

    BigBase() : Base() {
        ++objects;
        Base::size += static_cast<T*>(this)->getSize();
//        Base::size += sizeof(*static_cast<T*>(this));
    }

    BigBase(const BigBase& base) : Base(base) {
        ++objects;
        Base::size += static_cast<T*>(this)->getSize();
//        Base::size += sizeof(*static_cast<T*>(this));
    }

    static int numberOfObjects() {
        return objects;
    }

    static int totalNumberOfObjects() {
        return overall_objects;
    }

    static int totalSize() {
        return size;
    };

    ~BigBase() override {
        Base::size -= static_cast<T*>(this)->getSize();
//        Base::size -= sizeof(*static_cast<T*>(this));
        --objects;
    }
};

template<typename T>
int BigBase<T>::objects{0};

template<typename T, int N>
struct A : BigBase<A<T, N>> {
    T data[N]{};

    static size_t getSize() {
        return N * sizeof(T);
    }

};

template<typename T, typename S>
struct P : BigBase<P<T, S>> {
    T a = T{};
    S b = S{};

    P() = default;

    P(T&& a, S&& b) : a(std::forward<T&&>(a)),
                      b(std::forward<S&&>(b)) {};

    size_t getSize() {
        return sizeof(T) + sizeof(S);
    }
};

int main() {
    using At = A<int, 10>;
    using Pt = P<int, double>;
    using APt = A<Pt, 5>;
    At a1, a2;
    At* pa = new At{};

    cout << At::numberOfObjects() << " " << At::totalNumberOfObjects()
         << " " << At::totalSize() << endl;

    Pt p1{1, 5.3};
    Pt p3{p1};
    cout << At::numberOfObjects() << " " << At::totalNumberOfObjects()
         << " " << At::totalSize() << endl;
    cout << Pt::numberOfObjects() << " " << Pt::totalNumberOfObjects()
         << " " << Pt::totalSize() << endl;

    delete pa;
    cout << At::numberOfObjects() << " " << At::totalNumberOfObjects()
         << " " << At::totalSize() << endl;

    // Here total size counts elements of A::data twice.
    APt ap;
    cout << Pt::numberOfObjects() << " " << Pt::totalNumberOfObjects()
         << " " << Pt::totalSize() << endl;
    cout << APt::numberOfObjects() << " " << APt::totalNumberOfObjects()
         << " " << APt::totalSize() << endl;

    return 0;
}
/** Expected output (can depend on compiler)
3 3 120
3 5 152
2 5 152
2 4 112
7 10 280
1 10 280
*/