package com.software.dm.swallow.stormy.scala
package algoac
package inter


import scala.collection.Iterable

/**
  * @author darlingming@126.com
  * @tparam T
  * @tparam D
  */
trait State[T, D] {
  def nextState(c: T): D

  def addState(c: T): D

  def nextStateIgnoreRootState(c: T): D

  def getValues: Iterable[T]

  def getStates: Iterable[D]
}