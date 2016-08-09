package com.perf

import akka.actor.{Actor, ActorRef, Props}

/**
  * Created by rami on 8/9/16.
  */
object RequestHandler {
  def apply() = Props[RequestHandler]
}

class RequestHandler extends Actor {

  override def receive: Receive = {
    case c =>
      val aService = context actorSelection "/user/AService"
      aService ! c
      context become waitingForResponse(sender)
  }

  def waitingForResponse(o: ActorRef): Receive = {
    case r =>
      o ! r
      context stop self
  }
}
