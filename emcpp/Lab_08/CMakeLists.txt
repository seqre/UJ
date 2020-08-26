cmake_minimum_required(VERSION 3.15)
project(Lab_08)

set(CMAKE_CXX_STANDARD 17)

add_executable(${PROJECT_NAME}_type_traits type_traits.cpp)
add_executable(${PROJECT_NAME}_vector_traits Vector.h vector_traits.cpp VectorTraits.h)
add_executable(${PROJECT_NAME}_vector_policies Vector.h vector_policies.cpp VectorTraits.h VectorPolicies.h)