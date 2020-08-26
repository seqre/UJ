#include <iostream>
#include <fstream>
#include <vector>
#include <map>

using namespace std;

/**
 * Trie structure
 *
 * HINT: use std::map to implement it!
 *       My implementation adds less than 25 lines of code. 
 */
class Trie {
    std::map<std::string, std::unique_ptr<Trie>> data{};
    bool last_word = false;
public:
    static void printSentence(const std::vector<std::string>& sentence) {
        for (const auto& w : sentence)
            cout << w << " ";
    }

    void add(const std::vector<std::string>& sentence) {
        cout << "Adding : ";
        printSentence(sentence);
        cout << "\n";

        auto temp = this;
        for (auto const& word : sentence) {
            if (temp->data.find(word) == temp->data.end()) {
                temp->data.emplace(word, std::make_unique<Trie>());
            }
            temp = temp->data.at(word).get();
        }
        temp->last_word = true;
    }

    void printPossibleEndings(const std::vector<std::string>& beginningOfSentence) {
        cout << "Endings for \"";
        printSentence(beginningOfSentence);
        cout << "\" are :\n";

        auto temp = this;
        for (auto const& word : beginningOfSentence) {
            temp = temp->data.at(word).get();
        }

        temp->traverseTrie();
    }

    void load(const std::string& fileName) {
        ifstream file(fileName);
        if (!file) {
            cerr << "Error when openning file " << fileName << endl;
            return;
        }
        string word;
        vector<string> sentence;
        while (file >> word) {
            sentence.push_back(word);
            // is it end of the sentence?
            if (word.find_last_of('.') != string::npos) {
                add(sentence);
                sentence.clear();
            }
        }
    }

private:
    void traverseTrie(const std::string& beginning = " >") {
        for (auto const& pair : data) {
            pair.second->traverseTrie(beginning + " " + pair.first);
        }
        if (last_word) std::cout << beginning << std::endl;
    }
};


int main() {
    Trie dictionary;
    dictionary.load("sample.txt");

    dictionary.printPossibleEndings({"Curiosity"});
    dictionary.printPossibleEndings({"Curiosity", "killed"});
    dictionary.printPossibleEndings({"The", "mouse", "was", "killed"});

    return 0;
}
/* Expected output:
Adding : Curiosity killed the cat.
Adding : Curiosity killed the mouse.
Adding : Curiosity saved the cat.
Adding : Curiosity killed the cat and the mouse.
Adding : The cat was killed by curiosity.
Adding : The mouse was killed by cat.
Adding : The mouse was killed by curiosity.
Endings for "Curiosity " are :
 > killed the cat.
 > killed the mouse.
 > killed the cat and the mouse.
 > saved the cat.

Endings for "Curiosity killed " are :
 > killed the cat.
 > killed the mouse.
 > killed the cat and the mouse.

Endings for "The mouse was killed " are :
 > by cat.
 > by curiosity.

 */