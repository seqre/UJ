#include <iostream>
#include <cmath>

#include "source.cpp"

using std::cout;
using std::endl;

double wielomian(double x) {
    return (((x - 6) * x + 11) * x) - 6;
}

double wielomianSinExp(double x) {
    return ((((x - 6) * x + 11) * x) - 4 + sin(15 * x)) * exp(-x * x);
}

double kwadrat(double x) {
    return (x * x - 2);
}

double kwadrat100(double x) {
    return 1e100 * (x * x - 2);
}

double kwadrat_10(double x) {
    return 1e-10 * (x * x - 2);
}

int main() {
    cout.precision(17);                                                                             // Spodziewany wynik
    cout << "wielomian" << endl;
    cout << wyznaczMiejsceZerowe(wielomian, 0, 4, 20, 1e-15, 1e-14) << endl;      // 1 lub 2 lub 3
    cout << wyznaczMiejsceZerowe(wielomian, 0, 40, 20, 1e-15, 1e-14) << endl;     // 1 lub 2 lub 3
    cout << wyznaczMiejsceZerowe(wielomian, 1, 2, 2, 1e-15, 1e-14) << endl;       // 1 lub 2
    cout << wyznaczMiejsceZerowe(wielomian, -150, 1.9, 20, 1e-15, 1e-14) << endl; // 1
    cout << wyznaczMiejsceZerowe(wielomian, 1.5, 2.99, 20, 1e-15, 1e-14) << endl; // 2
    cout << wyznaczMiejsceZerowe(wielomian, 2.01, 40, 20, 1e-15, 1e-14) << endl;  // 3
    cout << wyznaczMiejsceZerowe(wielomian, 1.5, 6, 20, 1e-15, 1e-14) << endl;    // 1 lub 2 lub 3

    cout << "\nwielomian sin exp" << endl;
    cout << wyznaczMiejsceZerowe(wielomianSinExp, -1, 3, 60, 1e-60, 1e-14) << endl;  // 0.43636925909804245
    cout << wyznaczMiejsceZerowe(wielomianSinExp, -3, 3, 60, 1e-160, 1e-14) << endl; // 0.43636925909804245

    cout << "\nkwadrat" << endl;
    cout << wyznaczMiejsceZerowe(kwadrat, 0, 4, 15, 1e-11, 1e-14) << endl;          // 1.414213562373095
    cout << wyznaczMiejsceZerowe(kwadrat100, 0, 4, 15, 1e-11, 1e-14) << endl;       // 1.414213562373095
    cout << wyznaczMiejsceZerowe(kwadrat_10, 0, 4, 10, 1e-10, 1e-14) << endl;       // każdy punkt z przedziału [1, 1.73205]
    cout << wyznaczMiejsceZerowe(kwadrat_10, 0, 4, 15, 1e-160, 1e-14) << endl;      // 1.414213562373095
    return 0;
}