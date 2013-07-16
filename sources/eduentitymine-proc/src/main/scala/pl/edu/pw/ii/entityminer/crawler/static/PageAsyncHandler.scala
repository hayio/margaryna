package pl.edu.pw.ii.entityminer.crawler.static

import pl.edu.pw.ii.entityminer.web.WebPage


/**
 * Created with IntelliJ IDEA.
 * User: ralpher
 * Date: 12/30/12
 * Time: 11:13 AM
 * To change this template use File | Settings | File Templates.
 */
/**
 * Interfejs dla handlera obslugujacego zdarzenia pobrania kolejnej strony www.
 * Opcja asynchroniczna.
 */
trait PageAsyncHandler
{
  /**
   * Funkcja definiujaca, co nalezy zrobic z pobrana strona.
   * @param webPage struktura zawierajaca m.in. cialo pobranej strony www.
   */
  def onNextDownloadedPage(webPage: WebPage): Unit

  /**
   * Co zrobic, gdy nie udalo sie pomyslnie sciagnac strony.
   * @param p1 argument sytuacji wyjatkowej
   */
  def onThrowable(p1: Throwable, url: String): Unit
}