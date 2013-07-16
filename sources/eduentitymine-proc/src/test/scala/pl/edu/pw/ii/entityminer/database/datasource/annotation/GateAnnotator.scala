package pl.edu.pw.ii.entityminer.database.datasource.annotation

import gate._
import java.io.{OutputStreamWriter, BufferedOutputStream, FileOutputStream, File}
import java.util
import collection.JavaConversions._

/**
 * Created with IntelliJ IDEA.
 * User: ralpher
 * Date: 7/15/13
 * Time: 11:11 PM
 * To change this template use File | Settings | File Templates.
 */
class GateAnnotator {
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
    val outputFileName = document.getName() + "_anno.xml";
    val outputFile = new File(file.getParentFile(), outputFileName);
    val fos = new FileOutputStream(outputFile);
    val bos = new BufferedOutputStream(fos);
    val out = new OutputStreamWriter(bos, "UTF-8");
    out.write(document.toXml());
    out.close();
  }

  def showAnnotations(filePath: String) = {
    val file = new File(filePath)
    println(file.toURI.toURL)
    val document = Factory.newDocument(file.toURI.toURL, "UTF-8") // dokument otagggwany
    val annotationSet = document.getAnnotations // annotacje zdefiniowane przez uzytkownika

    val annotTypesRequired = new util.HashSet[String]();
    annotTypesRequired.add("Submission Deadline"); // filtr annotacji, ktore chcemy wyswietlic
    val annos = new util.HashSet[Annotation](annotationSet.get(annotTypesRequired));
    //    val annos = new util.HashSet[Annotation](annotationSet.get()); // mozemy takze wyswietlic wszystkie annotacje
    annos.foreach { annot =>
      println("wartosc kolejnej annotacji " + Utils.contentFor(document, annot))
    }
  }
}
