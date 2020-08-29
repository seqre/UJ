#include <iostream>
#include <list>
#include <cmath>

using namespace std;

template<typename T, T (* f)(T), int N>
void process(T array[]) {
    std::transform(array, array + N, array, f);
}

int main() {
    // function template process
    double a[] = {1, 2, 3, 4};
    process<double, sin, 4>(a);
    for (auto x: a)
        cout << x << " "; // 0.841471 0.909297 0.14112 -0.756802
    cout << endl;
    return 0;
}