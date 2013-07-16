package pl.edu.pw.ii.entityminer.web

trait Text {
    val text: String

  /**
   * Frequency of terms in this text fragment.
   */
    lazy val terms: FreqBagOf[String] = {
        def createWordsMap(words: List[String]) = {
            var resultMap = Map[String, Int]()
            //@TODO sprawdzic co zmnienia to w moim projekcie wydobywania imion
            words.foreach{
//            words.map(w => w.toLowerCase()).foreach{
                nextWord => {
                    resultMap.get(nextWord) match {
                        case Some(occurances) =>
                            resultMap += (nextWord -> (occurances + 1))
                        case None =>
                            resultMap += (nextWord -> 1)
                    }
                }
            }
            resultMap
        }
        new FreqBagOf[String](createWordsMap(words))
    }

    lazy val words: List[String] = text.replaceAll("<[^>]*>", " ").split(Text.whitespaces).toList.
                        filterNot(_.equals("")).
                        filterNot(Text.stopwords.contains).
                        filterNot( x => x.size < Text.minWordLenght )
}

object Text {
    val stopwords = Set("of", "the", "and", "with", "this", "have", "that", "also", "which", "other", "some", "here", "it's", "than", "will", "were", "when", "it", "in", "for", "is", "on", "to")
    val whitespaces = "\\W+"
    val minWordLenght = 2
}