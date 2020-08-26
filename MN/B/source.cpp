// Marek Grzelak
#include <cstdio>
#include <cmath>
#include <algorithm>

const double LIMIT = 1e-14;

void printVector(const double* x, unsigned N) {
    for (unsigned i = 0; i < N; ++i)
        printf("%17.17f ", x[i]);
    printf("\n");
}

double* copyVector(const double* x, unsigned N) {
    double* temp = new double[N];

    for (unsigned i = 0; i < N; ++i) {
        temp[i] = x[i];
    }

    return temp;
}

double getDenominator(const double* x) {
    return x[0] * x[3] - x[1] * x[2];
}

double* inverseMatrix(const double* x, double denominator) {
    double* result = new double[4]{
            x[3] / denominator,
            (-x[1]) / denominator,
            (-x[2]) / denominator,
            x[0] / denominator
    };

    delete[] x;
    return result;
}

void negateMatrix(double* x, unsigned N) {
    for (unsigned i = 0; i < N; ++i) {
        x[i] = -x[i];
    }
}

/*
x - tablica długości N, określająca wektor, dla którego liczona jest wartość funkcji
y - tablica długości M, do której funkcja wpisze obliczone f(x). Funkcja zakłada, że
    tablica y ma co najmniej M elementów!
Df - tablica długości M*N, do której funkcja wpisze obliczoną macierz różniczki Df(x).
    Funkcja zakłada, że tablica Df ma co najmniej N*M elementów!
 */
typedef void (* FuncPointer)(const double* x, double* y, double* Df);


/*
f - wskaźnik do funkcji R3 -> R2
x - tablica liczb double długości 3, zawierająca punkt początkowy bliski miejsca zerowego
    funkcji (czyli f(x)≈(0,0))
k - określa liczbę punktów do wyznaczenia
h - krok zmiany parametru. Funkcję f możemy traktować jako
    f(a,b,c) = (f1(a,b,c),f2(a,b,c))
 */

bool is_okay(double a, double b) {
    return std::max(std::abs(a), std::abs(b)) <= LIMIT;
}

int findCurve(FuncPointer f, const double* x, unsigned k, double h) {
    const int N_X = 3;
    const int M_Y = 2;

    double* my_x = copyVector(x, N_X);
    double* my_y = new double[M_Y];
    double* my_df = new double[N_X * M_Y];
    double* my_j = NULL;
    double* my_h;
    int iterations;

    for (int i = 1; i <= k; ++i) {
        my_x[2] = x[2] + i * h;

        iterations = 100;
        while (iterations--) {
            f(my_x, my_y, my_df);

            if (is_okay(my_y[0], my_y[1])) break;

            my_j = new double[4]{my_df[0], my_df[1], my_df[3], my_df[4]};

            double denom = getDenominator(my_j);
            if (std::abs(denom) <= LIMIT) {
                return i;
            }
            my_j = inverseMatrix(my_j, denom);

            negateMatrix(my_j, 4);

            my_h = new double[2]{
                    my_j[0] * my_y[0] + my_j[1] * my_y[1],
                    my_j[2] * my_y[0] + my_j[3] * my_y[1]
            };

            my_x[0] += my_h[0];
            my_x[1] += my_h[1];

            delete[] my_h;
        }

        if (iterations > 0) {
            printVector(my_x, N_X);
        } else {
            return i;
        }
        my_x[0] = x[0];
        my_x[1] = x[1];
    }

    delete[] my_x;
    delete[] my_y;
    delete[] my_df;
    delete[] my_j;

    return 0;
}

/*
f - wskaźnik do funkcji R3 -> R
x - tablica liczb double długości 3, zawierająca punkt początkowy bliski miejsca zerowego
    funkcji (czyli f(x)≈0)
k1,k2 - określają liczby punktów do wyznaczenia
h1,h2 - kroki zmiany parametrów. Przy ustalonej wartości ostatnich dwóch zmiennych b,c jest
    to funkcja jednej zmiennej fb,c:R ->R, której miejsca zerowe możemy wyliczyć na przykład
    za pomocą metody Newtona
 */

int findSurface(FuncPointer f, double* x, unsigned k1, unsigned k2, double h1, double h2) {
    const int N_X = 3;
    const int M_Y = 1;

    double* my_x = copyVector(x, N_X);
    double* my_y = new double[M_Y];
    double* my_df = new double[N_X * M_Y];
    double my_j;
    double my_h;
    int iterations;

    for (int i = 1; i <= k1; ++i) {
        my_x[1] = x[1] + i * h1;

        for (int j = 1; j <= k2; ++j) {
            my_x[2] = x[2] + j * h2;

            iterations = 100;
            while (iterations--) {
                f(my_x, my_y, my_df);

                if (std::abs(my_y[0]) <= LIMIT) break;

                my_j = my_df[0];

                if (std::abs(my_j) <= LIMIT) {
                    return j;
                }

                my_j = 1 / my_j;
                my_j = -my_j;
                my_h = my_j * my_y[0];
                my_x[0] += my_h;
            }

            if (iterations > 0) {
                printVector(my_x, N_X);
            } else {
                return i * k1 + j;
            }
        }
        printf("\n");
        my_x[0] = x[0];
    }

    delete[] my_x;
    delete[] my_y;
    delete[] my_df;

    return 0;
}

/*
f - wskaźnik do funkcji R4 -> R2
x - tablica liczb double długości 4, zawierająca punkt początkowy spełniający warunek
    f(x[0],x[1],x[2],x[3])≈(x[0],x[1]))
k1,k2 - określają liczby punktów do wyznaczenia
h1,h2 - kroki zmiany parametrów. Państwa zadaniem jest obliczenie i wydrukowanie na ekran
    punktów stałych fa,b wraz z wartościami a,b dla (a,b) kolejno równych
 */

bool is_okay_2(double* x, double* y) {
    return std::max(std::abs(y[0]), std::abs(y[1])) <= LIMIT;
}

int findFixedPoints(FuncPointer f, double* x, unsigned k1, unsigned k2, double h1, double h2) {
    const int N_X = 4;
    const int M_Y = 2;

    double* my_x = copyVector(x, N_X);
    double* my_y = new double[M_Y];
    double* my_df = new double[N_X * M_Y];
    double* my_j = NULL;
    double* my_h;
    int iterations;

    for (int i = 1; i <= k1; ++i) {
        my_x[2] = x[2] + i * h1;

        for (int j = 1; j <= k2; ++j) {
            my_x[3] = x[3] + j * h2;

            iterations = 100;
            while (iterations--) {
                f(my_x, my_y, my_df);
                my_y[0] -= my_x[0];
                my_y[1] -= my_x[1];

                if (is_okay_2(my_x, my_y)) break;

                my_j = new double[4]{my_df[0], my_df[1], my_df[4], my_df[5]};
                my_j[0] -= 1;
                my_j[3] -= 1;

                double denom = getDenominator(my_j);
                if (std::abs(denom) <= LIMIT) {
                    return j;
                }
                my_j = inverseMatrix(my_j, denom);

                negateMatrix(my_j, 4);

                my_h = new double[2]{
                        my_j[0] * my_y[0] + my_j[1] * my_y[1],
                        my_j[2] * my_y[0] + my_j[3] * my_y[1]
                };

                my_x[0] += my_h[0];
                my_x[1] += my_h[1];

                delete[] my_h;
            }

            if (iterations > 0) {
                printVector(my_x, N_X);
            } else {
                return i * k1 + j;
            }
            my_x[0] = x[0];
            my_x[1] = x[1];
        }
        printf("\n");
    }

    delete[] my_x;
    delete[] my_y;
    delete[] my_df;
    delete[] my_j;

    return 0;
}