#!/bin/bash

mkdir -p 4_metrics

echo "Doing $1 now"

und create -db $1.udb -languages Java
und -db $1.udb add $(find $1 -name "*.java")
und analyze $1.udb
und import 4_core.xml $1.udb
und metrics $1.udb
awk '!/^File|^Package|^Kind/' $1.csv > 4_metrics/$1_OO-metrics.csv
rm $1.csv
rm $1.udb
