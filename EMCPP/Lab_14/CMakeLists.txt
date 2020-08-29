cmake_minimum_required(VERSION 3.15)
project(Lab_14)

set(CMAKE_CXX_STANDARD 17)

add_executable(${PROJECT_NAME}_recurrent recurrent.cpp)
add_executable(${PROJECT_NAME}_unfolding unfolding.cpp)
add_executable(${PROJECT_NAME}_integer_list integer_list.cpp)