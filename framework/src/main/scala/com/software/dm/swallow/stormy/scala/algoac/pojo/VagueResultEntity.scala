package com.software.dm.swallow.stormy.scala.algoac.pojo


/**
  * @author darlingming@126.com
  * @param obj
  * @param mustEqual
  *  2017
  * @version v1.0.0.1
  */
final class VagueResultEntity(var obj: Any, var mustEqual: Boolean) {


  def getObj: Any = obj

  def setObj(obj: Any): Unit = {
    this.obj = obj
  }

  def isMustEqual: Boolean = mustEqual

  def setMustEqual(mustEqual: Boolean): Unit = {
    this.mustEqual = mustEqual
  }

  override def toString: String = "VagueResultEntity [obj=" + obj + ", mustEqual=" + mustEqual + "]"
}

