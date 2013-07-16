package pl.edu.pw.ii.entityminer.database.datasource.pagestorage

import scala.collection.mutable

/**
 * Created with IntelliJ IDEA.
 * User: ralpher
 * Date: 7/8/13
 * Time: 12:01 AM
 * To change this template use File | Settings | File Templates.
 */
class PageDirectoryManifest(manifest: PageDirectoryManifest.ManifestType) extends Serializable {
  def result() = manifest
}

object PageDirectoryManifest {
  type Builder = mutable.MapBuilder[String, (Set[String], String), PageDirectoryManifest.ManifestType]
  /** Map uri -> set of A tag labels and uri on local disk*/
  type ManifestType = Map[String, (Set[String], String)]

  def getManifestBuilder(): Builder = new mutable.MapBuilder[String, (Set[String], String), ManifestType](Map[String, (Set[String], String)]())

  def addToBuilder(manifestBuilder: Builder, tuple: (String, (Set[String], String))) = {
    manifestBuilder += tuple
    manifestBuilder
  }

  def build(manifestBuilder: Builder) = new PageDirectoryManifest(manifestBuilder.result())
}
