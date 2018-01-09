package com.software.dm.swallow.stormy.spark

import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.streaming.{Minutes, Seconds, StreamingContext}
import org.apache.spark.streaming.kafka.KafkaUtils

object FilterUser {

  def exec(args: Array[String]): Unit = {

    val Array(zkQuorum, group, topics, numThreads) = args
    val sparkConf = new SparkConf().setAppName("KafkaWordCount_exec")
    val ssc = new StreamingContext(sparkConf, Seconds(2))
//    ssc.checkpoint("checkpoint")

    val topicMap = topics.split(",").map((_, numThreads.toInt)).toMap
    val lines = KafkaUtils.createStream(ssc, zkQuorum, group, topicMap).map(_._2)
//    org.apache.spark.streaming.dstream.DStream[U]
    val words : DStream[Array[String]] = lines.map(_.split(","))
//    words.reduceByWindow(f => f,  Minutes(10), Seconds(2))
//    val wordCounts = words.map(x => (x, 1L)) .reduceByKeyAndWindow(_ + _, _ - _, Minutes(10), Seconds(2), 2)
//    wordCounts.print()

    ssc.start()
    ssc.awaitTermination()
  }
}
