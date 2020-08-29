cmake_minimum_required(VERSION 3.15)
project(Lab_13)

set(CMAKE_CXX_STANDARD 17)

add_executable(${PROJECT_NAME}_SFINAE ex1.cpp)
add_executable(${PROJECT_NAME}_enable_if ex2.cpp)
add_executable(${PROJECT_NAME}_tag_dispatching ex3.cpp)