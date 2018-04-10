package com.software.dm.swallow.stormy.scala
package algoac

import impl.AhoCorasickCharacterTree
import pojo.Param
import pojo.ResultSetEntity

import scala.collection.mutable._

/**
  *
  * escription
  *
  * @author DM
  *         2017
  * @version v1.0.0.1
  *          public
  *
  */
final class AnalysisFactroy() {
  /**
    *
    */
  final private val rootAc = new AhoCorasickCharacterTree
  private val paramList = new ListBuffer[Param]

  /**
    *
    * @param param
    */
  def addParam(param: Param): Unit = {
    paramList.append(param)
  }

  /**
    * @param value
    * @return null?"":val
    */
  def trimAsterisk(value: String): String = {

    if (value == null)
      return "";
    val tca = value.toCharArray();
    var i = 0
    var j = tca.length - 1;
    while (i <= j && tca(i) == '*') {
      i += 1;
    }
    while (j > i && tca(j) == '*') {
      j -= 1;
    }
    if (i == 0 && j == tca.length - 1) value else value.substring(i, {
      j += 1; j
    });
  }

  /**
    *
    */
  def init(): Unit = {

    for (param <- paramList) {
      val value = trimAsterisk(param.getText)
      val v = value.split("\\*")
      for (i <- 0 until v.length) {
        val reg = new ResultSetEntity(v.length - i, v(i), v, param.getType, param);
        rootAc.addKeyWord(reg)
      }

    }
    paramList.clear()
    rootAc.prepare()


  }

  final private val resultDataSet = new HashSet[Param]
  final private val tempDataMap: HashMap[Any, Integer] = new HashMap[Any, Integer]
  final private val coll = new HashSet[Any]

  /**
    *
    * @param value
    * @return
    */
  def serachResult(value: String): Set[Param] = {
    tempDataMap.clear()
    resultDataSet.clear()
    coll.clear()
    rootAc.search(value, coll)
    import scala.collection.JavaConversions._
    for (obj <- coll) {
      val reg = obj.asInstanceOf[ResultSetEntity]
      val regLen: Int = reg.getValues.length
      if (regLen == 1) resultDataSet.add(reg.getSource.asInstanceOf[Param])
      else {
        var rrde: Int = tempDataMap.get(reg.getSource).get
        if (null != rrde) {
          rrde = rrde + reg.getRegnum
          if (rrde == ((regLen * (regLen + 1)) >> 1)) {
            resultDataSet.add(reg.getSource.asInstanceOf[Param])
            tempDataMap.remove(reg.getSource)
          }
          else tempDataMap.put(reg.getSource, rrde)
        }
        else tempDataMap.put(reg.getSource, reg.getRegnum)
      }
    }
    resultDataSet
  }
}
