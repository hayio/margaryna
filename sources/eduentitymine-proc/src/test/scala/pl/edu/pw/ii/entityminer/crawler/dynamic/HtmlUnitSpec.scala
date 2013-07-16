package pl.edu.pw.ii.entityminer.crawler.dynamic

import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers
import collection.mutable
import com.gargoylesoftware.htmlunit.html.DomNode
import pl.edu.pw.ii.entityminer.Conf

/**
 * Created with IntelliJ IDEA.
 * User: ralpher
 * Date: 2/16/13
 * Time: 5:58 PM
 * To change this template use File | Settings | File Templates.
 */
class HtmlUnitSpec extends FlatSpec with ShouldMatchers {

  "HtmlUnitSpider" should "render js" in {
    val htmlUnitSpider = new HtmlUnitSpider()
    val page = htmlUnitSpider.getPageOmmitScripts(Conf.WIKICFP_CONFERENCE_START_PAGE_CPOMPUTER_SC)

    println("to rezultat testu")
    val body = page.getBody
    val stack = new mutable.Stack[DomNode]
    stack.push(body)
    while (stack.nonEmpty) {
      val tmp = stack.pop()
      println("text content of " + tmp.getLocalName)
      println(tmp.getTextContent)
      val iter = tmp.getChildren.iterator()
      while (iter.hasNext) {
        stack.push(iter.next())
      }
    }

    htmlUnitSpider.closeAllWindows()
  }
}
