#!/bin/bash
und create -db $1.udb -languages Java
und -db $1.udb add $(find $1 -name "*.java")
und settings -MetricFileNameDisplayMode FullPath $1.udb
und analyze -all $1.udb >> $1-LOG.txt
und -db $1.udb settings -metrics all settings -metricsOutputFile $1-metrics.csv metrics
und export -dependencies file csv $1.csv $1.udb
und export -dependencies class csv $1-class.csv $1.udb
