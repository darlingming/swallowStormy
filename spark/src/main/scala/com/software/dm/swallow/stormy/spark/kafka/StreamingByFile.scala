package com.software.dm.swallow.stormy.spark.kafka

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

object StreamingByFile {

  def main(args: Array[String]) {
    val conf = new SparkConf().setMaster("local[*]").setAppName("StreamingByFile")
    val sc = new StreamingContext(conf,Seconds(5))
    val lines = sc.textFileStream("D://aa.txt")
    val words = lines.flatMap(_.split(" "))
    val wordCounts = words.map(x => (x , 1)).reduceByKey(_ + _)

    wordCounts.print()
    sc.start()
    sc.awaitTermination()
  }
}
