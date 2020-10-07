#!/bin/bash

#input="/home/user/Documents/academic/empirical_studies/waste_case_studies/rep_case_studies/folders.txt"

input="/home/user/Documents/academic/empirical_studies/waste_case_studies/projects/folders.txt"
while IFS= read -r var
do
  sh streamline_network.sh $var
  
  sleep 30
done < "$input"
