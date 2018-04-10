package com.software.dm.swallow.stormy.scala
package algoac
package inter

import pojo.VagueResultEntity

import scala.collection.mutable._


/**
  *
  *
  * @author DM
  *         2017
  * @version v1.0.0.1
  *
  *
  */
object VagueState {
  /**
    *
    */
  val STAR_CHAR = '*'
  val patternMap = new HashMap[String, List[Any]]
}

/**
  *
  * @tparam T
  */
trait VagueState[T] {
  /**
    *
    * @param t
    * @param nextNormalState
    */
  def addNextNormalStateMap(t: T, nextNormalState: VagueState[T]): Unit

  /**
    *
    * @param t
    * @param nextVagueState
    */
  def addNextVagueStateMap(t: T, nextVagueState: VagueState[T]): Unit

  /**
    *
    * @return
    */
  def getNextVagueStateMap: Map[T, VagueState[T]]

  def getNextNormalStateMap: Map[T, VagueState[T]]

  /**
    *
    * @param vagueResultEntityList
    */
  def addVagueResultEntityList(vagueResultEntityList: VagueResultEntity): Unit

  def getVagueResultEntityList: ListBuffer[VagueResultEntity]

  /**
    *
    * @param pattern
    * @param vagueResultEntity
    */
  def addState(pattern: String, vagueResultEntity: VagueResultEntity): Unit

  /**
    *
    * @param patterns
    * @param resultDataSet
    */
  def serachVagueResult(patterns: String, resultDataSet: Set[Any]): Unit
}
