
name := "middle-earth"
version := "0.1"
scalaVersion := "2.12.6"

libraryDependencies ++= Seq(
  "com.typesafe"        %  "config"     % "1.3.2",
  "org.scalatest"       %% "scalatest"  % "3.2.2",
  "com.github.blemale"  %% "scaffeine"  % "4.0.2"
)

addCommandAlias( "me", "clean ; compile ; test:compile ; test")

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