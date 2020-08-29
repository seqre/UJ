cmake_minimum_required(VERSION 3.15)
project(Lab_06)

set(CMAKE_CXX_STANDARD 17)

add_executable(${PROJECT_NAME}_range Range.h range.cpp)
add_executable(${PROJECT_NAME}_matrixIterator matrixIterator.cpp matrixIterator.cpp)
add_executable(${PROJECT_NAME}_filter Filter.h filter.cpp)