package pl.edu.pw.ii.entityminer

import gate._
import java.io.{OutputStreamWriter, BufferedOutputStream, FileOutputStream, File}
import collection.JavaConversions._

/**
 * Przyklad wziety ze stron:
 * http://www.wikicfp.com/cfp/servlet/event.showcfp?eventid=29810&copyownerid=49152
 * http://www.cumulusdublin.com/
 * http://www.cumulusdublin.com/call.html
 */
object Main {
  Gate.runInSandbox(true) // gate nie wymaga ustawiania home itp., jesli to nie jest konieczne
  Gate.setGateHome(new File("data/"))
  Gate.init()

	def main(args : Array[String]) = {
    showAnnotations("data/Cumulus Dublin 2013 - call for papers.anno.xml")
    automateAnnotate("data/Cumulus Dublin 2013 - main.htm")
	}

  /**
   * Czytanie annotacji z pliku z annotacjami
   * @param filePath
   */
  def showAnnotations(filePath: String) = {
    val file = new File(filePath)
    println(file.toURI.toURL)
    val document = Factory.newDocument(file.toURI.toURL, "UTF-8") // dokument otagggwany
    val annotationSet = document.getAnnotations // annotacje zdefiniowane przez uzytkownika

    val annotTypesRequired = new java.util.HashSet[String]()
    annotTypesRequired.add("Submission Deadline") // filtr annotacji, ktore chcemy wyswietlic
    val annos = new java.util.HashSet[Annotation](annotationSet.get(annotTypesRequired))
//    val annos = new util.HashSet[Annotation](annotationSet.get()); // mozemy takze wyswietlic wszystkie annotacje
    annos.foreach { annot =>
      println("wartosc kolejnej annotacji " + Utils.contentFor(document, annot))
    }
  }

  /**
   *Annotowanie programowe
   * @param filePath
   */
  def automateAnnotate(filePath: String) = {
    val file = new File(filePath)
    println(file.toURI.toURL)
    val document = Factory.newDocument(file.toURI.toURL, "UTF-8")
    println("fast info " + " " + document.getCollectRepositioningInfo + " " + document.getMarkupAware + " " + document.getPreserveOriginalContent + " " + document.getSourceUrl)
    val annotationSet = document.getAnnotations

    // na pewno sa lepsze metody szukania i analizy dokumentu
    val whenInfo = "7-9 November 2013"
    var idx0 = document.getContent.toString.indexOf(whenInfo)
    annotationSet.add(idx0, idx0 + whenInfo.size, "When", null)

    //zapis do pliku
    val outputFileName = document.getName + ".anno.xml"
    val outputFile = new File(file.getParentFile, outputFileName)
    val fos = new FileOutputStream(outputFile)
    val bos = new BufferedOutputStream(fos)
    val out = new OutputStreamWriter(bos, "UTF-8")
    out.write(document.toXml)
    out.close()
  }

}