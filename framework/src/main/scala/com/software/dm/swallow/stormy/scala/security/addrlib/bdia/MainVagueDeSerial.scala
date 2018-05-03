package com.software.dm.swallow.stormy.scala.security.addrlib.bdia

import java.io.File
import java.text.SimpleDateFormat
import java.util
import java.util.{Arrays, Date}

import com.software.dm.swallow.stormy.hadoop.tools.AbstractCommonUtils
import com.software.dm.swallow.stormy.scala.security.addrlib.bdia.obtain.SerialVagueFactroy



/**
  * @author DM
  * @version v1.0.0.1
  *  Description
  *  date 2017
  */
object MainVagueDeSerial {
  def main(args: Array[String]): Unit = {
    try {
      val df = new SimpleDateFormat("yyyyMMdd")
      val serialPath = "D:/DearM/repo/yun/serial" + File.separatorChar + df.format(new Date) + ".serial"
      val sf = new SerialVagueFactroy(serialPath)
      // Set<Param> pset =
      // applicationContainer.getAfDomain().serachResult("mp.weixin.qq.comclient.map.baidu.com");
      // System.out.println(pset);
      val url = "http://gdl.sregame-download.com/mobile/immortal_texas/src/common/layer/SubLayer.lua?game_timestamp=1.0.84"
//      System.out.println(AbstractCommonUtils.toLowerCase(url))
      val fullDomain = AbstractCommonUtils.getFullDomainWithBareUrl(url.getBytes)
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
//      import scala.collection.JavaConversions._
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

