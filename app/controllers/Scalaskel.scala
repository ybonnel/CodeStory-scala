package controllers

import models.scalaskel.{Coin, Change}
import play.api.mvc._
import play.api.libs.json._

object Scalaskel extends Controller {

  def calculate(cents: Int, currentChange: Option[Change], lastCoin: Coin): List[Change] = {
    if (cents == 0) {
      return List(currentChange).filter(change => change.isDefined).map(change => change.get)
    }


    var changes: List[Change] = List()

    Coin.allCoin.filter(aCoin => aCoin.value >= lastCoin.value && aCoin.canPay(cents)).foreach(
      aCoin => {
        val newChange = new Change(currentChange.getOrElse(new Change()), aCoin)

        changes = changes ::: calculate(cents - aCoin.value, Option(newChange), aCoin)
      }
    )

    return changes
  }


  def change(cents: Integer) = Action {
    val changes = calculate(cents, Option.empty, Coin(1))
    val jsonResult = changes.map(change => change.toJson)
    Ok(Json.toJson(jsonResult))
  }

}

