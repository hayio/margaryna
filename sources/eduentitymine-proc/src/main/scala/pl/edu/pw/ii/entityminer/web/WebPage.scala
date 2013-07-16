package pl.edu.pw.ii.entityminer.web

import java.util.regex.Matcher

/**
 * Created by IntelliJ IDEA.
 * User: ralpher
 * Date: 3/23/11
 * Time: 8:37 PM
 * To change this template use File | Settings | File Templates.
 */

class WebPage(val url: String, val content: String) extends Text {

  override def toString = "WebPage(" + url.toString + ")"

  override def hashCode = url.hashCode

  override def equals(other: Any) = other.isInstanceOf[WebPage] && other.asInstanceOf[WebPage].content == content

  lazy val title: String = {
    //TODO
    "some title"
  }

  lazy val body = {
    def lowerTags(s: String): String =
      "<[^>]*>".r.replaceAllIn(s, x => Matcher.quoteReplacement(x.toString.toLowerCase))

    def removeScripts(s: String, sb: StringBuilder = new StringBuilder): String = {
      val scriptStartPattern = "<script[^>]*>".r
      val scriptEndPattern = "</script>".r
      val start = scriptStartPattern.findFirstMatchIn(s)
      val end = scriptEndPattern.findFirstMatchIn(s)
      if (start.isDefined && end.isDefined) {
        sb.append(s.substring(0, start.get.start))
        removeScripts(s.substring(end.get.end), sb)
      } else if (start.isDefined) {
        sb.append(s.substring(0, start.get.start))
        sb.toString
      } else {
        sb.append(s)
        sb.toString
      }
    }

    def cleanBody(content: String) = {
      val c2 = "&[a-z]+;".r.replaceAllIn(content,
        x => WebPage.entities.getOrElse(x.toString, Matcher.quoteReplacement(x.toString)))
      val c3 = "&#([0-9]+);".r.replaceAllIn(c2,
        x => Matcher.quoteReplacement(Integer.parseInt(x.group(1)).asInstanceOf[Char].toString))

      c3.replaceAll("\\s+", " ")
        .replaceAll("<br/>|<br>|</tr>", "\n")
        //            .replaceAll("<[^>]*>", " ")
        .replaceAll(" +", " ")
        .replaceAll("( *\n+ *)+", "\n")
        .trim
    }

    //@TODO co znienia brak to lower case w wydobywaniu imion?
    val lowerContent = content.toLowerCase //lowerTags(content)
    //        val lowerContent = lowerTags(content)
    val noScriptContent = removeScripts(lowerContent)
    val bodyStartPattern = "<body[^>]*>".r
    val bodyEndPattern = "</body>".r
    val match1 = bodyStartPattern.findFirstMatchIn(noScriptContent)
    val match2 = noScriptContent.lastIndexOf("</body>")
    val body1 = {
      if (match1.isDefined && match2 != -1) //match2.isDefined)
        noScriptContent.substring(match1.get.end, match2) //match2.get.start)
      else if (match1.isDefined)
        noScriptContent.substring(match1.get.end)
      else
        ""
    }
    cleanBody(body1)
  }

  lazy val text = body
}

object WebPage {
  def apply(url: String, content: String) = new WebPage(url, content)

  def apply(content: String) = new WebPage("", content)

  lazy val entities = Map(
    "&quot;" -> "\"",
    "&amp;" -> "&",
    "&lt;" -> "<",
    "&gt;" -> ">",
    "&raquo;" -> ">>",
    "&laquo;" -> "<<",
    "&nbsp;" -> " ")
}