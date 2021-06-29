import SbtCommon._

name := "scaffeine"
version := "0.1"

libraryDependencies ++= Seq(
  "org.scalatest"       %%  "scalatest" % "3.2.2",
  "com.github.blemale"  %% "scaffeine"  % "4.0.2"
)

// compiler
scalacOptions ++= commonOptions

// plugins
coverageEnabled := false