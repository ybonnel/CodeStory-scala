package models.scalaskel

import play.api.libs.json._
import play.api.Logger


case class Change(foo:Int, bar:Int, qix:Int, baz:Int) {
  def this() = this(0, 0, 0, 0)
  def this(change:Change, coin:Coin) = this(
    if (coin.isFoo) change.foo + 1 else change.foo,
    if (coin.isBar) change.bar + 1 else change.bar,
    if (coin.isQix) change.qix + 1 else change.qix,
    if (coin.isBaz) change.baz + 1 else change.baz
  )

  def toJson:JsObject = {
    val fields = Seq("foo" -> foo,
      "bar" -> bar,
      "qix" -> qix,
      "baz" -> baz).filter( value => value._2 != 0)

    return JsObject(fields.map(f => (f._1, JsNumber(f._2))))
  }

}

