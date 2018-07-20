package com.software.dm.swallow.stormy.spark.user

import org.apache.spark.{SparkConf, SparkContext}


class UserQuery(val conf: SparkConf, val sparkContext: SparkContext) {

}

/**
  *
  */
object UserQuery {
//  val conf: SparkConf = new SparkConf().setAppName("SparkStreaming")
//
//  val sparkContext = SparkContext.getOrCreate(conf)
//
//
//  def init(path: String): DataFrame = {
//    this.sparkContext.setLogLevel(Level.WARN.toString)
//
//    val rddUser: RDD[Array[String]] = sparkContext.wholeTextFiles(path, 10).map(x => x._2.split(" "))
//    //    rddUser.cache()
//
//    val schema = StructType(
//      List(
//        StructField("userid", StringType, true),
//        StructField("value", StringType, true)
//      )
//    )
//
//    val sparkSession = new SparkSession(sparkContext)
//    val rowRDD: RDD[Row] = rddUser.map(x => Row(x(0), x(1)))
//
//    val personDF: DataFrame = sparkSession.createDataFrame(rowRDD, schema)
//    personDF.createOrReplaceGlobalTempView("t_persion")
//    personDF.cache()
//    //    val df: DataFrame = sparkSession.sql("select * from t_persion order by age desc limit 2")
//    personDF
//
//  }
//
//  def query: Unit = {
//
//  }
//
//  /**
//    *
//    * @param df
//    * @return
//    */
//  def updateDataFrame(df: DataFrame): DataFrame = {
//
//    df
//  }
//
//  def queryUser: Unit = {
//    val spark = SparkSession.builder.config(sparkContext.getConf).getOrCreate()
//
//    import spark.implicits._
//
//    val rddUser: RDD[Array[String]] = sparkContext.wholeTextFiles(path, 10).map(x => x._2.split(" "))
//
//    // Convert RDD[String] to DataFrame
//    val wordsDataFrame = rddUser.toDF("")
//
//    // Create a temporary view
//    wordsDataFrame.createOrReplaceTempView("words")
//    //---------
//    val rddsc = rdd.sparkContext.textFile("D:\\DearM\\data\\dm\\spark\\input\\persion\\words.txt")
//    val wordsLocalDataFrame = rddsc.toDF("value")
//    wordsLocalDataFrame.createOrReplaceTempView("t_local_word")
//  }

  def main(args: Array[String]): Unit = {

    //    sparkContext.parallelize()
  }
}
