# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.

cmake_minimum_required(VERSION 3.6)
project(wcvcodec)

# silk
include_directories(src/main/cpp/silk/interface)
include_directories(src/main/cpp/silk/src)

aux_source_directory(src/main/cpp/silk/src SILK_SRC)


# lame
include_directories(src/main/cpp/lame/frontend)
include_directories(src/main/cpp/lame/include)
include_directories(src/main/cpp/lame/libmp3lame)
include_directories(src/main/cpp/lame/mpglib)

aux_source_directory(src/main/cpp/lame/frontend LAME_FRONTEND_SRC)
aux_source_directory(src/main/cpp/lame/libmp3lame LAME_SRC)
aux_source_directory(src/main/cpp/lame/mpglib LAME_MPGLIB_SRC)


# wcv codec (WeChat Voice Codec)
include_directories(src/main/cpp)
aux_source_directory(src/main/cpp/silk SILK_CODEC_SRC)
aux_source_directory(src/main/cpp/lame LAME_CODEC_SRC)
add_library(wcvcodec SHARED src/main/cpp/WcvCodec.c ${SILK_SRC} ${SILK_CODEC_SRC}
        ${LAME_FRONTEND_SRC} ${LAME_SRC} ${LAME_MPGLIB_SRC} ${LAME_CODEC_SRC})
find_library(android-log log)
target_link_libraries(wcvcodec ${android-log})