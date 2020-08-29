cmake_minimum_required(VERSION 3.15)
project(Lab_04)

set(CMAKE_CXX_STANDARD 17)

add_executable(${PROJECT_NAME}_templates templates.cpp)
add_executable(${PROJECT_NAME}_compare compare.cpp)
add_executable(${PROJECT_NAME}_template template.cpp)
add_executable(${PROJECT_NAME}_selectIf selectIf.cpp)
