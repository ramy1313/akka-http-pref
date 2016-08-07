package com.perf

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server._
import akka.stream.scaladsl.Flow
import akka.stream.{ActorMaterializer, ActorMaterializerSettings, Fusing}

import scala.io.StdIn

/**
  * Created by rami on 8/7/16.
  */
object PerfServer extends App {
  implicit val system = ActorSystem()
  //implicit val materializer = ActorMaterializer()
  implicit val materializer =  ActorMaterializer(ActorMaterializerSettings(system).withAutoFusing(false))
  implicit val executionContext = system.dispatcher

  val route: Route =
    path("backend") {
      get {
        complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Say hello to akka-http</h1>"))
      }
    }

//  val prefused = Fusing.aggressive(route)
//  val httpHandler = Flow.fromGraph(prefused)

  val bindingFuture = Http().bindAndHandle(route, "0.0.0.0", 8080)

  println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
  StdIn.readLine() // let it run until user presses return
  bindingFuture
    .flatMap(_.unbind()) // trigger unbinding from the port
    .onComplete(_ => system.terminate()) // and shutdown when done
}
