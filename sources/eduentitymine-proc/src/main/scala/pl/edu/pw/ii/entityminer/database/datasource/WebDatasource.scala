package pl.edu.pw.ii.entityminer.database.datasource

/**
 * Created with IntelliJ IDEA.
 * User: Rafael Hazan
 * Date: 7/1/13
 * Time: 9:09 PM
 */
trait WebDatasource {
  def nextConferencePages(conferenceSubpages: Set[String]): Set[ConferenceData]
}
