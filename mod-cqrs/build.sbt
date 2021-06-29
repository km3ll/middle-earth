import SbtCommon._

name := "cqrs"
version := "0.1"

libraryDependencies ++= Seq(

  "org.typelevel"               %%  "cats-core"       % "2.1.1",
  "com.typesafe"                %   "config"          % "1.3.2",
  "ch.qos.logback"              %   "logback-classic" % "1.3.0-alpha4",
  "com.typesafe.scala-logging"  %%  "scala-logging"   % "3.9.2",
  "org.scalatest"               %%  "scalatest"       % "3.2.2"
)

// compiler
scalacOptions ++= commonOptions

// plugins
coverageEnabled := false