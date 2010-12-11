package keke.edgebox.restlet.resource

import org.restlet.resource.{Directory => Dir}
import org.restlet.{Application, Context}

class Directory(ctx: Context, ref: String) extends Dir(ctx, ref) {
  private lazy val theCtx = {
    val c = Application.getCurrent.getContext
    setContext(c)
    c
  }

  override def getContext = {
    theCtx
  }
}