package keke.edgebox.restlet.filter

import org.restlet.routing.Filter
import org.restlet.{Response, Request, Context, Restlet}
import org.restlet.engine.application.EncodeRepresentation
import org.restlet.data.Encoding

/**
 * @author keke
 */
class GzipFilter(ctx: Context, next: Restlet) extends Filter(ctx, next) {
  override def afterHandle(request: Request, response: Response) = {
    val e = response.getEntity
    if (e ne null) {
      val ge = new EncodeRepresentation(Encoding.GZIP, e)
      response.setEntity(ge)
    }
  }
}