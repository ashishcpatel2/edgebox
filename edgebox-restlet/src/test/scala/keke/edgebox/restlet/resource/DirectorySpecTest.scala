package keke.edgebox.restlet.resource

import org.specs.SpecificationWithJUnit
import org.specs.mock.Mockito
import org.junit.runner.RunWith
import org.specs.runner.JUnitSuiteRunner
import org.restlet.{Context, Application}

/**
 * @author keke
 */
@RunWith(classOf[JUnitSuiteRunner])
class DirectorySpecTest extends SpecificationWithJUnit with Mockito {
  "Dirctory" should {
    "get context from Applicaton" in {
      val mockApp = mock[Application]
      Application.setCurrent(mockApp)
      mockApp.getContext returns new Context 
      val fixture = new Directory(null, "war:///abc")
      fixture.getContext must notBeNull
      fixture.getContext must notBeNull
      there was one(mockApp).getContext
    }
  }
}