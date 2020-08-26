#ifndef MEC_STRING_H
#define MEC_STRING_H


#include <cstdio>
#include <ostream>

class String {
    std::shared_ptr<std::string> data;
public:
    explicit String(size_t size = 0); // creates an empty string
    explicit String(const std::string& str); // copy column-string
    String(const String&) = default; // no copy
    String& operator=(const String&) = default; // no copy

    // makes a copy of a string if it has more than one reference.
    char& operator[](int i);

    // no copy
    char operator[](int i) const;

    // concatenation creates a new string only if both strings are non empty
    friend String operator+(const String& a, const String& b);

    // no copy
    friend std::ostream& operator<<(std::ostream& out, const String& s);
};


#endif //MEC_STRING_H
