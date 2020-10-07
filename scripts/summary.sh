#!/bin/bash

#project name
echo "Doing $1 now"

#number of .java files/classes
total_java=$(find $1 -name "*.java" | wc -l)
echo "total classes - $total_java"

#total java sloc of all classes/of the project
find $1 -name "*.java" > log.txt
while IFS= read -r var
do
  cat $var | wc -l >> sloc.txt
  
  #sleep 30
done < log.txt

total_sloc=$(awk '{s+=$1} END {printf "%.0f\n", s}' sloc.txt)

echo "total sloc - $total_sloc"

#github url
git=$(git -C $1 remote show origin | grep -w "Fetch URL" | awk -F " " '{print $3}')
echo "GitHub url - $git"

echo "$1,$git,$total_java,$total_sloc" >> projects_summary.txt

rm log.txt && rm sloc.txt
