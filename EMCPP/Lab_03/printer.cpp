#include <iostream>
#include <utility>
#include <vector>
#include <fstream>
#include <algorithm>

class Printer {
private:
    std::ostream& out;
    std::string left;
    std::string right;

public:
    Printer(std::ostream& out, std::string left, std::string right) : out(out), left(std::move(left)),
                                                                      right(std::move(right)) {};

    template<typename T>
    void operator()(T t) const {
        out << left << t << right;
    };
};

int main() {
    Printer print(std::cout, "[ ", " ] ");   // creates unary functor
    //that takes argument x of any type
    // and prints [ x ]
    print("hello");    // [ hello ]
    std::vector<int> v = {1, 2, 3, 4};
    std::for_each(v.begin(), v.end(), print);  // [ 1 ] [ 2 ] [ 3 ] [ 4 ]

    std::ofstream file("myFile.txt");
    Printer filePrinter(file, "- ", "\n");
    filePrinter(5);
    filePrinter("My text");
    return 0;
}
/** myFile.txt
- 5
- My text
*/