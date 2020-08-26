cmake_minimum_required(VERSION 3.15)
project(Lab_11)

set(CMAKE_CXX_STANDARD 17)

add_executable(${PROJECT_NAME}_random random.cpp)
add_executable(${PROJECT_NAME}_mandelbrot
        mandelbrot/EasyBMP.cpp
        mandelbrot/ex_11_2_Mandelbrot.cpp
        )
add_executable(${PROJECT_NAME}_ascii ASCIIsum.cpp)
