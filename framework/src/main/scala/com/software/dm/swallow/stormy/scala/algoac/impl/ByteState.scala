package com.software.dm.swallow.stormy.scala.algoac.impl

import com.software.dm.swallow.stormy.scala.algoac.inter.AbstractState


/**
  *
  * Description
  * @author DearM
  * date 2016
  * @version v1.0.0.1
  *
  */
class ByteState(  depth: Int) extends AbstractState[Byte]( depth: Int)  {


  override def addState(c: Byte): AbstractState[Byte] = {
    var abstractState = this.nextStateIgnoreRootState(c)
    if (abstractState == null) {
      abstractState = new ByteState(this.depth + 1)
      this.success.put(c, abstractState)
    }
    abstractState
  }
}