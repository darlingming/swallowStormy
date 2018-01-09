package com.software.dm.swallow.stormy.scalacode

class 伴生类 private(val color: String) {

  println("创建" + this)

  override def toString(): String = "颜色标记：" + color

}

object 伴生类 {

  private val markers: Map[String, 伴生类] = Map(
    "red" -> new 伴生类("red"),
    "blue" -> new 伴生类("blue"),
    "green" -> new 伴生类("green")
  )

  def apply(color: String) = {
    if (markers.contains(color)) markers(color) else null
  }


  def getMarker(color: String) = {
    if (markers.contains(color)) markers(color) else null
  }

  def main(args: Array[String]) {
    println(伴生类("red"))
    // 单例函数调用，省略了.(点)符号
    println(伴生类 getMarker "blue")
  }
}