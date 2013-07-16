package pl.edu.pw.ii.entityminer.database.datasource.annotation

import org.scalatest.matchers.ShouldMatchers
import org.scalatest.FlatSpec
import pl.edu.pw.ii.entityminer.database.datasource.wikicfp.WikicfpDatasource

/**
  * Created with IntelliJ IDEA.
  * User: Rafael Hazan
  * Date: 7/2/13
  * Time: 11:46 PM
  */
class PageAnnotationSpec extends FlatSpec with ShouldMatchers {

   "Conferece web pages" should "be properly annotated." in {
     val annotator = new WebPageAnnotator
     annotator.annotatePageInDirectory("data\\test\\pagestorage\\0\\")
   }
 }
