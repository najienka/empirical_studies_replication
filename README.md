# oo_centrality_metrics
replication package for evaluating the association between oo metrics and centrality/network metrics


This README should allow the replication of the approach to extract OO and centrality metrics
It is a combination of bash commands (starting with '$')
================================================================================================================

There are few steps to perform, each with some pre-requisite tool:
1) cloning repositories
2) storing metadata via CVSAnaly2
3) evaluating SLOCs
4) extracting OO metrics (via SciTools Understand) 
5) evaluating contributors
    5.1) removing duplicates
    5.2) extracting distinct Java files worked on by each contributor
    5.3) evaluating Top, Middle and Bottom developers
6) collating OO data and authors data

================================================================================================================

# OO Metrics
$ find src/ -name "*.java" > source.txt
$ javac @source.txt
