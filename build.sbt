ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.13"

lazy val root = (project in file("."))
  .settings(
    name := "bitcoin"
  )

libraryDependencies += "com.softwaremill.sttp.client4" %% "core" % "4.0.0-M9"

libraryDependencies += "org.json4s" %% "json4s-native" % "4.1.0-M4"
