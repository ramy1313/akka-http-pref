package com.perf

import akka.actor.{Actor, Props}
import com.perf.AService._

/**
  * Created by rami on 8/8/16.
  */
object AService {
  def apply() = Props[AService]

  case class AddValueToTom(v: String)

  case class CreateQuacker(i: String, v: String)

  case class CreateTom(v: String)

  case class CreateJerry(v: String)

  case class CreateSpike(v: String)

  case class Bite()

  case class Ack()

}

class AService extends Actor {

  val spike = context actorOf SpikePersistentActor()

  override def receive: Receive = {
    case c: CreateQuacker =>
      context.actorOf(QuackerPersistentActor(c.i)) forward c
    case c: CreateTom =>
      context.actorOf(TomPersistentActor()) forward c
    case c: CreateJerry =>
      context.actorOf(JerryPersistentActor()) forward c
    case c: CreateSpike =>
      spike forward c
    case b: Bite =>
      spike forward b
  }
}
