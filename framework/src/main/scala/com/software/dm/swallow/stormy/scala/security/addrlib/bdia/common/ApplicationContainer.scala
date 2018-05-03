package com.software.dm.swallow.stormy.scala
package security.addrlib
package bdia
package common


import java.util.regex.Pattern

import com.esotericsoftware.kryo.Kryo
import algoac.AnalysisFactroy
import algoac.impl.AhoCorasickCharacterTree
import algoac.impl.CharacterState
import algoac.pojo.Param
import algoac.pojo.ResultSetEntity
import bean._

import scala.collection.mutable._



/**
  * @author DM
  * @version v1.0.0.1
  *          Description
  *          date 2017
  */
object ApplicationContainer {

  def getKryo: Kryo = {
    val kryo = new Kryo
    kryo.setRegistrationRequired(true)
    kryo.register(classOf[ApplicationContainer])
    kryo.register(classOf[AnalysisFactroy])
    kryo.register(classOf[HashMap[_, _]])
    kryo.register(classOf[ListBuffer[_]])
    kryo.register(classOf[HashSet[_]])
    kryo.register(classOf[Param])
    kryo.register(classOf[T_extract_rule])
    kryo.register(classOf[T_basic_type_rel])
    kryo.register(classOf[T_theme_url_rule])
    kryo.register(classOf[T_theme_basic_type_rel])
    kryo.register(classOf[T_basic_type_rel])
    kryo.register(classOf[T_url_rule])
    kryo.register(classOf[T_ip_rule])
    kryo.register(classOf[AhoCorasickCharacterTree])
    kryo.register(classOf[CharacterState])
    kryo.register(classOf[ResultSetEntity])
    kryo.register(classOf[Array[String]])
    kryo.register(classOf[Array[Integer]])
    kryo.register(classOf[Pattern])
    kryo.register(classOf[Array[Pattern]])
    kryo
  }
}

final class ApplicationContainer() // TODO Auto-generated constructor stub
{
  final private val domainEqualsMap = new HashMap[String, ListBuffer[Any]]
  final private val afDomain = new AnalysisFactroy
  final private val afRule = new AnalysisFactroy
  final private val extractPatternRuleMap = new HashMap[T_extract_rule, Pattern]
  final private val extractDoMainPatternRuleMap = new HashMap[T_extract_rule, Pattern]
  final private val iPMap = new HashMap[String, T_ip_rule]
  final private val basicTypeRelMap = new HashMap[Long, ListBuffer[T_basic_type_rel]]
  final private val themeBasicTypeRelMap = new HashMap[Long, ListBuffer[T_theme_basic_type_rel]]
  final private val urlRuleList = new ListBuffer[T_url_rule]
  final private val themeUrlRuleList = new ListBuffer[T_theme_url_rule]
  final private val extractRuleList = new ListBuffer[T_extract_rule]

  def clear(): Unit = {
    this.urlRuleList.clear()
    this.themeUrlRuleList.clear()
    // this.extractRuleList.clear();
    this.basicTypeRelMap.clear()
    this.themeBasicTypeRelMap.clear()
  }

  def getDomainEqualsMap: Map[String, ListBuffer[Any]] = domainEqualsMap

  def getAfDomain: AnalysisFactroy = afDomain

  def getAfRule: AnalysisFactroy = afRule

  def getExtractPatternRuleMap: Map[T_extract_rule, Pattern] = extractPatternRuleMap

  def getExtractDoMainPatternRuleMap: Map[T_extract_rule, Pattern] = extractDoMainPatternRuleMap

  def getiPMap: Map[String, T_ip_rule] = iPMap

  def getBasicTypeRelMap: Map[Long, ListBuffer[T_basic_type_rel]] = basicTypeRelMap

  def getThemeBasicTypeRelMap: Map[Long, ListBuffer[T_theme_basic_type_rel]] = themeBasicTypeRelMap

  def getUrlRuleList: ListBuffer[T_url_rule] = urlRuleList

  def getThemeUrlRuleList: ListBuffer[T_theme_url_rule] = themeUrlRuleList

  def getExtractRuleList: ListBuffer[T_extract_rule] = extractRuleList
}
