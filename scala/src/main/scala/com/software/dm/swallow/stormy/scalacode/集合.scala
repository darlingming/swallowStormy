package com.software.dm.swallow.stormy.scalacode

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

object ListObject1 {
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
