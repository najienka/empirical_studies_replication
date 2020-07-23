#!/bin/bash

cd /home/user/Documents/academic/andrea_semantic/github_projects

input="/home/user/Documents/academic/andrea_semantic/github_projects/projects.txt"
while IFS= read -r var
do
  git clone "$var"
done < "$input"                        

