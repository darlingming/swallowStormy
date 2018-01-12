package com.software.dm.swallow.stormy.spark.user

import com.software.dm.swallow.stormy.spark.common.MD5
import org.apache.log4j.Level
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD
import org.apache.spark.sql._
import org.apache.spark.storage.StorageLevel

import scala.collection.mutable.ListBuffer
import scala.util.control.Breaks

/**
  *
  * @param sparkConf
  */
class Md5RDDMatch(sparkConf: SparkConf) {
  var sparkSession: SparkSession = _

  def preStart(): Unit = {

    sparkSession = SparkSession.builder().config(sparkConf).getOrCreate()
  }
}

/**
  *
  */
object Md5RDDMatch {
  // 9999999999L 99999999L
  val maxLength = 99999999L
  val len = maxLength.toString.length
  //计数
  var poi = 0L
  //分割处理  10000000L
  val n = 10000000L

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
  private[this] val prefixs_cmcc = Array("158", "134", "135", "136", "137", "138", "139",
    "147", "150", "151", "152", "157", "159", "182", "183", "184", "187", "188", "178"
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


  /**
    *
    * @param sc
    * @param path
    * @return
    */
  def dataSource(sc: SparkContext, path: String): RDD[Tuple2[String, String]] = {
    val rddUserSource: RDD[String] = sc.textFile(path)
    val rddUser: RDD[Tuple2[String, String]] = rddUserSource.map(x => (x, ""))
    rddUser.persist(StorageLevel.MEMORY_AND_DISK)
    //    rddUser.cache()
    rddUser.checkpoint()
    rddUser
  }

  /**
    *
    * @param rddUser
    * @param rddUserResult
    * @return
    */
  def refDataSource(rddUser: RDD[Tuple2[String, String]], rddUserResult: RDD[Tuple2[String, String]]): RDD[Tuple2[String, String]] = {

    val rddUser_new = rddUser.leftOuterJoin(rddUserResult).filter(x => x._2._2 == None).map(x => (x._1, ""))
    rddUser.unpersist(true)
    rddUser_new.persist(StorageLevel.MEMORY_AND_DISK)
    //    rddUser_new.cache()
    rddUser_new.checkpoint()
    rddUser_new
  }

  /**
    *
    * @param inputPath
    * @param outputPath
    * .master("local[2]")
    */
  def run(inputPath: String, outputPath: String, prefixArrays: Array[String]): Unit = {
    val loop = new Breaks;
    val appName = "Md5RDDMatchV1.0"
    val master = "local[*]"
    //    val conf = new SparkConf().setAppName(appName).setMaster(master)
    val conf = new SparkConf().setAppName(appName)
    val sc = new SparkContext(conf)

    //    conf.set("spark.executor.heartbeatInterval", "300")

    sc.setCheckpointDir("checkpoint")
    sc.setLogLevel(Level.INFO.toString)

    var rdduser: RDD[Tuple2[String, String]] = this.dataSource(sc, inputPath)
    val listBuffer: ListBuffer[Tuple2[String, String]] = new ListBuffer[Tuple2[String, String]]
    loop.breakable {
      for (perfix <- prefixArrays; m <- poi to maxLength) {
        val mStr = m.toString
        val str = addStr(perfix, mStr)
        listBuffer += str
        if ((m > 0 && m % n == 0) || (m + 1) == maxLength) {
          //          println(listBuffer)
          val rddmd5: RDD[Tuple2[String, String]] = sc.parallelize(listBuffer)
          val rddmd5Result = rdduser.join(rddmd5).map(x => (x._1, x._2._2))
          rddmd5Result.cache()
//.coalesce(1) .coalesce(2,true)
          if (!rddmd5Result.isEmpty()) {
            rddmd5Result.map(x => x._1 + "," + x._2).saveAsTextFile(outputPath + "/part_" + System.nanoTime())
            rdduser = this.refDataSource(rdduser, rddmd5Result)
            if (rdduser.isEmpty()) {
              loop.break
            }
          }

          listBuffer.clear()
        }
      }
    }
    sc.stop()
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
    //    val inputPath = "D:\\DearM\\data\\dm\\spark\\input\\persion\\dm_data_03"
    //    val outputPath = "D:\\DearM\\data\\dm\\spark\\output\\Md5RDDMatch"
    //    val operator = 1
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
