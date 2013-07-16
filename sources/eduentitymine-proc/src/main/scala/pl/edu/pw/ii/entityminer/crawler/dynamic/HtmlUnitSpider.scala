package pl.edu.pw.ii.entityminer.crawler.dynamic

import com.gargoylesoftware.htmlunit._
import html.HtmlPage

/**
 * Created with IntelliJ IDEA.
 * User: ralpher
 * Date: 2/16/13
 * Time: 5:39 PM
 * To change this template use File | Settings | File Templates.
 */
class HtmlUnitSpider {
  private val webClient = new WebClient(BrowserVersion.FIREFOX_10)

  def getPageOmmitScripts(url: String): HtmlPage = {
    webClient.setThrowExceptionOnScriptError(false);
    new ScriptOmmitingConnectionWrapper(webClient)
    val page: HtmlPage = webClient.getPage(url)
    webClient.waitForBackgroundJavaScript(10000);
    page
  }

  def closeAllWindows() = webClient.closeAllWindows();
}
