#include "String.h"

String::String(size_t size) : data(std::make_shared<std::string>(size, ' ')) {
}

String::String(const std::string& str) : data(std::make_shared<std::string>(str)) {
}

char& String::operator[](int i) {
    if (this->data.use_count() > 1) {
        this->data = std::make_shared<std::string>(*this->data);
    }

    return (*this->data)[i];
}

char String::operator[](int i) const {
    return (*this->data)[i];
}

String operator+(const String& a, const String& b) {
    if (!a.data->empty() && !b.data->empty()) {
        return String{*a.data + *b.data};
    } else if (!a.data->empty()) {
        return a;
    }
    return b;
}

std::ostream& operator<<(std::ostream& out, const String& s) {
    out << *s.data;
    return out;
}
