import org.specs2.mutable.Specification
import org.specs2.specification.Scope

class VendingMachineSpec extends Specification {
  trait vendingMachineTestingScope extends Scope {
    val vendingMachine = VendingMachine()
  }

  "お金の投入と払い戻し".txt

  "初期状態" in new vendingMachineTestingScope {
    "総額は0円、釣り銭は0円" in {
      vendingMachine.amount must_== 0
      vendingMachine.changeBox.amount must_== 0
    }

    "払い戻し操作" in {
      vendingMachine.refund()
      vendingMachine.amount must_== 0
      vendingMachine.changeBox.amount must_== 0
    }
  }

  "10円玉、50円玉、100円玉、500円玉、1000円札を投入と総額は1660円" in new vendingMachineTestingScope {
    vendingMachine.insertCoin(Yen._10)
    vendingMachine.insertCoin(Yen._50)
    vendingMachine.insertCoin(Yen._100)
    vendingMachine.insertCoin(Yen._500)
    vendingMachine.insertBill(Yen._1000)

    vendingMachine.amount must_== 1660
  }

  "150円投入して払い戻しすると総額は0円、釣り銭は150円" in new vendingMachineTestingScope {
    vendingMachine.insertCoin(Yen._50)
    vendingMachine.insertCoin(Yen._100)

    vendingMachine.refund()

    vendingMachine.amount must_== 0
    vendingMachine.changeBox.amount must_== 150
  }

  "扱えないお金".txt

  "10000円札を入れると総額は0円、釣り銭は10000円" in new vendingMachineTestingScope {
    vendingMachine.insertBill(Yen._10000)

    vendingMachine.amount must_== 0
    vendingMachine.changeBox.amount must_== 10000
  }

  "ジュースの管理".txt

  "初期状態で、コーラ（値段:120円、名前”コーラ”）を5本格納している" in new vendingMachineTestingScope {
    vendingMachine.juiceName must_== "コーラ"
    vendingMachine.juiceCount must_== 5
  }

  "購入".txt
  
  "購入可能判定" in {
    "在庫があっても、投入金額が110円なら購入不可" in new vendingMachineTestingScope {
      vendingMachine.insertCoin(Yen._100)
      vendingMachine.insertCoin(Yen._10)

      vendingMachine.purchaseLampLighted must_== false
    }

    "在庫があり投入金額が120円なら購入可能" in new vendingMachineTestingScope {
      vendingMachine.insertCoin(Yen._100)
      vendingMachine.insertCoin(Yen._10)
      vendingMachine.insertCoin(Yen._10)

      vendingMachine.purchaseLampLighted must_== true
    }

    "在庫が0の場合は購入不可" in new vendingMachineTestingScope {
      vendingMachine.insertCoin(Yen._500)
      vendingMachine.insertCoin(Yen._500)

      for (_ <- 1 to 5)
        vendingMachine.buy()

      vendingMachine.juiceCount  must_== 0
      vendingMachine.purchaseLampLighted must_== false
    }
  }

  "購入処理" in {
    "120円でコーラを購入" in new vendingMachineTestingScope {
      vendingMachine.insertCoin(Yen._100)
      vendingMachine.insertCoin(Yen._10)
      vendingMachine.insertCoin(Yen._10)

      vendingMachine.buy()

//      vendingMachine.amount must_== 0
      vendingMachine.salesVolume must_== 120
    }
  }
}
