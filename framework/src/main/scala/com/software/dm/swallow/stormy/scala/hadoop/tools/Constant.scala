package com.software.dm.swallow.stormy.scala.hadoop.tools

/**
  * @author DearM
  * @version v1.0.0.1
  *          Description Constant
  *          date 2011
  */
object Constant {
  val LINE_SEPARATOR: String = System.getProperty("line.separator")
  val CURRENT_DIR: String = System.getProperty("user.dir")
  val FILE_SEPARATOR: String = System.getProperty("file.separator")
  /**
    * char '@'
    */
  val PUB_AT_CHAR = '@'
  /**
    * char '*'
    */
  val PUB_ASTERISK_CHAR = '*'
  /**
    * str "@"
    */
  val PUB_AT = "@"
  /**
    * space " "
    */
  val PUB_SPACE = " "
  /**
    * , str
    */
  val PUB_COMMA = ","
  /**
    * , char
    */
  val PUB_COMMA_CHAR = ','
  /**
    * char .
    */
  val PUB_POINT_CHAR = '.'
  /**
    * string .
    */
  val PUB_POINT_STR = "."
  val PUB_UNDERLINE_STR = "_"
  val PUB_UNDERLINE_CHAR = '_'
  /**
    * tab \t string
    */
  val PUB_TAB = "\t"
  /**
    * \t char
    */
  val PUB_TAB_CHAR = '\t'
  /**
    * \u0001 str
    */
  val PUB_FIELD_STR = "\u0001"
  /**
    * \u0001 char
    */
  val PUB_FIELD_CHAR = '\u0001'
  /**
    * \u0002 str
    */
  val PUB_LINE_STR = "\u0002"
  /**
    * \u0002 char
    */
  val PUB_LINE_CHAR = '\u0002'
  /**
    * \u0003 char
    */
  val PUB_FLAG_CHAR = '\u0003'
  /**
    * \u0003 str
    */
  val PUB_FLAG_STR = "\u0003"
  /**
    * \u0004 char
    */
  val PUB_EVE_LINE_CHAR = '\u0004'
  /**
    * | char
    */
  val PUB_VERTICAL_LINE_CHAR = '|'
  /**
    * | String
    */
  val PUB_VERTICAL_LINE_STR = "|"
  val MAP_KEY = "MAP_KEY"
  val MAP_VALUE = "MAP_VALUE"
  val REDUCE_KEY = "REDUCE_KEY"
  val REDUCE_VALUE = "REDUCE_VALUE"
  val MAP_REDUCE_FLAG = "MAP_REDUCE_FLAG"
}

trait Constant {
  @throws[Exception]
  def init(): Unit
}
