#!/bin/bash
mkdir -p coupling_metrics

echo "Doing $1 now"

und create -db $1.udb -languages Java
und -db $1.udb add $(find $1 -name "*.java")
und settings -MetricFileNameDisplayMode FullPath $1.udb
und analyze -all $1.udb >> coupling_metrics/$1-LOG.txt
und -db $1.udb settings -metrics all settings -metricsOutputFile coupling_metrics/$1-metrics.csv metrics
und export -dependencies file csv coupling_metrics/$1.csv $1.udb
und export -dependencies class csv coupling_metrics/$1-class.csv $1.udb
rm $1.udb
