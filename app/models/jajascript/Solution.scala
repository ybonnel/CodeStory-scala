package models.jajascript

import play.api.libs.json._


case class Solution(endTime:Int, price:Int, acceptedFlights:Seq[Flight] ) {

  def toJson:JsObject = {
    Json.obj(
      "gain" -> JsNumber(price),
      "path" -> JsArray(acceptedFlights.map(flight => JsString(flight.name)))
    )
  }
}
