import sbt._
import Keys._

object SbtSettings {

  val domainDependencies: Seq[ModuleID] = Seq(
    "org.typelevel"               %% "cats-effect"    % "2.0.0" withSources () withJavadoc (),
    "com.typesafe"                %  "config"         % "1.3.2",
    "org.scalacheck"              %% "scalacheck"     % "1.14.0",
    "org.scalatest"               %% "scalatest"      % "3.0.5",
    "com.typesafe.scala-logging"  %% "scala-logging"  % "3.9.2"
  )

  val persistenceDependencies: Seq[ModuleID] = Seq(
    "org.cassandraunit"           %  "cassandra-unit" % "3.1.3.2",
    "org.typelevel"               %% "cats-effect"    % "2.0.0" withSources () withJavadoc (),
    "com.typesafe"                %  "config"         % "1.3.2",
    "com.h2database"              %  "h2"             % "1.4.189",
    "ch.qos.logback"              % "logback-classic" % "1.1.3",
    "io.monix"                    %% "monix"          % "2.3.3",
    "com.outworkers"              %% "phantom-dsl"    % "2.31.0",
    "com.outworkers"              %% "phantom-jdk8"   % "2.31.0",
    "org.scalacheck"              %% "scalacheck"     % "1.14.0",
    "org.scalatest"               %% "scalatest"      % "3.0.5",
    "com.typesafe.scala-logging"  %% "scala-logging"  % "3.9.2",
    "com.typesafe.slick"          %% "slick"          % "3.3.0"
  )
  
  val compilerOptions: Seq[String] = Seq(
    "-deprecation",
    "-encoding",
    "UTF-8",
    "-feature",
    "-language:existentials",
    "-language:higherKinds",
    "-language:implicitConversions",
    "-language:experimental.macros",
    "-unchecked",
    "-Xfatal-warnings",
    "-Xlint",
    "-Yno-adapted-args",
    "-Ypartial-unification",
    "-Ywarn-dead-code",
    "-Ywarn-numeric-widen",
    "-Ywarn-value-discard",
    "-Ywarn-unused-import",
    "-Xfuture"
  )

}
