#include <iostream>
#include <vector>
#include <algorithm>
#include <iterator>
#include <numeric>
#include <cctype>
#include <ctime>
#include <thread>
#include <utility>
#include <thread>
#include <chrono>
#include <future>
#include <condition_variable>

using namespace std;

template<int N>
struct Array {
    int a[N]{};
    int working_threads = 0;
    std::mutex mutex;
//    std::mutex mutex2;

    long long sum = 0;

    int f(int x) {
        int y = x % 11;
        return (y * y + 1);
    }

    void generateArray(int threads_num) {
        int step = (N / threads_num) + (N % threads_num == 0 ? 0 : 1);

        /*
         * There are better solutions for this problem, but I interpreted exercise description literally and that's
         * how I figured out the code.
         *
         * Better way, for example, would be to execute threads within this function, although I assumed that we need
         * to execute them from main() as it how it was done at the beginning.
         */

        mutex.lock();
        if (working_threads == threads_num) working_threads = 0; // To reset counter after computeSum()
        const int start = working_threads++ * step;
        const int end = std::min(start + step, N);
        if (working_threads == threads_num) working_threads = 0; // To reset counter for computeSum()
//        std::cout << start << " " << end << std::endl;
        mutex.unlock();

        /*
         * The function is supposed to work correctly, although sometimes, after computing sum, it gives bad results.
         * From what I deduced, the problem lies within rand() function as it might be thread unsafe, what I think is
         * the root of the problem. After wrapping the invocation of rand(), sum is always correct, but it doesn't
         * speed up the process, what's more, it makes it even slower as we have additional 56n locks and n unlocks.
         */

        /*
         * Also, the whole algorithm could be improved, as these two loops are unneeded, but I guess it's the point of
         * the exercise to make current algorithm parallel, so I left it as it is.
         */

        int i = start;
        while (i < end) {
//            mutex2.lock();
            a[i++] = rand();
//            mutex2.unlock();
        }
        i = start;
        while (i < end) {
            a[i] = f(a[i]);
            i++;
        }
    }

    long long computeSum(int threads_num) {
        int step = (N / threads_num) + (N % threads_num == 0 ? 0 : 1);


        std::vector<std::future<long long>> futures;
        futures.reserve(N);
        for (int j = 0; j < N; j += step) {
            futures.push_back(
                    std::async(std::launch::async,
                               [&](int s, int e) {
                                   long long sum_i = 0;
                                   for (int i = s; i < e; ++i) {
                                       sum_i += a[i];
                                   }
                                   return sum_i;
                               },
                               j,
                               std::min(j + step, N)
                    )
            );
        }

        sum = 0;
        for (auto& x : futures) {
            sum += x.get();
        }
        return sum;
    }
};

int main() {
    srand(2019);
    using A = Array<1000>;
    A array;

    const int threads_num = 100;
    std::vector<std::thread> threads;
    threads.reserve(threads_num);

    for (int j = 0; j < threads_num; ++j) {
        threads.emplace_back(&A::generateArray, &array, threads_num);
    }
    for (int k = 0; k < threads_num; ++k) {
        if (threads.at(k).joinable()) {
            threads.at(k).join();
        }
    }

    for (int i = 0; i < 40; i++) {
        cout << array.a[0 + i] << "  ";
    }

    long long sum = array.computeSum(threads_num);
    cout << "\n sum = " << sum << endl;
}