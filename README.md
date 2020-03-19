# oo_centrality_metrics
replication package for evaluating the association between oo metrics and centrality/network metrics

`OO metrics`

$ java -jar DesigniteJava.jar -i /Users/najienka/Documents/academic/research/datasets/top_ranked_100_projects/github_projects/HanLP -o /Users/najienka/Documents/academic/research/datasets/top_ranked_100_projects/github_projects/HanLP/result/

# OO Metrics
$ for i in `cat all`; do ./prova-OO_METRICS.pl $i; done
$ for i in `cat all`; do mv $i/$i-CK-ClassMetrics.txt CK-Metrics; done
$ for i in `cat all`; do ./prova_CLEAN_OO.pl CK-Metrics/$i-CK-ClassMetrics.txt >> sql_oo.csv; done
