package com.software.dm.swallow.stormy.scala
package algoac


import impl.VagueStateCharacter
import inter.VagueState
import pojo.VagueResultEntity
import scala.collection.mutable._

/**
  *
  *
  * @author darlingming@126.com
  *         2017
  * @version v1.0.0.1
  *
  *
  */
final class VagueAnalysisFactroy() extends  Serializable{
  final private val vagueState = new VagueStateCharacter(new HashMap[Character, VagueState[Character]], new HashMap[Character, VagueState[Character]])

  /**
    *
    * @param pattern
    * @param obj
    */
  def addData(pattern: String, obj: Any): Unit = {
    if (null == pattern || pattern.isEmpty) return
    val bool = pattern.charAt(pattern.length - 1) != VagueState.STAR_CHAR
    val vagueResultEntity = new VagueResultEntity(obj, bool)
    this.vagueState.addState(pattern.toLowerCase, vagueResultEntity)
  }

  /**
    * HashSet
    *
    */
  final private val resultDataSet = new HashSet[Any]

  @SuppressWarnings(Array("unused"))
  private def print(vagueState: VagueState[Character]): Unit = {
    if (vagueState.getVagueResultEntityList != null)
      println(vagueState.getVagueResultEntityList.toArray)
    var vaguestateMap = vagueState.getNextNormalStateMap
    if (null != vaguestateMap) {
      for ((k,v)<- vaguestateMap ) {
        print(v)
      }
    }
    vaguestateMap = vagueState.getNextVagueStateMap
    if (null != vaguestateMap) {
      for ((k,v) <- vaguestateMap ) {
        print(v)
      }
    }
  }

  /**
    *
    * @param value
    * @return Set<Object>
    */
  def serachResult(value: String): Set[Any] = {
    resultDataSet.clear()
//     this.print(vagueState);
    this.vagueState.serachVagueResult(value, resultDataSet)
    resultDataSet
  }
}
