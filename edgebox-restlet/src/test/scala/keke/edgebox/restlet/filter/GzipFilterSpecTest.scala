package keke.edgebox.restlet.filter

import org.junit.runner.RunWith
import org.specs.runner.JUnitSuiteRunner
import org.specs.SpecificationWithJUnit
import org.specs.mock.Mockito
import org.restlet.{Response, Request, Restlet}
import org.restlet.engine.application.EncodeRepresentation
import org.restlet.representation.StringRepresentation
import org.restlet.data.{MediaType, Method}

@RunWith(classOf[JUnitSuiteRunner])
class GzipFilterSpecTest extends SpecificationWithJUnit with Mockito {
  val Next = mock[Restlet]
  "GzipFilter" should {
    "Do gzip for representation" in {
      val fixture = new GzipFilter(null, Next)
      val r = new Request(Method.GET, "abc")
      val resp = new Response(r)
      Next.handle(r, resp) answers {
        p =>
          val r = p.asInstanceOf[Array[Object]](1).asInstanceOf[Response]
          r.setEntity(new StringRepresentation("", MediaType.TEXT_PLAIN))
      }
      fixture.handle(r, resp)
      resp.getEntity must notBeNull
      resp.getEntity.isInstanceOf[EncodeRepresentation] must_== true
    }
    "Do nothing for null" in {
      val fixture = new GzipFilter(null, Next)
      val r = new Request(Method.GET, "abc")
      val resp = new Response(r)
      fixture.handle(r, resp)
      resp.getEntity must beNull
    }
  }
}