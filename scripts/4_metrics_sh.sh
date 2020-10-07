#!/bin/bash

input="/home/user/Documents/academic/empirical_studies/waste_case_studies/projects/list-active.txt"
while IFS= read -r var
do
  sh ./4_streamline_OO.sh $var
  #perl prova_OO_METRICS.pl $var
  #awk '!/^File|^Package|^Kind/' $var/$var.csv > metrics/$var"_OO-metrics.csv"
  sleep 20
done < "$input"
