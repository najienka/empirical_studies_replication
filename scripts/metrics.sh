#!/bin/bash

#input="/home/user/Documents/academic/empirical_studies/waste_case_studies/rep_case_studies/folders.txt"

input="/home/user/Documents/academic/empirical_studies/waste_case_studies/projects/folders.txt"
while IFS= read -r var
do
  sh ./streamline_OO.sh $var
  #perl prova_OO_METRICS.pl $var
  #awk '!/^File|^Package|^Kind/' $var/$var.csv > metrics/$var"_OO-metrics.csv"
  sleep 30
done < "$input"
