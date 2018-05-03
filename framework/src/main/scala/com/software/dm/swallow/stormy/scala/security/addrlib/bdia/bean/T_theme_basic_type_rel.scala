package com.software.dm.swallow.stormy.scala.security.addrlib.bdia.bean

/**
  *
  */
class T_theme_basic_type_rel {

  private var subject_id: String = null
  private var n_id = 0L
  private var type_id: String = null
  private var type_level = 0
  private var all_name: String = null

  def this(subject_id: String, n_id: Long, type_id: String, type_level: Int, all_name: String) {
    this()
    this.subject_id = subject_id
    this.n_id = n_id
    this.type_id = type_id
    this.type_level = type_level
    this.all_name = all_name
  }

  def getType_level: Int = type_level

  def getSubject_id: String = subject_id

  def getN_id: Long = n_id

  def getType_id: String = type_id

  def getAll_name: String = all_name
}
