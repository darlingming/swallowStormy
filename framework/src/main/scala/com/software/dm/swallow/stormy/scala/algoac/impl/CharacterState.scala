package com.software.dm.swallow.stormy.scala
package algoac
package impl

import inter.AbstractState


/**
  *
  * Description
  * author DearM
  * date 2016
  *
  * @version v1.0.0.1
  *
  */
class CharacterState(depth: Int) extends AbstractState[Character](depth: Int) {


  override def addState(c: Character): AbstractState[Character] = {
    var abstractState = this.nextStateIgnoreRootState(c)
    if (abstractState == null) {
      abstractState = new CharacterState(this.depth + 1)
      this.success.put(c, abstractState)
    }
    abstractState
  }
}

object CharacterState {

}