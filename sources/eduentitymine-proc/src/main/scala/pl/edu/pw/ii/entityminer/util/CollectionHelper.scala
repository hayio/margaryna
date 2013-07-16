package pl.edu.pw.ii.entityminer.util

import collection.mutable.ListBuffer

/**
 * Created by IntelliJ IDEA.
 * User: ralpher
 * Date: 03.08.11
 * Time: 16:07
 * To change this template use File | Settings | File Templates.
 */

/**
 * Obiekt wykonujący działania na kolekcjach, które są często wykonywane,
 * a nie ma ich w standardowej bibliotece.
 */
object CollectionHelper {
    /**
     * Zippuje dwie listy, przy czym, jeżeli są one różnej długości,
     * to brakujące elementy dla tej dłuższej uzupełniane są odpowiednimi wartościami domyślnymi.
     * @param default1 domyślna wartość zippowania, jeżeli elementy z pierwszej krótszej kolekcji zostaną wyczerpane.
     */
    def zipWithDefault[A, B](l1 : List[A], l2 : List[B], default1: A, default2: B) : List[Tuple2[_, _]] = {
        def zipHelper (l1$ : List[_], l2$ : List[_], acc : List[Tuple2[_, _]]) : List[Tuple2[_, _]] = l1$ match {
            case Nil => l2$ match {
                case Nil  => acc reverse
                case l2$head :: l2$tail =>
                    zipHelper(l1$, l2$.tail, Tuple2(default1, l2$head) :: acc)
            }
            case l1$head :: l1$tail => l2$ match {
                case Nil =>
                    zipHelper(l1$tail, l2$, Tuple2(l1$head, default2) :: acc)
                case l2$head :: l2$tail =>
                    zipHelper(l1$tail, l2$.tail, Tuple2(l1$head, l2$.head) :: acc)
            }
        }
        zipHelper(l1, l2, List[Tuple2[_, _]]())
    }

    /**
     * Tak samo jak powyższa, z tym że jest jedna wartość domyślna dla obydwu kolekcji.
     */
    def zipWithDefault[A](l1 : List[A], l2 : List[A], default: A) : List[Tuple2[_, _]] = zipWithDefault(l1, l2, default, default)

    /**
     * Łączy listę map w jedną, wykonując podanę metodę na wartościach takich samych elementów.
     */
    def mergeMap[A, B](ms: List[Map[A, B]])(f: (B, B) => B): Map[A, B] =
        (Map[A, B]() /: (for (m <- ms; kv <- m) yield kv)) { (a, kv) =>
            a + (if (a.contains(kv._1)) kv._1 -> f(a(kv._1), kv._2) else kv)
        }

    /**
     * Łączy listę map w zbiór kluczy tych map.
     */
    def mergeMapsToKeySet[A, B](ms: List[Map[A, B]]): Set[A] =
        (Set[A]() /: (for (m <- ms; kv <- m) yield kv)) { (a, kv) =>
            a + kv._1
        }

    /**
     * Zwraca listy wejściowe z usuniętymi elementami, które wcześniej były zawarte w obydwu.
     */
    def disjunctiveSequences(seqs1: List[List[String]], seqs2: List[List[String]]): (List[List[String]], List[List[String]]) = {
        val (buf1, buf2) = seqs1.foldLeft((new ListBuffer[List[String]](), seqs2.toBuffer)) {
            case ((buffer1, buffer2), seq1) =>
                var isDisjunctive = true
                val iter2 = seqs2.iterator
                var done = false
                var startedComparing = false
                while( iter2.hasNext && !done ){
                    val seq2 = iter2.next()
                    if (seq2.size != seq1.size && startedComparing) { //taki myk
                        done = true
                    }
                    else {
                        startedComparing = true
                        if (seq1 == seq2) {
                            buffer2 -= seq2
                            println("to sie powtarza: " + seq1)
                            isDisjunctive = false
                        }
                    }
                }
                if (isDisjunctive) {
                    buffer1 += seq1
                }
            (buffer1, buffer2)
        }
        (buf1.result, buf2.toList)
    }
}