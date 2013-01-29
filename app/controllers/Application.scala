package controllers

import play.api._
import play.api.mvc._
import groovy.lang.GroovyShell
import java.text.{DecimalFormatSymbols, DecimalFormat, NumberFormat}
import java.util.Locale

object Application extends Controller {

  val fixResponses = Map(
    "Quelle est ton adresse email" -> "ybonnel@gmail.com",
    "Es tu abonne a la mailing list(OUI/NON)" -> "OUI",
    "Es tu heureux de participer(OUI/NON)" -> "OUI",
    "Es tu pret a recevoir une enonce au format markdown par http post(OUI/NON)" -> "OUI",
    "Est ce que tu reponds toujours oui(OUI/NON)" -> "NON",
    "As tu bien recu le premier enonce(OUI/NON)" -> "OUI",
    "As tu bien recu le second enonce(OUI/NON)" -> "OUI",
    "As tu passe une bonne nuit malgre les bugs de l etape precedente(PAS_TOP/BOF/QUELS_BUGS)" -> "QUELS_BUGS",
    "As tu copie le code de ndeloof(OUI/NON/JE_SUIS_NICOLAS)" -> "NON",
    "Souhaites-tu-participer-a-la-suite-de-Code-Story(OUI/NON)" -> "OUI"
  )

  val calculatePattern = "[\\-\\*\\+/\\(\\)0-9 ,]+"

  def calculate(query:String):String = {
    val shell = new GroovyShell()

    val result = shell.evaluate("return " + query.replace(' ', '+').replace(',', '.'))
    val format = new DecimalFormat("#0.#", new DecimalFormatSymbols(Locale.FRANCE))
    format.setMaximumFractionDigits(500);

    if (result.isInstanceOf[Integer]) {
      result.toString
    } else {
      format.format(result)
    }
  }

  def queryHandler(query:String):Result = {
    val response = fixResponses.get(query)
    if (response.isDefined) {
      Logger.info("Response : ".concat(response.get))
      Ok(response.get)
    } else if (query.matches(calculatePattern)){
      Ok(calculate(query))
    } else {
      BadRequest("I can't respond to your query")
    }
  }

  def index = Action {
    request =>
      val query = request.getQueryString("q")

      query match {
        case None => BadRequest("I can't respond to your query")
        case _ => queryHandler(query.get)
      }
  }

  def enonce(id: Integer) = Action {
    request =>
      Logger.info("Enonce " + id + " : " + request.body.toString())
      Created("OK")
  }

}