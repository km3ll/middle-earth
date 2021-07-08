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

  val httpDependencies: Seq[ModuleID] = Seq(
    "com.typesafe.akka"           %% "akka-actor-typed"   % "2.6.8",
    "com.typesafe.akka"           %% "akka-stream"        % "2.6.8",
    "com.typesafe.akka"           %% "akka-http"          % "10.2.4",
    "de.heikoseeberger"           %% "akka-http-circe"    % "1.20.1",
    "com.typesafe.akka"           %% "akka-http-testkit"  % "10.2.4",
    "io.circe"                    %% "circe-core"         % "0.9.3",
    "io.circe"                    %% "circe-generic"      % "0.9.3",
    "io.circe"                    %% "circe-parser"       % "0.9.3",
    "io.circe"                    %% "circe-java8"        % "0.9.3",
    "com.typesafe"                %  "config"             % "1.3.2",
    "ch.qos.logback"              % "logback-classic"     % "1.1.3",
    "com.typesafe.scala-logging"  %% "scala-logging"      % "3.9.2"
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
