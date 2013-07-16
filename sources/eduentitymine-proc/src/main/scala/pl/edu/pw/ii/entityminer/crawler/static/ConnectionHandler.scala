package pl.edu.pw.ii.entityminer.crawler.static

import com.ning.http.client.AsyncHandler.STATE
import com.ning.http.client._

/**
 * Created by IntelliJ IDEA.
 * User: ralpher
 * Date: 27.11.11
 * Time: 09:01
 * To change this template use File | Settings | File Templates.
 */

/**
 * Handler odpowiedzi HttpAsyncDownloadera.
 * Definiuje zachowanie: jeśli plik jest za duży to należy zakończyć ściąganie.
 */
class ConnectionHandler extends AsyncHandler[Response]{
    val max: Long = 512 * 1024
    var sum: Long = 0
    val builder: Response.ResponseBuilder = new Response.ResponseBuilder

    def onThrowable(p1: Throwable) =
    {
    }

    def onBodyPartReceived(content: HttpResponseBodyPart) =
    {
        sum += content.getBodyPartBytes.size
        if(sum > max) {
//            println("za duzy plik!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
            STATE.ABORT
        }
        else {
            builder.accumulate(content)
            STATE.CONTINUE
        }
    }

    def onStatusReceived(status: HttpResponseStatus) =
    {
        builder.accumulate(status)
        STATE.CONTINUE
    }

    def onHeadersReceived(headers: HttpResponseHeaders) =
    {
        builder.accumulate(headers)
        STATE.CONTINUE
    }

    def onCompleted =
    {
        val response: Response = builder.build
        response
    }
}