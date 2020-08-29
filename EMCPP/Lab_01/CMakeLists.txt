cmake_minimum_required(VERSION 3.15)
project(Lab_01)

set(CMAKE_CXX_STANDARD 17)

add_executable(${PROJECT_NAME}_matrix_test matrix_test.cpp Matrix.cpp Matrix.h MatrixWithLabel.cpp MatrixWithLabel.h)