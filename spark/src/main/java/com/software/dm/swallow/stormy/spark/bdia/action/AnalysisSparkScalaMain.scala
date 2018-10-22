package com.software.dm.swallow.stormy.spark.bdia.action

import java.io.{File, IOException, InputStream}
import java.text.SimpleDateFormat
import java.util
import java.util.{ArrayList, Date, List}

import com.software.dm.swallow.stormy.hadoop.tools.AbstractCommonUtils
import com.software.dm.swallow.stormy.security.`match`.bdia.MainVagueSerial
import com.software.dm.swallow.stormy.security.`match`.bdia.bean.{T_extract_rule, T_theme_url_rule, T_url_rule}
import com.software.dm.swallow.stormy.security.`match`.bdia.obtain.SerialVagueFactroy
import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.spark.api.java.JavaRDD
import org.apache.spark.input.PortableDataStream
import org.apache.spark.{SparkConf, SparkContext}

/**
  * DM
  * 2018-10-17
  *
  **/
object AnalysisSparkScalaMain {


  def init: Unit = {

  }


  def main(args: Array[String]): Unit = {
    val appName = "RddSparkAction"
    val master = "local[2]"
    val conf = new SparkConf().setAppName(appName).setMaster(master)
//    conf.set("spark.executor.heartbeatInterval", "300")
//    conf.set("Dsun.io.serialization.extendedDebugInfo", "true")
//    conf.set("sun.io.serialization.extendedDebugInfo", "true")
//    conf.set("spark.kryoserializer.buffer.max", "128")
//    conf.set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
    val sc = new SparkContext(conf)
    //    MainVagueSerial.main(null);


    val df = new SimpleDateFormat("yyyyMMdd")
    val serialPath = "D:/DearM/repo/yun/serial" + File.separatorChar + df.format(new Date) + ".serial"
    val pds = sc.binaryFiles(serialPath, 1).first._2
    val is = pds.open
    val sf = new SerialVagueFactroy(is)

    val output = "data/spark.rddmultiple/dm_spark"
    val fileSystem = FileSystem.get(sc.hadoopConfiguration)
    fileSystem.delete(new Path(output), true)


    var input = "D:\\DearM\\dpi\\input\\bjdx\\dpi_bjdx"

    input = "D:\\DearM\\dpi\\input\\bjdx\\bjdx-000841_0"
    val javaRDD = sc.textFile(input)

    val mapRdd = javaRDD.flatMap(x => {


      val values: Array[String] = x.split(",", -1)
      val url: String = values(24)
      val fullDomain: Array[String] = AbstractCommonUtils.getFullDomainWithBareUrl(url.getBytes)
      val resList = new util.ArrayList[String]
      if (null != fullDomain) {
        //        println(fullDomain(0))
        sf.execute(fullDomain)
        val url_rule: T_url_rule = sf.getBasicAppResult
        val theme_url_ruleList: util.List[T_theme_url_rule] = sf.getThemeResultList
        val extract_ruleList: util.List[T_extract_rule] = sf.getExtractResultList
        if (null != url_rule)
          resList.add("APP" + "\001" + values(0) + "\001" + url_rule.getBqz_id)
        //basic
        //app
        //theme
        //subject
        import scala.collection.JavaConversions._
        for (t_extract_rule <- extract_ruleList) {
          val s1: String = sf.outputExtract(url, t_extract_rule)
          resList.add("Subject" + "\001" + values(0) + "\001" + s1)
        }
        import scala.collection.JavaConversions._
        for (t_theme_url_rule <- theme_url_ruleList) {
          import scala.collection.JavaConversions._
          for (t_theme_basic_type_rel <- t_theme_url_rule.getThemeTypes) {
            //           println(t_theme_basic_type_rel.getType_id() + "===" + t_theme_url_rule.getRule());
            resList.add("Theme" + "\001" + values(0) + "\001" + t_theme_basic_type_rel.getType_id)
          }
        }
      }

      resList.toArray();
    })
    mapRdd.repartition(5).saveAsTextFile(output)
  }
}
