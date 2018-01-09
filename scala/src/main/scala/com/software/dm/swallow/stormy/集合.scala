package com.software.dm.swallow.stormy

object 集合 {
  def main(args: Array[String]): Unit = {
    // 定义整型 List
    val list = List(1, 2, 3, 4)

    // 定义 Set
    var set = Set(1, 3, 5, 7)

    // 定义 Map
    val map = Map("one" -> 1, "two" -> 2, "three" -> 3)

    // 创建两个不同类型元素的元组
    val tuples = (10, "Runoob")

    // 定义 Option
    val opt: Option[Int] = Some(5)


  }
}

package paglist {

  object ListObject {
    // 字符串列表
    val site: List[String] = List("Runoob", "Google", "Baidu")

    // 整型列表
    val nums: List[Int] = List(1, 2, 3, 4)

    // 空列表
    val empty: List[Nothing] = List()

    // 二维列表
    val dim: List[List[Int]] =
      List(
        List(1, 0, 0),
        List(0, 1, 0),
        List(0, 0, 1)
      )
    // 字符串列表
    val site1 = "Runoob" :: ("Google" :: ("Baidu" :: Nil))

    // 整型列表
    val nums1 = 1 :: (2 :: (3 :: (4 :: Nil)))

    // 空列表
    val empty1 = Nil

    // 二维列表
    val dim1 = (1 :: (0 :: (0 :: Nil))) ::
      (0 :: (1 :: (0 :: Nil))) ::
      (0 :: (0 :: (1 :: Nil))) :: Nil


    def main(args: Array[String]) {
      val site1 = "Runoob" :: ("Google" :: ("Baidu" :: Nil))
      val site2 = "Facebook" :: ("Taobao" :: Nil)

      // 使用 ::: 运算符
      var fruit = site1 ::: site2
      println("site1 ::: site2 : " + fruit)

      // 使用 Set.:::() 方法
      fruit = site1.:::(site2)
      println("site1.:::(site2) : " + fruit)

      // 使用 concat 方法
      fruit = List.concat(site1, site2)
      println("List.concat(site1, site2) : " + fruit)

      val t = (4, 3, 2, 1)

      t.productIterator.foreach { i => println("Value = " + i) }
    }
  }

}
