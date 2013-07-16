package pl.edu.pw.ii

import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.context.ApplicationContext
import pl.edu.pw.ii.entityminer.conf.AppBeanConfig

/**
 * Created with IntelliJ IDEA.
 * User: Rafael Hazan
 * Date: 7/2/13
 * Time: 12:24 AM
 */
package object entityminer {
  val ctx: ApplicationContext = new AnnotationConfigApplicationContext(classOf[AppBeanConfig])
}
