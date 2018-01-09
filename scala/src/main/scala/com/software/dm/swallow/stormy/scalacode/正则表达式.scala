package com.software.dm.swallow.stormy.scalacode

import scala.util.matching.Regex

/**
  *
  * 表达式	匹配规则
  * ^	匹配输入字符串开始的位置。
  * $	匹配输入字符串结尾的位置。
  * .	匹配除"\r\n"之外的任何单个字符。
  * [...]	字符集。匹配包含的任一字符。例如，"[abc]"匹配"plain"中的"a"。
  * [^...]	反向字符集。匹配未包含的任何字符。例如，"[^abc]"匹配"plain"中"p"，"l"，"i"，"n"。
  * \\A	匹配输入字符串开始的位置（无多行支持）
  * \\z	字符串结尾(类似$，但不受处理多行选项的影响)
  * \\Z	字符串结尾或行尾(不受处理多行选项的影响)
  * re*	重复零次或更多次
  * re+	重复一次或更多次
  * re?	重复零次或一次
  * re{ n}	重复n次
  * re{ n,}
  * re{ n, m}	重复n到m次
  * a|b	匹配 a 或者 b
  * (re)	匹配 re,并捕获文本到自动命名的组里
  * (?: re)	匹配 re,不捕获匹配的文本，也不给此分组分配组号
  * (?> re)	贪婪子表达式
  * \\w	匹配字母或数字或下划线或汉字
  * \\W	匹配任意不是字母，数字，下划线，汉字的字符
  * \\s	匹配任意的空白符,相等于 [\t\n\r\f]
  * \\S	匹配任意不是空白符的字符
  * \\d	匹配数字，类似 [0-9]
  * \\D	匹配任意非数字的字符
  * \\G	当前搜索的开头
  * \\n	换行符
  * \\b	通常是单词分界位置，但如果在字符类里使用代表退格
  * \\B	匹配不是单词开头或结束的位置
  * \\t	制表符
  * \\Q	开始引号：\Q(a+b)*3\E 可匹配文本 "(a+b)*3"。
  * \\E	结束引号：\Q(a+b)*3\E 可匹配文本 "(a+b)*3"。
  *
  */
object 正则表达式 {
  def main(args: Array[String]) {
    val pattern = "(S|s)cala".r
    val pattern1 = new Regex("(S|s)cala") // 首字母可以是大写 S 或小写 s
    val str = "Scala is Scalable and cool"

    println((pattern findAllIn str).mkString(","))

    println(pattern replaceFirstIn(str, "Java"))
  }
}
