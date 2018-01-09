package com.software.dm.swallow.stormy

/**
  * Scala 访问修饰符基本和Java的一样，分别有：private，protected，public。
如果没有指定访问修饰符符，默认情况下，Scala对象的访问级别都是 public。
Scala 中的 private 限定符，比 Java 更严格，在嵌套类情况下，外层类甚至不能访问被嵌套类的私有成员。
  */
object 访问修饰符 {


  def main(args: Array[String]): Unit = {

  }
}
class Outer{
  class Inner{
    private def f(){println("f")}
    class InnerMost{
      f() // 正确
    }
  }

//  (new Inner).f() //错误
}

package p{
  class Super{
    protected def f() {println("f")}
  }
  class Sub extends Super{
    f()
  }
  class Other{
//    (new Super).f() //错误
  }
}

class Public_Outer {
  class Inner {
    def f() { println("f") }
    class InnerMost {
      f() // 正确
    }
  }
  (new Inner).f() // 正确因为 f() 是 public
}


/**
private[x]

或

protected[x]
这里的x指代某个所属的包、类或单例对象。如果写成private[x],读作"这个成员除了对[…]中的类或[…]中的包中的类及它们的伴生对像可见外，对其它所有类都是private。
这种技巧在横跨了若干包的大型项目中非常有用，它允许你定义一些在你项目的若干子包中可见但对于项目外部的客户却始终不可见的东西。

  */
package bobsrocckets{
  package navigation{
//    private[bobsrockets] class Navigator{
//
//      protected[navigation] def useStarChart(){}
//      class LegOfJourney{
//        private[Navigator] val distance = 100
//      }
//      private[this] var speed = 200
//    }
  }
//  package launch{
//    import navigation._
//    object Vehicle{
//      private[launch] val guide = new Navigator
//    }
//  }
}

