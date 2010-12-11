package keke.edgebox.restlet.filter

import org.restlet.routing.Filter
import org.restlet.{Response, Request, Context, Restlet}
import org.restlet.data.CacheDirective
import scala.collection.mutable.Buffer
import java.util.Calendar

/**
 * Created by IntelliJ IDEA.
 * User: qiqiqiqk
 * Date: Nov 27, 2010
 * Time: 9:48:46 PM
 * To change this template use File | Settings | File Templates.
 */

class CacheFilter(ctx: Context, next: Restlet) extends Filter(ctx, next) {
  import scala.collection.JavaConverters._
  private val NoCaches = Buffer(CacheDirective.noCache, CacheDirective.noStore).asJavaCollection
  private val MaxAge = 60 * 60 * 24 * 30 * 12 // one year
  override def afterHandle(request: Request, response: Response) = {
    val s = response.getStatus
    if (s.isSuccess) {
      val e = response.getEntity
      if (e ne null) {
        val path = request.getResourceRef.getPath
        if (path.contains(".nocache.")) {
          response.getCacheDirectives.addAll(NoCaches)
        } else {
          val c = Calendar.getInstance
          c.add(Calendar.YEAR, 1)
          e.setExpirationDate(c.getTime)
          response.getCacheDirectives.add(CacheDirective.maxAge(MaxAge))
        }
      }
    }
  }
}