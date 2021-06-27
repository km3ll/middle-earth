import SbtCommon._

name := "core"
version := "0.1"

libraryDependencies ++= Seq(
  "org.scalatest"       %%  "scalatest" % "3.2.2"
)

// compiler
scalacOptions ++= commonOptions

addSbtPlugin("org.scalariform" % "sbt-scalariform" % "1.8.2")
addSbtPlugin("org.scoverage" % "sbt-scoverage" % "1.6.1")

//FIXME
//addCommandAlias( "me", "clean ; compile ; test:compile ; coverage ; test ; coverageReport")

// coverage
//coverageEnabled := true
//coverageMinimum := 90
//coverageExcludedPackages := ".*AkkaHttpMain.*;.*Book.*;.*BookProto.*;"