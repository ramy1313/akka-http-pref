package com.perf.gatling

import io.gatling.core.Predef._
import io.gatling.core.scenario.Simulation
import io.gatling.http.Predef._
import scala.concurrent.duration._
import language.postfixOps

/**
  * Created by rami on 8/7/16.
  */
class LoadTest extends Simulation {

  private val baseSmwAppUrl = s"http://localhost:8080"

  val httpConf = http
    .baseURL(baseSmwAppUrl).disableWarmUp

  val loadTestScenario = scenario("loadTestScenario")
    .exec(http("loadTest")
      .get("/backend"))

//  setUp(
//    loadTestScenario.inject(rampUsers(82000) over (1 minute))
//  ).protocols(httpConf)

//  setUp(
//    loadTestScenario.inject(atOnceUsers(7000))
//  ).protocols(httpConf)

  setUp(
    loadTestScenario.inject(constantUsersPerSec(1350) during(1 minute))
  ).protocols(httpConf)
}
