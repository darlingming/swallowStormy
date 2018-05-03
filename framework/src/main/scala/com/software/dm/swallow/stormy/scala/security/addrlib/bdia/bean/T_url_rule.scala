package com.software.dm.swallow.stormy.scala
package security.addrlib.bdia.bean

import scala.collection.mutable.ListBuffer


class T_url_rule {
  val ACTION_BASIC_APP = 0

  private var n_id = 0L
  private var domain_one: String = null
  private var brand: String = null
  private var domain_comp: String = null
  private var service_name: String = null
  private var rule: String = null
  private var rule_type = 0
  private var rule_type_level = 0
  private var is_app_url = 0
  private var bqz_id: String = null
  private var bqz_name: String = null

  private var basicTypes: ListBuffer[T_basic_type_rel] = null

  def this(n_id: Long, domain_one: String, brand: String, domain_comp: String, service_name: String, rule: String, rule_type: Int, rule_type_level: Int, is_app_url: Int, bqz_id: String, bqz_name: String, basicTypes: ListBuffer[T_basic_type_rel]) {
    this()
    this.n_id = n_id
    this.domain_one = domain_one
    this.brand = brand
    this.domain_comp = domain_comp
    this.service_name = service_name
    this.rule = rule
    this.rule_type = rule_type
    this.rule_type_level = rule_type_level
    this.is_app_url = is_app_url
    this.bqz_id = bqz_id
    this.bqz_name = bqz_name
    this.basicTypes = basicTypes
  }

  def getN_id: Long = n_id

  def getDomain_one: String = domain_one

  def getBrand: String = brand

  def getDomain_comp: String = domain_comp

  def getService_name: String = service_name

  def getRule: String = rule

  def getRule_type: Int = rule_type

  def getRule_type_level: Int = rule_type_level

  def getIs_app_url: Int = is_app_url

  def getBqz_id: String = bqz_id

  def getBqz_name: String = bqz_name

  def getBasicTypes: ListBuffer[T_basic_type_rel] = basicTypes
}
