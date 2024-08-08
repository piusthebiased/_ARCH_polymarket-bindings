#
# Eduard Maximovich. 2018
#
# @author Eduard Maximovich <edward.vstock@gmail.com>
# native library secp256k1 and jni bindings
#

cmake_minimum_required(VERSION 3.4)
project(secp256k1_java
        VERSION 1.0.0
        DESCRIPTION "Optimized C library for EC operations on curve secp256k1 with JNI-Bindings"
        LANGUAGES CXX
        )
set(CMAKE_CXX_STANDARD 14)

if (WIN32)
	execute_process(COMMAND cmd /C "echo ${CMAKE_PROJECT_VERSION} > ${CMAKE_CURRENT_SOURCE_DIR}/version")
else ()
	execute_process(COMMAND bash -c "echo ${CMAKE_PROJECT_VERSION} > ${CMAKE_CURRENT_SOURCE_DIR}/version")
endif ()

include(CheckTypeSize)
include_directories(src)
include_directories(libs)

list(APPEND CMAKE_MODULE_PATH ${CMAKE_CURRENT_SOURCE_DIR}/modules)

set(BUILD_SECP256K1_STATIC ON CACHE BOOL "Build static library")

set(CMAKE_RUNTIME_OUTPUT_DIRECTORY bin)
set(CMAKE_ARCHIVE_OUTPUT_DIRECTORY lib)
set(CMAKE_LIBRARY_OUTPUT_DIRECTORY lib)
set(CMAKE_PDB_OUTPUT_DIRECTORY lib)

set(LIB_NAME_MAIN secp256k1_core)
set(LIB_NAME_JNI secp256k1_jni)

check_type_size("unsigned __int128" HAVE_BIGNUM LANGUAGE CXX)

set(COMMON_COMPILE_FLAGS ENABLE_MODULE_RECOVERY ENABLE_MODULE_ECDH USE_ECMULT_STATIC_PRECOMPUTATION USE_FIELD_INV_BUILTIN USE_NUM_NONE USE_SCALAR_INV_BUILTIN)
if (MSVC)
	set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} /W2 /MP")
	set(CMAKE_CXX_FLAGS_DEBUG "${CMAKE_CXX_FLAGS_DEBUG} /DEBUG /Od")
	set(CMAKE_CXX_FLAGS_RELEASE "${CMAKE_CXX_FLAGS_RELEASE} /O2")
	string(REGEX REPLACE "\\/W3" " " CMAKE_CXX_FLAGS ${CMAKE_CXX_FLAGS})

	set(COMPILE_FLAGS USE_FIELD_10X26 USE_SCALAR_8X32)
	add_compile_options(/wd4267)
	add_compile_options(/wd4319)
else ()
	#  -DUSE_ECMULT_STATIC_PRECOMPUTATION -DUSE_FIELD_5X52 -DUSE_SCALAR_4X64
	set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -Wall -Wextra")
	set(CMAKE_CXX_FLAGS_DEBUG "${CMAKE_CXX_FLAGS_DEBUG} -pedantic -g")
	set(CMAKE_CXX_FLAGS_RELEASE "${CMAKE_CXX_FLAGS_RELEASE} -O2")
	add_definitions(-DHAVE_BUILTIN_EXPECT -DUSE_FIELD_10X26 -DUSE_SCALAR_8X32)
	if (HAVE_BIGNUM)
		add_definitions(-DHAVE___INT128)
	endif ()
	set(COMPILE_OPTIONS -O3 -W -Wno-narrowing -Wall -Wextra -Wcast-align  -Wshadow -Wno-unused-function -Wno-long-long -Wno-overlength-strings -fPIC -fvisibility=hidden)
	if (APPLE)
		set(COMPILE_OPTIONS ${COMPILE_OPTIONS} -Wnested-externs -Wstrict-prototypes)
	endif (APPLE)
endif ()

set(HEADERS
    include/secp256k1_config.h
    include/secp256k1.h
    include/secp256k1_ecdh.h
    include/secp256k1_recovery.h
    )

set(all_sources
    ${HEADERS}
    src/basic-config.h
    src/ecdsa.h
    src/ecdsa_impl.h
    src/eckey.h
    src/eckey_impl.h
    src/ecmult.h
    src/ecmult_const.h
    src/ecmult_const_impl.h
    src/ecmult_gen.h
    src/ecmult_gen_impl.h
    src/ecmult_impl.h
    src/field.h
    src/field_5x52.h
    src/field_5x52_impl.h
    src/field_5x52_int128_impl.h
    src/field_10x26.h
    src/field_10x26_impl.h
    src/field_impl.h
    src/group.h
    src/group_impl.h
    src/hash.h
    src/hash_impl.h
    src/num.h
    src/num_gmp.h
    src/num_gmp_impl.h
    src/num_impl.h
    src/scalar.h
    src/scalar_4x64.h
    src/scalar_4x64_impl.h
    src/scalar_8x32.h
    src/scalar_8x32_impl.h
    src/scalar_impl.h
    src/scalar_low.h
    src/scalar_low_impl.h
    src/scratch.h
    src/scratch_impl.h
    src/modules/ecdh/main_impl.h
    src/modules/recovery/main_impl.h
    src/secp256k1.cpp
    src/util.h
    src/ecmult_static_context.h
    )

set(bignum_cxx
    libs/uint128_t/uint128_t.h
    libs/uint128_t/uint128_t.cpp
    )

set(main_c_sources ${all_sources})

set(secp256k1_jni_headers
    src/java/NativeSecp256k1.h
    )
set(secp256k1_jni_sources
    ${secp256k1_jni_headers}
    src/java/NativeSecp256k1.cpp
    )

# Main lib
if (NOT HAVE_BIGNUM)
	set(main_c_sources
	    ${main_c_sources}
	    ${bignum_cxx}
	    )
endif ()

set(SECP256K1_EXPORING 1)
if(NOT BUILD_SECP256K1_STATIC)
	set(SECP256K1_SHARED 1)
endif()

configure_file(
	${CMAKE_CURRENT_SOURCE_DIR}/cfg/secp256k1_config.h.in
	${CMAKE_CURRENT_SOURCE_DIR}/include/secp256k1_config.h
)

set(INSTALL_TARGETS ${LIB_NAME_MAIN})

if (BUILD_SECP256K1_STATIC)
	add_library(${LIB_NAME_MAIN} STATIC ${main_c_sources})
else ()
	add_library(${LIB_NAME_MAIN} SHARED ${main_c_sources})
endif ()
set_target_properties(${LIB_NAME_MAIN} PROPERTIES PUBLIC_HEADER "${HEADERS}")

target_compile_definitions(${LIB_NAME_MAIN} PRIVATE ${COMMON_COMPILE_FLAGS} ${COMPILE_FLAGS})

target_include_directories(${LIB_NAME_MAIN} PUBLIC
                           $<BUILD_INTERFACE:${CMAKE_CURRENT_SOURCE_DIR}/include>
                           $<INSTALL_INTERFACE:include>)

target_compile_options(${LIB_NAME_MAIN} PRIVATE ${COMPILE_OPTIONS})

if (NOT DISABLE_JNI)

	if (ANDROID_PLATFORM)
		# JNI
		add_library(${LIB_NAME_JNI} SHARED ${secp256k1_jni_sources})
		target_link_libraries(${LIB_NAME_JNI} ${LIB_NAME_MAIN})
		target_include_directories(${LIB_NAME_JNI} PRIVATE ${CMAKE_SOURCE_DIR} ${CMAKE_SOURCE_DIR}/src)
	else ()
		find_package(JNI)
		if (JNI_FOUND)
			message(STATUS "JNI libs: ${JNI_LIBRARIES}")
			add_library(${LIB_NAME_JNI} SHARED ${secp256k1_jni_sources})
			list(APPEND INSTALL_TARGETS ${LIB_NAME_JNI})
			#			set_target_properties(${LIB_NAME_JNI} PROPERTIES PUBLIC_HEADER "${secp256k1_jni_headers}")

			target_link_libraries(${LIB_NAME_JNI} ${LIB_NAME_MAIN})
			target_link_libraries(${LIB_NAME_JNI} ${JAVA_JVM_LIBRARY})
			target_include_directories(${LIB_NAME_JNI} PRIVATE ${JNI_INCLUDE_DIRS} ${CMAKE_SOURCE_DIR} ${CMAKE_SOURCE_DIR}/src)
		else ()
			message(WARN "JNI not found")
		endif ()
	endif ()
endif ()

include(package)
