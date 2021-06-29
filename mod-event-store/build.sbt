import SbtCommon._

name := "eventstore"
version := "0.1"

libraryDependencies ++= Seq(
  "org.typelevel" %%  "cats-core" % "2.1.1",
  "org.scalatest" %%  "scalatest" % "3.2.2"
)

// compiler
scalacOptions ++= commonOptions

// plugins
coverageEnabled := false