package pl.edu.pw.ii.entityminer.util

import java.io._
import io.Source
import xml.XML
import org.apache.commons.io.IOUtils
import scala.Serializable

/**
 * Created by IntelliJ IDEA.
 * User: ralpher
 * Date: 3/19/11
 * Time: 6:22 PM
 * To change this template use File | Settings | File Templates.
 */

/**
 * Klasa opakowujaca operacje na plikach.
 */
class FileHelper(file: File) {

  /**
   * Dopisuje do pliku.
   * @param text tekst do dopisania.
   */
  def append(text: String): Unit = {
    val fw = new FileWriter(file, true)
    try {
      fw.write(text)
    }
    finally {
      fw.close
    }
  }

  def write(text: String): Unit = {
    val fw = new FileWriter(file, false)
    try {
      fw.write(text)
    }
    finally {
      fw.close
    }
  }

  def writeList(listToWrite: List[String]): Unit = {
    val fw = new FileWriter(file)
    try {
      listToWrite.foreach(s => fw.write(s + "\n"))
    }
    finally {
      fw.close
    }
  }

  /**
   * Dla każdej linii w pliku uruchamia podaną procedure.
   */
  def foreachLine(proc: String => Unit): Unit = {
    val br = new BufferedReader(new FileReader(file))
    try {
      while (br.ready) proc(br.readLine)
    }
    finally {
      br.close
    }
  }

  /**
   * Kasuje opakowany plik.
   */
  def deleteAll: Unit = {
    def deleteFile(dfile: File): Unit = {
      if (dfile.isDirectory) {
        val subfiles = dfile.listFiles
        if (subfiles != null) {
          subfiles.foreach {
            f => deleteFile(f)
          }
        }
      }
      dfile.delete
    }

    deleteFile(file)
  }
}

/**
 * Pomocnik do operacji na plikach.
 */
object FileHelper {

  def writeStreamToFile(in: InputStream, outFile: String) {
    var out: FileOutputStream = null
    try {
      out = new FileOutputStream(new File(outFile))
      IOUtils.copy(in, out)
    } finally {
      IOUtils.closeQuietly(in)
      IOUtils.closeQuietly(out)
    }
  }

  def saveBinary[T <: Serializable](obj: T)(filename: String) = {
    val fileOut = new FileOutputStream(filename);
    val out = new ObjectOutputStream(fileOut);
    out.writeObject(obj);
    out.close();
    fileOut.close();
  }

  def getResource(filename: String) = {
    val is = getResourceStream(filename)
    Source.fromInputStream(is, "utf-8")
  }

  def getResourceAsXml(filename: String) = {
    println("filehelper filename: " + filename)
    val fileXml = XML.load(this.getClass.getResourceAsStream(filename))
    assert(fileXml.isInstanceOf[scala.xml.Elem])
    fileXml
  }

  def getResourceStream(filename: String) = {
    val res = if (filename.startsWith("/")) {
      filename
    }
    else {
      "/" + filename
    }
    this.getClass.getResourceAsStream(res)
  }


}