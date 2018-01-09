package com.software.dm.swallow.stormy.scalacode

object 提取器 {
  def main(args: Array[String]) {

    println("Apply 方法 : " + apply1("Zara", "gmail.com"));
    println("Unapply 方法 : " + unapply1("Zara@gmail.com"));
    println("Unapply 方法 : " + unapply1("Zara Ali"));

    val x = 提取器(6)

    println(x)

    x match
    {
      case 提取器(num) => println(x + " 是 " + num + " 的两倍！")
      //unapply 被调用
      case _ => println("无法计算")
    }

  }


  // 注入方法 (可选)
  def apply1(user: String, domain: String) = {
    user + "@" + domain
  }

  // 提取方法（必选）
  def unapply1(str: String): Option[(String, String)] = {
    val parts = str split "@"
    if (parts.length == 2) {
      Some(parts(0), parts(1))
    } else {
      None
    }
  }


  def apply(x: Int) = x*2
  def unapply(z: Int): Option[Int] = if (z%2==0) Some(z/2) else None
}


