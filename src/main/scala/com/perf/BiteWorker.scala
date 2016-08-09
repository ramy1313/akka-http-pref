package com.perf

import akka.actor.{Actor, ActorRef, Props}
import akka.stream.ActorMaterializer
import com.perf.AService._
import com.perf.BiteWorker.BiteQ
import com.perf.JerryView.{GetJerryV, GetJerryVResult}
import com.perf.SpikeView.{GetSpikeV, GetSpikeVResult}
import com.perf.TomView.{GetTomV, GetTomVResult}

/**
  * Created by rami on 8/9/16.
  */
object BiteWorker {
  def apply(mat: ActorMaterializer) = Props(classOf[BiteWorker], mat)

  case class BiteQ(q: String)
}
class BiteWorker(implicit val mat: ActorMaterializer) extends Actor {
  var originalSender: ActorRef = null
  var tomV = ""
  var jerryV = ""
  var spikeV = ""
  var quackerV = ""
  override def receive: Receive = {
    case BiteQ(q) =>
      originalSender = sender
      context.actorOf(QuackerView(q, mat)) ! GetQuacker(q)
    case GetQuackerResult(i, v) =>
      quackerV = v
      context.actorSelection("/user/AService/QueryAndViewManager/TomView") ! GetTomV()
    case GetTomVResult(v) =>
      tomV = v
      context.actorSelection("/user/AService/QueryAndViewManager/JerryView") ! GetJerryV()
    case GetJerryVResult(v) =>
      jerryV = v
      context.actorSelection("/user/AService/QueryAndViewManager/SpikeView") ! GetSpikeV()
    case GetSpikeVResult(v) =>
      spikeV = v
      context.actorSelection("/user/AService/Spike") ! Bite()
    case Ack() =>
      originalSender ! BiteQResult(tomV, jerryV, spikeV, quackerV)
      context stop self
  }
}
