package com.software.dm.swallow.stormy.scala.algoac.impl

import java.util

import com.software.dm.swallow.stormy.scala.algoac.inter.AbstractAhoCorasick
import com.software.dm.swallow.stormy.scala.algoac.pojo.ResultSetEntity


/**
  *
  *
  * @author darlingming@126.com
  *         2016
  * @version v1.0.0.1
  *
  */
class AhoCorasickCharacterTree() extends AbstractAhoCorasick[Character](new CharacterState(0)) {
  /**
    *
    */
  override def search(keys: Any): util.Collection[Any] = {
    if (!this.prepared) throw new IllegalStateException("can't start search until prepare()")
    val set = new util.HashSet[Any]
    this.search(keys, set)
    set
  }

  override def addKeyWord(keyword: Any): AhoCorasickCharacterTree = {
    var tmpState = this.rootState
    if (keyword.isInstanceOf[String]) {
      for (c <- keyword.toString.toCharArray) {
        tmpState = tmpState.addState(c)
      }
      tmpState.addOutput(keyword)
    } else if (keyword.isInstanceOf[ResultSetEntity]) {
      val reg = keyword.asInstanceOf[ResultSetEntity]
      for (c <- reg.getKeyWord.toCharArray) {
        if (c >= 65 && c <= 90) {
          val c1 = (32 + c).toChar
          tmpState = tmpState.addState(c1)
        } else {
          tmpState = tmpState.addState(c)
        }

      }
      tmpState.addOutput(reg)
    }
    this
  }

  /**
    *
    * @param keys
    * @param set
    */
  def search(keys: Any, set: util.Set[Any]): Unit = {
    if (!this.prepared) throw new IllegalStateException("can't start search until prepare()")
    var currentState = this.rootState
    for (c <- keys.toString.toCharArray) {
      if (c >= 65 && c <= 90) {
        val c1 = (32 + c).toChar
        currentState = this.getState(c1, currentState)
      } else {
        currentState = this.getState(c, currentState)
      }


      if (null != currentState.getOutput) set.addAll(currentState.getOutput)
    }
  }


}
