package test

import org.specs2.mutable._

import play.api.test._
import play.api.test.Helpers._
import io.Source

class JajascriptSpec extends Specification {
  def testJajascriptLevel(level: Int) = {
    "answer to jajascript level " + level in {
      running(FakeApplication()) {
        val request = Source.fromInputStream(getClass().getResourceAsStream("/jajascript/request" + level + ".json")).mkString
        val response = Source.fromInputStream(getClass().getResourceAsStream("/jajascript/response" + level + ".json")).mkString
        val answer = route(FakeRequest(POST, "/jajascript/optimize").withBody(request)).get
        status(answer) must equalTo(OK)
        contentAsString(answer) must equalTo(response)
      }
    }
  }

  "Jajascript" should {
    (1 to 20).foreach(level => testJajascriptLevel(level))
    testJajascriptLevel(30)
    testJajascriptLevel(50)
    100.to(800, 100).foreach(level => testJajascriptLevel(level))
    testJajascriptLevel(1000)
    testJajascriptLevel(2000)
    testJajascriptLevel(10000)
  }
}
