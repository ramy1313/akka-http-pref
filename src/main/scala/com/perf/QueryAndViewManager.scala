package com.perf

import akka.actor.{Actor, ActorSystem, Props}
import akka.persistence.query.PersistenceQuery
import akka.persistence.query.journal.leveldb.scaladsl.LeveldbReadJournal
import akka.stream.ActorMaterializer
import com.perf.JerryPersistentActor.JerryCreatedEvent
import com.perf.QueryAndViewManager.{UpdateBite, UpdateJerry, UpdateSpike, UpdateTom}
import com.perf.SpikePersistentActor.{SpikeBiteEvent, SpikeCreatedEvent}
import com.perf.TomPersistentActor.TomCreated

/**
  * Created by rami on 8/9/16.
  */
object QueryAndViewManager {
  def apply(mat: ActorMaterializer) = Props(classOf[QueryAndViewManager], mat)

  case class UpdateTom(V: String)

  case class UpdateJerry(V: String)

  case class UpdateSpike(V: String)

  case class UpdateBite()
}
class QueryAndViewManager(implicit val mat: ActorMaterializer) extends Actor {
  override def receive: Receive = {case _=>}

  val tomView = context actorOf (TomView(), "TomView")

  val jerryView = context actorOf (JerryView(), "JerryView")

  val spikeView = context actorOf (SpikeView(), "SpikeView")

  val system: ActorSystem = context system

  val queries = PersistenceQuery(system).readJournalFor[LeveldbReadJournal](LeveldbReadJournal.Identifier)

  queries.eventsByPersistenceId("Tom") runForeach { eventEnvelope =>
    eventEnvelope.event match {
      case event: TomCreated =>
        tomView ! UpdateTom(event.v)
    }
  }

  queries.eventsByPersistenceId("Jerry") runForeach { eventEnvelope =>
    eventEnvelope.event match {
      case event: JerryCreatedEvent =>
        jerryView ! UpdateJerry(event.v)
    }
  }

  queries.eventsByPersistenceId("Spike") runForeach { eventEnvelope =>
    eventEnvelope.event match {
      case event: SpikeCreatedEvent =>
        spikeView ! UpdateSpike(event.v)
      case event: SpikeBiteEvent =>
        spikeView ! UpdateBite()
    }
  }
}
