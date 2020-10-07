#!/bin/bash

mkdir -p readability
rm java.txt && rm readability/readability_metrics.csv

input="/home/user/Documents/academic/empirical_studies/waste_case_studies/projects/list-active.txt"
#all java classes from every project
while IFS= read -r var
do
  echo "Doing $var now"
  find $var -name "*.java" >> java.txt
done < "$input"


java_input="/home/user/Documents/academic/empirical_studies/waste_case_studies/projects/java.txt"
#readability per file
while IFS= read -r var
do
  echo "Extracting readability metric for $var"
  sh sread.sh $var >> readability/readability_metrics.csv
  sleep 5
done < "$java_input"
