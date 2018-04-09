package com.software.dm.swallow.stormy.scala.algoac.inter

import com.software.dm.swallow.stormy.scala.algoac.pojo.VagueResultEntity
import java.util
import java.util.{HashMap, List, Map, Set}


/**
  *
  *
  * @author DM
  *  e 2017
  * @version v1.0.0.1
  *
  *
  */
object VagueState {
  /**
    *
    */
  val STAR_CHAR = '*'
  val patternMap = new util.HashMap[String, util.List[Any]]
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
  def addNextVagueStateMap(t: T, nextVagueState:  VagueState[T]): Unit

  /**
    *
    * @return
    */
  def getNextVagueStateMap: util.Map[T, VagueState[T]]

  def getNextNormalStateMap: util.Map[T,  VagueState[T]]

  /**
    *
    * @param vagueResultEntityList
    */
  def addVagueResultEntityList(vagueResultEntityList: VagueResultEntity): Unit

  def getVagueResultEntityList: util.List[VagueResultEntity]

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
  def serachVagueResult(patterns: String, resultDataSet: util.Set[Any]): Unit
}
