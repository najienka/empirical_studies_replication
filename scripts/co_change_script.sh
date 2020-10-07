#!/bin/bash


repository_name=$1
echo "Processing $1 now"

SINCE=1/9/2015
UNTIL=1/9/2020
cd $repository_name
git log -r  --since=$SINCE --until=$UNTIL --name-only --pretty=format:'' | 
awk 'BEGIN{ FS="/" } /^$|\.m$|\.java$/{ print $NF}' | #Remove the directory path and get only file name
awk 'BEGIN{ RS="" ; FS="\n"} { for(i=1;i<=NF;i++) for(j=1;j<i;j++) print $i,$j}' | #Print Source Target Pair
sort | uniq -c | sed -e 's/^ *//;s/ /,/g' | #Count Number of Occurence 
awk 'BEGIN{ print "Weight,Source,Target"} {print}' > ../change_metrics/${repository_name}_Edges.csv

