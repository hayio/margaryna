package pl.edu.pw.ii.entityminer.database.datasource.pagestorage

import pl.edu.pw.ii.entityminer.database.datasource.{ConferenceData, WebDatasource}
import java.io.{ObjectInputStream, FileInputStream, File}
import pl.edu.pw.ii.entityminer
import pl.edu.pw.ii.entityminer.crawler.static.HttpClient
import pl.edu.pw.ii.entityminer.util.{NetHelper, FileHelper}
import scala.collection.JavaConversions.iterableAsScalaIterable
import pl.edu.pw.ii.entityminer.Conf
import org.jaxen.dom.DOMXPath
import org.w3c.dom.Node
import scala.collection.mutable
import java.net.{URISyntaxException, URI}
import pl.edu.pw.ii.entityminer.database.datasource.wikicfp.WikicfpDatasource

/**
 * Created with IntelliJ IDEA.
 * User: Rafael Hazan
 * Date: 7/4/13
 * Time: 3:50 PM
 */
class PageDirectoryCollector {
  val INDEX = "0.html"
  val MANIFEST = "pageManifest"

  val httpClient = entityminer.ctx.getBean(classOf[HttpClient])

  def collectPages(data: Set[ConferenceData], outDir: String) {
    data.foldLeft(0) {
      case (iter, conferenceData) =>
        val outDirForWebPage = outDir + Conf.SLASH + iter.toString
        (new File(outDirForWebPage)).mkdirs();
        WikicfpDatasource.saveConference(conferenceData, outDirForWebPage)
        var subpageIter = 1

        val conferenceDomainName = NetHelper.getDomainName(conferenceData.uri)
        val response = httpClient.syncDownload(NetHelper.composeUri(conferenceDomainName, conferenceData.uri))
        FileHelper.writeStreamToFile(response.getResponseBodyAsStream, outDirForWebPage + Conf.SLASH + INDEX)
        val mainPage = httpClient.cleanAndConvertToDocument(response)
        var subpageNodes = (new DOMXPath("//a")).selectNodes(mainPage).toList.asInstanceOf[List[Node]]

        val manifestBuilder = PageDirectoryManifest.getManifestBuilder()
        PageDirectoryManifest.addToBuilder(manifestBuilder, (conferenceData.uri -> (Set(INDEX), INDEX)))

        subpageNodes.foreach { case subpageNode =>
          if (subpageNode.getAttributes.getNamedItem(NetHelper.HREF_ATTRIBUTE) != null) {
            try {
              val subpageUri = subpageNode.getAttributes.getNamedItem(NetHelper.HREF_ATTRIBUTE).getNodeValue
              val subpageLabel = subpageNode.getTextContent
              val subpageDomainName = NetHelper.getDomainName(subpageUri)

              if (! fillManifestIfItContainsUri(manifestBuilder, subpageUri, subpageLabel)) {
                if (conferenceDomainName == subpageDomainName ||
                    subpageUri.startsWith(Conf.SLASH)) {
                  FileHelper.writeStreamToFile(httpClient.syncDownload(NetHelper.composeUri(conferenceDomainName, subpageUri)).
                    getResponseBodyAsStream, outDirForWebPage + Conf.SLASH + subpageIter + NetHelper.HTML_SUFFIX)
                  PageDirectoryManifest.addToBuilder(manifestBuilder, (subpageUri -> (Set(subpageLabel), subpageIter + NetHelper.HTML_SUFFIX)))
                  subpageIter += 1
                }
              }
            } catch {
              case e: URISyntaxException =>
                e.printStackTrace()
            }
          }
        }
        val resManifest = PageDirectoryManifest.build(manifestBuilder)
        FileHelper.saveBinary(resManifest)(outDirForWebPage + Conf.SLASH + MANIFEST)

        iter + 1
    }
  }

  def readPageManifest(mainDir: String) = {
    val fileIn = new FileInputStream(mainDir + Conf.SLASH + MANIFEST)

    val in = new ObjectInputStream(fileIn);
    val pageManifest = in.readObject().asInstanceOf[PageDirectoryManifest];
    in.close();
    fileIn.close();

    pageManifest
  }

  private def fillManifestIfItContainsUri(manifest: PageDirectoryManifest.Builder, subpageUri: String, label: String): Boolean = {
    println("normalizacja adresu " + subpageUri + " na " + NetHelper.strictNormalieUri(subpageUri))
    manifest.result().foreach { case (uri: String, (labels: Set[String], localUri: String)) =>
      if (URI.create(NetHelper.strictNormalieUri(subpageUri)) == URI.create(NetHelper.strictNormalieUri(uri))) {
        PageDirectoryManifest.addToBuilder(manifest, (uri -> (labels + label, localUri)))
        return true
      }
    }
    false
  }
}
