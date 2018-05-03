package com.software.dm.swallow.stormy.scala
package security.addrlib.bdia
package obtain

import java.io.BufferedInputStream
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStream
import java.util.regex.Pattern

import com.esotericsoftware.kryo.Kryo
import com.esotericsoftware.kryo.io.Input
import bean.{T_extract_rule, T_ip_rule, T_theme_url_rule, T_url_rule}
import common.ApplicationVagueContainer

import scala.collection.mutable._
import scala.io.{BufferedSource, Source}


/**
  * @author DM
  * @version v1.0.0.1
  *          Description
  *          date 2017
  */
final class SerialVagueFactroy {
  private var basicAppResult: T_url_rule = null
  final private val themeResultList: ListBuffer[T_theme_url_rule] = new ListBuffer[T_theme_url_rule]
  final private val extractResultList: ListBuffer[T_extract_rule] = new ListBuffer[T_extract_rule]
  private var ipResult: T_ip_rule = null
  private var avc: ApplicationVagueContainer = null

  def this(serialPath: String) {
    this()
    this.deSerial(serialPath)
  }

  def this(is: InputStream) {
    this()
    this.deSerial(is)
  }

  /**
    * @param serialPath
    * @return
    * @throws IOException
    */
  @throws[IOException]
  private def deSerial(serialPath: String): ApplicationVagueContainer = {
println("===============")
    val kryo: Kryo = ApplicationVagueContainer.getKryo
    val is: InputStream = new BufferedInputStream(new FileInputStream(serialPath))
    // InputStream is = new BufferedInputStream(new
    // GZIPInputStream(ClassLoader.getSystemResourceAsStream(Constant.REPO_SERIAL)));
    val applicationVagueContainer: ApplicationVagueContainer = kryo.readObject(new Input(is, 10240), classOf[ApplicationVagueContainer])
    is.close()
//    val extractPatternRuleMap: Map[T_extract_rule, Pattern] = applicationVagueContainer.getExtractPatternRuleMap
//
//    for ((k, v) <- extractPatternRuleMap) {
//      println(k + "==" + v)
//    }
    this.avc = applicationVagueContainer

    println("===" + applicationVagueContainer.getiPMap.size)

    applicationVagueContainer
  }

  /**
    * @param is
    * @return
    * @throws IOException
    */
  @throws[IOException]
  private def deSerial(is: InputStream): ApplicationVagueContainer = {
    val kryo: Kryo = ApplicationVagueContainer.getKryo
    val applicationVagueContainer: ApplicationVagueContainer = kryo.readObject(new Input(is, 10240), classOf[ApplicationVagueContainer])
    is.close()
    val extractPatternRuleMap: Map[T_extract_rule, Pattern] = applicationVagueContainer.getExtractPatternRuleMap
    import scala.collection.JavaConversions._
    for (elem <- extractPatternRuleMap.entrySet) {
      val ter: T_extract_rule = elem.getKey
      val p: Pattern = elem.getValue
      extractPatternRuleMap.put(ter, Pattern.compile(p.pattern, Pattern.CASE_INSENSITIVE))
    }
    val extractDoMainPatternRuleMap: Map[T_extract_rule, Pattern] = applicationVagueContainer.getExtractDoMainPatternRuleMap
    import scala.collection.JavaConversions._
    for (elem <- extractDoMainPatternRuleMap.entrySet) {
      val ter: T_extract_rule = elem.getKey
      val p: Pattern = elem.getValue
      extractDoMainPatternRuleMap.put(ter, Pattern.compile(p.pattern, Pattern.CASE_INSENSITIVE))
    }
    val extractRuleList: ListBuffer[T_extract_rule] = applicationVagueContainer.getExtractRuleList

    for (t_extract_rule <- extractRuleList) {
      val ps: Array[Pattern] = t_extract_rule.getParamRegexPatternArray
      if (null != ps) {
        var i: Int = 0
        while (i < ps.length) {
          if (null != ps(i)) {
            ps(i) = Pattern.compile(ps(i).pattern, Pattern.CASE_INSENSITIVE)
          }
          i += 1
        }
      }
    }
    this.avc = applicationVagueContainer
    return applicationVagueContainer
  }

  /**
    * @param tur
    */
  private def checkBasic(tur: T_url_rule): Unit = {
    if (null != this.basicAppResult) {
      if (this.basicAppResult.getRule_type_level < tur.getRule_type_level) {
        this.basicAppResult = tur
      }
    } else {
      this.basicAppResult = tur
    }
  }

  /**
    *
    */
  final private val tempResultSet: Set[Any] = new HashSet[Any]

  private def clear(): Unit = {
    this.tempResultSet.clear()
    this.basicAppResult = null
    this.ipResult = null
    this.themeResultList.clear()
    this.extractResultList.clear()
  }

  /**
    * @param ip
    * @param port
    */
  def executeIP(ip: String, port: String): T_ip_rule = {
    this.ipResult = null
    if (!(ip.isEmpty) && !(port.isEmpty)) {
      this.ipResult = this.avc.getiPMap.get(ip + port).getOrElse(null)
    }
    return this.ipResult
  }

  /**
    *
    *
    * @param fullDomain
    */
  def execute(fullDomain: Array[String]): Boolean = {
    var outputBool: Boolean = false
    this.clear()
    // String[] fullDomain =
    // AbstractCommonUtils.getFullDomainWithBareUrl(sourceurl.getBytes());
    // domain match
    val domain: String = fullDomain(0)
    val url: String = fullDomain(1)
    val domainEqualsMap: Map[String, ListBuffer[Any]] = this.avc.getDomainEqualsMap
    System.out.println("domainEqualsMap===" + domainEqualsMap)

    System.out.println("themeBasicTypeRelMap===" + this.avc.getDomainEqualsMap.size)


    val domainEqualsSet: ListBuffer[Any] = domainEqualsMap.get(domain).getOrElse(null)
    if (null != domainEqualsSet) {
      for (obj <- domainEqualsSet) {
        if (obj.isInstanceOf[T_url_rule]) {
          val tur: T_url_rule = obj.asInstanceOf[T_url_rule]
          if (tur.getRule_type == 1) {
            outputBool = true
            this.checkBasic(tur)
          }
          else {
            tempResultSet.add(tur)
          }
        }
        else {
          if (obj.isInstanceOf[T_theme_url_rule]) {
            val ttur: T_theme_url_rule = obj.asInstanceOf[T_theme_url_rule]
            if (ttur.getRule_type == 1) {
              outputBool = true
              themeResultList.append(ttur)
            }
            else {
              tempResultSet.add(ttur)
            }
          }
          else {
            if (obj.isInstanceOf[T_extract_rule]) {
              tempResultSet.add(obj)
            }
          }
        }
      }
    }
    // domain
    var paramSet: Set[Any] = avc.getAfDomain.serachResult(domain)
    if (!(paramSet.isEmpty)) {
      for (obj <- paramSet) {
        if (obj.isInstanceOf[T_url_rule]) {
          val tur: T_url_rule = obj.asInstanceOf[T_url_rule]
          if (tur.getRule_type == 2) {
            outputBool = true
            this.checkBasic(tur)
          }
          else {
            tempResultSet.add(tur)
          }
        }
        else {
          if (obj.isInstanceOf[T_theme_url_rule]) {
            val ttur: T_theme_url_rule = obj.asInstanceOf[T_theme_url_rule]
            if (ttur.getRule_type == 2) {
              outputBool = true
              themeResultList.append(ttur)
            }
            else {
              tempResultSet.add(ttur)
            }
          }
          else {
            if (obj.isInstanceOf[T_extract_rule]) {
              tempResultSet.add(obj)
            }
          }
        }
      }
    }
    //
    paramSet = avc.getAfRule.serachResult(url)
    if (!(paramSet.isEmpty)) {
      for (obj <- paramSet) {
        if (obj.isInstanceOf[T_url_rule]) {
          val tur: T_url_rule = obj.asInstanceOf[T_url_rule]
          if (tur.getRule_type == 3) {
            outputBool = true
            this.checkBasic(tur)
          }
          else {
            if (this.tempResultSet.contains(tur)) {
              outputBool = true
              this.checkBasic(tur)
              this.tempResultSet.remove(obj)
            }
          }
        }
        else {
          if (obj.isInstanceOf[T_theme_url_rule]) {
            val ttur: T_theme_url_rule = obj.asInstanceOf[T_theme_url_rule]
            if (ttur.getRule_type == 3) {
              outputBool = true
              themeResultList.append(ttur)
            }
            else {
              if (this.tempResultSet.contains(ttur)) {
                outputBool = true
                themeResultList.append(ttur)
                this.tempResultSet.remove(obj)
              }
            }
          }
          else {
            if (obj.isInstanceOf[T_extract_rule]) {
              val ter: T_extract_rule = obj.asInstanceOf[T_extract_rule]
              if (ter.getRule_type == 2) {
                outputBool = true
                this.extractResultList.append(ter)
              }
              else {
                if (this.tempResultSet.contains(ter)) {
                  outputBool = true
                  this.extractResultList.append(ter)
                  this.tempResultSet.remove(obj)
                }
              }
            }
          }
        }
      }
    }
    // extract pattern
    val extractPattern: Map[T_extract_rule, Pattern] = avc.getExtractPatternRuleMap
    for ((t_extract_rule, pattern) <- extractPattern) {
      if (null != pattern && pattern.matcher(url).find) {
        outputBool = true
        this.extractResultList.append(t_extract_rule)
      }
    }
    val extractDoMainPattern: Map[T_extract_rule, Pattern] = avc.getExtractDoMainPatternRuleMap
    if (!(this.tempResultSet.isEmpty) && !(extractDoMainPattern.isEmpty)) {
      val patternInt: Int = extractDoMainPattern.size
      val tempInt: Int = this.tempResultSet.size
      if (patternInt > tempInt) {
        for (setobj <- this.tempResultSet) {
          if (setobj.isInstanceOf[T_extract_rule]) {
            val domainTer: T_extract_rule = setobj.asInstanceOf[T_extract_rule]
            val p: Pattern = extractDoMainPattern.get(domainTer).getOrElse(null)
            if (null != p && p.matcher(url).find) {
              outputBool = true
              this.extractResultList.append(domainTer)
            }
          }
        }
      }
      else {
        for ((t_extract_rule, extract) <- extractPattern) {
          if (this.tempResultSet.contains(t_extract_rule)) {
            if (null != extract && extract.matcher(url).find) {
              outputBool = true
              this.extractResultList.append(t_extract_rule)
              this.tempResultSet.remove(t_extract_rule)
            }
          }
        }
      }
    }
    // for (Map.Entry<T_extract_rule, Pattern> entryMap :
    // extractPattern.entrySet()) {
    // T_extract_rule t_extract_rule = entryMap.getKey();
    // if (t_extract_rule.getRule_type() == 1 &&
    // entryMap.getValue().matcher(url).find()) {
    // outputBool = true;
    // this.extractResultList.add(t_extract_rule);
    // } else {
    // if (this.tempResultSet.contains(t_extract_rule) &&
    // entryMap.getValue().matcher(url).find()) {
    // outputBool = true;
    // this.extractResultList.add(t_extract_rule);
    // this.tempResultSet.remove(t_extract_rule);
    // // System.out.println(t_extract_rule.getRid());
    // }
    // }
    // }
    // end
    return outputBool
  }

  def outputClear(): Unit = {
  }

  def outputIpClear(): Unit = {
  }

  def outputBasic(): Unit = {
  }

  def outputApp(): Unit = {
  }

  def outputTheme(): Unit = {
  }

  /**
    * @param url
    * @param t_extract_rule
    */
  def outputExtract(url: String, t_extract_rule: T_extract_rule): Unit = {
    val result: String = ExtractUtils.extractResult(url, t_extract_rule)
    //    System.out.println("===========" + Arrays.toString(t_extract_rule.getParamRegexPatternArray))
  }

  def writer(): Unit = {
  }

  def getBasicAppResult: T_url_rule = {
    return basicAppResult
  }

  def getThemeResultList: ListBuffer[T_theme_url_rule] = {
    return themeResultList
  }

  def getExtractResultList: ListBuffer[T_extract_rule] = {
    return extractResultList
  }

  def getIpResult: T_ip_rule = {
    return ipResult
  }
}
