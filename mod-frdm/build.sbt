import SbtCommon._

name := "frdm"
version := "0.1"

libraryDependencies ++= Seq(
  "org.typelevel"               %%  "cats-core"       % "2.1.1",
  "com.typesafe"                %   "config"          % "1.3.2",
  "com.h2database"              %   "h2"              % "1.4.189",
  "ch.qos.logback"              %   "logback-classic" % "1.3.0-alpha4",
  "org.scalacheck"              %%  "scalacheck"      % "1.14.1",
  "org.scalatest"               %%  "scalatest"       % "3.2.2",
  "com.typesafe.scala-logging"  %%  "scala-logging"   % "3.9.2",
  "com.typesafe.slick"          %%  "slick"           % "3.3.0"
)

// compiler
scalacOptions ++= commonOptions