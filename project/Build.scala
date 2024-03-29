import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "CodeStory-scala"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    // Add your project dependencies here,
    anorm,
    "org.codehaus.groovy" % "groovy" % "2.0.6"
  )


  val main = play.Project(appName, appVersion, appDependencies).settings(
    // Add your own project settings here      
  )

}
