package com.software.dm.swallow.stormy.scala.security.addrlib.bdia.bean

import scala.collection.mutable.ListBuffer


class T_theme_url_rule {
  val ACTION_THEME = 1


  private var n_id = 0L
  private var domain_comp: String  = null
  private var rule : String = null
  private var rule_type = 0
  private var themeTypes : ListBuffer[T_theme_basic_type_rel]  = null

  def this(n_id: Long, domain_comp: String, rule: String, rule_type: Int, themeTypes:  ListBuffer[T_theme_basic_type_rel]) {
    this()
    this.n_id = n_id
    this.domain_comp = domain_comp
    this.rule = rule
    this.rule_type = rule_type
    this.themeTypes = themeTypes
  }

  def getN_id: Long = n_id

  def getDomain_comp: String = domain_comp

  def getRule: String = rule

  def getRule_type: Int = rule_type

  def getThemeTypes:  ListBuffer[T_theme_basic_type_rel] = themeTypes
}
