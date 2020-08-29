
// Compilation:
//   g++ ex_11_2_Mandelbrot.cpp EasyBMP.cpp -O2 -std=c++14 -o mandelbrot

#include "EasyBMP.h"
#include <complex>
#include <chrono>
#include <vector>
#include <thread>

using namespace std;
typedef complex<double> Complex;

/**
 * Returns functional object that converts pixel position to point in the complex plane.
 * @param imageWidth   image width
 * @param imageHeight  image hight
 * @param x1           minimal x
 * @param y1           minimal y
 * @param x2           maximal x
 * @param y2           maximal y
 * @return             functional object
 */
auto scaleFromPixelToComplex(int imageWidth, int imageHeight,
                             double x1, double y1, double x2, double y2) {
    double sx = (x2 - x1) / imageWidth;
    double sy = (y2 - y1) / imageHeight;
    return [=](int x, int y) {
        return Complex(x1 + sx * x, y2 - sy * y);
    };
}

/**
 * Checks if given complex number c is in not the Mandelbrot set.
 * @param c complex number
 * @param maxNumberOfIterations  maximal number of iterations
 * @param escapeThreshold
 * @return number of iterations after which image of 0 has module greather
 *         than escapeTreshold or 0 is maxNumberOfIteration was reached.
 */
static auto MandelbrotSetIterations(Complex c,
                                    size_t maxNumberOfIterations,
                                    double escapeThreshold = 4.0
) {
    Complex x = 0;
    for (int i = 0; i < maxNumberOfIterations; i++) {
        x = x * x + c;
        if (abs(x) > escapeThreshold)
            return i + 1;
    }
    return 0;
}


int main(int argc, char* argv[]) {

    const int imageWidth = 1000;                // bitmap width
    const int imageHeight = 1000;               // bitmap height
    const char* fileName = "mandelbrot.bmp";     // file name to output bitmap

    double x1 = -2, x2 = 1,   // Rectangle in the complex plane [x1, x2]x[y1,,y2]
    y1 = -1, y2 = 1;          // that will plotted on bitmap.

    size_t maxNumberOfIteration = 2000;
    double escapeThreshold = 4;

    BMP Output;                                // We create bitmap
    Output.SetSize(imageWidth, imageHeight);  // We set bitmap sizes
    Output.SetBitDepth(24);        // We set color depth
    // Each pixel has 24 bits (8 bits for each RGB component)

    auto pixelToComplex = scaleFromPixelToComplex(imageWidth, imageHeight, x1, y1, x2, y2);

    auto iterationsToPixel = [](size_t numerOfIterations) {
        RGBApixel pixel;
        pixel.Blue = 0;
        pixel.Green = (ebmpBYTE) 0;
        pixel.Red = (ebmpBYTE) 0;
        if (numerOfIterations > 0) {
            pixel.Green = (ebmpBYTE) 255 - 10 * (numerOfIterations % 20);
            pixel.Red = (ebmpBYTE) 255 - 10 * (numerOfIterations % 10);
        }
        pixel.Alpha = 0;
        return pixel;
    };

    auto parallelizeMePls = [&](int y_start, int y_max) {
        for (unsigned y = y_start; y < y_max; y++) {
            for (unsigned x = 0; x < imageWidth; x++) {

                Complex c = pixelToComplex(x, y);

                auto numberOfIteration = MandelbrotSetIterations(c, maxNumberOfIteration, escapeThreshold);
                auto pixel = iterationsToPixel(numberOfIteration);
                Output.SetPixel(x, y, pixel);
            }
        }
        std::cout << "Thread " << std::this_thread::get_id() << " ended working on range: [" << y_start << "," << y_max
                  << ")" << std::endl;
    };


    const int threads_num = 200;
    const int step = (imageHeight / threads_num) + (imageHeight % threads_num == 0 ? 0 : 1);

    std::vector<std::thread> threads;
    threads.reserve(threads_num);

    auto start = std::chrono::steady_clock::now();
    for (int y = 0; y < imageHeight; y += step) {
        threads.emplace_back(parallelizeMePls, y, std::min(y + step, imageHeight));
    }
    for (auto& t : threads) {
        if (t.joinable()) {
            t.join();
        }
    }
    auto stop = std::chrono::steady_clock::now();

    cout << "Time used : " << std::chrono::duration_cast<std::chrono::milliseconds>(stop - start).count() << " ms."
         << endl;
    cout << "Bitmap written to " << fileName << "." << endl;
    Output.WriteToFile(fileName);
    return 0;
}

/*
 * Sequential time:     ~ 35000 ms
 * Parallel time:
 *      5   threads:    ~ 18500 ms
 *      10  threads:    ~ 15550 ms
 *      25  threads:    ~ 13500 ms
 *      50  threads:    ~ 13500 ms
 *      100 threads:    ~ 11500 ms
 *      200 threads:    ~ 11500 ms
*/
