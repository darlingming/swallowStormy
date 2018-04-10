package com.software.dm.swallow.stormy.scala
package algoac
package inter

import com.software.dm.swallow.stormy.scala.algoac.pojo.VagueResultEntity


import scala.collection.mutable._

/**
  *
  * @author DM (darlingming@126.com)
  * @version v1.0.0.1
  * @tparam T
  */
abstract class AbstractVagueState[T](var nextNormalStateMap: Map[T, VagueState[T]], var nextVagueStateMap: Map[T, VagueState[T]], value: Char) extends VagueState[T] {

  def this(value: Char) {
    this(null, null, value)
  }


  def this(nextNormalStateMap: Map[T, VagueState[T]], nextVagueStateMap: Map[T, VagueState[T]]) {

    this(nextNormalStateMap, nextVagueStateMap, '0')
  }

  /**
    *
    */
  private var vagueResultEntityList: ListBuffer[VagueResultEntity] = null

  def getVagueResultEntityList: ListBuffer[VagueResultEntity] = vagueResultEntityList

  def addVagueResultEntityList(vagueResultEntityList: VagueResultEntity): Unit = {
    if (this.vagueResultEntityList == null)
      this.vagueResultEntityList = new ListBuffer[VagueResultEntity]
    this.vagueResultEntityList.append(vagueResultEntityList)
  }

  //  protected var value: Char
  //  //
  //  protected var nextNormalStateMap: Map[T, Nothing] = null
  //  //
  //  protected var nextVagueStateMap: Map[T, Nothing] = null

  def getNextNormalStateMap: Map[T, VagueState[T]] = this.nextNormalStateMap

  def addNextNormalStateMap(t: T, nextNormalState: VagueState[T]): Unit = {
    if (null == this.nextNormalStateMap) this.nextNormalStateMap = new HashMap[T, VagueState[T]]
    this.nextNormalStateMap.put(t, nextNormalState)
  }

  def getNextVagueStateMap: Map[T, VagueState[T]] = this.nextVagueStateMap

  def addNextVagueStateMap(t: T, nextVagueState: VagueState[T]): Unit = {
    if (null == this.nextVagueStateMap) this.nextVagueStateMap = new HashMap[T, VagueState[T]]
    this.nextVagueStateMap.put(t, nextVagueState)
  }

  /**
    *
    * @param value
    * @return
    */
  def getInstance(value: T): VagueState[T]

  /**
    *
    * @return value
    */
  def getValue: Char = value
}
