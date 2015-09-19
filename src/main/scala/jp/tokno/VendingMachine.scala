package jp.tokno

object VendingMachine {
  def apply(): VendingMachine = {
    val vendingMachine = new VendingMachine
    vendingMachine.juiceStock.add(Juice("コーラ", 120), 5)

    vendingMachine
  }
}

// 自販機
class VendingMachine {
  /** コインメック */
  private val coinMech = new CoinMech

  /** 売り上げ金額 */
  def salesVolume = coinMech.salesVolume

  /** 釣り銭箱 */
  val changeBox = new CashBox

  private val juiceStock = new JuiceStore

  /** 投入金額の合計 */
  def amount = coinMech.paymentVolume

  /** 硬貨を入れる */
  def insertCoin(money: Yen): Unit = insert(money, CashValidator.CoinVali)

  /** 紙幣を入れる */
  def insertBill(money: Yen): Unit = insert(money, CashValidator.BillVali)

  /** 有効貨幣なら総額に加算。そうでなければ釣り銭箱へ。 */
  private def insert(money: Yen, vali: CashValidator): Unit = {
    if (vali.validate(money))
      coinMech.insert(money) // FIXME 紙幣がコインメックに入ってる
    else
      changeBox.monies += money
  }

  /** 払い戻し */
  def refund(): Unit = coinMech.dumpTo(changeBox)

  /** 購入 */
  def buy(): Unit = {
    if (!purchaseLampLighted)
      return

    coinMech.purchase(juiceStock.juicePrice)

    juiceStock.take() // TODO ジュースはどこにいく？
  }

  def juiceCount = juiceStock.count
  def juiceName = juiceStock.juiceName
  def juicePrice = juiceStock.juicePrice

  // ジュースを購入可能ならランプが点灯
  def purchaseLampLighted = juicePrice <= amount && juiceCount != 0
}
