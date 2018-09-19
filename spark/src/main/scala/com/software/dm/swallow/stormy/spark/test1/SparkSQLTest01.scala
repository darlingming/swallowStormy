package com.software.dm.swallow.stormy.spark.test1



import org.apache.spark.sql.{DataFrame, Dataset, SparkSession}

/**
  * Created by
  * 1. 创建sparkSession
  * 2. 获取sparkContext
  * 3. 读取HDFS数据文件
  * 4. map以\t分隔字段
  * 5. map将字段转为case class的属性
  * 6. 隐式转换为DataFrame
  * 7. DF创建temp view
  * 8. 准备sparkSession.sql查询
  * 9. 执行查询并将结果写入jdbc数据库
  *
  * bin/spark-submit \
  * --class com.something.SparkSQLTest01 \
  * --master yarn \
  * --deploy-mode client \
  * ~/original-something-1.0-SNAPSHOT.jar
  *
  */

case class Elec(id:Int, time:BigInt, value:Int)

object SparkSQLTest01 {
  def main(args: Array[String]) {
    val spark = SparkSession
      .builder()
      .appName("SparkSQLTest01")
      .getOrCreate()

    import spark.implicits._

    val elecDF = spark.sparkContext
      .textFile("hdfs://xxxxxxxx:8020/2015-01-01.dat")
      .map(_.split("\t"))
      .map(attr => Elec(attr(0).trim.toInt, attr(1).trim.toInt, attr(2).trim.toInt))
      .toDF()

    elecDF.createOrReplaceTempView("elec")

    val distinctDF = spark.sql("select max(value) from elec")

    //val map: Dataset[String] = distinctDF.map(max => "max value: " + max(0))

    val sql: DataFrame = spark.sql("select max(value), min(value) from elec")

    sql.write.format("jdbc")
      .option("url", "jdbc:mysql://xxxxxxxx:3306/test_db01")
      .option("dbtable", "sqlresult02")
      .option("user", "xxxxxx")
      .option("password", "xxxxxx")
      .save()

  }


}
