package test

import org.specs2.mutable._

import play.api.test._
import play.api.test.Helpers._

/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 * For more information, consult the wiki.
 */
class ApplicationSpec extends Specification {
  
  "Application" should {
    
    "send email on email query" in {
      running(FakeApplication()) {
        val answer = route(FakeRequest(GET, "/?q=Quelle+est+ton+adresse+email")).get

        status(answer) must equalTo(OK)
        contentAsString(answer) must equalTo("ybonnel@gmail.com")
      }
    }

    "should answer to ml" in {
      running(FakeApplication()) {

        val answer = route(FakeRequest(GET, "/?q=Es+tu+abonne+a+la+mailing+list(OUI/NON)")).get

        status(answer) must equalTo(OK)
        contentAsString(answer) must equalTo("OUI")
      }
    }

    "should answer to participate" in {
      running(FakeApplication()) {

        val answer = route(FakeRequest(GET, "/?q=Es tu heureux de participer(OUI/NON)")).get

        status(answer) must equalTo(OK)
        contentAsString(answer) must equalTo("OUI")
      }
    }

    "should answer to markdown ready" in {
      running(FakeApplication()) {

        val answer = route(FakeRequest(GET, "/?q=Es tu pret a recevoir une enonce au format markdown par http post(OUI/NON)")).get

        status(answer) must equalTo(OK)
        contentAsString(answer) must equalTo("OUI")
      }
    }

    "should not answer always yes" in {
      running(FakeApplication()) {

        val answer = route(FakeRequest(GET, "/?q=Est ce que tu reponds toujours oui(OUI/NON)")).get

        status(answer) must equalTo(OK)
        contentAsString(answer) must equalTo("NON")
      }
    }

    "should manage post enonce" in {
      running(FakeApplication()) {

        val answer = route(FakeRequest(POST, "/enonce/1").withTextBody("enonce")).get

        status(answer) must equalTo(CREATED)
      }
    }

    "should know first enonce" in {
      running(FakeApplication()) {

        val answer = route(FakeRequest(GET, "/?q=As tu bien recu le premier enonce(OUI/NON)")).get

        status(answer) must equalTo(OK)
        contentAsString(answer) must equalTo("OUI")
      }
    }

    "should have good night" in {
      running(FakeApplication()) {

        val answer = route(FakeRequest(GET, "/?q=As tu passe une bonne nuit malgre les bugs de l etape precedente(PAS_TOP/BOF/QUELS_BUGS)")).get

        status(answer) must equalTo(OK)
        contentAsString(answer) must equalTo("QUELS_BUGS")
      }
    }

    "should not copy" in {
      running(FakeApplication()) {

        val answer = route(FakeRequest(GET, "/?q=As tu copie le code de ndeloof(OUI/NON/JE_SUIS_NICOLAS)")).get

        status(answer) must equalTo(OK)
        contentAsString(answer) must equalTo("NON")
      }
    }

    "should have good night" in {
      running(FakeApplication()) {

        val answer = route(FakeRequest(GET, "/?q=Souhaites-tu-participer-a-la-suite-de-Code-Story(OUI/NON)")).get

        status(answer) must equalTo(OK)
        contentAsString(answer) must equalTo("OUI")
      }
    }
  }
}