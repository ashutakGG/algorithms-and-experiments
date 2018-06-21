name := "algorithms-and-experiments"

version := "0.1"

scalaVersion := "2.12.6"

lazy val root = (project in file("."))
  .settings(
    name := "algorithms-and-experiments",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % Test
  )