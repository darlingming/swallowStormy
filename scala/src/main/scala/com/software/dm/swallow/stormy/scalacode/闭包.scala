package com.software.dm.swallow.stormy.scalacode

/**
  * 闭包是一个函数，返回值依赖于声明在函数外部的一个或多个变量。
  * 闭包通常来讲可以简单的认为是可以访问一个函数里面局部变量的另外一个函数。
  */
object 闭包 {


  def main(args: Array[String]): Unit = {
    println("muliplier(1) value = " + multiplier(1))
    println("muliplier(2) value = " + multiplier(2))
  }


  var factor = 3
  val multiplier = (i: Int) => i * factor


}
