import sbt.Keys.libraryDependencies

name := "algorithms-and-experiments"

version := "0.1"

scalaVersion := "2.12.6"



lazy val root = (project in file("."))
  .settings(
    name := "algorithms-and-experiments",
    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest" % "3.0.5" % Test,
      "com.novocode" % "junit-interface" % "0.11" % "test"
    )
  )