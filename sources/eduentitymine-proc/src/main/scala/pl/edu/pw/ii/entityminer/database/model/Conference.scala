package pl.edu.pw.ii.entityminer.database.model

import org.springframework.data.mongodb.core.mapping.{DBRef, Document}
import org.bson.types.ObjectId

/**
 * Created with IntelliJ IDEA.
 * User: ralpher
 * Date: 4/18/13
 * Time: 9:45 PM
 * To change this template use File | Settings | File Templates.
 */
@Document
case class Conference(
                       name: String,
                       uri: String,
                       @DBRef
                       source: DataWebSource,
                       category: String,
                       when: String,
                       where: String,
                       submissionDeadline: String,
                       notificationDue: String,
                       finalVersionDue: String
                       ) {
  private var _id: ObjectId = _
}
