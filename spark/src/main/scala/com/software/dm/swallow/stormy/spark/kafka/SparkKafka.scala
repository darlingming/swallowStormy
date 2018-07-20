package com.software.dm.swallow.stormy.spark.kafka

import org.apache.log4j.lf5.LogLevel
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.broadcast.Broadcast
import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.util.LongAccumulator

import scala.util.Random

/**
  *
  */
object SparkKafka {

  @volatile private var instance: LongAccumulator = null

  def getInstance(sc: SparkContext): LongAccumulator = {
    if (instance == null) {
      synchronized {
        if (instance == null) {
          instance = sc.longAccumulator("WordsInBlacklistCounter")
        }
      }
    }
    instance
  }


  def main(args: Array[String]): Unit = {
    if (args.length < 4) {
      System.err.println("Usage: SparkKafka <zkQuorum> <group> <topics> <numThreads>")
      System.exit(1)
    }
    val Array(zkQuorum, group, topics, numThreads) = args
    val appName = "appName"
    val master = "local[*]"
    val conf = new SparkConf().setAppName(appName).setMaster(master)
    val sc = new SparkContext(conf)
    sc.setLogLevel(LogLevel.WARN.toString)
    val ssc = new StreamingContext(sc, Seconds(60))
    //    ssc.checkpoint("checkpoint")

    val topicMap = topics.split(",").map((_, numThreads.toInt)).toMap
    val kafkaStreams = KafkaUtils.createStream(ssc, zkQuorum, group, topicMap)
    val lines = kafkaStreams.map(_._2)
    //    lines.cache()
    val broadcastVar = sc.broadcast(List("bye", "delete", "quit", "drop"))


    val tmpRdd = sc.makeRDD(List(("liming", "1"), ("zhangsan", "2")))
    lines.foreachRDD(r => {
      val rond = new Random()
      val rdd1 = r.flatMap(_.split(" ")).distinct().map(x => (x, rond.nextInt(100).toString))

      val nRDD = rdd1.filter(f => {
        !broadcastVar.value.contains(f._1)

      })
      val resRdd = nRDD.fullOuterJoin(tmpRdd)
  val outputPath = "/home/programDev/dm/md5/ouput/20180117"
      resRdd.map(x => x._1 + "," + x._2).saveAsTextFile(outputPath + "/part_" + System.nanoTime())


    })
    //    lines.print()


    // Start the context
    ssc.start()
    ssc.awaitTermination()

  }
}

object WordBlacklist {

  @volatile private var instance: Broadcast[Seq[String]] = null

  def getInstance(sc: SparkContext): Broadcast[Seq[String]] = {
    if (instance == null) {
      synchronized {
        if (instance == null) {
          val wordBlacklist = Seq("a", "b", "c")
          instance = sc.broadcast(wordBlacklist)
        }
      }
    }
    instance
  }
}