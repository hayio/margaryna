package pl.edu.pw.ii.entityminer.database.datasource.annotation

import pl.edu.pw.ii.entityminer.database.datasource.pagestorage.PageDirectoryCollector
import pl.edu.pw.ii.entityminer.database.datasource.wikicfp.WikicfpDatasource

/**
 * Created with IntelliJ IDEA.
 * User: ralpher
 * Date: 7/13/13
 * Time: 6:20 PM
 * To change this template use File | Settings | File Templates.
 */
class WebPageAnnotator {
  val pageCollector = new PageDirectoryCollector

      def annotatePageInDirectory(directory: String) = {
        val manifest = pageCollector.readPageManifest(directory)
        val conferenceData = WikicfpDatasource.loadConference(directory)

        println("manifest " + manifest.result())
        println("data " + conferenceData.place)
      }
}
