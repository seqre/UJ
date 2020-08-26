// Marek Grzelak

#include <iostream>
#include <memory>

class spline {
private:
    const std::size_t n;
    std::unique_ptr<double[]> h = nullptr;
    std::unique_ptr<double[]> x = nullptr;
    std::unique_ptr<double[]> y = nullptr;
    std::unique_ptr<double[]> z = nullptr;

public:
    spline(int n);

    void set_points(double x[], double y[]);

    double operator()(double z) const;

private:
    void calculate();

    inline double getA(int i) const;

    inline double getB(int i) const;

    inline double getC(int i) const;

    inline int find_range(const double& z) const;
};