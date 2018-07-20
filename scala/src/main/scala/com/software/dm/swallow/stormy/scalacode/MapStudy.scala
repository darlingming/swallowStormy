package com.software.dm.swallow.stormy.scalacode

class MapStudy {
  var a: Map[String, Int] = Map("k1" -> 1, "k2" -> 2) //初始化构造函数
  a += ("k3" -> 3) //添加元素
  a += ("k4" -> 4) //添加元素
  a += ("k1" -> 100) //已经存在添加元素会覆盖
  a -= ("k2", "k1") //删除元素
  //    a("k1") = "foo"//不支持
  println(a.contains("k6")) //是否包含某元素
  println(a.size) //打印大小
  println(a.get("k1").getOrElse("default")) //根据key读取元素，不存在就替换成默认值
  a.foreach { case (e, i) => println(e, i) } //遍历打印1
  for ((k, v) <- a) println(k, v) //遍历打印2
  println(a.isEmpty) //判断是否为空
  a.keys.foreach(println) //只打印key
  a.values.foreach(println) //只打印value
  a = Map() //数据清空使用再次new
  println(a.size)
  a.toSeq.sortBy(_._1) //升序排序 key
  a.toSeq.sortBy(_._2) //升序排序 value
  a.toSeq.sortWith(_._1 > _._1) //降序排序 key
  a.toSeq.sortWith(_._2 > _._2) //降序排序 value

  //下面自定义按英文字母或数字排序
  implicit val KeyOrdering = new Ordering[String] {
    override def compare(x: String, y: String): Int = {
      x.compareTo(y)
    }
  }
  println(a.toSeq.sorted)

  def map3(): Unit = {
    //不可变Map+var关键词修饰例子
    var a: scala.collection.mutable.Map[String, Int] = scala.collection.mutable.Map("k1" -> 1, "k2" -> 2) //初始化构造函数
    a += ("k3" -> 3) //添加元素
    a += ("k4" -> 4) //添加元素
    a += ("k1" -> 100) //已经存在添加元素会覆盖
    a += ("k1" -> 100, "k9" -> 9) //添加多个元素
    a -= ("k2", "k1") //删除元素
    a ++= List("CA" -> 23, "CO" -> 25) //追加集合
    a --= List("AL", "AZ") //删除集合

    a.retain((k, v) => k == "k1") //只保留等于k1元素，其他的删除
    a.put("put1", 200) //put
    a.remove("k2") //remove
    a.clear() //清空
    a("k3") = 100 //支持

    println(a.contains("k6")) //是否包含某元素
    println(a.size) //打印大小
    println(a.get("k1").getOrElse("default")) //根据key读取元素，不存在就替换成默认值
    a.foreach { case (e, i) => println(e, i) } //遍历打印1
    for ((k, v) <- a) println(k, v) //遍历打印2
    println(a.isEmpty) //判断是否为空
    a.keys.foreach(println) //只打印key
    a.values.foreach(println) //只打印value
    a = scala.collection.mutable.Map() //引用能变
    println(a.size)
    a.toSeq.sortBy(_._1) //排序 key
    a.toSeq.sortBy(_._2) //排序 value
    a.toSeq.sortWith(_._1 > _._1) //降序排序 key
    a.toSeq.sortWith(_._2 > _._2) //降序排序 value

    //下面自定义按英文字母或数字排序
    implicit val KeyOrdering = new Ordering[String] {
      override def compare(x: String, y: String): Int = {
        x.compareTo(y)
      }
    }
    println(a.toSeq.sorted)
  }
}
