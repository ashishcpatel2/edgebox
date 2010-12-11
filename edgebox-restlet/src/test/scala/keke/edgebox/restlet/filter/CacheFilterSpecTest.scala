package keke.edgebox.restlet.filter

import org.junit.runner.RunWith
import org.specs.runner.JUnitSuiteRunner
import org.specs.SpecificationWithJUnit
import org.specs.mock.Mockito
import org.restlet.{Response, Request, Restlet}
import org.restlet.data.{MediaType, CacheDirective, Method}
import java.util.Calendar

@RunWith(classOf[JUnitSuiteRunner])
class CacheFilterSpecTest extends SpecificationWithJUnit with Mockito {
  import scala.collection.JavaConverters._
  val Next = mock[Restlet]
  "CacheFilter" should {
    "force no cache when url contains '.nocache.'" in {
      val fixture = new CacheFilter(null, Next)
      val r = new Request(Method.GET, "/abc.nocache.js")
      val resp = new Response(r)
      Next.handle(any[Request], any[Response]) answers {
        p =>
          val rr = p.asInstanceOf[Array[Object]](1).asInstanceOf[Response]
          rr.setEntity("abc", MediaType.TEXT_PLAIN)
      }
      fixture.handle(r, resp)

      resp.getCacheDirectives.asScala must containAll(List(CacheDirective.noCache, CacheDirective.noStore))
    }

    "enable do browser ache for entity'" in {
      val fixture = new CacheFilter(null, Next)
      val r = new Request(Method.GET, "/abc.js")
      val resp = new Response(r)
      Next.handle(any[Request], any[Response]) answers {
        p =>
          val rr = p.asInstanceOf[Array[Object]](1).asInstanceOf[Response]
          rr.setEntity("abc", MediaType.TEXT_PLAIN)
      }
      fixture.handle(r, resp)

      resp.getCacheDirectives.asScala must contain(CacheDirective.maxAge(60 * 60 * 24 * 30 * 12))
      val c = Calendar.getInstance
      c.setTime(resp.getEntity.getExpirationDate)
      val t = Calendar.getInstance
      c.get(Calendar.YEAR) - t.get(Calendar.YEAR) must_== 1
    }
  }
}