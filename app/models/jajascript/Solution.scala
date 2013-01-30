package models.jajascript

import play.api.libs.json._
import java.util


case class Solution(endTime: Int, price: Int, acceptedFlights: util.BitSet) {

  def toJson(flights: Seq[Flight]): JsObject = {

    val flightsWithPosition = flights.zip((0 to flights.size))

    Json.obj(
      "gain" -> JsNumber(price),
      "path" -> JsArray(flightsWithPosition.filter(
        (value: (Flight, Int)) => acceptedFlights.get(value._2)).map(
          (value: (Flight, Int)) => JsString(value._1.name)))
    )
  }
}
