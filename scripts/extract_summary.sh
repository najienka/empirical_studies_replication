#!/bin/bash


input="/home/user/Documents/academic/empirical_studies/waste_case_studies/projects/list-active.txt"
while IFS= read -r var
do
  sh ./summary.sh $var
  #sleep 30
done < "$input"
