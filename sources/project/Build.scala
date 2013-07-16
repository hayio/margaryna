import sbt._

object EduEndtityMineBuild extends Build {

  lazy val root = 
    Project("eduentitymine", file(".")) aggregate(eduentitymine-proc, eduentitymine-view)
			
  lazy val eduentitymine-proc = 
    Project("eduentitymine-proc", file("eduentitymine-proc")) 
	
  lazy val eduentitymine-view = 
    Project("eduentitymine-view", file("eduentitymine-view")) dependsOn(eduentitymine-proc)
}
