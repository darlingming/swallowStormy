package com.software.dm.swallow.stormy.scala
package algoac
package inter


import scala.collection.mutable.{HashMap, HashSet, Set}
import scala.collection.Iterable


/**
  * @author darlingming@126.com
  * @param depth
  * @tparam T
  */
abstract class AbstractState[T](val depth: Int) extends State[T, AbstractState[T]] {

  final protected var rootState = if (this.depth == 0) this else null

  private var failure: AbstractState[T] = null
  private var output: Set[Any] = null
  protected var success = new HashMap[T, AbstractState[T]]
  protected var prepared = false


  def nextState(c: T): AbstractState[T] = this.nextState(c, false)

  def nextStateIgnoreRootState(c: T): AbstractState[T] = this.nextState(c, true)

  def nextState(c: T, ignoreroot: Boolean): AbstractState[T] = {
    val currentState = this.success.get(c).getOrElse(null)
    if (!ignoreroot && currentState == null && this.rootState != null) return this.rootState
    currentState
  }

  def getValues: Iterable[T] = this.success.keys

  def getStates: Iterable[AbstractState[T]] = this.success.values

  def addOutput(output: Any): Unit = {
    if (this.output == null) this.output = new HashSet[Any]
    this.output.add(output)
  }

  def addAllOutput(output: Set[Any]): Unit = {
    if (output == null || output.isEmpty) return
    if (this.output == null) { //			return;
      this.output = new HashSet[Any]
    }
    this.output.add(output)
  }

  def getOutput: Set[Any] = { //		return this.output == null ? new HashSet<Object>() : this.output;
    this.output
  }

  def getFailure: AbstractState[T] = failure

  def setFailure(failure: AbstractState[T]): Unit = {
    this.failure = failure
  }

  def getDepth: Int = depth
}
