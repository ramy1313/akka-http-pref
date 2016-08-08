package com.perf

import akka.actor.Props
import akka.persistence.PersistentActor
import com.perf.AService._
import com.perf.JerryPersistentActor.JerryCreatedEvent
import com.perf.QuackerPersistentActor.QuackerCreatedEvent
import com.perf.SpikePersistentActor.{SpikeBiteEvent, SpikeCreatedEvent}
import com.perf.TomPersistentActor.TomCreated

/**
  * Created by rami on 8/8/16.
  */
object QuackerPersistentActor {
  def apply(i: String) = Props(classOf[QuackerPersistentActor], i)

  case class QuackerCreatedEvent(v: String)
}
class QuackerPersistentActor(i: String) extends PersistentActor {

  override def receiveRecover: Receive = {
    case _ =>
  }

  override def receiveCommand: Receive = {
    case c: CreateQuacker =>
      val event = QuackerCreatedEvent(c.v)
      persist(event) {
        event =>
          sender ! Ack()
          context stop self
      }
  }

  override def persistenceId: String = i
}

object TomPersistentActor {
  def apply() = Props[TomPersistentActor]

  case class TomCreated(v: String)
}

class TomPersistentActor extends PersistentActor {
  override def receiveRecover: Receive = {
    case _ =>
  }

  override def receiveCommand: Receive = {
    case c: CreateTom =>
      val event = TomCreated(c.v)
      persist(event) {
        event =>
          sender ! Ack()
          context stop self
      }
  }

  override def persistenceId: String = "Tom"
}

object JerryPersistentActor {
  def apply() = Props[JerryPersistentActor]

  case class JerryCreatedEvent(v: String)
}

class JerryPersistentActor extends PersistentActor {
  override def receiveRecover: Receive = {
    case _ =>
  }

  override def receiveCommand: Receive = {
    case c: CreateJerry =>
      val event = JerryCreatedEvent(c.v)
      persist(event) {
        event =>
          sender ! Ack()
          context stop self
      }
  }

  override def persistenceId: String = "Jerry"
}

object SpikePersistentActor {
  def apply() = Props[SpikePersistentActor]

  case class SpikeCreatedEvent(v: String)

  case class SpikeBiteEvent(t: Long)
}

class SpikePersistentActor extends PersistentActor {
  var biteCount = 0

  override def receiveRecover: Receive = {
    case _ =>
  }

  override def receiveCommand: Receive = {
    case c: CreateSpike =>
      val event = SpikeCreatedEvent(c.v)
      persist(event) {
        event =>
          sender ! Ack()
      }
    case b: Bite =>
      val event = SpikeBiteEvent(System.currentTimeMillis)
      persist(event) {
        event =>
          biteCount += 1
          sender ! Ack()
      }
  }

  override def persistenceId: String = "Spike"
}