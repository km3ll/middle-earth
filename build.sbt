name := "khazad-dum"
version := "0.1"
scalaVersion := "2.12.6"

libraryDependencies ++= Seq(
  "com.typesafe.akka"           %% "akka-http"                  % "10.2.3",
  "de.heikoseeberger"           %% "akka-http-circe"            % "1.20.1",
  "com.typesafe.akka"           %% "akka-http-testkit"          % "10.2.3",
  "com.typesafe.akka"           %% "akka-actor-typed"           % "2.6.8",
  "com.typesafe.akka"           %% "akka-stream"                % "2.6.8",
  "com.typesafe.akka"           %% "akka-stream-testkit"        % "2.6.8",
  "ch.qos.logback"              % "logback-classic"             % "1.3.0-alpha4",
  "org.typelevel"               %% "cats-core"                  % "2.1.1",
  "io.circe"                    %% "circe-core"                 % "0.9.3",
  "io.circe"                    %% "circe-generic"              % "0.9.3",
  "io.circe"                    %% "circe-parser"               % "0.9.3",
  "io.circe"                    %% "circe-java8"                % "0.9.3",
  "com.typesafe"                %  "config"                     % "1.3.2",
  "io.gatling.highcharts"       %  "gatling-charts-highcharts"  % "2.3.1",
  "io.gatling"                  %  "gatling-test-framework"     % "2.3.1",
  "com.h2database"              % "h2"                          % "1.4.189",
  "org.scalacheck"              %% "scalacheck"                 % "1.14.1",
  "com.typesafe.scala-logging"  %% "scala-logging"              % "3.9.2",
  "com.thesamet.scalapb"        %% "scalapb-runtime"            % "0.9.4" % "protobuf",
  "org.scalatest"               %% "scalatest"                  % "3.2.2",
  "com.typesafe.slick"          %% "slick"                      % "3.3.0"
)

scalacOptions ++= Seq(
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

enablePlugins( GatlingPlugin, ProtocPlugin )

addCommandAlias( "me", "clean ; compile ; test:compile ; coverage ; test ; coverageReport")

// coverage
coverageEnabled := true
coverageMinimum := 90
coverageExcludedPackages := ".*AkkaHttpMain.*;.*Book.*;.*BookProto.*;"

// protobuf
Compile / PB.protoSources := Seq(file("src/main/scala/me/khazaddum/protobuf"))
Compile / PB.targets := Seq(
  scalapb.gen( flatPackage = true ) -> ( target.value / "proto-generated")
)


// modules
lazy val root = project
  .in(file("."))
  .aggregate(akkaHttp, core, cqrs, eventStore, frdm, gatling, scaffeine)

lazy val core = project.in( file( "core-domain" ) )

lazy val akkaHttp = project.in( file( "mod-akka-http" ) )
lazy val cqrs = project.in( file( "mod-cqrs" ) )
lazy val eventStore = project.in( file( "mod-event-store" ) )
lazy val frdm = project.in( file( "mod-frdm" ) )
lazy val gatling = project.in( file( "mod-gatling" ) )
lazy val scaffeine = project.in( file( "mod-scaffeine" ) )