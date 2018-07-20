package com.software.dm.swallow.stormy.spark.rddaction

import org.apache.spark.{SparkConf, SparkContext}

/**
  *
  */
object RddSparkAction {

  def main(args: Array[String]): Unit = {
    val appName = "RddSparkAction"
    val master = "local[2]"
    val conf = new SparkConf().setAppName(appName).setMaster(master)
    conf.set("spark.executor.heartbeatInterval","300")
    val sc = new SparkContext(conf)
    val data1 = Array(1, 2, 3, 4, 5)
    val distData1 = sc.parallelize(data1)

    val data2 = Array(1, 7, 9, 4, 5)
    val distData2 = sc.parallelize(data2)

    var disrdd1 = distData1.map(x => (x, 1))
    val disrdd2 = distData2.map(x =>(x, 2) )
    val rdd3 = disrdd1.join(disrdd2).filter(x => {
      x._1==5 && x._2._2==2
    })
    rdd3.collect().foreach(println)

    disrdd1 = disrdd1.leftOuterJoin(rdd3).filter(x => {
       x._2._2 == None
    }).map(x=>(x._1,1))

//    disrdd1.collect().foreach(println)
    disrdd1.map(x =>x._1.toString +","+ x._2.toString).collect().foreach(println)

//    disrdd1.coalesce(6).

//    sc.doubleAccumulator(1)
  }
}
