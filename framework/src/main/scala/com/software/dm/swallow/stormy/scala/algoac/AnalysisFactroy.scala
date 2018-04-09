package com.software.dm.swallow.stormy.scala.algoac

import com.software.dm.swallow.stormy.scala.algoac.impl.AhoCorasickCharacterTree
import com.software.dm.swallow.stormy.scala.algoac.pojo.Param
import com.software.dm.swallow.stormy.scala.algoac.pojo.ResultSetEntity
import com.software.dm.swallow.stormy.hadoop.tools.AbstractCommonUtils
import java.util._


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
  private val paramList = new ArrayList[Param]

  /**
    *
    * @param param
    */
  def addParam(param: Param): Unit = {

    paramList.add(param)
  }

  /**
    *
    */
  def init(): Unit = {
    import scala.collection.JavaConversions._
    for (param <- paramList) {
      val value = AbstractCommonUtils.trimAsterisk(param.getText)
      val v = value.split("\\*")
      var i = 0
      while ( {
        i < v.length
      }) {
        val reg = new ResultSetEntity(v.length - i, v(i), v, param.getType, param)
        rootAc.addKeyWord(reg)

        {
          i += 1;
          i - 1
        }
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
        var rrde: Int = tempDataMap.get(reg.getSource)
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
