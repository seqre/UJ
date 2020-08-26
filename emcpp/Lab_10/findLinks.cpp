#include <iostream>
#include <regex>
#include <string>

int main() {
    // INPUT
    std::string s;
    for (std::string temp; std::getline(std::cin, temp);) {
        s += temp;
    }

    //CONSTANTS
    auto const flags = std::regex_constants::optimize;

    const std::string links_inside_a_tag_regex = R"(<a\s+(?:[^>]*?\s+)?href=["'](http[s]?:\/\/[\w][\.\w]+(\/?|(\/[\w.%]+)+)\/?)["'])";
    const std::string emails_regex = R"([\w.+-]+@[\w-]+(\.[\w-]+)+)";

    auto const words_end = std::sregex_iterator();

    //LINKS
    std::regex l_regex{links_inside_a_tag_regex, flags};
    auto l_words_begin = std::sregex_iterator(s.begin(), s.end(), l_regex);


    std::cout << "Links: " << std::endl;
    for (std::sregex_iterator i = l_words_begin; i != words_end; ++i) {
        std::smatch match = *i;
        std::cout << match[1].str() << std::endl;
    }

    //EMAILS
    std::regex e_regex{emails_regex, flags};
    auto e_words_begin = std::sregex_iterator(s.begin(), s.end(), e_regex);

    std::cout << std::endl << "Emails: " << std::endl;
    for (std::sregex_iterator i = e_words_begin; i != words_end; ++i) {
        std::smatch match = *i;
        std::cout << match[0].str() << std::endl;
    }

    return 0;
}

