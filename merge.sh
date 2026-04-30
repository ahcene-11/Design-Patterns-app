#!/bin/bash
# Script de lancement convivial pour la fusion de dessins

if [ "$#" -ne 3 ]; then
    echo "Usage: ./merge.sh <fichier1.vec> <fichier2.vec> <fusion.vec>"
    exit 1
fi

# Crée le dossier bin s'il n'existe pas
mkdir -p bin

# Compile tous les fichiers Java
echo "Compilation..."
javac -d bin $(find src/main/java -name "*.java")

# Lance le programme
echo "Execution..."
java -cp bin app.Merge "$@"