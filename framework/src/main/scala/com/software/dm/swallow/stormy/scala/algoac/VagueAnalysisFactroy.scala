package com.software.dm.swallow.stormy.scala.algoac

import java.util
import java.util._

import com.software.dm.swallow.stormy.scala.algoac.impl.VagueStateCharacter
import com.software.dm.swallow.stormy.scala.algoac.inter.VagueState
import com.software.dm.swallow.stormy.scala.algoac.pojo.VagueResultEntity


/**
  *
  *
  * @author darlingming@126.com
  *   2017
  * @version v1.0.0.1
  *
  *
  */
final class VagueAnalysisFactroy() {
  final private val vagueState = new VagueStateCharacter(new util.HashMap[Character, VagueState[Character]], new util.HashMap[Character, VagueState[Character]])

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
  final private val resultDataSet = new util.HashSet[Any]

  @SuppressWarnings(Array("unused")) private def print(vagueState: VagueState[Character]): Unit = {
    if (vagueState.getVagueResultEntityList != null) System.out.println(util.Arrays.toString(vagueState.getVagueResultEntityList.toArray))
    var vaguestateMap = vagueState.getNextNormalStateMap
    if (null != vaguestateMap) {
      import scala.collection.JavaConversions._
      for (iterable_element <- vaguestateMap.entrySet) {
        print(iterable_element.getValue)
      }
    }
    vaguestateMap = vagueState.getNextVagueStateMap
    if (null != vaguestateMap) {
      import scala.collection.JavaConversions._
      for (iterable_element <- vaguestateMap.entrySet) {
        print(iterable_element.getValue)
      }
    }
  }

  /**
    *
    * @param value
    * @return Set<Object>
    */
  def serachResult(value: String): util.Set[Any] = {
    resultDataSet.clear()
//     this.print(vagueState);
    this.vagueState.serachVagueResult(value, resultDataSet)
    resultDataSet
  }
}
