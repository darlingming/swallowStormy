package com.software.dm.swallow.stormy


/**
  * Scala 语言中提供的数组是用来存储固定大小的同类型元素，
  * 数组对于每一门编辑应语言来说都是重要的数据结构之一。
  * 声明数组变量并不是声明 number0、number1、...、number99 一个个单独的变量，
  * 而是声明一个就像 numbers 这样的变量，然后使用 numbers[0]、numbers[1]、...、numbers[99]
  * 来表示一个个单独的变量。数组中某个指定的元素是通过索引来访问的。
  * 数组的第一个元素索引为0，最后一个元素的索引为元素总数减1。
  */
object 数组 {
  def main(args: Array[String]) {
    var myList = Array(1.9, 2.9, 3.4, 3.5)

    // 输出所有数组元素
    for (x <- myList) {
      println(x)
    }

    // 计算数组所有元素的总和
    var total = 0.0;
    for (i <- 0 to (myList.length - 1)) {
      total += myList(i);
    }
    println("总和为 " + total);

    // 查找数组中的最大元素
    var max = myList(0);
    for (i <- 1 to (myList.length - 1)) {
      if (myList(i) > max) max = myList(i);
    }
    println("最大值为 " + max);
    //多维数组

    import Array._
    var myMatrix = ofDim[Int](3, 3)

    // 创建矩阵
    for (i <- 0 to 2) {
      for (j <- 0 to 2) {
        myMatrix(i)(j) = j;
      }
    }

    // 打印二维阵列
    for (i <- 0 to 2) {
      for (j <- 0 to 2) {
        print(" " + myMatrix(i)(j));
      }
      println();
    }
    //数组合并

    var myList1 = Array(1.9, 2.9, 3.4, 3.5)
    var myList2 = Array(8.9, 7.9, 0.4, 1.5)

    var myList3 = concat(myList1, myList2)

    // 输出所有数组元素
    for (x <- myList3) {
      println(x)
    }

    //区间数组
    var myList4 = range(10, 20, 2)
    var myList5 = range(10, 20)

    // 输出所有数组元素
    for (x <- myList4) {
      print(" " + x)
    }
    println()
    for (x <- myList5) {
      print(" " + x)
    }


  }
}
