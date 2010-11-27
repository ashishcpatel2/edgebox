package keke.edgebox.restlet.filter

import org.restlet.routing.Filter
import org.restlet.{Restlet, Response, Request, Context}
import scala.util.matching.Regex
import org.restlet.data.Reference

/**
 * Implement the [[http://docs.djangoproject.com/en/dev/misc/design-philosophies/ Django's normalized URL]].
 *
 * By default, an slash will be appended to URl which ends with non slash, except for those are matched by the `regexps` parameter.
 *
 * @see [[http ://docs.djangoproject.com/en/dev/ref/middleware/#module-django.middleware.common Django Common Middleware]]
 * @author keke
 * @version 0.0.1
 * @since 0.0.1
 * @param ctx the [[Context]]
 * @param next the next [[Restlet]]
 * @param regeps the array of regular expression strings
 */
class NormalizeUrlFilter(ctx: Context, next: Restlet, regexps: Array[String] = Array.empty) extends Filter(ctx, next) {
  require(next ne null)

  private lazy val regexpList = regexps.map(new Regex(_))

  override protected def beforeHandle(request: Request, response: Response) = {
    val ref = request.getResourceRef
    val path = ref.getPath
    if (path.endsWith("/")) {
      Filter.CONTINUE
    } else {
      regexpList.find(_.findFirstIn(path).isDefined) match {
        case Some(_) => Filter.CONTINUE
        case None =>
          val newRef = new Reference(ref)
          newRef.setPath(path + '/')
          response.redirectPermanent(newRef)
          Filter.STOP
      }
    }
  }
}