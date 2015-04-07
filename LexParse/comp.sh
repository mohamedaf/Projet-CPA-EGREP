#!/bin/bash

./jflex-1.6.0/bin/jflex TME4.l
./byacc -J -Jthrows TME4.y

echo "comp.sh ....... done"
