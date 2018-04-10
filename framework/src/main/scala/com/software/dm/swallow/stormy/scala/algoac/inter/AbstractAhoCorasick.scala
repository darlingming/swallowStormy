package com.software.dm.swallow.stormy.scala
package algoac
package inter


import java.util.concurrent.LinkedBlockingQueue
import scala.collection.mutable._

/**
  * @author darlingming@#126.com
  * @param rootState
  * @tparam T
  */
abstract class AbstractAhoCorasick[T](val rootState: AbstractState[T]) {
  protected var prepared = false

  def prepare(): Unit = {
    if (!this.prepared) this.prepareFailTransitions()
  }

  /**
    * DANGER DANGER: dense algorithm code ahead. Very order dependent.
    * Initializes the fail transitions of all states except for the root.
    */
  private def prepareFailTransitions(): Unit = {
    val queue = new LinkedBlockingQueue[AbstractState[T]]
    for (abstractState <- this.rootState.getStates) {
      queue.add(abstractState)
      abstractState.setFailure(this.rootState)
    }
    this.prepared = true
    while (!queue.isEmpty) {
      val currentstate: AbstractState[T] = queue.remove
      for (c <- currentstate.getValues) {
        val targetState = currentstate.nextState(c)
        queue.add(targetState)
        var failurestate = currentstate.getFailure
        while ( {
          failurestate.nextState(c) == null
        }) failurestate = failurestate.getFailure
        val newState = failurestate.nextState(c)
        targetState.setFailure(newState)
        targetState.addAllOutput(newState.getOutput)
      }
    }
  }

  /**
    *
    * @param c
    * @param currentState
    * @return
    */
  protected def getState(c: T, currentState: AbstractState[T]): AbstractState[T] = {
    var newCurrentState = currentState.nextState(c)
    while ( {
      newCurrentState == null
    }) {
      val currentFailureState = currentState.getFailure
      newCurrentState = currentFailureState.nextState(c)
    }
    newCurrentState
  }

  protected def getStateIgnore(c: T, currentState: AbstractState[T]): AbstractState[T] = {
    var newState = currentState.nextStateIgnoreRootState(c)
    if (newState == null) {
      for (tmp <- currentState.getStates) {
        newState = this.getStateIgnore(c, tmp)
        if (newState != null) return newState
      }
    }
    newState
  }

  /**
    *
    * @param keyword
    * @return
    */
  def addKeyWord(keyword: Any): AbstractAhoCorasick[T]

  /**
    *
    * @param keys
    * @return
    */
  def search(keys: Any): Iterable[Any]

  /**
    *
    * @param keys
    * @param set
    */
  def search(keys: Any, set: Set[Any]): Unit
}