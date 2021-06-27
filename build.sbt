import SbtCommon._

organization := "middle-earth"
name := "khazad-dum"
version := "0.1"
scalaVersion := "2.12.6"

libraryDependencies ++= Seq(
  "com.typesafe"    %  "config"     % "1.3.2",
  "org.scalatest"   %% "scalatest"  % "3.2.2"
)

// alias
addCommandAlias( "me", "clean ; compile ; test:compile ;")

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