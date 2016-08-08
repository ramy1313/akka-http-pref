import io.gatling.sbt.GatlingPlugin

name := "akka-http-perf"

version := "1.0"

scalaVersion := "2.11.8"

val akkaVersion = "2.4.9-RC2"

val akkaHttpVersion = "2.4.9-RC2"

val AkkaActor = "com.typesafe.akka" %% "akka-actor" % akkaVersion
val AkkaKernel = "com.typesafe.akka" %% "akka-kernel" % akkaVersion
val AkkaHttp = "com.typesafe.akka" %% "akka-http-experimental" % akkaHttpVersion

val AkkaPersistence = "com.typesafe.akka" %% "akka-persistence" % akkaVersion
val AkkaPersistenceQuery = "com.typesafe.akka" %% "akka-persistence-query-experimental" % akkaVersion

val AkkaPersistenceLevelDB = "org.iq80.leveldb" % "leveldb" % "0.9"

val LogbackClassic = "ch.qos.logback" % "logback-classic" % "1.1.3"


val GatlingCharts = "io.gatling.highcharts" % "gatling-charts-highcharts" % "2.2.2"
val GatlingTest = "io.gatling" % "gatling-test-framework" % "2.2.2"

libraryDependencies ++= Seq(
  AkkaActor,
  AkkaKernel,
  AkkaHttp,
  LogbackClassic,
  AkkaPersistence,
  AkkaPersistenceQuery,
  AkkaPersistenceLevelDB,
  GatlingCharts % "test",
  GatlingTest % "test"
)

val test = project.in(file(".")).enablePlugins(GatlingPlugin)

mainClass in(Compile, run) := Some("com.perf.PerfServer")