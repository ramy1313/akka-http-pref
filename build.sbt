name := "akka-http-perf"

version := "1.0"

scalaVersion := "2.11.8"

val akkaVersion = "2.4.8"

val akkaHttpVersion = "2.4.8"

val AkkaActor = "com.typesafe.akka" %% "akka-actor" % akkaVersion
val AkkaKernel = "com.typesafe.akka" %% "akka-kernel" % akkaVersion
val AkkaHttp = "com.typesafe.akka" %% "akka-http-experimental" % akkaHttpVersion

libraryDependencies ++= Seq(
  AkkaActor,
  AkkaKernel,
  AkkaHttp)

mainClass in(Compile, run) := Some("com.perf.PerfServer")