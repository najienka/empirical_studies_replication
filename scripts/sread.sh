#!/bin/bash

# each java file
printf "$1\t" && cat $1 | java -jar readability.jar | sed -n 3p
