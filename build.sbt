import SbtCommon._

organization := "middle-earth"
name := "khazad-dum"
version := "0.1"
scalaVersion := "2.12.6"

libraryDependencies ++= Seq(
  "com.typesafe"    %  "config"     % "1.3.2",
  "org.scalatest"   %% "scalatest"  % "3.2.2"
)

// compiler
scalacOptions ++= commonOptions

// modules
lazy val root = project
  .in(file("."))
  .aggregate(core, akkahttp, cqrs, eventstore, frdm, gatling, protobuf, scaffeine, scalacheck)

lazy val core       = project.in( file( "core-domain" ) )
lazy val akkahttp   = project.in( file( "mod-akka-http" ) )
lazy val cqrs       = project.in( file( "mod-cqrs" ) )
lazy val eventstore = project.in( file( "mod-event-store" ) )
lazy val frdm       = project.in( file( "mod-frdm" ) )
lazy val gatling    = project.in( file( "mod-gatling" ) )
lazy val protobuf   = project.in( file( "mod-protobuf" ) )
lazy val scaffeine  = project.in( file( "mod-scaffeine" ) )
lazy val scalacheck = project.in( file( "mod-scalacheck" ) )

// alias
addCommandAlias( "me", "clean ; compile ; test:compile ;")
addCommandAlias( "core", "core/clean ; core/compile ; core/test:compile ; core/test ;")
addCommandAlias( "akkahttp", "akkahttp/clean ; akkahttp/compile ; akkahttp/test:compile ; akkahttp/test ;")
addCommandAlias( "cqrs", "cqrs/clean ; cqrs/compile ; cqrs/test:compile ; cqrs/test ;")
addCommandAlias( "eventstore", "eventstore/clean ; eventstore/compile ; eventstore/test:compile ; eventstore/test ;")
addCommandAlias( "frdm", "frdm/clean ; frdm/compile ; frdm/test:compile ; frdm/test ;")
addCommandAlias( "gatling", "gatling/clean ; gatling/compile ; gatling/test:compile ; gatling/test ;")
addCommandAlias( "protobuf", "protobuf/clean ; protobuf/compile ; protobuf/test:compile ; protobuf/test ;")
addCommandAlias( "scaffeine", "scaffeine/clean ; scaffeine/compile ; scaffeine/test:compile ; scaffeine/test ;")
addCommandAlias( "scalacheck", "scalacheck/clean ; scalacheck/compile ; scalacheck/test:compile ; scalacheck/test ;")