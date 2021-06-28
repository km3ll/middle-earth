import SbtCommon._
import scoverage.ScoverageKeys

name := "core"
version := "0.1"

libraryDependencies ++= Seq(
  "org.scalatest"   %%  "scalatest" % "3.2.2"
)

// compiler
scalacOptions ++= commonOptions

// plugins
coverageEnabled := true
coverageMinimumStmtTotal := 90
coverageExcludedPackages := ".*CoreMain.*;"