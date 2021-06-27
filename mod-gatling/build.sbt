import SbtCommon._

name := "gatling"
version := "0.1"

libraryDependencies ++= Seq(
  "io.gatling.highcharts" %  "gatling-charts-highcharts"  % "2.3.1",
  "io.gatling"            %  "gatling-test-framework"     % "2.3.1",
  "org.scalatest"         %% "scalatest"                  % "3.2.2"
)

// compiler
scalacOptions ++= commonOptions

//addSbtPlugin("io.gatling" % "gatling-sbt" % "3.2.1")
//enablePlugins( GatlingPlugin )