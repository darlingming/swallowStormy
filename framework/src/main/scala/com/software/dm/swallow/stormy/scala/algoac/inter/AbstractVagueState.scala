package com.software.dm.swallow.stormy.scala.algoac.inter

import com.software.dm.swallow.stormy.scala.algoac.pojo.VagueResultEntity
import java.util
import java.util.{ArrayList, HashMap, List, Map}

import org.apache.commons.lang.ObjectUtils.Null


/**
  *
  * @author DM (darlingming@126.com)
  * @version v1.0.0.1
  * @tparam T
  */
abstract class AbstractVagueState[T](var nextNormalStateMap: util.Map[T, VagueState[T]], var nextVagueStateMap: util.Map[T, VagueState[T]], value: Char) extends  VagueState[T] {

  def this(value: Char) {
    this(null, null, value)
  }


  def this(nextNormalStateMap: Map[T, VagueState[T]], nextVagueStateMap: Map[T, VagueState[T]]) {

    this(nextNormalStateMap, nextVagueStateMap, '0')
  }

  /**
    *
    */
  private var vagueResultEntityList: util.ArrayList[VagueResultEntity] = null

  def getVagueResultEntityList: util.ArrayList[VagueResultEntity] = vagueResultEntityList

  def addVagueResultEntityList(vagueResultEntityList: VagueResultEntity): Unit = {
    if (this.vagueResultEntityList == null)
      this.vagueResultEntityList = new util.ArrayList[VagueResultEntity]
    this.vagueResultEntityList.add(vagueResultEntityList)
  }

  //  protected var value: Char
  //  //
  //  protected var nextNormalStateMap: util.Map[T, Nothing] = null
  //  //
  //  protected var nextVagueStateMap: util.Map[T, Nothing] = null

  def getNextNormalStateMap: util.Map[T, VagueState[T]] = this.nextNormalStateMap

  def addNextNormalStateMap(t: T, nextNormalState: VagueState[T]): Unit = {
    if (null == this.nextNormalStateMap) this.nextNormalStateMap = new util.HashMap[T, VagueState[T]]
    this.nextNormalStateMap.put(t, nextNormalState)
  }

  def getNextVagueStateMap: util.Map[T, VagueState[T]] = this.nextVagueStateMap

  def addNextVagueStateMap(t: T, nextVagueState: VagueState[T]): Unit = {
    if (null == this.nextVagueStateMap) this.nextVagueStateMap = new util.HashMap[T, VagueState[T]]
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
