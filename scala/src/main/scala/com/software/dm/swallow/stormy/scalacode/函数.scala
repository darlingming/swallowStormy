package com.software.dm.swallow.stormy

/**
  * 函数是一组一起执行一个任务的语句。 您可以把代码划分到不同的函数中。如何划分代码到不同的函数中是由您来决定的，但在逻辑上，划分通常是根据每个函数执行一个特定的任务来进行的。
  * Scala 有函数和方法，二者在语义上的区别很小。Scala 方法是类的一部分，而函数是一个对象可以赋值给一个变量。换句话来说在类中定义的函数即是方法。
  * 我们可以在任何地方定义函数，甚至可以在函数内定义函数（内嵌函数）。更重要的一点是 Scala 函数名可以有以下特殊字符：+, ++, ~, &,-, -- , \, /, : 等。
  *
  */
object 函数 {


}
package function {

  package callbyname {

    /**
      * Scala的解释器在解析函数参数(function arguments)时有两种方式：
      * 传值调用（call-by-value）：先计算参数表达式的值，再应用到函数内部；
      * 传名调用（call-by-name）：将未计算的参数表达式直接应用到函数内部
      */
    object Test {
      def main(args: Array[String]) {
        delayed(time());
      }

      def time() = {
        println("获取时间，单位为纳秒")
        System.nanoTime
      }

      def delayed(t: => Long) = {
        println("在 delayed 方法内")
        println("参数： " + t)
        t
      }
    }

  }

  package 指定函数参数名 {

    object Test {
      def main(args: Array[String]) {
        printInt(b = 5, a = 7);
      }

      def printInt(a: Int, b: Int) = {
        println("Value of a : " + a);
        println("Value of b : " + b);
      }
    }

  }

  package 可变参数 {

    object Test {
      def main(args: Array[String]) {
        printStrings("Runoob", "Scala", "Python");
      }

      def printStrings(args: String*) = {
        var i: Int = 0;
        for (arg <- args) {
          println("Arg value[" + i + "] = " + arg);
          i = i + 1;
        }
      }
    }

  }

  /**
    *
    * 递归函数在函数式编程的语言中起着重要的作用。
    * Scala 同样支持递归函数。
    * 递归函数意味着函数可以调用它本身。
    */

  package 递归函数 {

    object Test {
      def main(args: Array[String]) {
        for (i <- 1 to 10)
          println(i + " 的阶乘为: = " + factorial(i))
      }

      def factorial(n: BigInt): BigInt = {
        if (n <= 1)
          1
        else
          n * factorial(n - 1)
      }
    }

  }

  /**
    * 函数参数指定默认参数值，使用了默认参数，你在调用函数的过程中可以不需要传递参数，
    * 这时函数就会调用它的默认参数值，如果传递了参数，则传递值会取代默认值。
    */
  package 默认参数值 {

    object Test {
      def main(args: Array[String]) {
        println("返回值 : " + addInt());
      }

      def addInt(a: Int = 5, b: Int = 7): Int = {
        var sum: Int = 0
        sum = a + b

        return sum
      }
    }

  }

  /**
    * 高阶函数（Higher-Order Function）就是操作其他函数的函数。
    * Scala 中允许使用高阶函数, 高阶函数可以使用其他函数作为参数，或者使用函数作为输出结果。
    * 以下实例中，apply() 函数使用了另外一个函数 f 和 值 v 作为参数，而函数 f 又调用了参数 v：
    */
  package 高阶函数 {

    object Test {
      def main(args: Array[String]) {

        println(apply(layout, 10))

      }

      // 函数 f 和 值 v 作为参数，而函数 f 又调用了参数 v
      def apply(f: Int => String, v: Int) = f(v)

      def layout[A](x: A) = "[" + x.toString() + "]"

    }

  }

  /**
    * 我们可以在 Scala 函数内定义函数，定义在函数内的函数称之为局部函数。
    * 以下实例我们实现阶乘运算，并使用内嵌函数：
    */
  package 函数嵌套 {

    object Test {
      def main(args: Array[String]) {
        println(factorial(0))
        println(factorial(1))
        println(factorial(2))
        println(factorial(3))
      }

      def factorial(i: Int): Int = {
        def fact(i: Int, accumulator: Int): Int = {
          if (i <= 1)
            accumulator
          else
            fact(i - 1, i * accumulator)
        }

        fact(i, 1)
      }
    }

  }

  /**
    * Scala 中定义匿名函数的语法很简单，箭头左边是参数列表，右边是函数体。
    * 使用匿名函数后，我们的代码变得更简洁了。
    */
  package 匿名函数 {

    object Demo {
      def main(args: Array[String]) {
        println("multiplier(1) value = " + multiplier(1))
        println("multiplier(2) value = " + multiplier(2))
      }

      var factor = 3
      val multiplier = (i: Int) => i * factor
    }

  }

  /**
    * Scala 偏应用函数是一种表达式，你不需要提供函数需要的所有参数，只需要提供部分，或不提供所需参数。
    */
  package 偏应用函数 {

    import java.util.Date

    object Test {
      def main(args: Array[String]) {
        val date = new Date
        val logWithDateBound = log(date, _: String)

        logWithDateBound("message1")
        Thread.sleep(1000)
        logWithDateBound("message2")
        Thread.sleep(1000)
        logWithDateBound("message3")
      }

      def log(date: Date, message: String) = {
        println(date + "----" + message)
      }
    }

  }

  /**
    * 柯里化(Currying)指的是将原来接受两个参数的函数变成新的接受一个参数的函数的过程。
    * 新的函数返回一个以原有第二个参数为参数的函数。
    */
  package 函数柯里化 {

    object Test {
      def main(args: Array[String]) {
        val str1: String = "Hello, "
        val str2: String = "Scala!"
        println("str1 + str2 = " + strcat(str1)(str2))
      }

      def strcat(s1: String)(s2: String) = {
        s1 + s2
      }
    }

  }

}