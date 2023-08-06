#!/bin/bash
echo "Building the app"

./gradlew build 

echo "running the app"

java -jar -DPORT=8787 build/libs/block-gen-0.0.1-SNAPSHOT.jar


