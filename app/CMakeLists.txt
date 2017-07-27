# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.

cmake_minimum_required(VERSION 3.4.1)

# Creates and names a library, sets it as either STATIC
# or SHARED, and provides the relative paths to its source code.
# You can define multiple libraries, and CMake builds them for you.
# Gradle automatically packages shared libraries with your APK.

add_library( # Sets the name of the library.
             HDiffPatch

             # Sets the library as a shared library.
             SHARED

             # Provides a relative path to your source file(s).
             src/main/cpp/libHDiffPatch/HDiff/private_diff/libdivsufsort/config.h
             src/main/cpp/libHDiffPatch/HDiff/private_diff/libdivsufsort/divsufsort.c
             src/main/cpp/libHDiffPatch/HDiff/private_diff/libdivsufsort/divsufsort.c.inc.h
             src/main/cpp/libHDiffPatch/HDiff/private_diff/libdivsufsort/divsufsort.h
             src/main/cpp/libHDiffPatch/HDiff/private_diff/libdivsufsort/divsufsort64.c
             src/main/cpp/libHDiffPatch/HDiff/private_diff/libdivsufsort/divsufsort64.h
             src/main/cpp/libHDiffPatch/HDiff/private_diff/libdivsufsort/divsufsort_private.h
             src/main/cpp/libHDiffPatch/HDiff/private_diff/libdivsufsort/sssort.c.inc.h
             src/main/cpp/libHDiffPatch/HDiff/private_diff/libdivsufsort/trsort.c.inc.h
             src/main/cpp/libHDiffPatch/HDiff/private_diff/libdivsufsort/utils.c.inc.h
             src/main/cpp/libHDiffPatch/HDiff/private_diff/bytes_rle.cpp
             src/main/cpp/libHDiffPatch/HDiff/private_diff/bytes_rle.h
             src/main/cpp/libHDiffPatch/HDiff/private_diff/pack_uint.h
             src/main/cpp/libHDiffPatch/HDiff/private_diff/sais.hxx
             src/main/cpp/libHDiffPatch/HDiff/private_diff/suffix_string.cpp
             src/main/cpp/libHDiffPatch/HDiff/private_diff/suffix_string.h
             src/main/cpp/libHDiffPatch/HDiff/diff.cpp
             src/main/cpp/libHDiffPatch/HDiff/diff.h
             src/main/cpp/libHDiffPatch/HPatch/patch.c
             src/main/cpp/libHDiffPatch/HPatch/patch.h
             src/main/cpp/Native_HDiff.cpp
             src/main/cpp/Native_HPatch.cpp
             )

# Searches for a specified prebuilt library and stores the path as a
# variable. Because CMake includes system libraries in the search path by
# default, you only need to specify the name of the public NDK library
# you want to add. CMake verifies that the library exists before
# completing its build.

find_library( # Sets the name of the path variable.
              log-lib

              # Specifies the name of the NDK library that
              # you want CMake to locate.
              log )

# Specifies libraries CMake should link to your target library. You
# can link multiple libraries, such as libraries you define in this
# build script, prebuilt third-party libraries, or system libraries.

target_link_libraries( # Specifies the target library.
                       HDiffPatch

                       # Links the target library to the log library
                       # included in the NDK.
                       ${log-lib} )