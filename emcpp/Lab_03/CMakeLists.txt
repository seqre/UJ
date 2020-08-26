cmake_minimum_required(VERSION 3.15)
project(Lab_03)

set(CMAKE_CXX_STANDARD 17)

add_executable(${PROJECT_NAME}_function_pointers function_pointers.cpp)
add_executable(${PROJECT_NAME}_lambda lambda.cpp)
add_executable(${PROJECT_NAME}_printer printer.cpp)

