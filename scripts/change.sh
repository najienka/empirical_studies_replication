#!/bin/bash

mkdir -p change_metrics
#input="/home/user/Documents/academic/empirical_studies/waste_case_studies/rep_case_studies/folders.txt"

input="/home/user/Documents/academic/empirical_studies/waste_case_studies/projects/folders.txt"
while IFS= read -r var
do
  sh ./co_change_script.sh $var
  #perl prova_OO_METRICS.pl $var
  #awk '!/^File|^Package|^Kind/' $var/$var.csv > metrics/$var"_OO-metrics.csv"
  sleep 30
#done < "$input"
done < <(tail -n +144 $input)
#https://askubuntu.com/questions/289160/how-to-read-file-starting-from-a-specific-number-of-line-within-the-while-read
