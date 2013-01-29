import org.specs2.mutable._

import play.api.test._
import play.api.test.FakeApplication
import play.api.test.FakeApplication
import play.api.test.FakeApplication
import play.api.test.FakeApplication
import play.api.test.Helpers._
import io.Source

class CalculateSpec extends Specification {

  "Calculate" should {
    (1 to 100).foreach(number => {
       "answer to " + number + "+" + number in {
         running(FakeApplication()) {
           val answer = route(FakeRequest(GET, "/?q=" + number + '+' + number)).get
           status(answer) must equalTo(OK)
           contentAsString(answer) must equalTo("" + (number + number))
         }
       }
    })

    (1 to 100).foreach(number => {
      "answer to " + number + "*" + number in {
        running(FakeApplication()) {
          val answer = route(FakeRequest(GET, "/?q=" + number + '*' + number)).get
          status(answer) must equalTo(OK)
          contentAsString(answer) must equalTo("" + (number * number))
        }
      }
    })

    "should answer to more complicated" in {
      running(FakeApplication()) {
        val answer = route(FakeRequest(GET, "/?q=1+2*2")).get
        status(answer) must equalTo(OK)
        contentAsString(answer) must equalTo("5")
      }
    }

    "should answer with parenthesis" in {
      running(FakeApplication()) {
        val answer = route(FakeRequest(GET, "/?q=(1+2)*2")).get
        status(answer) must equalTo(OK)
        contentAsString(answer) must equalTo("6")

        val answer2 = route(FakeRequest(GET, "/?q=(1+2+3+4+5+6+7+8+9+10)*2")).get
        status(answer2) must equalTo(OK)
        contentAsString(answer2) must equalTo("110")
      }
    }
    "should answer divide" in {
      running(FakeApplication()) {
        val answer = route(FakeRequest(GET, "/?q=(1+2)/2")).get
        status(answer) must equalTo(OK)
        contentAsString(answer) must equalTo("1,5")
      }
    }

    "should answer with double parenthesis" in {
      running(FakeApplication()) {
        val answer = route(FakeRequest(GET, "/?q=((1+2)+3+4+(5+6+7)+(8+9+10)*3)/2*5")).get
        status(answer) must equalTo(OK)
        contentAsString(answer) must equalTo("272,5")
      }
    }

    "should answer with coma" in {
      running(FakeApplication()) {
        val answer = route(FakeRequest(GET, "/?q=1,5*4")).get
        status(answer) must equalTo(OK)
        contentAsString(answer) must equalTo("6")
      }
    }

    "should answer to a big calculate" in {
      running(FakeApplication()) {

        val answer = route(FakeRequest(GET, "/?q=((1,1+2)+3,14+4+(5+6+7)+(8+9+10)*4267387833344334647677634)/2*553344300034334349999000")).get
        status(answer) must equalTo(OK)
        contentAsString(answer) must equalTo("31878018903828899277492024491376690701584023926880")

        val answer2 = route(FakeRequest(GET, "/?q=((1,1+2)+3,14+4+(5+6+7)+(8+9+10)*4267387833344334647677634)/2*553344300034334349999000/31878018903828899277492024491376690701584023926880")).get
        status(answer2) must equalTo(OK)
        contentAsString(answer2) must equalTo("1")

      }
    }

    "should answer to minus" in {
      running(FakeApplication()) {
        val answer = route(FakeRequest(GET, "/?q=(-1)+(1)")).get
        status(answer) must equalTo(OK)
        contentAsString(answer) must equalTo("0")

      }
    }

    "should answer with lot of decimal" in {
      running(FakeApplication()) {
        val answer = route(FakeRequest(GET, "/?q=1,0000000000000000000000000000000000000000000000001*1,0000000000000000000000000000000000000000000000001")).get
        status(answer) must equalTo(OK)
        contentAsString(answer) must equalTo("1,00000000000000000000000000000000000000000000000020000000000000000000000000000000000000000000000001")

      }
    }
  }
}
