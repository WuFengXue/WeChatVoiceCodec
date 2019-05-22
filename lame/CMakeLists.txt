cmake_minimum_required(VERSION 3.6)
project(lame)

include_directories(src/main/cpp/lame/frontend)
include_directories(src/main/cpp/lame/include)
include_directories(src/main/cpp/lame/libmp3lame)
include_directories(src/main/cpp/lame/mpglib)

aux_source_directory(src/main/cpp/lame/frontend LAME_FRONTEND_SRC)
aux_source_directory(src/main/cpp/lame/libmp3lame LAME_SRC)
aux_source_directory(src/main/cpp/lame/mpglib LAME_MPGLIB_SRC)
add_executable(lame ${LAME_FRONTEND_SRC} ${LAME_SRC} ${LAME_MPGLIB_SRC})