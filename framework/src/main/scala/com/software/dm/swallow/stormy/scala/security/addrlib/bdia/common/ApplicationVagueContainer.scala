package com.software.dm.swallow.stormy.scala
package security.addrlib
package bdia
package common

//import java.util
//import java.{ListBuffer, HashMap, List, Map}
//import java.regex.Pattern

import java.util.regex.Pattern

import com.esotericsoftware.kryo.Kryo
import algoac.VagueAnalysisFactroy
import algoac.impl.VagueStateCharacter
import algoac.pojo.VagueResultEntity
import bean.{T_url_rule, _}
import algoac.inter.{AbstractVagueState, VagueState}

import scala.collection.mutable._

object ApplicationVagueContainer {

  def getKryo: Kryo = {
    val kryo = new Kryo
    kryo.setRegistrationRequired(true)
    kryo.register(classOf[ApplicationVagueContainer])
    kryo.register(classOf[VagueAnalysisFactroy])
    kryo.register(classOf[VagueStateCharacter])
    kryo.register(classOf[HashMap[_, _]])
    kryo.register(classOf[ListBuffer[_]])
    kryo.register(classOf[HashSet[_]])
    kryo.register(classOf[VagueResultEntity])
    kryo.register(classOf[T_extract_rule])
    kryo.register(classOf[T_basic_type_rel])
    kryo.register(classOf[T_theme_url_rule])
    kryo.register(classOf[T_theme_basic_type_rel])
    kryo.register(classOf[T_basic_type_rel])
    kryo.register(classOf[T_url_rule])
    kryo.register(classOf[T_ip_rule])
    kryo.register(classOf[AbstractVagueState[_]])
    kryo.register(classOf[VagueState[_]])
    kryo.register(classOf[Array[String]])
    kryo.register(classOf[Array[Integer]])
    kryo.register(classOf[Pattern])
    kryo.register(classOf[Array[Pattern]])


    kryo.register(classOf[scala.collection.immutable.$colon$colon[_]])
    kryo.register(scala.collection.immutable.Nil.getClass)
    kryo
  }
}

class ApplicationVagueContainer {

  private val domainEqualsMap: Map[String, ListBuffer[Any]] = new HashMap[String, ListBuffer[Any]]
  private val afDomain: VagueAnalysisFactroy = new VagueAnalysisFactroy
  private val afRule: VagueAnalysisFactroy = new VagueAnalysisFactroy
  private val extractPatternRuleMap: HashMap[T_extract_rule, Pattern] = new HashMap[T_extract_rule, Pattern]
  private val extractDoMainPatternRuleMap: HashMap[T_extract_rule, Pattern] = new HashMap[T_extract_rule, Pattern]
  private val iPMap: HashMap[String, T_ip_rule] = new HashMap[String, T_ip_rule]

  private val basicTypeRelMap: HashMap[Long, ListBuffer[T_basic_type_rel]] = new HashMap[Long, ListBuffer[T_basic_type_rel]]
  private val themeBasicTypeRelMap = new HashMap[Long, ListBuffer[T_theme_basic_type_rel]]

  private val urlRuleList: ListBuffer[T_url_rule] = new ListBuffer[T_url_rule]
  private val themeUrlRuleList: ListBuffer[T_theme_url_rule] = new ListBuffer[T_theme_url_rule]
  private val extractRuleList: ListBuffer[T_extract_rule] = new ListBuffer[T_extract_rule]


  def clear(): Unit = { // this.urlRuleList.clear();
    // this.themeUrlRuleList.clear();
    // this.extractRuleList.clear();
    this.basicTypeRelMap.clear()
    this.themeBasicTypeRelMap.clear()
  }


  def getDomainEqualsMap: Map[String, ListBuffer[Any]] = domainEqualsMap

  def getAfDomain: VagueAnalysisFactroy = afDomain

  def getAfRule: VagueAnalysisFactroy = afRule

  // public Map<String, Set<T_extract_rule>> getExtractStringRuleMap() {
  // return extractStringRuleMap;
  // }

  def getExtractPatternRuleMap: Map[T_extract_rule, Pattern] = extractPatternRuleMap

  def getExtractDoMainPatternRuleMap: Map[T_extract_rule, Pattern] = extractDoMainPatternRuleMap

  def getiPMap: Map[String, T_ip_rule] = iPMap

  def getBasicTypeRelMap: Map[Long, ListBuffer[T_basic_type_rel]] = basicTypeRelMap

  def getThemeBasicTypeRelMap: Map[Long, ListBuffer[T_theme_basic_type_rel]] = themeBasicTypeRelMap

  def getUrlRuleList: ListBuffer[T_url_rule] = urlRuleList

  def getThemeUrlRuleList: ListBuffer[T_theme_url_rule] = themeUrlRuleList

  def getExtractRuleList: ListBuffer[T_extract_rule] = extractRuleList

}
