package pl.edu.pw.ii.entityminer.util

import java.net.URI
import pl.edu.pw.ii.entityminer.Conf

/**
 * Created with IntelliJ IDEA.
 * User: Rafael Hazan
 * Date: 7/4/13
 * Time: 8:21 PM
 */
object NetHelper {
  val HREF_ATTRIBUTE = "href"
  val HTML_SUFFIX = ".html"
  val HTTP_PREFIX = "http"
  val HTTPS_PREFIX = "https"
  val WWW = "www"
  val HTTP_WWW = HTTP_PREFIX + "://" + WWW + "."
  val HTTPS_WWW = HTTPS_PREFIX + "://" + WWW + "."

  def getDomainName(url: String): String = {
    val uri = new URI(normalizeUri(url))
    val domain = uri.getHost()
    return if (domain == null) null else if (domain.startsWith("www.")) domain.substring(4) else domain
  }

  def composeUri(domain: String, partialUri: String) = {
    val partialUriNormalized = normalizeUri(partialUri)
    if (partialUriNormalized.startsWith(HTTP_PREFIX))
      partialUriNormalized
    else if (partialUriNormalized.startsWith(WWW))
      partialUriNormalized
    else
      HTTP_WWW + domain + partialUriNormalized
  }

  def strictNormalieUri(uri: String) = {
    val tmp = normalizeUri(uri) match {
      case str if str.startsWith(HTTP_WWW) => str
      case str if str.startsWith(HTTPS_WWW) => str
      case str if str.startsWith(HTTP_PREFIX + "://") => HTTP_WWW + str.substring((HTTP_PREFIX + "://").length)
      case str if str.startsWith(HTTPS_PREFIX + "://") => HTTPS_WWW + str.substring((HTTPS_PREFIX + "://").length)
      case str if str.startsWith(WWW + ".") => HTTP_WWW + str.substring((WWW + ".").length)
      case rest => HTTP_WWW + rest
    }
    tmp match {
      case uri if uri.endsWith("/") => uri.dropRight(1)
      case uri => uri
    }
  }

  private def normalizeUri(uri: String) = uri.replaceAll("\\\\", "/").trim
}
