cmake_minimum_required(VERSION 3.15)
project(Lab_09)

set(CMAKE_CXX_STANDARD 17)

add_executable(${PROJECT_NAME}_stdalgorithms ex_9_1_stdalgorithms.cpp)
add_executable(${PROJECT_NAME}_words ex_9_2_words.cpp)
add_executable(${PROJECT_NAME}_Trie ex_9_3_Trie.cpp)

file(COPY ${CMAKE_CURRENT_SOURCE_DIR}/sample.txt DESTINATION ${CMAKE_CURRENT_BINARY_DIR})