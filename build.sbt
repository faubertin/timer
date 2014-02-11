import AssemblyKeys._

name := "timer"

version := "1.0"

scalaVersion := "2.10.3"

libraryDependencies += "org.scala-lang" % "scala-swing" % "2.10.3"

libraryDependencies += "org.quartz-scheduler" % "quartz" % "2.2.1"

assemblySettings

mainClass in assembly := Some("timer.Main")
