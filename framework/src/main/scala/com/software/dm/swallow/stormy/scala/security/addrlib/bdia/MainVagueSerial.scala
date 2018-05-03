package com.software.dm.swallow.stormy.scala
package security.addrlib
package bdia

import java.text.SimpleDateFormat
import java.util
import java.util.Date

import builder.CreateVagueFactroy






/**
  * @author DM
  * @version v1.0.0.1
  *  Description
  *  date 2017
  */
object MainVagueSerial {
  val param_Flag = "-D"
  val CONF_PATH = "conf.path"
  val SERIAL_NAME = "serial.name"
  val VERSION = "BDIA_FS_V4.0.2.3"

  def main(args: Array[String]): Unit = {
    val df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    System.out.println(df.format(new Date) + " start......")
    System.out.println("Serial_Version:" + VERSION)
    println( args.mkString(","))
    val paramMap = new util.HashMap[String, String]

    if (null != args && args.length > 0 && (args.length & 1) == 0) {
      var i :Int= 0
      while(i < args.length){
        if (param_Flag.equals(args(i))) {
          i += 1
          val  vs:Array[String] = args(i).split("=", -1)
          if (vs.length == 2) {
            paramMap.put(vs(0), vs(1))
          }
        }
        i += 1
      }
    }
     val cf :CreateVagueFactroy= new CreateVagueFactroy()
    cf.exec(paramMap.get(CONF_PATH), paramMap.get(SERIAL_NAME));
    System.out.println(df.format(new Date()) + " end......");

  }
}

class MainVagueSerial() {
}
