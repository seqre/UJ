cmake_minimum_required(VERSION 3.15)
project(Lab_10)

set(CMAKE_CXX_STANDARD 17)

add_executable(${PROJECT_NAME}_literals ex_10_1_literals.cpp)
add_executable(${PROJECT_NAME}_chrono ex_10_2_chrono.cpp)
add_executable(${PROJECT_NAME}_regex findLinks.cpp)