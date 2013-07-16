package pl.edu.pw.ii.entityminer.database.datasource.wikicfp

import org.scalatest.matchers.ShouldMatchers
import org.scalatest.FlatSpec

/**
 * Created with IntelliJ IDEA.
 * User: Rafael Hazan
 * Date: 7/2/13
 * Time: 11:46 PM
 */
class WikicfpSpec extends FlatSpec with ShouldMatchers {

  "Wikicfp" should "extract links from page 1" in {
    val scrapper = new WikicfpDatasource
    scrapper.setUp()
    val conferenceSubpages = scrapper.nextPage()
    val scrappedData = scrapper.nextConferencePages(conferenceSubpages)

    println(scrappedData)
  }
}
