#!/bin/bash

if [ "$#" -ne 2 ]; then
    echo "Usage: ./v2bmp.sh <source.vec> <destination.png>"
    exit 1
fi

# Compilation
echo "Compilation..."
mkdir -p bin
javac -d bin $(find src/main/java -name "*.java")

# Exécution
echo "Execution..."
java -cp bin app.V2Bmp "$@"