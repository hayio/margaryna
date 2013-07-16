package pl.edu.pw.ii.entityminer.crawler.dynamic

import com.gargoylesoftware.htmlunit.util.WebConnectionWrapper
import com.gargoylesoftware.htmlunit.{WebClient, WebRequest}

/**
 * Created with IntelliJ IDEA.
 * User: ralpher
 * Date: 2/16/13
 * Time: 10:58 PM
 * To change this template use File | Settings | File Templates.
 */
class ScriptOmmitingConnectionWrapper(webClient: WebClient) extends WebConnectionWrapper(webClient) {
  override def getResponse(request: WebRequest) = {
    var response = super.getResponse(request)
    val url = request.getUrl.toExternalForm

    val filter = new PassingByWebResponseFilter with TrashScriptOmmitFilter
    filter.processWebResponse(url, response)
    response
  }
}
