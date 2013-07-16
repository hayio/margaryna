package pl.edu.pw.ii.entityminer.crawler.xpath

import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers
import pl.edu.pw.ii.entityminer.Conf
import pl.edu.pw.ii.entityminer.crawler.static.WebpageAsyncHttpClient
import org.htmlcleaner.{DomSerializer, TagNode, CleanerProperties, HtmlCleaner}
import org.jaxen.dom.DOMXPath
import org.w3c.dom.Node
import java.lang.String

/**
 * Created with IntelliJ IDEA.
 * User: Rafael Hazan
 * Date: 4/21/13
 * Time: 11:31 PM
 */
class JaxenSpec extends FlatSpec with ShouldMatchers {

  "Jaxen" should "extract xpath" in {
    val client = new WebpageAsyncHttpClient
    val response = client.syncDownload(Conf.WIKICFP_CONFERENCE_START_PAGE_AI)

    val cleaner: HtmlCleaner = new HtmlCleaner
    val props: CleanerProperties = cleaner.getProperties
    props.setNamespacesAware(false)
    props.setOmitDeprecatedTags(false)
    props.setOmitUnknownTags(false)
    val tagNode: TagNode = cleaner.clean(response.getResponseBodyAsStream)
    val document = new DomSerializer(props).createDOM(tagNode)
    var xpathExpr: DOMXPath = new DOMXPath("//tr[3]/td[2]/text()")
    // /body/div[4]/center/form/table/tbody/tr[3]/td/table/tbody/tr[3]/td[2]
    val list = xpathExpr.selectNodes(document)
    println("result:")
    println(list)
    client.printNode(document," ")

  }
}