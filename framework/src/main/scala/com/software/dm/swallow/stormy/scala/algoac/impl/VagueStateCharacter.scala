package com.software.dm.swallow.stormy.scala.algoac.impl

import java.util
import java.util.Map

import com.software.dm.swallow.stormy.scala.algoac.inter.{AbstractVagueState, VagueState}
import com.software.dm.swallow.stormy.scala.algoac.pojo.VagueResultEntity

import scala.util.control.Breaks._


/**
  *
  *
  * @author DM
  *         2017
  * @version v1.0.0.1
  *
  */
class VagueStateCharacter(nextNormalStateMap: Map[Character, VagueState[Character]], nextVagueStateMap: Map[Character, VagueState[Character]], value: Char) extends AbstractVagueState[Character](nextNormalStateMap, nextVagueStateMap, value) {


  //  def this(value: Char) {
  //
  //    this(null, null, value)
  //  }
  //
  //  def this(nextNormalStateMap: Map[T, VagueState[T]] , Map[T, VagueState[T]] nextVagueStateMap) {
  //
  //    this(null, null, null)
  //  }
  def this(value: Char) {
    this(null, null, value)
  }


  def this(nextNormalStateMap: Map[Character, VagueState[Character]], nextVagueStateMap: Map[Character, VagueState[Character]]) {

    this(nextNormalStateMap, nextVagueStateMap, '0')
  }

  /**
    *
    * @param pattern
    * @param vagueResultEntity
    */
  def addState(pattern: String, vagueResultEntity: VagueResultEntity): Unit = {
    var isStar = false
    var currentVagueState: VagueState[Character] = this
    val values = pattern.toCharArray
    for (i <- 0 until values.length) {
      breakable {
        if (values(i) == VagueState.STAR_CHAR) {
          if (isStar)
            break
          isStar = true
          break
        }
        var newVagueState: VagueState[Character] = null
        if (isStar) {
          val vaguestateMap: util.Map[Character, VagueState[Character]] = currentVagueState.getNextVagueStateMap
          if (null != vaguestateMap) newVagueState = vaguestateMap.get(values(i))
          if (null == vaguestateMap || null == newVagueState) {
            newVagueState = this.getInstance(values(i))
            currentVagueState.addNextVagueStateMap(values(i), newVagueState)
            currentVagueState = newVagueState
          }
          else currentVagueState = newVagueState
          isStar = false
        }
        else { //
          val normalStateMap = currentVagueState.getNextNormalStateMap
          if (null != normalStateMap) newVagueState = normalStateMap.get(values(i))
          if (null == normalStateMap || null == newVagueState) {
            newVagueState = this.getInstance(values(i))
            currentVagueState.addNextNormalStateMap(values(i), newVagueState)
            currentVagueState = newVagueState
          }
          else currentVagueState = newVagueState
        }
      }
    }
    currentVagueState.asInstanceOf[VagueStateCharacter].addVagueResultEntityList(vagueResultEntity)

  }

  /**
    *
    */
  def serachVagueResult(patterns: String, resultDataSet: util.Set[Any]): Unit = {
    if (null != patterns && !patterns.isEmpty) {
      val c_patterns = patterns.toCharArray
      for (i <- 0 until c_patterns.length) {
        if (c_patterns(i) >= 65 && c_patterns(i) <= 90)
          c_patterns(i) = (32 + c_patterns(i)).toChar
      }
      this.serachResult(this, c_patterns, 0, resultDataSet, false)
    }
  }

  /**
    *
    *
    * @param c_patterns
    * @param poi
    * @param resultDataSet
    */
  def serachResult(serachVaguestate: VagueState[Character], c_patterns: Array[Char], poi: Int, resultDataSet: util.Set[Any], skipVague: Boolean): Unit = {

    // try output
    if (!skipVague) this.tryOutputResult(serachVaguestate, c_patterns.length, poi, resultDataSet)
    if (poi >= c_patterns.length) return
//    println("poi====" + poi + "c_patterns=" + c_patterns(poi))
    var vs: VagueState[Character] = null
    val normalStateMap: util.Map[Character, VagueState[Character]] = serachVaguestate.getNextNormalStateMap
    if (null != normalStateMap && !skipVague) {
      vs = normalStateMap.get(c_patterns(poi))
      if (null != vs) this.serachResult(vs, c_patterns, poi + 1, resultDataSet, false)
    }
    val vagueStateMap = serachVaguestate.getNextVagueStateMap
    if (null != vagueStateMap) {
      vs = vagueStateMap.get(c_patterns(poi))
      if (null != vs) this.serachResult(vs, c_patterns, poi + 1, resultDataSet, false)
      this.serachResult(serachVaguestate, c_patterns, poi + 1, resultDataSet, true)
    }
  }

  /**
    *
    * @param vs
    * @param len
    * @param poi
    * @param resultDataSet
    */
  def tryOutputResult(vs: VagueState[Character], len: Int, poi: Int, resultDataSet: util.Set[Any]): Unit = {
    val vagueResultEntityList = vs.getVagueResultEntityList

    if (null != vagueResultEntityList) {
      import scala.collection.JavaConversions._
      for (vagueResultEntity <- vagueResultEntityList) {
        if (vagueResultEntity.isMustEqual) {
          if (len == poi)
            resultDataSet.add(vagueResultEntity.getObj)
        } else {
          resultDataSet.add(vagueResultEntity.getObj)
        }
        //          println("vagueResultEntity===" + vagueResultEntity);
      }
    }
  }

  /**
    *
    * @param value
    * @return
    */
  def getInstance(value: Character): VagueState[Character] = {
    new VagueStateCharacter(value)
  }


}
