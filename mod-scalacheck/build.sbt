name := "scalacheck"
version := "0.1"

libraryDependencies ++= Seq(
  "org.scalacheck"    %% "scalacheck"   % "1.14.1",
  "org.scalatest"     %% "scalatest"    % "3.2.2"
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