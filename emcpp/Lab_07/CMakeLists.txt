cmake_minimum_required(VERSION 3.15)
project(Lab_07)

set(CMAKE_CXX_STANDARD 17)

set(BOOST_ROOT "/usr/local/include/boost")
find_package(Boost 1.72.0)
if (Boost_FOUND)
    include_directories(${Boost_INCLUDE_DIRS})
endif ()

add_executable(${PROJECT_NAME}_ctrp crtp.cpp)
add_executable(${PROJECT_NAME}_proxy proxy.h proxy.cpp)
add_executable(${PROJECT_NAME}_operators operators.cpp)