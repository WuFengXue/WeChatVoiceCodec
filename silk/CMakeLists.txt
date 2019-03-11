# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.

cmake_minimum_required(VERSION 3.4.1)

include_directories(src/main/cpp/silk/interface)
include_directories(src/main/cpp/silk/src)

aux_source_directory(src/main/cpp/silk/src SILK_SRC)

add_executable(decoder src/main/cpp/silk/test/Decoder.c ${SILK_SRC})
add_executable(encoder src/main/cpp/silk/test/Encoder.c ${SILK_SRC})
add_executable(signalcompare src/main/cpp/silk/test/signalCompare.c ${SILK_SRC})