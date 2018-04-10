package com.software.dm.swallow.stormy.scala
package algoac
package pojo


/**
  * @author darlingming@126.com
  * @param `type`
  * @param text
  * @param obj
  */
final class Param(val `type`: Int = 0, val text: String, val obj: Any) {



  def getType: Int = `type`

  def getText: String = text

  def getObj: Any = obj

  override def toString: String = "Param [type=" + `type` + ", text=" + text + ", obj=" + obj + "]"
}

