package com.perf

import akka.actor.{Actor, ActorSystem, Props}
import akka.persistence.query.{EventEnvelope, PersistenceQuery}
import akka.persistence.query.journal.leveldb.scaladsl.LeveldbReadJournal
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Keep, Sink}
import com.perf.AService.{AnError, GetQuacker, GetQuackerResult, Result}
import com.perf.JerryView.{GetJerryV, GetJerryVResult}
import com.perf.QuackerPersistentActor.QuackerCreatedEvent
import com.perf.QueryAndViewManager.{UpdateBite, UpdateJerry, UpdateSpike, UpdateTom}
import com.perf.SpikeView.{GetSpikeV, GetSpikeVResult}
import com.perf.TomView.{GetTomV, GetTomVResult}

/**
  * Created by rami on 8/8/16.
  */
object QuackerView {
  def apply(i: String, mat: ActorMaterializer) = Props(classOf[QuackerView], i, mat)
}
class QuackerView(i: String, implicit val mat: ActorMaterializer) extends Actor{

  val system: ActorSystem = context system

  import context.dispatcher

  val queries = PersistenceQuery(system).readJournalFor[LeveldbReadJournal](LeveldbReadJournal.Identifier)


  override def receive: Receive = {
    case g: GetQuacker =>
      val originalSender = sender

      val queryGraph = queries.currentEventsByPersistenceId(i)
        .toMat(Sink.fold[String, EventEnvelope]("")((state, eventEnvelope) =>
        eventEnvelope.event.asInstanceOf[QuackerCreatedEvent].v))(Keep.right)

      val resultFuture = queryGraph.run()

      resultFuture onSuccess {
        case s: String =>
          originalSender ! GetQuackerResult(i, s)
          context stop self
      }

      resultFuture onFailure {
        case _ =>
          originalSender ! AnError()
          context stop self
      }
  }
}

object TomView {
  def apply() = Props[TomView]

  case class GetTomV()
  case class GetTomVResult(v: String) extends Result
}

class TomView extends Actor {
  var value = ""
  override def receive: Receive = {
    case UpdateTom(v) =>
      value = v
    case GetTomV() =>
      sender ! GetTomVResult(value)
  }
}

object JerryView {
  def apply() = Props[JerryView]

  case class GetJerryV()
  case class GetJerryVResult(v: String)
}

class JerryView extends Actor {
  var value = ""
  override def receive: Receive = {
    case UpdateJerry(v) =>
      value = v
    case GetJerryV() =>
      sender ! GetJerryVResult(value)
  }
}

object SpikeView {
  def apply() = Props[SpikeView]

  case class GetSpikeV()
  case class GetSpikeVResult(v: String)
}

class SpikeView extends Actor {
  var value = ""
  var biteCount = 0
  override def receive: Receive = {
    case UpdateSpike(v) =>
      value = v
    case UpdateBite() =>
      biteCount += 1
    case GetSpikeV() =>
      sender ! GetSpikeVResult(value)
  }
}
