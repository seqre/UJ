// Marek Grzelak
#include <cmath>
#include <algorithm>

double wyznaczMiejsceZerowe(
        double (* f)(double), // funkcja której zera szukamy w [a0, b0]
        double a0,            // lewy koniec przedziału
        double b0,            // prawy koniec przedziału
        int M,                // maksymalna dozwolona liczba wywołań funkcji f
        double eps,           // spodziewana dokładność zera
        double delta          // wystarczający błąd bezwzględny wyniku
) {
    double a = a0;
    double b = b0;
    double b_prev;
    double s = a;
    double fa, fb, fb_prev, fs;
    int func_uses = 0;

    auto sgn = [](double x) -> int {
        return x > 0 ? 1 : (x < 0 ? -1 : 0);
    };

    auto secant_val = [](double b_prev, double fb_prev, double b, double fb) -> double {
        if (fb != fb_prev) return b - ((b - b_prev) / (fb - fb_prev)) * fb;
        return (b_prev + b) / 2;
    };

    auto my_func = [f, &func_uses](double x) -> double {
        func_uses++;
        return f(x);
    };

    auto secant = [&]() -> bool {
        s = secant_val(b_prev, fb_prev, b, fb);
        fs = my_func(s);

        if (std::abs(s - b_prev) < delta || std::abs(fs) < eps) return true;

        b_prev = b;
        fb_prev = fb;
        b = s;
        fb = fs;

        return false;
    };

    auto bisection = [&]() -> bool {
        s = (a + b) / 2;
        fs = my_func(s);

        if (std::abs(s - a) < delta || std::abs(fs) < eps) return true;

        if (sgn(fa) != sgn(fs)) {
            b = s;
            fb = fs;
        } else {
            a = s;
            fa = fs;
        }

        return false;
    };

    fa = my_func(a);
    fb = my_func(b);

    if (std::abs(fa) < eps) return a;
    if (std::abs(fb) < eps) return b;

    b_prev = a;
    fb_prev = fa;

    while (sgn(fa) == sgn(fb)) {
        if (secant()) return s;
    }

    a = std::min(b_prev, b);
    fa = a == b_prev ? fb_prev : fb;
    b = std::max(b_prev, b);
    fb = b == b_prev ? fb_prev : fb;

    while (func_uses < (M / 2)) {
        if (bisection()) return s;
    }

    b_prev = a;
    fb_prev = fa;

    while (func_uses < M) {
        if (secant()) return s;
    }

    return s;
}