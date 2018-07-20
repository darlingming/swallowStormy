#!/usr/bin/env bash

#zookeeper.connect=cloudera01:2181,cloudera02:2181,cloudera03:2181
 # --conf "spark.executor.extraJavaOptions=-XX:+PrintGCDetails -XX:+PrintGCTimeStamps"

echo "flume kafka spark streaming is a magic streaming programming" >> /tmp/test/raw_data.txt

kafka –create-topic.sh –replica 3 –partition 8 –topic mytopic –zookeeper hadoop1:2181,hadoop1:2182,hadoop1:2183



#启动flume采集端进程
flume-ng agent –conf conf –conf-file conf/flume-kafka-spark-collect.conf –name a1 -Dflume.root.logger=INFO,console
#启动flume聚合/分发端进程
flume-ng agent –conf conf –conf-file conf/flume-kafka-spark-receiver.conf –name producer -Dflume.root.logger=INFO,console