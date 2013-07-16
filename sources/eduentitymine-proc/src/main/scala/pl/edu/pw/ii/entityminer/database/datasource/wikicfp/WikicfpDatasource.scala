package pl.edu.pw.ii.entityminer.database.datasource.wikicfp

import pl.edu.pw.ii.entityminer.crawler.static.HttpClient
import pl.edu.pw.ii.entityminer
import org.jaxen.dom.DOMXPath
import scala.collection.JavaConversions.iterableAsScalaIterable
import scala.collection.mutable
import pl.edu.pw.ii.entityminer.database.datasource.{ConferenceData, WebDatasource}
import org.w3c.dom.Node
import pl.edu.pw.ii.entityminer.util.FileHelper
import java.io.{ObjectInputStream, FileInputStream}
import pl.edu.pw.ii.entityminer.Conf
import pl.edu.pw.ii.entityminer.database.datasource.pagestorage.PageDirectoryManifest

/**
 * Created with IntelliJ IDEA.
 * User: Rafael Hazan
 * Date: 7/1/13
 * Time: 9:10 PM
 */
class WikicfpDatasource extends WebDatasource {

  val DOMAIN_URL = "http://www.wikicfp.com/"
  val BASE_URL = DOMAIN_URL + "cfp/call?conference=artificial%20intelligence&page="

  val CONFERENCE_SUBPAGES_XPATH: String = "/html/body/div[4]/center/form/table/tbody/tr[3]/td/table/tbody/tr/td/a/@href"
  val CONFERENCE_LINK_XPATH: String = "/html/body/div[4]/center/table/tbody/tr[3]/td/a/text()"
  val NAME_XPATH = "/html/body/div[4]/center/table/tbody/tr[2]/td/h2/span/span/text()"
  val WHEN_XPATH = "/html/body/div[4]/center/table/tbody/tr[5]/td/table/tbody/tr/td/table/tbody/tr/td/table/tbody/tr/td/text()"
  val WHERE_XPATH = "/html/body/div[4]/center/table/tbody/tr[5]/td/table/tbody/tr/td/table/tbody/tr/td/table/tbody/tr[2]/td/text()"
  val SUBMISSION_DEADLINE_XPATH = "/html/body/div[4]/center/table/tbody/tr[5]/td/table/tbody/tr/td/table/tbody/tr/td/table/tbody/tr[3]/td/span/span[3]/text()"
  val NOTIFICATION_DUE_XPATH = "/html/body/div[4]/center/table/tbody/tr[5]/td/table/tbody/tr/td/table/tbody/tr/td/table/tbody/tr[4]/td/span/span[3]/text()"
  val FINAl_VERSION_DUE_XPATH = "/html/body/div[4]/center/table/tbody/tr[5]/td/table/tbody/tr/td/table/tbody/tr/td/table/tbody/tr[5]/td/span/span[3]/text()"

  val httpClient = entityminer.ctx.getBean(classOf[HttpClient])

  var startPage: Int = 0
  var pageIndex: Int = 0

  def setUp() {
    startPage = 1
    pageIndex = 1
  }

  def nextPage() = {
    val response = httpClient.syncDownload(BASE_URL + pageIndex)
    pageIndex += 1

    val document = httpClient.cleanAndConvertToDocument(response)
    var xpathExpr: DOMXPath = new DOMXPath(CONFERENCE_SUBPAGES_XPATH)
    val subpages = xpathExpr.selectNodes(document).
      toSet.map { node: Any => DOMAIN_URL + node.toString.drop(7).dropRight(1) } // remove 7 chars: href="/ and frol right "
    println(subpages)
    subpages
  }

  def nextConferencePages(conferenceSubpages: Set[String]): Set[ConferenceData] = {
    conferenceSubpages.foldLeft (new mutable.SetBuilder[ConferenceData, Set[ConferenceData]](Set[ConferenceData]())) {
      case (aggregator, conferenceSubpageUri) => //TODO typ przechowujacy dane o stronie
        val response = httpClient.syncDownload(conferenceSubpageUri)

        val document = httpClient.cleanAndConvertToDocument(response)
        var uriXpathExpr: DOMXPath = new DOMXPath(CONFERENCE_LINK_XPATH)
        var nameXpathExpr: DOMXPath = new DOMXPath(NAME_XPATH)
        var whenXpathExpr: DOMXPath = new DOMXPath(WHEN_XPATH)
        var whereXpathExpr: DOMXPath = new DOMXPath(WHERE_XPATH)
        var submissionXpathExpr: DOMXPath = new DOMXPath(SUBMISSION_DEADLINE_XPATH)
        var notificationXpathExpr: DOMXPath = new DOMXPath(NOTIFICATION_DUE_XPATH)
        var finalVersionXpathExpr: DOMXPath = new DOMXPath(FINAl_VERSION_DUE_XPATH)
        val conferenceUri = (uriXpathExpr.selectNodes(document)).toList.asInstanceOf[List[Node]].head.getNodeValue
        println(conferenceUri)

        val conferenceData = new ConferenceData
        conferenceData.uri = conferenceUri
        var tmp = ""
        tmp = nameXpathExpr.selectNodes(document).toList.headOption.getOrElse("").toString.drop(8).replaceAll("]+", "").trim
        if (!tmp.isEmpty) {
          conferenceData.name = tmp
        }
        tmp = whenXpathExpr.selectNodes(document).toList.headOption.getOrElse("").toString.drop(8).replaceAll("]+", "").trim
        if (!tmp.isEmpty) {
          conferenceData.date = tmp
        }
        tmp = whereXpathExpr.selectNodes(document).toList.headOption.getOrElse("").toString.drop(8).replaceAll("]`+", "").trim
        if (!tmp.isEmpty) {
          conferenceData.place = tmp
        }
        tmp = submissionXpathExpr.selectNodes(document).toList.headOption.getOrElse("").toString.drop(8).replaceAll("]+", "").trim
        if (!tmp.isEmpty) {
          conferenceData.paperSubmissionDate = tmp
        }
        tmp = notificationXpathExpr.selectNodes(document).toList.headOption.getOrElse("").toString.drop(8).replaceAll("]+", "").trim
        if (!tmp.isEmpty) {
          conferenceData.paperNotificationDate = tmp
        }
        tmp = finalVersionXpathExpr.selectNodes(document).toList.headOption.getOrElse("").toString.drop(8).replaceAll("]+", "").trim
        if (!tmp.isEmpty) {
          conferenceData.paperFinalVersionDate = tmp
        }

        aggregator += conferenceData
        aggregator
    }.result()
  }
}

object WikicfpDatasource {
  val COFERENCE_DATA_DEFAULG_FILE_NAME = "conferenceData"

  def saveConference(data: ConferenceData, directory: String, filename: String = COFERENCE_DATA_DEFAULG_FILE_NAME) {
    FileHelper.saveBinary(data)(directory + Conf.SLASH + filename)
  }

  def loadConference(directory: String, filename: String = COFERENCE_DATA_DEFAULG_FILE_NAME): ConferenceData = {
    val fileIn = new FileInputStream(directory + Conf.SLASH + filename)
    val in = new ObjectInputStream(fileIn);
    val data = in.readObject().asInstanceOf[ConferenceData];
    in.close();
    fileIn.close();
    data
  }
}