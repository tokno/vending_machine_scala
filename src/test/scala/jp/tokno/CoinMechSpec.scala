package jp.tokno

import jp.tokno.{Yen, CoinMech, CashBox}
import org.specs2.mutable.Specification
import org.specs2.specification.Scope

class CoinMechSpec extends Specification {
  class coinMechTestingScope extends Scope {
    val coinMech = new CoinMech
  }

  "入金と払い戻し".txt

  "100円を2枚いれると総額は200円" in new coinMechTestingScope {
    coinMech.insert(Yen._100)
    coinMech.insert(Yen._100)

    coinMech.paymentVolume must_== 200
  }

  "500円と50円をいれると総額は550円" in new coinMechTestingScope {
    coinMech.insert(Yen._500)
    coinMech.insert(Yen._50)

    coinMech.paymentVolume must_== 550
  }

  "100円いれて払い戻し" in new coinMechTestingScope {
    val out = new CashBox

    coinMech.insert(Yen._100)
    coinMech.dumpTo(out)
    out.amount must_== 100
  }

  "購入と売り上げ".txt

  "120円入金し120円分購入を行う" in new coinMechTestingScope {
    coinMech.insert(Yen._100)
    coinMech.insert(Yen._10)
    coinMech.insert(Yen._10)

    coinMech.purchase(120)

    coinMech.salesVolume must_== 120
    coinMech.paymentVolume must_== 0
  }

  "釣り銭計算".txt

  "150円入れて120円購入すると釣り銭は30円" in new coinMechTestingScope {
    val out = new CashBox

    coinMech.insert(Yen._100)
    coinMech.insert(Yen._50)

    coinMech.purchase(120)
    coinMech.dumpTo(out)

    out.amount must_== 30
  }
}
