import NativePackagerKeys._

enablePlugins( JavaAppPackaging )

lazy val commonSettings = Seq(
  organization := "at.linuxhacker",
  version := "0.1",
  scalaVersion := "2.11.4",
  name := "SimpeBlockParser"
)

lazy val root = (project in file(".")).
  settings(commonSettings: _*)

mainClass in Compile := Some( "at.linuxhacker.stateTransition.test.Test" )

resolvers ++= Seq("snapshots" at "http://oss.sonatype.org/content/repositories/snapshots",
  "releases" at "http://oss.sonatype.org/content/repositories/releases",
  "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases" )



