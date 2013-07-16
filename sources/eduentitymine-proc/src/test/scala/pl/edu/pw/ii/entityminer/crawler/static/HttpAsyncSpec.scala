package pl.edu.pw.ii.entityminer.crawler.static

import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers
import pl.edu.pw.ii.entityminer.crawler.dynamic.HtmlUnitSpider
import pl.edu.pw.ii.entityminer.Conf
import scala.collection.mutable
import com.gargoylesoftware.htmlunit.html.DomNode

/**
 * Created with IntelliJ IDEA.
 * User: Rafael Hazan
 * Date: 4/21/13
 * Time: 11:31 PM
 */
class HttpAsyncSpec extends FlatSpec with ShouldMatchers {

  "Http async Spider" should "download webpage" in {
    val client = new WebpageAsyncHttpClient
    val pageContent = client.syncDownloadStr(Conf.WIKICFP_CONFERENCE_START_PAGE_AI)
    println(pageContent)
  }
}