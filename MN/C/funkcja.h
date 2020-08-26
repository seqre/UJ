
template<typename T>
T funkcja(const T& x, const T& y) {
    T w = sin(x * x - 2 * (y + 1)) / exp(-y * y + cos(x * y));
    return w;
} 
