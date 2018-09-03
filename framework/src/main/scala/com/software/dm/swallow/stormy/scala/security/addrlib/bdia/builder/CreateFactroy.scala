package com.software.dm.swallow.stormy.scala
package security.addrlib
package bdia
package builder

import java.io._
import java.text.SimpleDateFormat
import java.util.{Date, Properties}
import java.util.regex.Pattern

import bean.{T_basic_type_rel, T_extract_rule, T_ip_rule, T_theme_basic_type_rel, T_theme_url_rule, T_url_rule}

import com.software.dm.swallow.stormy.scala.algoac.pojo.Param
import com.software.dm.swallow.stormy.scala.hadoop.tools.Constant
import common.{ApplicationContainer, RepoConstant}

import scala.collection.mutable._
import scala.io.{BufferedSource, Source}
import scala.util.matching.Regex
import scala.collection.JavaConversions._

class CreateFactroy {


  /**
    * @return
    * @throws IOException
    */
  @throws[IOException]
  private def getProperties(filePath: String): Properties = {
    var inputStream: InputStream = null
    if (null != filePath) {
      val f: File = new File(filePath)
      if (f.exists && f.isFile) {
        inputStream = new FileInputStream(f)
      }
    }
    else {
      inputStream = classOf[CreateFactroy].getResourceAsStream(RepoConstant.REPO_NAME)
    }
    val prop: Properties = new Properties
    prop.load(inputStream)
    inputStream.close()
    println(prop.toString.replaceAll(", ", "\r\n"))
    return prop
  }

  /**
    * BASICAndApp
    *
    * @param urlRulePath
    * @param basicRefPath
    * @param btrMap
    * @param urlRuleList
    * @throws IOException
    */
  @throws[IOException]
  private def initUrlRule(urlRulePath: String, basicRefPath: String, btrMap: Map[Long, ListBuffer[T_basic_type_rel]],
                          urlRuleList: ListBuffer[T_url_rule]): Unit = {
    try {
      var bufferedSource: BufferedSource = Source.fromFile(basicRefPath, "utf8")
      var it = bufferedSource.getLines()
      while (it.hasNext) {
        val line = it.next()
        val values = line.split(RepoConstant.REPO_SPLIT_FLAG_PLAIN, -1)
        this.trim(values)
        val nid: Long = values(0).toLong

        val tbtr: T_basic_type_rel = new T_basic_type_rel(nid, values(1), values(2));
        var btrSet: ListBuffer[T_basic_type_rel] = btrMap.get(nid).getOrElse(null)
        if (null == btrSet) {
          btrSet = new ListBuffer[T_basic_type_rel]()
          btrSet.append(tbtr);
          btrMap += (nid -> btrSet)
        } else {
          btrSet.append(tbtr);
        }
      }
      //--
      bufferedSource.close()


      val bufferedSourceUrl: BufferedSource = Source.fromFile(urlRulePath, "utf8")
      val its = bufferedSourceUrl.getLines()
      while (its.hasNext) {
        val line = its.next()
        if (!line.isEmpty) {
          val values = line.split(RepoConstant.REPO_SPLIT_FLAG_PLAIN, -1);

          this.trim(values)
          val nid: Long = values(0).toLong
          val tbtr: T_url_rule = new T_url_rule(//
            nid,
            values(1),
            values(2), //
            values(3), //
            values(4), //
            values(5), //
            if (values(6).isEmpty()) -1 else values(6).toInt, //
            if (values(7).isEmpty()) -1 else values(7).toInt, //
            if (values(8).isEmpty()) -1 else values(8).toInt, //
            values(9), //
            values(10), //
            btrMap.get(nid).getOrElse(null)
          )
          urlRuleList.append(tbtr)
        }
      }


      bufferedSourceUrl.close()
      //----


    } catch {
      case fex: Exception => {
        println("eee")
        //        this.println(line);
      }
    }

  }

  /**
    * theme
    *
    * @param themeRulePath
    * @param themeRefPath
    * @param tbtrMap
    * @param themeUrlRuleList
    * @throws IOException
    */
  @throws[IOException]
  private def initThemeRule(themeRulePath: String, themeRefPath: String, tbtrMap: Map[Long, ListBuffer[T_theme_basic_type_rel]],
                            themeUrlRuleList: ListBuffer[T_theme_url_rule]): Unit = {
    try {

      var bufferedSource: BufferedSource = Source.fromFile(themeRefPath, "utf8")
      var it = bufferedSource.getLines()
      while (it.hasNext) {
        val line = it.next()
        val values = line.split(RepoConstant.REPO_SPLIT_FLAG_PLAIN, -1)
        this.trim(values)
        val nid: Long = values(0).toLong

        val tbtr: T_theme_basic_type_rel = new T_theme_basic_type_rel(values(0), nid, values(2),
          if (values(3).isEmpty()) -1 else values(3).toInt
          , values(4));
        var tbtrSet: ListBuffer[T_theme_basic_type_rel] = tbtrMap.get(nid).getOrElse(null)
        if (null == tbtrSet) {
          tbtrSet = new ListBuffer[T_theme_basic_type_rel]();
          tbtrSet.append(tbtr)
          tbtrMap += (nid -> tbtrSet)
        } else {
          tbtrSet.append(tbtr)
        }
      }


      bufferedSource.close()
      var themeRule: BufferedSource = Source.fromFile(themeRulePath, "utf8")
      val it1 = themeRule.getLines()
      while (it1.hasNext) {
        val line = it1.next()
        val values = line.split(RepoConstant.REPO_SPLIT_FLAG_PLAIN, -1)

        this.trim(values);
        val nid: Long = values(0).toLong
        var tbtrSet: ListBuffer[T_theme_basic_type_rel] = tbtrMap.get(nid).getOrElse(null)
        if (null != tbtrSet && !tbtrSet.isEmpty) {

          var ttur: T_theme_url_rule = new T_theme_url_rule(//
            nid, //
            values(1), //
            values(2), //
            if (values(3).isEmpty()) -1 else values(3).toInt, //
            tbtrSet
          )
          themeUrlRuleList.append(ttur);
        } else {
          println("T_theme_url_rule is not ref :" + line);

        }
      }
      themeRule.close
    } catch {
      case xd: Exception => {

      }
    }

  }


  /**
    * @param iPRulePath
    * @param iPMap
    * @throws IOException
    */
  @throws[IOException]
  private def initIPRule(iPRulePath: String, iPMap: Map[String, T_ip_rule]): Unit
  = {

    try {

      var iPRuleSource: BufferedSource = Source.fromFile(iPRulePath, "utf8")
      var it = iPRuleSource.getLines()
      while (it.hasNext) {
        val line = it.next()
        val values = line.split(RepoConstant.REPO_SPLIT_FLAG_PLAIN, -1)
        this.trim(values)
        val nid: Long = values(0).toLong
        if (!values(1).isEmpty && !values(2).isEmpty) {
          val nid: Long = values(0).toLong
          val tipr: T_ip_rule = new T_ip_rule(nid, values(1), values(2), if (values(3).isEmpty()) -1 else values(3).toInt, values(4), values(5))
          iPMap += ((values(1) + values(2)) -> tipr)
        } else {
          println("T_IP_RULE is error:" + line);
        }
        iPRuleSource.close();

      }
    } catch {
      case e: Exception => {
        //          println(line);
      }
    }

  }

  /**
    * @param msg
    */
  private def printlns(msg: String): Unit

  = {
    System.out.println(msg)
  }

  /**
    * @param extractPath
    * @param extractRuleList
    * @throws IOException
    */
  @throws[IOException]
  private def initExtract(extractPath: String, extractRuleList: ListBuffer[T_extract_rule]): Unit = {

    try {

      var extractSource: BufferedSource = Source.fromFile(extractPath, "utf8")
      var it = extractSource.getLines()
      var values: Array[String] = null

      while (it.hasNext) {
        val line = it.next()
        val values = line.split(RepoConstant.REPO_SPLIT_FLAG_PLAIN, -1)
        this.trim(values)
        val nid: Long = values(0).toLong

        var extract: T_extract_rule = new T_extract_rule(//
          if (values(0).isEmpty()) -1 else values(0).toInt, //
          if (values(1).isEmpty()) -1 else values(1).toInt, //
          if (values(2).isEmpty()) -1 else values(2).toInt, //
          values(3), //
          if (values(4).isEmpty()) -1 else values(4).toInt, //
          values(5), //
          values(6), //
          values(7), //
          values(8), //
          values(9), //
          values(10), //
          if (values(11).isEmpty()) -1 else values(1).toInt

        )

        if (!extract.getContent_rule.isEmpty && !extract.getUncode.isEmpty) {
          val rules: Array[String] = extract.getContent_rule.split(Constant.PUB_FIELD_STR, -1)

          val uncodes: Array[String] = extract.getUncode.split(Constant.PUB_COMMA, -1)
          if (uncodes.length == rules.length) {
            val ps: Array[Pattern] = new Array[Pattern](rules.length)
            var uncodesMap: Map[Int, Array[Int]] = HashMap[Int, Array[Int]]()
            for (j <- 0 until rules.length) {
              if (!rules(j).isEmpty)
                ps(j) = Pattern.compile(rules(j).toString(), Pattern.CASE_INSENSITIVE);

              val codes: Array[String] = uncodes(j).split(RepoConstant.DECODING_REPEAT_SEPARATOR)

              val ints: Array[Int] = new Array[Int](codes.length)
              for (i <- 0 until codes.length) {
                ints(i) = codes(i).toInt
              }
              uncodesMap += (j -> ints)
            }


            extract.setParamRegexPatternArray(ps);
            extract.setUncodes(uncodesMap);
          }

          extractRuleList.add(extract);

        } else {
          //          println("rules:" + rules.length + "uncodes:" + uncodes.length + " not equals " + line);
        }
      }
      extractSource.close();
    } catch {
      case xe: Exception => {

      }
      //       println(line);
    }

  }

  /**
    * ac
    * domain
    * obj
    */
  private def loadDomainEquals(ac: ApplicationContainer, domain: String, obj: Any): Unit

  = {
    var setObj: ListBuffer[Any] = ac.getDomainEqualsMap.get(domain).getOrElse(null)
    if (null == setObj) {
      setObj = new ListBuffer[Any]
      setObj.append(obj)
      ac.getDomainEqualsMap += (domain -> setObj)
    }
    else {
      setObj.append(obj)
    }
  }

  /**
    * @param ac
    * @param typeid
    * @param domain
    * @param obj
    */
  private def loadAfDomain(ac: ApplicationContainer, typeid: Int, domain: String, obj: Any): Unit = {
    val pDomain: Param = new Param(typeid, domain, obj)
    ac.getAfDomain.addParam(pDomain)
  }

  /**
    * @param ac
    * @param typeid
    * @param rule
    * @param obj
    */
  private def loadAfRule(ac: ApplicationContainer, typeid: Int, rule: String, obj: Any): Unit = {
    val pRule: Param = new Param(typeid, rule, obj)
    ac.getAfRule.addParam(pRule)
  }

  /**
    * @param ac
    */
  private def builderBasicAndApp(ac: ApplicationContainer): Unit = {
    // basicAndApp
    val urlRuleList: ListBuffer[T_url_rule] = ac.getUrlRuleList

    for (t_url_rule <- urlRuleList) {
      val ruleType: Int = t_url_rule.getRule_type

      ruleType match {
        case 1 =>
          this.loadDomainEquals(ac, t_url_rule.getDomain_comp, t_url_rule)
        // break //todo: break is not supported
        case 2 =>
          this.loadAfDomain(ac, t_url_rule.ACTION_BASIC_APP, t_url_rule.getDomain_comp, t_url_rule)
        // break //todo: break is not supported
        case 3 =>
          this.loadAfRule(ac, t_url_rule.ACTION_BASIC_APP, t_url_rule.getRule, t_url_rule)
        // break //todo: break is not supported
        case 4 =>
          this.loadDomainEquals(ac, t_url_rule.getDomain_comp, t_url_rule)
          this.loadAfRule(ac, t_url_rule.ACTION_BASIC_APP, t_url_rule.getRule, t_url_rule)
        // break //todo: break is not supported
        case 5 =>
          this.loadAfDomain(ac, t_url_rule.ACTION_BASIC_APP, t_url_rule.getDomain_comp, t_url_rule)
          this.loadAfRule(ac, t_url_rule.ACTION_BASIC_APP, t_url_rule.getRule, t_url_rule)
        //break //todo: break is not supported
      }
    }
  }

  private def builderTheme(ac: ApplicationContainer): Unit = {
    val themeUrlRuleList: ListBuffer[T_theme_url_rule] = ac.getThemeUrlRuleList

    for (t_theme_url_rule <- themeUrlRuleList) {
      val ruleType: Int = t_theme_url_rule.getRule_type
      ruleType match {
        case 1 =>
          this.loadDomainEquals(ac, t_theme_url_rule.getDomain_comp, t_theme_url_rule)
        //          break //todo: break is not supported
        case 2 =>
          this.loadAfDomain(ac, t_theme_url_rule.ACTION_THEME, t_theme_url_rule.getDomain_comp, t_theme_url_rule)
        //          break //todo: break is not supported
        case 3 =>
          this.loadAfRule(ac, t_theme_url_rule.ACTION_THEME, t_theme_url_rule.getRule, t_theme_url_rule)
        //          break //todo: break is not supported
        case 4 =>
          this.loadDomainEquals(ac, t_theme_url_rule.getDomain_comp, t_theme_url_rule)
          this.loadAfRule(ac, t_theme_url_rule.ACTION_THEME, t_theme_url_rule.getRule, t_theme_url_rule)
        //          break //todo: break is not supported
        case 5 =>
          this.loadAfDomain(ac, t_theme_url_rule.ACTION_THEME, t_theme_url_rule.getDomain_comp, t_theme_url_rule)
          this.loadAfRule(ac, t_theme_url_rule.ACTION_THEME, t_theme_url_rule.getRule, t_theme_url_rule)
        //          break //todo: break is not supported
      }
    }
  }

  /**
    * 1������ƥ�䣬��������ƥ�䣩 2��ģ��ƥ�䣨������ƥ�䣩 3������ģ��������ƥ�� 4������ģ����ģ��ƥ�� 5������ֵ������ƥ��
    * 6������ֵ��ģ��ƥ��
    *
    * @param ac
    */
  private def builderExtract(ac: ApplicationContainer): Unit = {
    val extractRuleList: ListBuffer[T_extract_rule] = ac.getExtractRuleList

    for (t_extract_rule <- extractRuleList) {
      val ruleType: Int = t_extract_rule.getRule_type

      val rule: String = t_extract_rule.getRule
      // String context_rule = t_extract_rule.getContent_rule();
      // if (!context_rule.isEmpty()) {
      // try {
      // String[] rules = context_rule.split("\u0001");
      // for (String string : rules) {
      // Pattern.compile(string);
      // }
      // t_extract_rule.setParamRegexs(rules);
      // } catch (Exception e) {
      // e.printStackTrace();
      // System.out.println(t_extract_rule);
      // continue;
      // }
      // }

      var ruleP: Pattern = null
      ruleType match {
        case 1 =>

          try {
            ruleP = Pattern.compile(rule)
          } catch {
            case e: Exception =>
              e.printStackTrace()
            //              this.println(t_extract_rule.toString)
            //              continue //todo: continue is not supported
          }
          ac.getExtractPatternRuleMap.put(t_extract_rule, ruleP)
        //          break //todo: break is not supported
        case 2 =>
          this.loadAfRule(ac, t_extract_rule.ACTION_EXTRACT, rule, t_extract_rule)
        //          break //todo: break is not supported
        case 3 =>
          try {
            ruleP = Pattern.compile(rule)
          } catch {
            case e: Exception =>
              e.printStackTrace()
            //              this.println(t_extract_rule.toString)
            //              continue //todo: continue is not supported
          }
          this.loadAfDomain(ac, t_extract_rule.ACTION_EXTRACT, t_extract_rule.getFullDomain, t_extract_rule)
          ac.getExtractDoMainPatternRuleMap.put(t_extract_rule, ruleP)
        //          break //todo: break is not supported
        case 4 =>
          this.loadAfDomain(ac, t_extract_rule.ACTION_EXTRACT, t_extract_rule.getFullDomain, t_extract_rule)
          this.loadAfRule(ac, t_extract_rule.ACTION_EXTRACT, t_extract_rule.getRule, t_extract_rule)
        //          break //todo: break is not supported
        case 5 =>
          try {
            ruleP = Pattern.compile(rule)
          } catch {
            case e: Exception =>
              e.printStackTrace()
            //              this.println(t_extract_rule.toString)
            //              continue //todo: continue is not supported
          }
          this.loadDomainEquals(ac, t_extract_rule.getFullDomain, t_extract_rule)
          ac.getExtractDoMainPatternRuleMap.put(t_extract_rule, ruleP)
        //          break //todo: break is not supported
        case 6 =>
          this.loadDomainEquals(ac, t_extract_rule.getFullDomain, t_extract_rule)
          this.loadAfRule(ac, t_extract_rule.ACTION_EXTRACT, rule, t_extract_rule)
        //          break //todo: break is not supported
      }
    }
  }

  private def builder(ac: ApplicationContainer): Unit

  = {
    this.builderBasicAndApp(ac)
    // theme
    this.builderTheme(ac)
    // extract
    this.builderExtract(ac)
  }

  /**
    *
    */
  def exec(configFilePath: String, serialName: String): Unit = {
    val ac: ApplicationContainer = new ApplicationContainer
    try {
      val prop: Properties = getProperties(configFilePath)
      val urlRulePath: String = prop.getProperty(RepoConstant.REPO_ROOT_PATCH) + File.separatorChar + prop.getProperty(RepoConstant.REPO_URL_RULE_PATCH)
      val basicRefPath: String = prop.getProperty(RepoConstant.REPO_ROOT_PATCH) + File.separatorChar + prop.getProperty(RepoConstant.REPO_BASIC_TYPE_REL_PATCH)
      this.initUrlRule(urlRulePath, basicRefPath, ac.getBasicTypeRelMap, ac.getUrlRuleList)
      // IP
      val iPRulePath: String = prop.getProperty(RepoConstant.REPO_ROOT_PATCH) + File.separatorChar + prop.getProperty(RepoConstant.REPO_IP_RULE_PATCH)
      this.initIPRule(iPRulePath, ac.getiPMap)
      val themeRulePath: String = prop.getProperty(RepoConstant.REPO_ROOT_PATCH) + File.separatorChar + prop.getProperty(RepoConstant.REPO_THEME_URL_RULE_PATCH)
      val themeRefPath: String = prop.getProperty(RepoConstant.REPO_ROOT_PATCH) + File.separatorChar + prop.getProperty(RepoConstant.REPO_THEME_BASIC_TYPE_REL_PATCH)
      this.initThemeRule(themeRulePath, themeRefPath, ac.getThemeBasicTypeRelMap, ac.getThemeUrlRuleList)
      val extractPath: String = prop.getProperty(RepoConstant.REPO_ROOT_PATCH) + File.separatorChar + prop.getProperty(RepoConstant.REPO_EXTRACT_RULE_PATCH)
      this.initExtract(extractPath, ac.getExtractRuleList)
      this.builder(ac)
      ac.getAfDomain.init()
      ac.getAfRule.init()
      println("BasicUrlRule size is " + ac.getUrlRuleList.size)
      println("AppIPRule size is " + ac.getiPMap.size)
      println("ThemeUrlRule size is " + ac.getThemeUrlRuleList.size)
      println("ExtractUrlRule size is " + ac.getExtractRuleList.size)
      println("ExtractPatternRule size is " + ac.getExtractPatternRuleMap.size)
      println("ExtractDoMainPatternRule size is " + ac.getExtractDoMainPatternRuleMap.size)
      ac.clear()
      this.serial(prop, ac, serialName)
    } catch {
      case e: Exception =>
        e.printStackTrace()
        return
    }
  }

  /**
    * @param prop
    * @param ac
    * @throws Exception
    */
  @throws[Exception]
  private def serial(prop: Properties, ac: ApplicationContainer, serialName: String): Unit

  = {
    val df: SimpleDateFormat = new SimpleDateFormat("yyyyMMdd")
//    val kryo: Kryo = ApplicationContainer.getKryo
    val serialName1 = if (serialName == null) {
      df.format(new Date) + ".serial"
    } else {
      serialName
    }
    val file: File = new File(prop.getProperty(RepoConstant.REPO_SERIAL_PATCH), serialName1)
    if (!(file.exists)) {
      file.getParentFile.mkdirs
      file.createNewFile
    }
    val os: OutputStream = new BufferedOutputStream(new FileOutputStream(file))
//    val out: Output = new Output(os, 10240)
//    kryo.writeObject(out, ac)
//    out.flush()
    os.close()
  }

  /**
    * @param values
    */
  private def trim(values: Array[String]): Unit = {

    if (null != values)
      for (i <- 0 until values.length) {
        var len = values(i).length
        var st = 0
        val vals = values(i).toCharArray
        while ((st < len) && (vals(st) <= ' ') && (vals(st) != '\001')) {
          st += 1
        }
        while ((st < len) && (vals(len - 1) <= ' ') && (vals(len - 1) != '\001')) {
          len -= 1
        }
        values(i) = if ((st > 0) || (len < vals.length)) values(i).substring(st, len) else values(i)
      }

  }

}
