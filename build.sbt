
name := "khazad-dum"
version := "0.1"
scalaVersion := "2.12.6"

libraryDependencies ++= Seq(
  "org.typelevel"       %% "cats-core"  % "2.1.1",
  "com.typesafe"        %  "config"     % "1.3.2",
  "com.github.blemale"  %% "scaffeine"  % "4.0.2",
  "org.scalacheck"      %% "scalacheck" % "1.14.1",
  "org.scalatest"       %% "scalatest"  % "3.2.2",
)

addCommandAlias( "me", "clean ; compile ; test:compile ; coverage ; test ; coverageReport")

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

coverageEnabled := true