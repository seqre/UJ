#include <iostream>
#include <algorithm>
#include <iterator>
#include <cctype>
#include <map>
#include <unordered_map>
#include <chrono>


/**
 * Removes all non alphanumeric characters from given word and converts to lower case.
 * @param[in,out] word on return word consist only of lower case characters.
 */
void toLowerAlpha(std::string& s1) {
    s1.erase(std::remove_if(s1.begin(), s1.end(), [](auto const& x) { return !std::isalnum(x); }),
             s1.end());
    std::transform(s1.begin(), s1.end(), s1.begin(), tolower);
}

int main() {
    std::map<std::string, int> words;
    std::string word;
//    auto start = std::chrono::high_resolution_clock::now();
    while (std::cin >> word) {
        toLowerAlpha(word);
        if (!word.empty()) {
            auto search = words.find(word);
            if (search != words.end()) {
                ++(search->second);
            } else {
                words.emplace(word, 1);
            }
        }
    }
//    auto end = std::chrono::high_resolution_clock::now();
//    std::chrono::duration<double, std::milli> diff = end - start;
//    std::cout << "Time: " << diff.count() << std::endl;
    /*
     * MAP              201.242
     * UNORDERED MAP    208.25
     */

    std::cout << "Number of distinct words : " << words.size() << std::endl;
    std::cout << "\nThe top 20 most popular words: \n";

    std::multimap<int, std::string, std::greater<>> multi_words{};
    std::for_each(words.begin(), words.end(),
                  [&multi_words](auto const& pair) { multi_words.insert(std::make_pair(pair.second, pair.first)); });
    std::for_each_n(multi_words.begin(), 20,
                    [](auto const& pair) { std::cout << pair.second << " : " << pair.first << std::endl; });

    return 0;
}
/*
 * Expected output for ./words < hamletEN.txt

Number of distinct words : 4726

The top 20 most popular words:
the : 1145
and : 967
to : 745
of : 673
i : 569
you : 550
a : 536
my : 514
hamlet : 465
in : 437
it : 417
that : 391
is : 340
not : 315
lord : 311
this : 297
his : 296
but : 271
with : 268
for : 248
your : 242

 */