package com.software.dm.swallow.stormy.scala
package algoac

object VagueAnalysisTest {
  /**
    *
    * @param args
    */
  def main(args: Array[String]): Unit = { // TODO Auto-generated method stub
    val test = new VagueAnalysisTest
    test.vagueAc()
  }
}

final class VagueAnalysisTest() // TODO Auto-generated constructor stub
{
  /**
    *
    */
  final private val rootVAF = new VagueAnalysisFactroy

  def initData(): Unit = {
    val resources = Array("api.iclient.ifeng.com/clientnews?id=cj33,focuscj33,hncj33&*", "client.cmread.com/cmread/portalapi*", "cart.m.yhd.com/cart/addpromotion*", "*", "*ab*cd*", "ab*cd", "*abcd*", "abcd", "abcd*", "*abcd", "*1", "*1*2*3*4*", "*333*", "*****D**M*******", "*.htsec.com", "www.toutiao.com*toutiao*")
    // String[] resources = { "*333*" };
    for (string <- resources) {
      rootVAF.addData(string, string)
    }
  }

  def serachData(value: String, j: Int): Unit = {
    //    println("====="+j+"=value="+value)
    val coll = rootVAF.serachResult(value)
    var i = 0
    for (obj <- coll) {
      System.out.println(j + "==Result ==" + {
        i += 1;
        i
      } + ":==" + obj)
    }
    // System.out.println(object);
  }

  /**
    * ok
    */
  private def vagueAc(): Unit = {
    initData()
    val starttime: Long = System.currentTimeMillis
    //
    val resources = Array("api.iclient.ifeng.com/clientnews?id=cj33,focuscj33,hncj33&page=2&gv=5.5.1&av=5.5.1&uid=869186017090736&deviceid=8", "client.cmread.com/cmread/portalapi?contentId=373616705&chapterId=391145526&pa", "cart.m.yhd.com/cart/addPromotion?opTypes=3&promotionIds=1329175&promotionLevelIds=0&merchantIds=2&pmids=48005178&nums=1&callback=jsonp1456884846512", "*", "abcd", "88abcd", "abcd555", "ab55555cd", "99ab99cd00", "1afdsf1", "332132DM11321312213213314333", "333", "stocknews.htsec.com", "www.toutiao.com?c=bb/toutiao/aa/vser.jsp")
    var j = 0
    for (string <- resources) {
      serachData(string, j)
      j += 1
    }
    // for (int i = 0; i < 1; i++) {
    serachData("123456789abcd", j)
    // }
    // serachData("cart.m.yhd.com/cart/addNormalPmInfo/home/publicfeed.php?/fund/bond/%loc?c=1&mars=0&obs=1".toLowerCase(),100);
    // serachData("abcd",100);
    // System.out.printf("vagueAc building time:%dms%n", System.currentTimeMillis - starttime)
    printf("vagueAc building time:%dms%n", System.currentTimeMillis - starttime)
  }
}
