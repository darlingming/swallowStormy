package com.software.dm.swallow.stormy.spark.user

//import com. dm.spark.common.MD5
import com.software.dm.swallow.stormy.spark.common.MD5
import org.apache.log4j.Level
import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.sql._
import org.apache.spark.sql.types.{StringType, StructField, StructType}
import org.apache.spark.storage.StorageLevel

import scala.collection.mutable.ListBuffer

class Md5Match(sparkConf: SparkConf) {
  var sparkSession: SparkSession = _

  def preStart(): Unit = {

    sparkSession = SparkSession.builder().config(sparkConf).getOrCreate()
  }
}

/**
  *
  */
object Md5Match {
  // 9999999999L 99999999L
  val maxLength = 99999999L
  val len = maxLength.toString.length
  //计数
  var poi = 0L
  //分割处理  10000000L
  val n = 2000000L

  private[this] val sb = new StringBuffer()
  /**
    * 移动号段：139 138 137 136 135 134 147 150 151 152 157 158 159 178 182 183 184 187 188
    * 联通号段：130 131 132 155 156 185 186 145 176
    * 电信号段：133 153 177 173 180 181 189
    * 虚拟运营商号段：170 171
    */
  private[this] val prefixs = Array("134", "135", "136", "137", "138", "139",
    "147", "150", "151", "152", "157", "158", "159", "182", "183", "184", "187", "188", "178",
    //联通
    "130", "131", "132", "155", "156", "185", "186", "145", "176",
    //电信
    "133", "153", "180", "181", "189", "173", "177"
  )
  private[this] val prefixs_cmcc = Array("134", "135", "136", "137", "138", "139",
    "147", "150", "151", "152", "157", "158", "159", "182", "183", "184", "187", "188", "178"
  )
  private[this] val prefixs_cucc = Array(
    //联通
    "130", "131", "132", "155", "156", "185", "186", "145", "176"
  )
  private[this] val prefixs_ctcc = Array(
    //电信
    "133", "153", "180", "181", "189", "173", "177"
  )

  private[this] val prefixs1 = Array("1584639")

  /**
    *
    * @param perfix
    * @param mStr
    * @return
    */
  def addStr(perfix: String, mStr: String): Tuple2[String, String] = {
    var restr = mStr
    var mLen = mStr.length
    if (mLen < len) {
      sb.delete(0, sb.length())
      sb.append(mStr)
      while (mLen < len) {
        sb.insert(0, "0")
        mLen += 1
      }
      //println(sb.toString)
      restr = sb.toString
    }
    restr = perfix + restr
    Tuple2(MD5.md5Hash(restr).toUpperCase, restr)
  }


  val schema = StructType(
    List(
      StructField("userid", StringType, true),
      StructField("value", StringType, true)
    )
  )

  /**
    *
    * @param sparkSession
    * @param rdd
    * @return
    */
  def dataTarget(sparkSession: SparkSession, rdd: RDD[Tuple2[String, String]]): DataFrame = {

    val rowRDD: RDD[Row] = rdd.map(x => Row(x._1, x._2))

    val personDF: DataFrame = sparkSession.createDataFrame(rowRDD, schema)
    personDF.createOrReplaceTempView("t_user_md5")
    //    personDF.cache()

    personDF
  }


  /**
    *
    * @param sparkSession
    * @param path
    * @return
    */
  def dataSource(sparkSession: SparkSession, path: String): DataFrame = {
    val user = StructType(
      List(
        StructField("userid", StringType, true)
      )
    )
    //    val rddUser: RDD[String] = sparkSession.sparkContext.wholeTextFiles(path).flatMap(x => x._2.split("\n"))
    val rddUserDS: Dataset[String] = sparkSession.read.textFile(path)

    val rddUser: DataFrame = rddUserDS.withColumnRenamed("value", "userid")

    rddUser.createOrReplaceGlobalTempView("t_user")
    rddUser.persist(StorageLevel.MEMORY_AND_DISK)
    rddUser.cache()

    rddUser.checkpoint()
    //    rddUser.printSchema()
    //    rddUser.show()
    //    println("=============="+ rddUser.count())
    rddUser
    /**
      * //    rddUser.collect().foreach(println)
      * val rowRDD: RDD[Row] = rddUser.map(x => Row(x))
      * val personDF: DataFrame = sparkSession.createDataFrame(rowRDD, user)
      *personDF.createOrReplaceGlobalTempView("t_user")
      *personDF.persist(StorageLevel.MEMORY_AND_DISK)
      * //        val df: DataFrame = sparkSession.sql("select count(*) from global_temp.t_user    ")
      * //        df.show()
      *personDF.cache()
      * personDF
      *
      */
  }


  /**
    *
    * @param inputPath
    * @param outputPath
    * .master("local[2]")
    */
  def run(inputPath: String, outputPath: String, prefixArrays: Array[String]): Unit = {
    val sparkSession = SparkSession.builder().appName("Md5Match").getOrCreate()

    val conf = new SparkConf()
      // 取storage区域（即存储区域）在总内存中所占比重，由参数spark.storage.memoryFraction确定，默认为0.6
      .set("spark.storage.memoryFraction", "0.6")
      .set("spark.memory.useLegacyMode", "true")
      // 系统可用最大内存，取参数spark.testing.memory，未配置的话取运行时环境中的最大内存
      .set("spark.testing.memory", Runtime.getRuntime.maxMemory.toString) //~500MB
      // 取storage区域（即存储区域）在系统为其可分配最大内存的安全系数，主要为了防止OOM，取参数spark.storage.safetyFraction
      .set("spark.storage.safetyFraction", "0.9")
      // 取Execution区域（即运行区域，为shuffle使用）在总内存中所占比重，由参数spark.shuffle.memoryFraction确定，默认为0
      .set("spark.shuffle.memoryFraction", "0.2")
      // 取Execution区域（即运行区域，为shuffle使用）在系统为其可分配最大内存的安全系数，主要为了防止OOM，取参数spark.shuffle.safetyFraction，默认为0.8
      .set("spark.shuffle.safetyFraction", "0.9")

      .set("spark.testing.reservedMemory", "300000000")

      .set("spark.shuffle.sort.bypassMergeThreshold", "0")
      .set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
      .set("spark.driver.memory", "1g")
      .set("spark.executor.memory", "500m")
      .set("spark.driver.cores", "1")
      .set("spark.sql.tungsten.enabled", "true")
      .set("spark.shuffle.file.buffer", "10m")
      .set("spark.shuffle.spill.numElementsForceSpillThreshold", Long.MaxValue.toString)
      .set("spark.shuffle.spill.memoryForceSpillThreshold", "64m")
      .set("--driver-memory", "500m")

    //    val sparkSession =SparkSession.builder().config(conf).appName("Md5Match").master("local[2]").getOrCreate()
    //    val sparkSession = SparkSession.builder().appName("Md5Match").master("local[*]").getOrCreate()


    sparkSession.sparkContext.setCheckpointDir("checkpoint")
    sparkSession.sparkContext.setLogLevel(Level.INFO.toString)


    import sparkSession.implicits._

    this.dataSource(sparkSession, inputPath)
    val listBuffer: ListBuffer[Tuple2[String, String]] = new ListBuffer[Tuple2[String, String]]

    //    val resDf1: DataFrame =  sparkSession.sql("select a.userid from global_temp.t_user a where a.userid = '079B652983F1803A8D75A8F16BDFA678' ")
    //    println(resDf1.count())

    for (perfix <- prefixArrays; m <- poi to maxLength) {
      val mStr = m.toString
      val str = addStr(perfix, mStr)
      listBuffer += str
      if ((m > 0 && m % n == 0) || (m + 1) == maxLength) {
        //          println(listBuffer)
        val rddmd5: RDD[Tuple2[String, String]] = sparkSession.sparkContext.parallelize(listBuffer)

        //          val rddArray: RDD[Tuple2[String, String]] = rddmd5.map(_.split(",")).map(x => (x(0), x(1)))
        this.dataTarget(sparkSession, rddmd5)

        val resDf: DataFrame = sparkSession.sql("select a.userid , a.value from t_user_md5 a join global_temp.t_user b on a.userid = b.userid")
        //        resDf.show() .format("text") ).save(
        //.coalesce() repartition
        //        resDf.map(t=>t.get(0)+"="+t.get(1)).write.format("text").mode(SaveMode.Append).parquet("D:\\DearM\\data\\dm\\spark\\output\\Md5Match")
        //          resDf.show()
        //          val count = resDf.count()
        //
        //          if (count > 0) {
        resDf.map(t => t.get(0) + "," + t.get(1)).coalesce(2).write.format("text").mode(SaveMode.Append).save(outputPath)
        //          }

        listBuffer.clear()
      }
    }

    sparkSession.stop()
  }

  def test: Unit = {

    val l = 4
    val poi = maxLength - 5
    val prefixArrays: Array[String] = if (l == 1) prefixs_cmcc else if (l == 2) prefixs_cucc else if (l == 3) prefixs_ctcc else prefixs_cmcc ++ prefixs_cucc ++ prefixs_ctcc
    for (perfix <- prefixArrays; m <- poi to maxLength) {
      println(s"perfix=$perfix,m=$m")
    }

  }

  def main(args: Array[String]): Unit = {
    //        val inputPath = "D:\\DearM\\data\\dm\\spark\\input\\persion\\dm_data_03"
    //        val outputPath = "D:\\DearM\\data\\dm\\spark\\output\\Md5Match"
    //    val operator =  3
    try {
      val Array(inputPath, outputPath, operator) = args
      val l = operator.toInt
      val prefixArrays: Array[String] = if (l == 1) prefixs_cmcc else if (l == 2) prefixs_cucc else if (l == 3) prefixs_ctcc else prefixs_cmcc ++ prefixs_cucc ++ prefixs_ctcc
      run(inputPath, outputPath, prefixArrays)
    } catch {
      case e: Throwable => e.printStackTrace()
        println("<useArgs input output operator<1,2,3>( 1:cmcc,2:cucc,3,ctcc)")
    }

    //    test
  }
}
