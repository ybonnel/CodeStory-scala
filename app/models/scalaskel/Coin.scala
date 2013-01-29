package models.scalaskel


case class Coin(value:Int) {

  def canPay(centsToPay:Int):Boolean = centsToPay >= value

  def isFoo:Boolean = value == 1
  def isBar:Boolean = value == 7
  def isQix:Boolean = value == 11
  def isBaz:Boolean = value == 21

}

object Coin {
  def allCoin = Seq(
    Coin(1),
    Coin(7),
    Coin(11),
    Coin(21)
  )
}
