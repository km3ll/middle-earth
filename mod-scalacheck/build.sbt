import SbtCommon._

name := "scalacheck"
version := "0.1"

libraryDependencies ++= Seq(
  "org.scalacheck"    %% "scalacheck"   % "1.14.1",
  "org.scalatest"     %% "scalatest"    % "3.2.2"
)

// compiler
scalacOptions ++= commonOptions