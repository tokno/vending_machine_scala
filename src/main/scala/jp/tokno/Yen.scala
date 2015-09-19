package jp.tokno

// 日本円
object Yen {
  def _1 = Yen(1)
  def _5 = Yen(5)
  def _10 = Yen(10)
  def _50 = Yen(50)
  def _100 = Yen(100)
  def _500 = Yen(500)
  def _1000 = Yen(1000)
  def _2000 = Yen(2000)
  def _5000 = Yen(5000)
  def _10000 = Yen(10000)
}

sealed case class Yen private (value: Int)
