package jp.tokno

// 貨幣識別ユニット。
// https://www.octy.co.jp/about/cm_bv.html
object CashValidator {
  /** ビルバリ */
  val BillVali = new CashValidator(Yen._1000)

  /** コインバリ */
  val CoinVali = new CashValidator(Yen._10, Yen._50, Yen._100, Yen._500)
}

class CashValidator(val validCash: Yen*) {
  def validate(money: Yen) = validCash.contains(money)
}
