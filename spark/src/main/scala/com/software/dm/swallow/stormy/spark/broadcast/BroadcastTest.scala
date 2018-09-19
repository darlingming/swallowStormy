package com.software.dm.swallow.stormy.spark.broadcast

import java.io.File
import java.text.SimpleDateFormat
import java.util.Date

import com.software.dm.swallow.stormy.hadoop.tools.AbstractCommonUtils
import com.software.dm.swallow.stormy.scala.security.addrlib.bdia.obtain.SerialVagueFactroy
import org.apache.spark.broadcast.Broadcast
import org.apache.spark.sql.types.StructField
import org.apache.spark.{SparkConf, SparkContext}
import org.nustaq.serialization.FSTConfiguration

class BroadcastTestObject extends  Serializable{
  val a: String = "aaa";

}

object BroadcastTest {
  def main(args: Array[String]): Unit = {
    val df = new SimpleDateFormat("yyyyMMdd")
    val serialPath = "D:/DearM/repo/yun/serial" + File.separatorChar + df.format(new Date) + ".serial"
    val sf = new SerialVagueFactroy(serialPath)
    // Set<Param> pset =
    // applicationContainer.getAfDomain().serachResult("mp.weixin.qq.comclient.map.baidu.com");
    // System.out.println(pset);

    val conf = new SparkConf().setAppName("SplitTest").setMaster("local")
//      .set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
//        .set("spark.serializer","org.apache.spark.serializer.KryoSerialization")
conf.registerKryoClasses( Array(classOf[ StructField],classOf[FSTConfiguration],classOf[SerialVagueFactroy]))
    val sc = new SparkContext(conf)
    val vcto = new BroadcastTestObject


    val bc: Broadcast[SerialVagueFactroy] = sc.broadcast(sf)


    val url = "http://gdl.sregame-download.com/mobile/immortal_texas/src/common/layer/SubLayer.lua?game_timestamp=1.0.84"
    //      System.out.println(AbstractCommonUtils.toLowerCase(url))
    val fullDomain = AbstractCommonUtils.getFullDomainWithBareUrl(url.getBytes)
    println("fullDomain=" + fullDomain.mkString(","))
    bc.value.execute(fullDomain)
    val res = bc.value.getBasicAppResult

    println(res)
    sc.stop()
  }
}
