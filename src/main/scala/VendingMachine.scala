object VendingMachine {
  def apply(): VendingMachine = {
    val vendingMachine = new VendingMachine
    vendingMachine.juiceStock.add(Juice("コーラ", 120), 5)

    vendingMachine
  }
}

// 自販機
class VendingMachine {
  /** 入金箱 */
  private val paymentBox = new CashBox

  /** 売り上げ金額 */
  private var _salesVolume = 0
  def salesVolume = _salesVolume

  /** 釣り銭箱 */
  val changeBox = new CashBox

  private val juiceStock = new JuiceStore

  /** 投入金額の合計 */
  def amount = paymentBox.amount

  /** 硬貨を入れる */
  def insertCoin(money: Yen): Unit = insert(money, CashValidator.CoinVali)

  /** 紙幣を入れる */
  def insertBill(money: Yen): Unit = insert(money, CashValidator.BillVali)

  /** 有効貨幣なら総額に加算。そうでなければ釣り銭箱へ。 */
  private def insert(money: Yen, vali: CashValidator): Unit = {
    val dist = if (vali.validate(money))
      paymentBox.monies
    else
      changeBox.monies

    dist += money
  }

  /** 払い戻し */
  def refund(): Unit = changeBox.merge(paymentBox)

  /** 購入 */
  def buy(): Unit = {
    if (!purchaseLampLighted)
      return

    val price = juiceStock.juicePrice
    _salesVolume += price

    juiceStock.take() // TODO ジュースはどこにいく？
  }

  def juiceCount = juiceStock.count
  def juiceName = juiceStock.juiceName
  def juicePrice = juiceStock.juicePrice

  // ジュースを購入可能ならランプが点灯
  def purchaseLampLighted = juicePrice <= amount && juiceCount != 0
}
