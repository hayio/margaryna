package pl.edu.pw.ii.entityminer.crawler.static

import com.ning.http.client._
import org.w3c.dom.Node
import org.htmlcleaner.{DomSerializer, TagNode, CleanerProperties, HtmlCleaner}

/**
 * Created by IntelliJ IDEA.
 * User: ralpher
 * Date: 3/9/11
 * Time: 7:04 PM
 * To change this template use File | Settings | File Templates.
 */

/**
 * Klasa zajmujaca sie asynchronicznym pobieraniem pojedynczej strony www.
 */
class WebpageAsyncHttpClient extends HttpClient {

  private val cleaner: HtmlCleaner = new HtmlCleaner
  private val props: CleanerProperties = {
    val tmp = cleaner.getProperties
    tmp.setNamespacesAware(false)
    tmp.setOmitDeprecatedTags(false)
    tmp.setOmitUnknownTags(false)
    tmp
  }
  private val httpClient = initHttpClient

  def cleanAndConvertToDocument(response: Response) = {
    val tagNode: TagNode = cleaner.clean(response.getResponseBodyAsStream)
    new DomSerializer(props).createDOM(tagNode)
  }

  def syncDownload(request: Request, handler: AsyncHandler[Response] = new ConnectionHandler) = {
    val f = httpClient.executeRequest(request, handler) //prepareRequest(request).execute(handler);f
    f
  }

  def syncDownloadStr(uri: String): String = {
    val request = buildStandartRequest(uri)
    syncDownload(request).get.getResponseBody
  }

  def syncDownload(uri: String) = {
    val request = buildStandartRequest(uri)
    syncDownload(request).get
  }

  def syncDownload(uri: String, connectionHandler: AsyncHandler[Response]): String = {
    val request = buildStandartRequest(uri)
    syncDownload(request, connectionHandler).get.getResponseBody
  }

  def asyncDownload(url: String, passedHandler: PageAsyncHandler) = {
    println("PageAsyncDownloader sciagam po stringu!" + url)

    try {
      val request = buildStandartRequest(url)
      val handler = new WrappingHandler(url, passedHandler)
      httpClient.prepareRequest(request)
        .execute(handler)
    }
    catch {
      case ex: Exception =>
        println("stack trace w PageAsyncDownloader " + url)
        ex.printStackTrace
        passedHandler.onThrowable(ex, url)
    }
  }

  def asyncDownload(request: Request, passedHandler: PageAsyncHandler) = {
    val handler = new WrappingHandler(request.getRawUrl, passedHandler)
    httpClient.prepareRequest(request)
      .execute(handler)
  }

  def printNode(rootNode : Node, spacer: String): Unit = {
    println(spacer + rootNode.getNodeName() + " -> " + rootNode.getNodeValue());
    val nl = rootNode.getChildNodes();
    var i = 0;
    while (i < nl.getLength()) {
      printNode(nl.item(i), spacer + "   ");
      i += 1;
    }
  }

  def close = httpClient.close

  private def initHttpClient = {
    val conf = new AsyncHttpClientConfig
    .Builder()
      .setAllowPoolingConnection(true)
      .setFollowRedirects(true)
      .setMaximumNumberOfRedirects(5)
      .setUserAgent("Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)")
      //                        .setUserAgent("Mozilla/5.0 (Windows; U; Windows NT 6.0; en-US; rv:1.9.0.1) Gecko/2008070208 Firefox/3.0.1")
      .setCompressionEnabled(true)
      .setConnectionTimeoutInMs(30000)
      .setMaxRequestRetry(5)
      .setMaximumConnectionsTotal(100)
      .setMaximumConnectionsPerHost(100)
      .setRequestTimeoutInMs(20000) //20*1000
      .build()
    new AsyncHttpClient(conf);
  }

  private def buildStandartRequest(uri: String) = {
    val builder = new RequestBuilder("GET");
    builder.setUrl(uri)
      .setHeader("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.0; en-US; rv:1.9.0.1) Gecko/2008070208 Firefox/3.0.1")
      //.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
      .setHeader("Accept", "text/html")
      .setHeader("Accept-Language", "en-us,en;q=0.5")
      .setHeader("Accept-Charset", "utf-8")
      .build()
  }
}