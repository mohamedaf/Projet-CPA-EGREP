#!/bin/bash

./jflex-1.6.0/bin/jflex TME4.l
./byacc -J -Jthrows TME4.y
javac *.java
echo "comp.sh ....... done"
