package com.software.dm.swallow.stormy.scala.security.addrlib.bdia.bean

class T_ip_rule {
  val ACTION_IP = 4

  private var n_id = 0L
  private var ip : String = null
  private var port : String =null
  private var rule_type: Int = 0
  private var bqz_id : String = null
  private var bqz_name : String = null

  def this(n_id: Long, ip: String, port: String, rule_type: Int, bqz_id: String, bqz_name: String) {
    this()
    this.n_id = n_id
    this.ip = ip
    this.port = port
    this.rule_type = rule_type
    this.bqz_id = bqz_id
    this.bqz_name = bqz_name
  }

  def getN_id: Long = n_id

  def getIp: String = ip

  def getPort: String = port

  def getRule_type: Int = rule_type

  def getBqz_id: String = bqz_id

  def getBqz_name: String = bqz_name
}
