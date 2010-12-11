package keke.edgebox.restlet.velocity

import org.apache.velocity.app.VelocityEngine
import org.restlet.ext.velocity.TemplateRepresentation
import org.restlet.data.MediaType
import java.util.Date


trait TemplateSupport {
  val velocityEngine: VelocityEngine
  val templateName: String
  val mediaType = MediaType.TEXT_HTML
  val useTemplateLastModified = false

  def getLastModified: Option[Date] = None

  def getTemplate(name: String) = {
    require(name ne null)
    velocityEngine.getTemplate(name)
  }

  import scala.collection.mutable.Map
  def getModel: Map[String, Object] = Map.empty

  import scala.collection.JavaConverters._
  def getRepresentation = {
    val t = getTemplate(templateName)
    val r = new TemplateRepresentation(t, getModel.asJava, mediaType)    
    val l = getLastModified.getOrElse {
      if (useTemplateLastModified) {
        new Date(t.getLastModified)
      } else {
        null
      }
    }
    if (l ne null)
      r.setModificationDate(l)
    r
  }
}