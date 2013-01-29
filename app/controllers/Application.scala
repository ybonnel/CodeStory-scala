package controllers

import play.api._
import play.api.mvc._

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
  
  def index = Action { request =>
    val query = request.getQueryString("q")
    if (query.isDefined) {
      val response = fixResponses.get(query.get)
      if (response.isDefined) {
        Logger.info("Response : ".concat(response.get))
        Ok(response.get)
      } else {
        BadRequest("I can't respond to your query")
      }
    } else {
      BadRequest("I can't respond to your query")
    }
  }

  def enonce(id: Integer) = Action { request =>
    Logger.info("Enonce " + id  +  " : " + request.body.toString())
    Created("OK")
  }
  
}