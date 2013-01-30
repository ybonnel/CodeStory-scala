package controllers

import play.api.mvc._
import play.api.libs.json._
import models.jajascript.{Optimize, Flight}
import play.Logger

object Jajascript extends Controller {

  private val patternArray = "\\[(.*)\\].*".r
  private val patternFlight = "\\{ ?\"VOL\" ?: ?\"([\\w\\-\\*]+)\" ?, ?\"DEPART\" ?: ?(\\d+) ?, ?\"DUREE\" ?: ?(\\d+) ?, ?\"PRIX\" ?: ?(\\d+) ?}".r("vol", "depart", "duree", "prix")


  def parseRequest(request:String):Seq[Flight] = {
    patternFlight.findAllMatchIn(patternArray.findFirstIn(request).get).map(
      matchFlight => {
        Flight(
          matchFlight.group("vol"),
          matchFlight.group("depart").toInt,
          matchFlight.group("duree").toInt,
          matchFlight.group("prix").toInt)
      }
    ).toSeq
  }


  def optimize = Action(parse.tolerantText(maxLength = 1024 * 1024 * 50)) {
    request =>

      val startTime = System.nanoTime()

      val flights = parseRequest(request.body)

      Logger.info("Json parse : " + (System.nanoTime() - startTime) + "ns")


      /*val jajascriptRequest = jsArray.value.map(jsValue => {
        val jsFlight = jsValue.asInstanceOf[JsObject]
        Flight(
          jsFlight.value.get("VOL").get.as[String],
          jsFlight.value.get("DEPART").get.as[Int],
          jsFlight.value.get("DUREE").get.as[Int],
          jsFlight.value.get("PRIX").get.as[Int])
      }
      )*/

      val optimize = Optimize(flights)

      val solution = optimize.optimize

      val elapsedTime = System.nanoTime() - startTime

      Logger.info("Time for " + flights.size + " flights : " + elapsedTime + "ns")

      Ok(Json.toJson(solution.toJson(optimize.flights)))
  }

}

