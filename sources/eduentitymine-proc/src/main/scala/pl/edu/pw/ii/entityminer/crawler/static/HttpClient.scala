package pl.edu.pw.ii.entityminer.crawler.static

import com.ning.http.client.Response
import org.w3c.dom.Document


/**
 * Created with IntelliJ IDEA.
 * User: ralpher
 * Date: 12/30/12
 * Time: 11:41 AM
 * To change this template use File | Settings | File Templates.
 */
trait HttpClient {
  def asyncDownload(url: String, passedHandler: PageAsyncHandler) : Unit
  def syncDownloadStr(uri: String): String
  def syncDownload(uri: String): Response

  def cleanAndConvertToDocument(response: Response): Document
}
