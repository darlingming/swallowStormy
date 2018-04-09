package com.software.dm.swallow.stormy.scala

import com.software.dm.swallow.stormy.scala.algoac.inter.VagueState
import scala.util.control.Breaks._
object TestMedth {

  def main(args: Array[String]): Unit = {


    for (i <- 1 until 10) {
      breakable {
        if (i == 5) break //ÍË³öbreakable¿é
        println(i)
      }
    }
    println("----------")
    var isStar =true

    val resources = Array("api.iclient.ifeng.com/clientnews?id=cj33,focuscj33,hncj33&*", "client.cmread.com/cmread/portalapi*", "cart.m.yhd.com/cart/addpromotion*", "*", "*ab*cd*", "ab*cd", "*abcd*", "abcd", "abcd*", "*abcd", "*1", "*1*2*3*4*", "*333*", "*****D**M*******", "*.htsec.com", "www.toutiao.com*toutiao*")
    // String[] resources = { "*333*" };
    for (string <- resources) {
      val values = string.toCharArray
      for (i <- 0 until values.length) {
        breakable {
          if (values(i) == VagueState.STAR_CHAR) {
            println("======")
            if (isStar)
              break
            isStar = true
            break
          }

        }
        println(values(i))
      }
    }




  }
}
