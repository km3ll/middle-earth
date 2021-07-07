import SbtSettings._

organization := "middle-earth"
name := "khazad-dum"
version := "0.1"
scalaVersion := "2.12.8"

lazy val domain = project
  .in( file( "core-domain" ) )
  .settings(
    libraryDependencies ++= domainDependencies,
    scalacOptions ++= compilerOptions
  )

// coverage
coverageMinimumStmtTotal := 90

// alias
addCommandAlias( "me", "clean ; update ; compile ; test:compile ; coverage ; test ; coverageReport")