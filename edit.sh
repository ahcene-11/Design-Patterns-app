#!/bin/bash
# Script de lancement convivial pour l'édition de dessins

# pas d'arguments
if [ "$#" -ne 0 ]; then
    echo "Usage: ./edit.sh"
    exit 1
fi

# Crée le dossier bin s'il n'existe pas
mkdir -p bin

# Compile tous les fichiers Java
echo "Compilation..."
javac -d bin $(find src/main/java -name "*.java")

# Lance le programme
echo "Execution..."
java -cp bin app.Edit