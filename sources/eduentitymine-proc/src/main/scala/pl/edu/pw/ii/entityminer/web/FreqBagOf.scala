package pl.edu.pw.ii.entityminer.web

import pl.edu.pw.ii.entityminer.util.CollectionHelper


/**
 * Created by IntelliJ IDEA.
 * User: ralpher
 * Date: 13.07.11
 * Time: 14:40
 * To change this template use File | Settings | File Templates.
 */

/**
 * Model dokumentu TF. Może także reprezentować model binarny, a także model zbiorczy wielu dokumentów.
 */
class FreqBagOf[T](val words: Map[T, Int]) {
    /**
     * Zwraca liczbe slow dokumentu.
     */
    def getWordSize = {
        words.foldLeft(0){
            case (numberAccumulator, (item, frequency)) =>
                numberAccumulator + frequency
        }
    }

    /**
     * Zwraca unie tego modelu i drugiego wejściowego.
     */
    def union(otherBag: FreqBagOf[T]): FreqBagOf[T] = {
        new FreqBagOf(CollectionHelper.mergeMap(List[Map[T, Int]](words, otherBag.words))( (a, b) => a + b))
    }

    /**
     * Serializuje model do obiekut XML.
     */
    def toXml = {
        <items>
        {for(wordTuple <- words) yield
        <itemTuple>
            <item>{wordTuple._1}</item>
            <frequency>{wordTuple._2}</frequency>
        </itemTuple>
        }
        </items>
    }

    /**
     * Zwraca odpowiadający temu model binarny.
     */
    def flatFreqs(): FreqBagOf[T] = {
        new FreqBagOf[T](words.mapValues( value => 1 ))
    }
}

/**
 * Obiekt fabryka modelu FreqBagOf.
 */
object FreqBagOf {
    /**
     * Zamienia tekst na model.
     */
    def toFreqBag(text: String) = new FreqBagOf(text.split("\\W+").toSet[String].map( x => (x, 1) ).toMap)

    def toFreqBag(seqText: List[String]) = new FreqBagOf(seqText.toSet[String].map( x => (x, 1) ).toMap)
}

