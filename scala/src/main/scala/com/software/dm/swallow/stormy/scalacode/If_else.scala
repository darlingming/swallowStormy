package com.software.dm.swallow.stormy.scalacode

/**
  * if(布尔表达式)
  * {
  * // 如果布尔表达式为 true 则执行该语句块
  * }
  *
  * if(布尔表达式){
  * // 如果布尔表达式为 true 则执行该语句块
  * }else{
  * // 如果布尔表达式为 false 则执行该语句块
  * }
  *
  *
  * if(布尔表达式 1){
  * // 如果布尔表达式 1 为 true 则执行该语句块
  * }else if(布尔表达式 2){
  * // 如果布尔表达式 2 为 true 则执行该语句块
  * }else if(布尔表达式 3){
  * // 如果布尔表达式 3 为 true 则执行该语句块
  * }else {
  * // 如果以上条件都为 false 执行该语句块
  * }
  */
object If_else {
  def main(args: Array[String]) {
    var x = 10;

    if (x < 20) {
      println("x < 20");
    }

    if (x < 20) println("x 小于 20")
    else println("x 大于 20")

    if (x == 10) {
      println("X 的值为 10");
    } else if (x == 20) {
      println("X 的值为 20");
    } else if (x == 30) {
      println("X 的值为 30");
    } else {
      println("无法判断 X 的值");
    }


    /**
      * if(布尔表达式 1){
      * // 如果布尔表达式 1 为 true 则执行该语句块
      * if(布尔表达式 2){
      * // 如果布尔表达式 2 为 true 则执行该语句块
      * }
      * }
      */
    var y = 10;
    if (x == 30) {
      if (y == 10) {
        println("X = 30 , Y = 10");
      }
    }

  }
}
