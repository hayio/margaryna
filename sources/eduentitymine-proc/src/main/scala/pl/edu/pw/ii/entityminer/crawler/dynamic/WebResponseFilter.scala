package pl.edu.pw.ii.entityminer.crawler.dynamic

import com.gargoylesoftware.htmlunit.WebResponse

/**
 * Created with IntelliJ IDEA.
 * User: ralpher
 * Date: 2/21/13
 * Time: 10:31 PM
 * To change this template use File | Settings | File Templates.
 */
trait WebResponseFilter {
  def processWebResponse(url: String, response: WebResponse) : WebResponse
}
