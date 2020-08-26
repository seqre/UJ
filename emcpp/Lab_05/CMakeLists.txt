cmake_minimum_required(VERSION 3.15)
project(Lab_05)

set(CMAKE_CXX_STANDARD 17)

add_executable(${PROJECT_NAME}_static tests/staticMatrixTest.cpp matrices/staticMatrix.h)
add_executable(${PROJECT_NAME}_dynamic tests/dynamicMatrixTest.cpp matrices/MatrixException.h matrices/dynamicMatrix.h)
add_executable(${PROJECT_NAME}_mixed tests/mixedMatrixTest.cpp)
add_executable(${PROJECT_NAME}_transpose tests/transpose.cpp)
