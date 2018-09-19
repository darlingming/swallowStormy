package com.software.dm.swallow.stormy.spark.test1

import java.io.File
import java.text.SimpleDateFormat
import java.util
import java.util.Date

import com.software.dm.swallow.stormy.hadoop.tools.AbstractCommonUtils
import com.software.dm.swallow.stormy.scala.security.addrlib.bdia.MainVagueSerial
import com.software.dm.swallow.stormy.scala.security.addrlib.bdia.obtain.SerialVagueFactroy
import org.apache.spark.sql.SparkSession

object SparkReadHdfs {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession
      .builder()
      .master("local[1]")

      .appName("SparkSQLTest01")
      .getOrCreate()
    val sc =  spark.sparkContext
    val df = new SimpleDateFormat("yyyyMMdd")
    val serialPath = "D:/DearM/repo/yun/serial" + File.separatorChar +  "20180809" + ".serial"
   var a = sc.binaryFiles(serialPath).first()._2
    try {

      MainVagueSerial.main(null);

      val sf = new SerialVagueFactroy(a.open())
      // Set<Param> pset =
      // applicationContainer.getAfDomain().serachResult("mp.weixin.qq.comclient.map.baidu.com");
      // println(pset);
      val url = "m.worldwayhk.com/country/atg.html"
      //      println(AbstractCommonUtils.toLowerCase(url))
      val fullDomain = AbstractCommonUtils.getFullDomainWithBareUrl(url.getBytes)
      //      println(util.Arrays.toString(fullDomain))
      sf.execute(fullDomain)
      // println(sf.basicAppResult.getBasicTypes().size());
      // for (T_theme_url_rule ttur : sf.themeResultList) {
      // println(ttur.getThemeTypes().size());
      // println(ttur.getN_id());
      // }
      println(sf.getBasicAppResult)
      println( sf.getThemeResultList.toList.mkString(","))
      println(sf.getExtractResultList)
      println(sf.getIpResult)
      //      import scala.collection.JavaConversions._
      for (t_extract_rule <- sf.getExtractResultList) {
        val s = sf.outputExtract(url, t_extract_rule)
        println(s)
      }

    } catch {
      case e: Exception =>
        // TODO Auto-generated catch block
        e.printStackTrace()
    }

    spark.stop();

  }
}
