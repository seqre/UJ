#include <iostream>

using namespace std;

const bool DEBUG = false;

template<int N>
class Vector {
    int data[N];
public:
    Vector() {
        cout << " Default constr" << endl;
    }

    Vector(std::initializer_list<int> list) {
        cout << " Init list constr" << endl;
        auto it = list.begin();
        for (int i = 0; i < N; i++) {
            data[i] = *it++;
        }
    }

    Vector(const Vector& m) {
        std::copy(m.data, m.data + N, data);
        cout << " Copy constr" << endl;
    }

    int operator[](int index) const {
        return data[index];
    }

    int& operator[](int index) {
        return data[index];
    }

    friend ostream& operator<<(ostream& out, const Vector& m) {
        for (auto x : m.data) {
            cout << x << ", ";
        }
        return out;
    }
};

template<typename L, typename R>
class Node {
protected:
    L left;
    R right;

public:
    Node(L&& l, R&& r) : left(std::forward<L>(l)), right(std::forward<R>(r)) {
        if (DEBUG) std::cout << "\t\tCreating Node: " << this << std::endl;
    }

    virtual ~Node() {
        if (DEBUG) std::cout << "\t\tDeleting Node: " << this << std::endl;
    }

    virtual int operator[](int i) const = 0;

    template<int N>
    operator Vector<N>() {
        Vector<N> res;
        for (int i = 0; i < N; ++i) {
            res[i] = (*this)[i];
        }
        return res;
    }
};

template<typename L, typename R>
class AddNode : public Node<L, R> {
public:
    AddNode(L&& l, R&& r) : Node<L, R>(std::forward<L>(l), std::forward<R>(r)) {
        if (DEBUG) std::cout << "\t\tCreating AddNode: " << this << std::endl;
    }

    ~AddNode() override {
        if (DEBUG) std::cout << "\t\tDeleting AddNode: " << this << std::endl;
    }

    int operator[](int i) const override {
        return this->left[i] + this->right[i];
    }
};

template<typename L, typename R>
class SubNode : public Node<L, R> {
public:
    SubNode(L&& l, R&& r) : Node<L, R>(std::forward<L>(l), std::forward<R>(r)) {
        if (DEBUG) std::cout << "\t\tCreating SubNode: " << this << std::endl;
    }

    ~SubNode() override {
        if (DEBUG) std::cout << "\t\tDeleting SubNode: " << this << std::endl;
    }

    int operator[](int i) const override {
        return this->left[i] - this->right[i];
    }
};

template<typename L, typename R>
class MultNode : public Node<L, R> {
public:
    MultNode(L&& l, R&& r) : Node<L, R>(std::forward<L>(l), std::forward<R>(r)) {
        if (DEBUG) std::cout << "\t\tCreating MultNode: " << this << std::endl;
    }

    ~MultNode() override {
        if (DEBUG) std::cout << "\t\tDeleting MultNode: " << this << std::endl;
    }

    int operator[](int i) const override {
        return this->left * this->right[i];
    }
};

template<typename L, typename R>
AddNode<L, R> operator+(L&& left, R&& right) {
    return AddNode<L, R>(std::forward<L>(left), std::forward<R>(right));
}

template<typename L, typename R>
SubNode<L, R> operator-(L&& left, R&& right) {
    return SubNode<L, R>(std::forward<L>(left), std::forward<R>(right));
}

template<typename L, typename R>
MultNode<L, R> operator*(L&& left, R&& right) {
    return MultNode<L, R>(std::forward<L>(left), std::forward<R>(right));
}

int main() {
    using V = Vector<10>;
    V v{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    V x(v);
    V y{4, 4, 2, 5, 3, 2, 3, 4, 2, 1};

    cout << "Lazy operations :\n";
    // It does not create temporary Vectors
    // It computes resulting vector coordinate by coordinate
    // (evaluating whole expression)
    V z = v + x + 3 * y - 2 * x;
    cout << z << endl;

    // Computes only one coordinate of Vector 	
    int e = (z + x + y)[2];
    cout << " e = " << e << endl;
    return 0;
}
/**
 Init list constr
 Copy constr
 Init list constr
Lazy operations :
 Default constr
12, 12, 6, 15, 9, 6, 9, 12, 6, 3,
e = 11
 */