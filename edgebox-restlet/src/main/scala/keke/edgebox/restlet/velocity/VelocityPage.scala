package keke.edgebox.restlet.velocity

import org.restlet.{Response, Request, Restlet}
import org.restlet.data.{CacheDirective, Method}

abstract class VelocityPage extends Restlet with TemplateSupport {
  import scala.collection.JavaConverters._
  override def handle(request: Request, response: Response) = {
    if (request.getMethod == Method.GET) {
      response.setEntity(getRepresentation)
      response.getCacheDirectives.addAll(getCacheDirectives.asJava)
    } else handleOther(request, response)
  }

  protected def getCacheDirectives: List[CacheDirective] = List.empty

  protected def handleOther(request: Request, response: Response) {}
}