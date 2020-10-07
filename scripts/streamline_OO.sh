#!/bin/bash

mkdir -p metrics #create directory if not exists

echo "Doing $1 now"

und create -db $1.udb -languages Java
und -db $1.udb add $(find $1 -name "*.java")
und analyze $1.udb
und import core.xml $1.udb
und metrics $1.udb
awk '!/^File|^Package|^Kind/' $1.csv > metrics/$1_OO-metrics.csv
rm $1.csv
rm $1.udb
