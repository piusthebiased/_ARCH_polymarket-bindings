#!/usr/bin/env bash

mkdir -p /tmp/pkgs

CMAKE_PATH=$(which cmake)

if [ "${CMAKE_PATH}" == "" ]; then
  # fetching cmake
  CMAKE_MAJOR="3.14"
  CMAKE_PATCH="5"
  CMAKE_VERS="${CMAKE_MAJOR}.${CMAKE_PATCH}"
  if [ ! -f "/tmp/pkgs/cmake.sh" ]; then
    curl -Lso /tmp/pkgs/cmake.sh https://cmake.org/files/v${CMAKE_MAJOR}/cmake-${CMAKE_VERS}-Linux-x86_64.sh
  fi

  sh /tmp/pkgs/cmake.sh --skip-license --prefix=/usr
fi
