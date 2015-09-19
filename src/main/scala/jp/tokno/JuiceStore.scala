package jp.tokno

import scala.collection.mutable

// ジュース保存庫
class JuiceStore {
  // ジュースの種類
  private def _juice = stock.keySet.head
  def juicePrice = _juice.price

  def juiceName = _juice.name
  def count = stock.getOrElse(_juice, 0)

  // ジュースの在庫管理
  // 種別 => 個数 のマップ
  private val stock = mutable.HashMap[Juice, Int]()

  // ジュースを追加。
  def add(juice: Juice, increments: Int): Unit = {
    val count = stock.getOrElse(juice, 0)

    stock.put(juice, count + increments)
  }

  // ジュースを1つ取り出す
  def take():Juice = {
    val count = stock.getOrElse(_juice, 0)

    if (count == 0)
      throw new NoSuchElementException("ジュースがありません")

    stock.put(_juice, count - 1)

    _juice.copy()
  }
}
