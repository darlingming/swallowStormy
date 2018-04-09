package com.software.dm.swallow.stormy.scala.algoac.inter

import java.util
import java.util.Collection




/**
  * @author darlingming@126.com
  * @tparam T
  * @tparam D
  */
trait State[T, D] {
  def nextState(c: T): D

  def addState(c: T): D

  def nextStateIgnoreRootState(c: T): D

  def getValues: util.Collection[T]

  def getStates: util.Collection[D]
}