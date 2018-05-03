package com.software.dm.swallow.stormy.scala
package security.addrlib.bdia

import java.io.File
import java.text.SimpleDateFormat
import java.util
import java.util.{Arrays, Date}

import com.software.dm.swallow.stormy.hadoop.tools.AbstractCommonUtils
import com.software.dm.swallow.stormy.security.`match`.bdia.obtain.SerialFactroy

object MainDeSerial {


  def main(args: Array[String]): Unit = {
    try {
      val df = "20171212" //new SimpleDateFormat("yyyyMMdd")
      val serialPath = "D:/DearM/repo/v4/serial" + File.separatorChar + df.format(new Date) + ".serial"
      val sf = new SerialFactroy(serialPath)
      // Set<Param> pset =
      // applicationContainer.getAfDomain().serachResult("mp.weixin.qq.comclient.map.baidu.com");
      // System.out.println(pset);
      val url = "http://APP.nuomi.com/naserver/ITEM/ITEMDETAILNEW/aaa&AS=TYRWINFFJKHRTUJHGFFjsjdfdsfhJJHG"
      System.out.println(AbstractCommonUtils.toLowerCase(url))
      val fullDomain = AbstractCommonUtils.getFullDomainWithBareUrl(url.getBytes)
//      System.out.println(util.Arrays.toString(fullDomain))
      sf.execute(fullDomain)
      // System.out.println(sf.basicAppResult.getBasicTypes().size());
      // for (T_theme_url_rule ttur : sf.themeResultList) {
      // System.out.println(ttur.getThemeTypes().size());
      // System.out.println(ttur.getN_id());
      // }
      System.out.println(sf.getThemeResultList)
      System.out.println(sf.getExtractResultList)
      System.out.println(sf.getIpResult)
      import scala.collection.JavaConversions._
      for (t_extract_rule <- sf.getExtractResultList) {
        sf.outputExtract(url, t_extract_rule)
      }
    } catch {
      case e: Exception =>
        // TODO Auto-generated catch block
        e.printStackTrace()
    }
  }

}
