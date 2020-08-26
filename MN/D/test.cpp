#include <iostream>
#include "vectalg.h"
using namespace std;

Vector solveEquations(
        const Matrix & A,
        const Vector & b,
        double  eps
);

int main(){
    int n = 0;
    double eps = 0;

    // wczytywanie danych
    cin >> n;
    Matrix a(n);
    Vector b(n);
    cin >> a >> b >> eps;

    Vector x = solveEquations(a, b, eps);

    cout.precision(17);

    auto residual = residual_vector(a, b, x);
    cout << "rozwiazanie = " << x << endl;
    cout << "rezydualny = " << residual << endl;
    cout << "blad = " << residual.max_norm()
         << " limit = " << eps << endl ;
    cout << "Test " << (residual.max_norm() < eps ? "":"nie ") << "zaliczony" << endl; 
    return 0;
}	
