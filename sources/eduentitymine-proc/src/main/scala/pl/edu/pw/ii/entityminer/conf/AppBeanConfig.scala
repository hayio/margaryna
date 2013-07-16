package pl.edu.pw.ii.entityminer.conf

import org.springframework.context.annotation.{Bean, Configuration}
import pl.edu.pw.ii.entityminer.crawler.static.{WebpageAsyncHttpClient, HttpClient}

/**
 * Created with IntelliJ IDEA.
 * User: Rafael Hazan
 * Date: 7/2/13
 * Time: 12:25 AM
 */
@Configuration
class AppBeanConfig {

  @Bean
  def HttpClient() : HttpClient = {
    return new WebpageAsyncHttpClient
  }

}
