package pl.edu.pw.ii.entityminer.database.datasource.pagestorage

import org.scalatest.matchers.ShouldMatchers
import org.scalatest.FlatSpec
import pl.edu.pw.ii.entityminer.database.datasource.wikicfp.WikicfpDatasource

/**
  * Created with IntelliJ IDEA.
  * User: Rafael Hazan
  * Date: 7/2/13
  * Time: 11:46 PM
  */
class PageStorageSpec extends FlatSpec with ShouldMatchers {

   "Conferece web pages" should "be saved in test directory with its subpages." in {
     val scrapper = new WikicfpDatasource
     scrapper.setUp()
     val conferenceSubpages = scrapper.nextPage()
     val scrappedData = scrapper.nextConferencePages(conferenceSubpages)
     val pageSaver = new PageDirectoryCollector
     pageSaver.collectPages(scrappedData, "data/test/pagestorage")
   }

  "It" should "read manifest." in {
    val pageSaver = new PageDirectoryCollector
    val manifest = pageSaver.readPageManifest("data/test/pagestorage/0")
    println(manifest.result())
  }
 }
