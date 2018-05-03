package com.software.dm.swallow.stormy.scala
package security.addrlib.bdia.bean

import scala.collection.mutable._
import java.util.regex.Pattern

class T_extract_rule {
  val ACTION_EXTRACT = 2

  private var rid = 0
  private var theme_id = 0
  private var plat_id = 0
  private var prod_id: String = null
  private var action_id = 0
  private var content_type: String = null
  private var fullDomain: String = null
  private var rule: String = null
  private var content_rule: String = null
  private var uncode: String = null
  private var uncodes: Map[Int, Array[Int]] = null
  private var spiderType: String = null
  private var rule_type:Int = 0
  // private String[] paramRegexs;
  private var paramRegexPatternArray: Array[Pattern] = null

  def this(rid: Int, theme_id: Int, plat_id: Int, prod_id: String, action_id: Int, content_type: String, fullDomain: String, rule: String, content_rule: String, uncode: String, spiderType: String, rule_type: Int) {
    this()
    this.rid = rid
    this.theme_id = theme_id
    this.plat_id = plat_id
    this.prod_id = prod_id
    this.action_id = action_id
    this.content_type = content_type
    this.fullDomain = fullDomain
    this.rule = rule
    this.content_rule = content_rule
    this.uncode = uncode
    this.spiderType = spiderType
    this.rule_type = rule_type
  }

  def getParamRegexPatternArray: Array[Pattern] = paramRegexPatternArray

  def setParamRegexPatternArray(paramRegexPatternArray: Array[Pattern]): Unit = {
    this.paramRegexPatternArray = paramRegexPatternArray
  }

  def getRid: Int = rid

  def getTheme_id: Int = theme_id

  def getPlat_id: Int = plat_id

  def getProd_id: String = prod_id

  def getAction_id: Int = action_id

  def getContent_type: String = content_type

  def getFullDomain: String = fullDomain

  def getRule: String = rule

  def getContent_rule: String = content_rule

  def getUncode: String = uncode

  def getSpiderType: String = spiderType

  def getRule_type: Int = rule_type

  def getUncodes: Map[Int, Array[Int]] = uncodes

  def setUncodes(uncodes: Map[Int, Array[Int]]): Unit = {
    this.uncodes = uncodes
  }

  override def toString: String = "T_extract_rule [rid=" + rid + ", theme_id=" + theme_id + ", plat_id=" + plat_id + ", prod_id=" + prod_id + ", action_id=" + action_id + ", content_type=" + content_type + ", fullDomain=" + fullDomain + ", rule=" + rule + ", content_rule=" + content_rule + ", uncode=" + uncode + ", spiderType=" + spiderType + ", rule_type=" + rule_type + "]"
}
