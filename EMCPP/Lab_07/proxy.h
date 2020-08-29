#ifndef MEC_PROXY_H
#define MEC_PROXY_H

#include "boost/type_index.hpp"
#include <typeinfo>

void privShowNames(int i) {
}

template<typename T, typename ...Types>
void privShowNames(int i, T&& t, Types&& ...args) {
    using boost::typeindex::type_id_with_cvr;
    std::cout << i << " > " << type_id_with_cvr<T>().pretty_name() << " [" << typeid(T).name() << "] = " << t
              << std::endl;
    privShowNames(i + 1, std::forward<Types>(args)...);
}

template<typename ...Types>
void showNames(Types&& ...args) {
    privShowNames(1, std::forward<Types>(args)...);
}

template<typename FUNCTOR>
class Proxy {
    FUNCTOR f;

public:
    Proxy(FUNCTOR&& f) : f(std::forward<FUNCTOR>(f)) {}

    template<typename ...Types>
    auto operator()(Types&& ...args) {
        showNames(std::forward<Types>(args)...);
        return f(std::forward<Types>(args)...);
    }
};

template<typename FUNCTOR> explicit Proxy(FUNCTOR&& f) -> Proxy<FUNCTOR>;

template<typename FUNCTOR>
Proxy<FUNCTOR> make_proxy(FUNCTOR&& f) {
    return Proxy<FUNCTOR>(std::forward<FUNCTOR>(f));
}

#endif //MEC_PROXY_H
