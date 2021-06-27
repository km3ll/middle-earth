name := "protobuf"
version := "0.1"

libraryDependencies ++= Seq(
  "com.thesamet.scalapb"  %%  "compilerplugin"  % "0.9.4",
  "org.scalatest"         %%  "scalatest"       % "3.2.2"
)

//addSbtPlugin("com.thesamet" % "sbt-protoc" % "0.99.25")
//enablePlugins( ProtocPlugin )

// protobuf
//Compile / PB.protoSources := Seq(file("src/main/scala/me/khazaddum/protobuf"))
//Compile / PB.targets := Seq(
//  scalapb.gen( flatPackage = true ) -> ( target.value / "proto-generated")
//)

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