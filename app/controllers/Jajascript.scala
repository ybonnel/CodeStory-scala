package controllers

import play.api.mvc._
import play.api.libs.json._
import models.jajascript.{Optimize, Flight}
import play.Logger

object Jajascript extends Controller {


  def optimize = Action(parse.text(maxLength = 1024 * 1024 * 50)) {
    request =>

      val startTime = System.nanoTime()

      val jajascriptRequest = Json.parse(request.body).asInstanceOf[JsArray].value.map(jsValue => {
        val jsFlight = jsValue.asInstanceOf[JsObject]
        Flight(
          jsFlight.value.get("VOL").get.as[String],
          jsFlight.value.get("DEPART").get.as[Int],
          jsFlight.value.get("DUREE").get.as[Int],
          jsFlight.value.get("PRIX").get.as[Int])
      }
      )

      val solution = Optimize(jajascriptRequest).optimize

      val elapsedTime = System.nanoTime() - startTime

      Logger.info("Time for " + jajascriptRequest.size + " flights : " + elapsedTime + "ns")

      Ok(Json.toJson(solution.toJson))
  }

}
