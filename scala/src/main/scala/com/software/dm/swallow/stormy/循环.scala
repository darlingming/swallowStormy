package com.software.dm.swallow.stormy

import scala.util.control.Breaks

/**
  *
  * 循环类型	描述
  * while 循环	运行一系列语句，如果条件为true，会重复运行，直到条件变为false。
  *do...while 循环	类似 while 语句区别在于判断循环条件之前，先执行一次循环的代码块。
  * for 循环	用来重复执行一系列语句直到达成特定条件达成，一般通过在每次循环完成后增加计数器的值来实现。
  */

object 循环 {

  def test: Unit ={
    var a = 0;
    val numList = List(1,2,3,4,5,6,7,8,9,10);

    val loop = new Breaks;
    loop.breakable {
      for( a <- numList){
        println( "Value of a: " + a );
        if( a == 4 ){
          loop.break;
        }
      }
    }
    println( "After the loop" );
  }

  def test1: Unit ={
    var a = 0;
    var b = 0;
    val numList1 = List(1,2,3,4,5);
    val numList2 = List(11,12,13);

    val outer = new Breaks;
    val inner = new Breaks;

    outer.breakable {
      for( a <- numList1){
        println( "Value of a: " + a );
        inner.breakable {
          for( b <- numList2){
            println( "Value of b: " + b );
            if( b == 12 ){
              inner.break;
            }
          }
        } // 内嵌循环中断
      }
    } // 外部循环中断
  }
  def main(args: Array[String]): Unit = {
    test1
    var a = 10
    do
       a = 1
    while (a>1)
  }
}
