package com.software.dm.swallow.stormy.spark.streaming.kafka

import java.io.File
import java.text.SimpleDateFormat
import java.util.Date

import com.software.dm.swallow.stormy.hadoop.tools.AbstractCommonUtils
import com.software.dm.swallow.stormy.scala.security.addrlib.bdia.obtain.SerialVagueFactroy
import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka010.{ConsumerStrategies, KafkaUtils, LocationStrategies}
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * Consumes messages from one or more topics in Kafka and does wordcount.
  * Usage: DirectKafkaWordCount <brokers> <topics>
  * <brokers> is a list of one or more Kafka brokers
  * <groupId> is a consumer group name to consume from topics
  * <topics> is a list of one or more kafka topics to consume from
  *
  * Example:
  * $ bin/run-example streaming.DirectKafkaWordCount broker1-host:port,broker2-host:port \
  * consumer-group topic1,topic2
  */
object SkDemo {
  val kafkaParams = Map[String, Object](
    "bootstrap.servers" -> "localhost:9092,anotherhost:9092",
    "key.deserializer" -> classOf[StringDeserializer],
    "value.deserializer" -> classOf[StringDeserializer],
    "group.id" -> "use_a_separate_group_id_for_each_stream",
    "auto.offset.reset" -> "latest",
    "enable.auto.commit" -> (false: java.lang.Boolean)
  )

  def main(args: Array[String]) {

    if (args.length < 3) {
      System.err.println(
        s"""
           |Usage: DirectKafkaWordCount <brokers> <topics>
           |  <brokers> is a list of one or more Kafka brokers
           |  <groupId> is a consumer group name to consume from topics
           |  <topics> is a list of one or more kafka topics to consume from
           |
        """.stripMargin)
      System.exit(1)
    }


    val Array(brokers, groupId, topics) = args

    println(brokers + "=" + groupId + "=" + topics);

    // Create context with 2 second batch interval
    val sparkConf = new SparkConf().setAppName("DirectKafkaWordCount").setMaster("local")

    //    sparkConf.set("spark.kryoserializer.buffer","256m");
    //     sparkConf.set("spark.serializer", "org.apache.spark.serializer.KryoSerializer");
    //     sparkConf.set("spark.kryoserializer.buffer", "256m");
    //     sparkConf.set("spark.kryoserializer.buffer.max","2046m");
    //     sparkConf.set("spark.akka.frameSize", "500");
    //     sparkConf.set("spark.rpc.askTimeout", "30");

    val ssc = new StreamingContext(sparkConf, Seconds(30));
    val sc = ssc.sparkContext;

        val rdd_data = sc.textFile("D://test.txt");
        val bc = sc.broadcast(rdd_data.collect().toSet);


    // Create direct kafka stream with brokers and topics
    val topicsSet = topics.split(",").toSet
    val kafkaParams = Map[String, Object](
      ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG -> brokers,
      ConsumerConfig.GROUP_ID_CONFIG -> groupId,
      ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG -> classOf[StringDeserializer],
      ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG -> classOf[StringDeserializer])
    val messages = KafkaUtils.createDirectStream[String, String](
      ssc,
      LocationStrategies.PreferConsistent,
      ConsumerStrategies.Subscribe[String, String](topicsSet, kafkaParams))

    // Get the lines, split them into words, count the words and print
    val lines = messages.map(_.value)

    lines.foreachRDD(foreachFunc = rdd => {
      val hadoopConf = sc.hadoopConfiguration
      //       hadoopConf.set("fs.defaultFS", "hdfs://myhadoop:9000/")
      val fileSystem = FileSystem.get(hadoopConf)
      //
      //
      val df = new SimpleDateFormat("yyyyMMdd")
      val serialPath = "D:/DearM/repo/yun/serial" + File.separatorChar + df.format(new Date) + ".serial"

      val sf = new SerialVagueFactroy(fileSystem.open(new Path(serialPath)))
      //       

      val listResult = rdd.collect();

      if (listResult != null && !listResult.isEmpty) {
        var i = listResult.length - 1
        while (i > -1) {
          val res = listResult(i)
  println("res==========="+res)
          //  val url = value
          val fullDomain = AbstractCommonUtils.getFullDomainWithBareUrl(res.getBytes)
          //      System.out.println(util.Arrays.toString(fullDomain))
          sf.execute(fullDomain)
          // System.out.println(sf.basicAppResult.getBasicTypes().size());
          // for (T_theme_url_rule ttur : sf.themeResultList) {
          // System.out.println(ttur.getThemeTypes().size());
          // System.out.println(ttur.getN_id());
          // }
          System.out.println(sf.getBasicAppResult)
          System.out.println(sf.getThemeResultList)
          System.out.println(sf.getExtractResultList)
          System.out.println(sf.getIpResult)
          i = i - 1
        }
      }


    }


    )



        val words = lines.flatMap(_.split(" "))
        val wordCounts = words.filter(!bc.value.contains(_)).map(x => (x, 1L)).reduceByKey(_ + _)
        wordCounts.print()

    // Start the computation
    ssc.start()
    ssc.awaitTermination()
  }

}
