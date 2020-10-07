#!/bin/bash

input="/home/user/Documents/academic/empirical_studies/waste_case_studies/projects/gitlist.txt"
while IFS= read -r var
do
  git clone "$var"
  sleep 30
done < "$input"
