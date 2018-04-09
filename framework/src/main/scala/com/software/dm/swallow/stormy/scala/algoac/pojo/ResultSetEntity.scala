package com.software.dm.swallow.stormy.scala.algoac.pojo

import java.util
import java.util.Arrays




/**
  *@author darlingming@126.com
  * @param regnum
  * @param keyWord
  * @param values
  * @param `type`
  * @param source
  * @version v1.0.0.1
  */
final class ResultSetEntity(val regnum: Int, var keyWord: String, var values: Array[String], var `type`: Int, var source: Any ) {



  def getSource: Any = source

  def getRegnum: Int = regnum

  def getKeyWord: String = keyWord

  def getValues: Array[String] = values

  def getType: Int = `type`

  override def toString: String = "ResultSetEntity [regnum=" + regnum + ", keyWord=" + keyWord + ", values=" +values  + ", type=" + `type` + "]"
}

