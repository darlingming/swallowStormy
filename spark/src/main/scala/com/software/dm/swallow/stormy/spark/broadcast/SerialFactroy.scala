package com.software.dm.swallow.stormy.spark.broadcast

import java.io.File
import java.text.SimpleDateFormat
import java.util.Date

import com.software.dm.swallow.stormy.scala.security.addrlib.bdia.obtain.SerialVagueFactroy

class SerialFactroy  extends  Serializable {
  val df = new SimpleDateFormat("yyyyMMdd")
  val serialPath = "D:/DearM/repo/yun/serial" + File.separatorChar + df.format(new Date) + ".serial"
  val sf = new SerialVagueFactroy(serialPath)
}
