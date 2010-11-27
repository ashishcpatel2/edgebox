package keke.edgebox.restlet.filter

import org.specs.SpecificationWithJUnit
import org.specs.mock.Mockito
import org.junit.runner.RunWith
import org.specs.runner.JUnitSuiteRunner
import java.lang.IllegalArgumentException
import org.restlet.{Response, Request, Restlet}
import org.restlet.data.{Status, Method}

/**
 * Created by IntelliJ IDEA.
 * User: qiqiqiqk
 * Date: Nov 27, 2010
 * Time: 11:48:01 AM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(classOf[JUnitSuiteRunner])
class NormalizeUrlFilterSpecTest extends SpecificationWithJUnit with Mockito {
  val next = mock[Restlet]
  "NormalizeUrlFilter" should {
    "not accept null filter as next" in {
      new NormalizeUrlFilter(null, null) must throwA[IllegalArgumentException]
    }

    "append a slash and redirect" in {
      val fixture = new NormalizeUrlFilter(null, next, Array(""""\w*\.\w*"""))
      val req = new Request(Method.GET, "/abc#ddd?ttt=bbb")
      val resp = new Response(req)
      fixture.handle(req, resp)
      resp.getStatus must_== Status.REDIRECTION_PERMANENT
      resp.getLocationRef.toString must_== "/abc/#ddd?ttt=bbb"
      there was no(next).handle(any[Request], any[Response])
    }

     "do nothing when path ending with a slash" in {
      val fixture = new NormalizeUrlFilter(null, next, Array(""""\w*\.\w*""", """^\/abc"""))
      val req = new Request(Method.GET, "/abc/#ddd?ttt=bbb")
      val resp = new Response(req)
      fixture.handle(req, resp)
      there was one(next).handle(any[Request], any[Response])
    }

    "do nothing when matching filters" in {
      val fixture = new NormalizeUrlFilter(null, next, Array(""""\w*\.\w*""", """^\/abc"""))
      val req = new Request(Method.GET, "/abc#ddd?ttt=bbb")
      val resp = new Response(req)
      fixture.handle(req, resp)
      there was one(next).handle(any[Request], any[Response])
    }
  }
}