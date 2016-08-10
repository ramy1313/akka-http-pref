package com.perf

import akka.actor.{ActorSystem, Inbox}
import akka.http.javadsl.model.headers.{AccessControlAllowOrigin, HttpOriginRange}
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.headers.{`Access-Control-Allow-Headers`, `Access-Control-Allow-Methods`}
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, HttpMethods, HttpResponse}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server._
import akka.stream.scaladsl.Flow
import akka.stream.{ActorMaterializer, ActorMaterializerSettings, Fusing}
import akka.util.Timeout
import akka.pattern.ask
import com.perf.AService._
import com.perf.BiteWorker.BiteQ

import scala.concurrent.duration._
import language.postfixOps
import scala.io.{Source, StdIn}
import scala.util.Try

/**
  * Created by rami on 8/7/16.
  */
object PerfServer extends App {
  implicit val system = ActorSystem()
  //implicit val materializer = ActorMaterializer()
  implicit val materializer =  ActorMaterializer(ActorMaterializerSettings(system).withAutoFusing(false))
  implicit val executionContext = system.dispatcher

  implicit val MAX_REQUEST_DURATION: Timeout = 10 seconds

  val aService = system.actorOf(AService(), "AService")

  val inbox = Inbox.create(system)

  inbox.send(aService, CreateTom("123"))
  val tomResp = inbox.receive(5 seconds)
  tomResp match {
    case ack: Ack =>
      println("Tom created")
  }

  inbox.send(aService, CreateJerry("123"))
  val jerryResp = inbox.receive(5 seconds)
  jerryResp match {
    case ack: Ack =>
      println("Jerry created")
  }

  inbox.send(aService, CreateSpike("123"))
  val spikeResp = inbox.receive(5 seconds)
  spikeResp match {
    case ack: Ack =>
      println("Spike created")
  }

  val bufferedSource = Source.fromFile("quackers.csv")
  for (line <- bufferedSource.getLines) {
    val cols = line.split(",").map(_.trim)
    inbox.send(aService, CreateQuacker(cols(0), cols(1)))
    val quackerResp = inbox.receive(5 seconds)
    quackerResp match {
      case ack: Ack =>
        println("Quacker created")
    }
    //println(s"${cols(0)}|${cols(1)}")
  }
  bufferedSource.close

  val route: Route =
    path("backend") {
      get {
        complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Say hello to akka-http</h1>"))
      }
    } ~ path("quacker") {
      get {
        parameter("i") { i =>
          onComplete((system.actorOf(RequestHandler()) ? GetQuacker(i)).mapTo[Result]) {
            case g: Try[GetQuackerResult] =>
              val gr = g.get
              val s = gr.i + " " + gr.v + "\n"
              val h = HttpResponse(status = 200, entity = s)
                .withHeaders(
                  AccessControlAllowOrigin.create(HttpOriginRange.ALL),
                  `Access-Control-Allow-Headers`("Access-Control-Allow-Origin", "Access-Control-Allow-Method", "Content-Type"),
                  `Access-Control-Allow-Methods`(HttpMethods.GET, HttpMethods.POST, HttpMethods.PUT, HttpMethods.OPTIONS, HttpMethods.DELETE)
                )
              complete(h)
            case _ =>
              val h = HttpResponse(status = 500, entity = "error")
                .withHeaders(
                  AccessControlAllowOrigin.create(HttpOriginRange.ALL),
                  `Access-Control-Allow-Headers`("Access-Control-Allow-Origin", "Access-Control-Allow-Method", "Content-Type"),
                  `Access-Control-Allow-Methods`(HttpMethods.GET, HttpMethods.POST, HttpMethods.PUT, HttpMethods.OPTIONS, HttpMethods.DELETE)
                )
              complete(h)
          }
        }
      }
    } ~ path("biteq") {
      get {
        parameter("i") { i =>
          onComplete((system.actorOf(RequestHandler()) ? BiteQ(i)).mapTo[Result]) {
            case g: Try[BiteQResult] =>
              val gr = g.get
              val s = gr.tom + " " + gr.jerry + " " + gr.spike + " " + gr.quacker+ "\n"
              val h = HttpResponse(status = 200, entity = s)
                .withHeaders(
                  AccessControlAllowOrigin.create(HttpOriginRange.ALL),
                  `Access-Control-Allow-Headers`("Access-Control-Allow-Origin", "Access-Control-Allow-Method", "Content-Type"),
                  `Access-Control-Allow-Methods`(HttpMethods.GET, HttpMethods.POST, HttpMethods.PUT, HttpMethods.OPTIONS, HttpMethods.DELETE)
                )
              complete(h)
            case _ =>
              val h = HttpResponse(status = 500, entity = "error")
                .withHeaders(
                  AccessControlAllowOrigin.create(HttpOriginRange.ALL),
                  `Access-Control-Allow-Headers`("Access-Control-Allow-Origin", "Access-Control-Allow-Method", "Content-Type"),
                  `Access-Control-Allow-Methods`(HttpMethods.GET, HttpMethods.POST, HttpMethods.PUT, HttpMethods.OPTIONS, HttpMethods.DELETE)
                )
              complete(h)
          }
        }
      }
    }

//  val prefused = Fusing.aggressive(route)
//  val httpHandler = Flow.fromGraph(prefused)

  val bindingFuture = Http().bindAndHandle(route, "0.0.0.0", 3000)

  println(s"Server online at http://localhost:3000/\nPress RETURN to stop...")
  StdIn.readLine() // let it run until user presses return
  bindingFuture
    .flatMap(_.unbind()) // trigger unbinding from the port
    .onComplete(_ => system.terminate()) // and shutdown when done
}
