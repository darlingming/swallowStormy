package com.software.dm.swallow.stormy.scala
package security.addrlib.bdia
package obtain


import java.util.regex.Matcher
import java.util.regex.Pattern

import bean.T_extract_rule
import common.RepoConstant
import sun.misc.BASE64Decoder

import scala.collection.mutable._


/**
  * @author DM
  * @version v1.0.0.1
  * Description
  * date 2017
  */
object ExtractUtils {
  private val base64Decoder: BASE64Decoder = new BASE64Decoder
  var p_email: Pattern = Pattern.compile("^[0-9a-z\\._-]+@[0-9a-z\\._-]+\\.(com|net|cn|cc|hk|org|tw|edu)$", Pattern.CASE_INSENSITIVE)

  def changeUrlcode(result: String, urlcodeType: String): String = {
    var res =result
    try {
      res = java.net.URLDecoder.decode(result.replaceAll("%(?![0-9a-fA-F]{2})", "%25"), urlcodeType)
    } catch {
      case e: Exception =>
        res
    }
      res
  }

  def changeMac(result: String): String = {
    val str: StringBuffer = new StringBuffer(result)
    str.insert(2, ":")
    str.insert(5, ":")
    str.insert(8, ":")
    str.insert(11, ":")
    str.insert(14, ":")
    return str.toString
  }

  def chageIdfa(result: String): String = {
    val str: StringBuffer = new StringBuffer(result)
    str.insert(8, "-")
    str.insert(13, "-")
    str.insert(18, "-")
    str.insert(23, "-")
    return str.toString
  }

  def chageMacTypeTwo(result: String): String = {
    return result.replaceAll("-", ":")
  }

  def unescape(src: String): String = {
    val tmp: StringBuffer = new StringBuffer
    tmp.ensureCapacity(src.length)
    var lastPos: Int = 0
    var pos: Int = 0
    var ch: Char = 0
    while ( {
      lastPos < src.length
    }) {
      pos = src.indexOf("%", lastPos)
      if (pos == lastPos) {
        if (src.charAt(pos + 1) == 'u') {
          ch = Integer.parseInt(src.substring(pos + 2, pos + 6), 16).toChar
          tmp.append(ch)
          lastPos = pos + 6
        }
        else {
          ch = Integer.parseInt(src.substring(pos + 1, pos + 3), 16).toChar
          tmp.append(ch)
          lastPos = pos + 3
        }
      }
      else {
        if (pos == -(1)) {
          tmp.append(src.substring(lastPos))
          lastPos = src.length
        }
        else {
          tmp.append(src.substring(lastPos, pos))
          lastPos = pos
        }
      }
    }
    return tmp.toString
  }

  def getRexStr(rex: String, str: String): String = {
    var result: String = ""
    val p: Pattern = Pattern.compile(rex.toLowerCase)
    val m: Matcher = p.matcher(str.toLowerCase)
    if (m.find) {
      result = m.group(1)
    }
    return result
  }

  def changeUnicode(result: String): String = {
    var res = result
    try {
      val sb: StringBuffer = new StringBuffer
      while ( {
        res.length > 0
      }) {
        if (res.startsWith("\\u")) {
          val x: Int = Integer.parseInt(result.substring(2, 6), 16)
          sb.append(x.toChar)
          res = res.substring(6)
        }
        else {
          sb.append(res.charAt(0))
          res = res.substring(1)
        }
      }
      res = sb.toString
    } catch {
      case e: Exception =>
        res = ""
    }
      res
  }

  /**
    * @param result
    * @param action_id
    * @return
    * @author DM
    */
  def removeDirtyData(result: String, action_id: Int): String = {
    var res = result
    if (res == null || res.isEmpty) {
      return ""
    }
    val temp: String = result.toLowerCase
    //		if ("null".equals(temp) || CommonUtils.checkExistsChar(temp) || "undefined".equals(temp)) {
    //			return "";
    //		}
    if (9002 == action_id) { // mac
      if ("|02:00:00:00:00:00|00:00:00:00:00:00|ff:ff:ff:ff:ff:ff|".indexOf("|" + temp + "|") != -(1)) {
        return ""
      }
    }
    else {
      if (9001 == action_id) { // idfa
        if ("|00000000-0000-0000-0000-000000000000|ffffffff-ffff-ffff-ffff-ffffffffffff|".indexOf("|" + temp + "|") != -(1)) {
          return ""
        }
      }
      else {
        if (9029 == action_id) { // carCode
          if (res.length > 2) {
            val flag: String = res.substring(0, 2)
            val car_val: String = carMap.get(flag).getOrElse(null)
            if (null != car_val) {
              res = car_val + res.substring(2)
            }
          }
        }
        else {
          if (9014 == action_id) { // mail
            val m: Matcher = p_email.matcher(res)
            if (!(m.matches)) {
              return ""
            }
          }
        }
      }
    }
    res
  }

  var carMap: Map[String, String] = new HashMap[String, String]() {}

  def changeBase64(target: String): String = {
    var result: String = null
    try {
      result = new String(base64Decoder.decodeBuffer(target))
    } catch {
      case e: Exception =>
        result = target
    }
    return result
  }

  /**
    * removeDirtyData(decodedStr, t_extract_rule.getAction_id()).replaceAll("["
    * + '\001' + "\\t\\n\\r]", "");
    *
    * @param extractedStr
    *
    * @return
    */
  private def decode(extractedStr: String, uncode: Array[Int]): String = {
    if (null == extractedStr || extractedStr.isEmpty) {
      return ""
    }
    var decodedStr: String = extractedStr
    for (codeInt <- uncode) {
      codeInt match {
        case 0 =>
//          break //todo: break is not supported
        case 1 =>
          decodedStr = changeUnicode(decodedStr) // unicode

//          break //todo: break is not supported
        case 2 =>
          decodedStr = changeUrlcode(decodedStr, "utf-8") // urlcode

//          break //todo: break is not supported
        case 3 =>
          decodedStr = changeMac(decodedStr) // Mac

//          break //todo: break is not supported
        case 4 =>
          decodedStr = chageIdfa(decodedStr)
//          break //todo: break is not supported
        case 5 =>
          decodedStr = chageMacTypeTwo(decodedStr)
//          break //todo: break is not supported
        case 6 =>
          decodedStr = changeUrlcode(decodedStr, "gb2312")
        //        break //todo: break is not supported
        case 7 =>
        //     break //todo: break is not supported
        case 8 =>
          decodedStr = changeBase64(decodedStr.toString) // base64

        // break //todo: break is not supported
        case 9 =>
          decodedStr = decodedStr.toLowerCase
        //  break //todo: break is not supported
        case 10 =>
          decodedStr = decodedStr.toUpperCase
        // break //todo: break is not supported
        case 11 =>
          var p: Pattern = Pattern.compile("\"scenicId\":.*?,", Pattern.CASE_INSENSITIVE)
          var m: Matcher = p.matcher(decodedStr)
          while ( {
            m.find
          }) {
            decodedStr = m.group.substring("\"scenicId\":".length, m.group.length - 1)
          }
        // break //todo: break is not supported
        case 12 =>
          val  p = Pattern.compile("\"productId\":.*?,", Pattern.CASE_INSENSITIVE)
         val  m = p.matcher(decodedStr)
          while ( {
            m.find
          }) {
            decodedStr = m.group.substring("\"productId\":".length, m.group.length - 1)
          }
//          break //todo: break is not supported
      }
    }
    return decodedStr
  }

  /**
    * @param url
    * @param bean
    * @return
    */
  def extractResult(url: String, bean: T_extract_rule): String = {
    val paramPatternArray: Array[Pattern] = bean.getParamRegexPatternArray
    if (null == paramPatternArray) {
      return ""
    }
    var tmpResult: String = ""
    var i: Int = 0
    while ( {
      i < paramPatternArray.length
    }) {
      if (i != 0) {
        if (91 == bean.getTheme_id) {
          tmpResult = tmpResult + RepoConstant.MULTI_RESULT_LONLAT_SEPARATOR
        }
        else {
          tmpResult = tmpResult + RepoConstant.MULTI_RESULT_SEPARATOR
        }
      }
      val paramPattern: Pattern = paramPatternArray(i)
      if (paramPattern != null) {
        val matcher: Matcher = paramPattern.matcher(url)
        if (matcher.find && matcher.groupCount > 0) {
          val resVal = matcher.group(1)
          var str_tmp: String = decode(resVal, bean.getUncodes.get(i).getOrElse(Array[Int]()))
          if (90 == bean.getTheme_id) {
            str_tmp = removeDirtyData(str_tmp, bean.getAction_id)
          }
          tmpResult = tmpResult + str_tmp
        }
      }

      {
        i += 1; i - 1
      }
    }
    //		return tmpResult.isEmpty() ? tmpResult : AbstractCommonUtils.convertChar(tmpResult, ConfigConstant.FILTER_CHARS);
    // tmpResult.replaceAll("[" + '\001' + "\\t\\n\\r]", "");
    return if (tmpResult.isEmpty) {
      tmpResult
    }
    else {
      tmpResult.replaceAll("[" + '\001' + "\\t\\n\\r]", "")
    }
  }
}

class ExtractUtils() // TODO Auto-generated constructor stub
{
}
