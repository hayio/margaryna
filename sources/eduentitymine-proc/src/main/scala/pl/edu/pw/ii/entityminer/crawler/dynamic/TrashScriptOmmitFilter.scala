package pl.edu.pw.ii.entityminer.crawler.dynamic

import com.gargoylesoftware.htmlunit.{WebResponseData, WebResponse}
import com.gargoylesoftware.htmlunit.util.NameValuePair

/**
 * Created with IntelliJ IDEA.
 * User: ralpher
 * Date: 2/21/13
 * Time: 10:41 PM
 * To change this template use File | Settings | File Templates.
 */
trait TrashScriptOmmitFilter extends WebResponseFilter {
  private val GOOGLE_SCRIPT_URL_PART : String = "google"
  private val WEBLOG_SCRIPT_URL_PART: String = "weblog"

  private def doesAffectThisPage(url: String) = {
    println("[doesAffectThisPage] trash")
    url.contains(GOOGLE_SCRIPT_URL_PART) || url.contains(WEBLOG_SCRIPT_URL_PART)
  }

  abstract override def processWebResponse(url: String, response: WebResponse) = {
    if (doesAffectThisPage(url)) {
      println("[processWebResponse] trash " + url)
      println("## Skipping " + response.getWebRequest.getUrl.toExternalForm)
      val body: Array[Byte] = new Array[Byte](0)
      val wrd = new WebResponseData(body, 200, "OK", new java.util.ArrayList[NameValuePair]())
      super.processWebResponse(url, new WebResponse(wrd, response.getWebRequest(), response.getLoadTime()))
    } else {
      super.processWebResponse(url, response)
    }
  }
}
