package test

import org.specs2.mutable._

import play.api.test._
import play.api.test.Helpers._
import io.Source

class ScalaskelSpec extends Specification {

  "Scalaskel" should {
    "answer to all scalaskel change" in {
      running(FakeApplication()) {
        (1 to 100).foreach(change => {
            val expected = Source.fromInputStream(getClass().getResourceAsStream("/scalaskel/assertChange" + change + ".json")).mkString
            val answer = route(FakeRequest(GET, "/scalaskel/change/" + change)).get
            status(answer) must equalTo(OK)
            contentAsString(answer) must equalTo(expected)
          }
        )
      }
    }
  }
}
