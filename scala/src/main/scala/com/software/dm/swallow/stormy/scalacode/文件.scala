package com.software.dm.swallow.stormy.scalacode

import java.io._
import scala.io.Source

object 文件 {
  def main(args: Array[String]) {
    println(文件.getClass.getResource("/").getPath)
    val writer = new PrintWriter(new File("scala/src/main/resources/test.txt"))
    println(writer.toString)
    writer.write("学习问题")
    writer.close()
    println("文件内容为:")

    Source.fromFile("scala/src/main/resources/test.txt").foreach {
      print
    }
  }
}
