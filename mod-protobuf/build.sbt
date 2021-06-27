import SbtCommon._

name := "protobuf"
version := "0.1"

libraryDependencies ++= Seq(
  "com.thesamet.scalapb"  %%  "compilerplugin"  % "0.9.4",
  "org.scalatest"         %%  "scalatest"       % "3.2.2"
)

// compiler
scalacOptions ++= commonOptions

//addSbtPlugin("com.thesamet" % "sbt-protoc" % "0.99.25")
//enablePlugins( ProtocPlugin )

// protobuf
//Compile / PB.protoSources := Seq(file("src/main/scala/me/khazaddum/protobuf"))
//Compile / PB.targets := Seq(
//  scalapb.gen( flatPackage = true ) -> ( target.value / "proto-generated")
//)