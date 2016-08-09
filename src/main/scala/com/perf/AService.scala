package com.perf

import akka.actor.{Actor, Props}
import akka.stream.ActorMaterializer
import com.perf.AService._
import com.perf.BiteWorker.BiteQ

/**
  * Created by rami on 8/8/16.
  */
object AService {
  def apply() = Props[AService]

  abstract class Result

  case class AddValueToTom(v: String)

  case class CreateQuacker(i: String, v: String)

  case class CreateTom(v: String)

  case class CreateJerry(v: String)

  case class CreateSpike(v: String)

  case class Bite()

  case class Ack()

  case class GetQuacker(i: String)

  case class GetQuackerResult(i: String, v: String) extends Result

  case class AnError() extends Result

  case class BiteQResult(tom: String, jerry: String, spike: String, quacker: String) extends Result
}

class AService extends Actor {

  val mat = ActorMaterializer()

  context actorOf (QueryAndViewManager(mat), "QueryAndViewManager")

  val spike = context actorOf (SpikePersistentActor(), "Spike")

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
    case g: GetQuacker =>
      context.actorOf(QuackerView(g.i, mat)) forward g
    case b: BiteQ =>
      context.actorOf(BiteWorker(mat)) forward b
  }
}
