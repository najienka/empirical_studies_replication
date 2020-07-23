#!/bin/bash
cd /home/user/Documents/academic/empirical_studies_2020/repositories/
for i in $(curl "https://api.github.com/search/repositories?q=language:java&sort=forks&order=desc&per_page=100" | grep -oP '"clone_url":\s*"\K[^"]+'); do git clone "$i" && sleep 200; done
