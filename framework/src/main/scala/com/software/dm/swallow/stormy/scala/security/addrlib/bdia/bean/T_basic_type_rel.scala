package com.software.dm.swallow.stormy.scala
package security.addrlib.bdia.bean

/**
  *@author darlingming@126.com
  *2018-05-01
  */
class T_basic_type_rel extends  Serializable {
  private var n_id = 0L
  private var type_id = ""
  private var all_name = ""

  def this(n_id: Long, type_id: String, all_name: String) {
    this()
    this.n_id = n_id
    this.type_id = type_id
    this.all_name = all_name
  }

  def getN_id: Long = n_id

  def getType_id: String = type_id

  def getAll_name: String = all_name
}
