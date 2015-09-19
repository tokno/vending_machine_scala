package jp.tokno

import scala.collection.mutable

// お金を入れる箱
class CashBox {
  val monies = mutable.Buffer[Yen]()

  // 入っているお金の総額
  def amount: Int = {
    monies.foldLeft(0) { (total, money) =>
      total + money.value
    }
  }

  // CashBoxの貨幣を統合
  def merge(that: CashBox): Unit = {
    this.monies ++= that.monies
    that.monies.clear()
  }
}
