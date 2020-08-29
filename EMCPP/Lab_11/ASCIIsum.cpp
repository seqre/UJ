#include <iostream>
#include <fstream>
#include <future>
#include <vector>

void computeAsciiSum(std::promise<long long>* p, const std::string& filename) {
    std::fstream file(filename, std::ios::in);

    long long sum = 0;

    std::string s;
    while (file >> s) {
        for (char c : s) {
            sum += (int) c;
        }
    }

    file.close();

    p->set_value(sum);
}

int main(int argc, char* argv[]) {
    std::vector<std::future<long long>> futures;
    std::vector<std::promise<long long>> promises;
    std::vector<std::thread> threads;
    futures.reserve(argc - 1);
    promises.reserve(argc - 1);
    threads.reserve(argc - 1);

    for (int i = 1; i < argc; ++i) {
        promises.emplace_back();
        futures.push_back(promises.at(i - 1).get_future());
        threads.emplace_back(computeAsciiSum, &promises.at(i - 1), argv[i]);
    }

    for (int j = 0; j < argc - 1; ++j) {
        if (threads.at(j).joinable()) {
            threads.at(j).join();
        }
    }

    for (int k = 0; k < futures.size(); ++k) {
        std::cout << "File " << argv[k + 1] << " has sum equal to: " << futures.at(k).get() << std::endl;
    }
    return 0;
}