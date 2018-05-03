package com.software.dm.swallow.stormy.scala
package security.addrlib
package bdia

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util
import java.util.{Arrays, Date, HashMap, Map}

import com.software.dm.swallow.stormy.security.`match`.bdia.builder.CreateFactroy


/**
  * @author DM
  * @version v1.0.0.1
  * Description
  *  date 2017
  */


object  MainSerial {

  val param_Flag = "-D"
  val CONF_PATH = "conf.path"
  val SERIAL_NAME = "serial.name"
  val VERSION = "BDIA_FS_V4.0.2.1"


  def main(args: Array[String]): Unit = {
    val df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    System.out.println(df.format(new Date) + " start......")
    System.out.println("Serial_Version:" + VERSION)
    println(args.mkString(","))
    val paramMap = new util.HashMap[String, String]
    if (null != args && args.length > 0 && (args.length & 1) == 0) {
      var i = 0
      while ( {
        i < args.length
      }) {
        if (param_Flag == args(i)) {
          val vs = args({
            i += 1;
            i
          }).split("=", -1)
          if (vs.length == 2) paramMap.put(vs(0), vs(1))
        }

        {
          i += 1;
          i - 1
        }
      }
    }
    val cf = new CreateFactroy
    cf.exec(paramMap.get(CONF_PATH), paramMap.get(SERIAL_NAME))
    println(df.format(new Date) + " end......")

  }
}