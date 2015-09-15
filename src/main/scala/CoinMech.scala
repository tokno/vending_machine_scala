import scala.collection.mutable

/**
 * 循環式硬貨選別機。通称コインメック。
 * http://www.fujielectric.co.jp/about/company/jihou_2010/pdf/83-05/FEJ-83-05-316-2010.pdf
 */
class CoinMech {
  private var payment = 0
  private var sales = 0

  /** 入金総額 */
  def paymentVolume = payment

  /** 売り上げ */
  def salesVolume = sales

  def insert(coin: Yen): Unit = {
    payment += coin.value
  }

  /** 払い戻し */
  def dumpTo(out: CashBox): Unit = {
    val change = new CashBox

    // 釣り銭は1円玉で返す。
    // TODO なるべく大きい硬貨で釣り銭を出す。
    change.monies ++= mutable.Buffer.fill(payment)(Yen._1)

    payment = 0
    out.merge(change)
  }

  /** 購入 */
  def purchase(amount: Int): Unit = {
    sales += amount
    payment -= amount
  }
}
