cmake_minimum_required(VERSION 3.15)
project(Lab_02)

set(CMAKE_CXX_STANDARD 17)

add_executable(${PROJECT_NAME}_smart_matrix_test matrix_test.cpp Matrix.cpp Matrix.h String.cpp String.h PairOfMatrices.h)