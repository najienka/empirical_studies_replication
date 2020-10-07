#!/bin/bash

mkdir -p nodes infomaps

echo "Doing $1 now"

#Understand
find ./$1 -name "*.java" > myFiles.txt
und -quiet create -db $1.udb -languages Java add @myFiles.txt analyze -all
und -quiet export -dependencies -col refs -format short class csv out.csv $1.udb

# turns textual nodes into IDs
awk '!/\(Anon|From Class/' out.csv | awk -F "," '{ print $1 }' > nodes.csv && awk '!/\(Anon|From Class/' out.csv | awk -F "," '{ print $2 }' >> nodes.csv
sort -u nodes.csv | awk '{print $s "," NR}' > nodes_id-$1.csv

# turns relations between textual nodes into relations between IDs
awk 'BEGIN {FS=OFS=","} FNR==NR{a[$1]=$2;next} $1 in a{print a[$1] " " a[$2] " " $3}' nodes_id-$1.csv out.csv > A_B_w-$1.csv

# generates network
./Infomap --silent --clu --ftree A_B_w-$1.csv .

# cleanup
mv nodes_id-$1.csv nodes/
mv A_B_w-$1.* infomaps/
