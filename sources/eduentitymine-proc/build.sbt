name := "eduentitymine-proc"

resolvers += "SpringSource Repository" at "http://repo.springsource.org/release"

resolvers += "Spring Maven Release Repository" at "http://repo.springsource.org/libs-release"

resolvers += "Artifactory on Wizzar server accessed via ssh tunnel" at "http://127.0.0.1:8081/artifactory/repo"

pomExtra := (
    <distributionManagement>
        <repository>
            <id>releases@wizzar.ii.pw.edu.pl</id>
            <name>Artifactory at Wizaar server</name>
            <url>http://127.0.0.1:8081/artifactory/ext-release-local</url>
        </repository>
        <snapshotRepository>
            <id>snapshots@wizzar.ii.pw.edu.pl</id>
            <name>Artifactory at Wizzar server</name>
            <url>http://127.0.0.1:8081/artifactory/ext-snapshot-local</url>
        </snapshotRepository>
    </distributionManagement>
)

libraryDependencies ++= Seq(
	"org.mongodb" % "mongo-java-driver" % "2.9.1",
	"org.springframework.data" % "spring-data-mongodb" % "1.0.3.RELEASE",
	"com.ning" % "async-http-client" % "1.7.5",
	"joda-time" % "joda-time" % "2.1",
	"org.joda" % "joda-convert" % "1.2",
	"nz.ac.waikato.cms.weka" % "weka-stable" % "3.6.9",
	"uk.ac.gate" % "gate-core" % "7.1",
	"cc.mallet" % "mallet" % "2.0.7",
	"tw.edu.ntu.csie" % "libsvm" % "3.1",
	"commons-logging" % "commons-logging" % "1.1.1",
	"net.sourceforge.htmlunit" % "htmlunit" % "2.11",
	"jaxen" % "jaxen" % "1.1.4",
    "net.sourceforge.htmlcleaner" % "htmlcleaner" % "2.2",
	"org.scalatest" %% "scalatest" % "1.6.1" % "test"
)

libraryDependencies ++= Seq("spring-core", "spring-context", "spring-aop", "spring-expression") map ("org.springframework" % _ % "3.2.0.RELEASE")