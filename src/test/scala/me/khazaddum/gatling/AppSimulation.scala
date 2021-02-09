package me.khazaddum.gatling

import io.gatling.core.Predef.{ Simulation, _ }
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._

import scala.concurrent.duration._

class AppSimulation extends Simulation {

  val systemScenario: ScenarioBuilder =
    scenario( "Multiple requests to system endpoints" )
      .exec( AppSteps.getStatusManyTimes )

  setUp( systemScenario
    .inject(
      nothingFor( 5.seconds ),
      constantUsersPerSec( 1 ) during 60.seconds
    )
    .protocols(
      http.baseURL( "http://localhost:8080" )
        .disableWarmUp
    ) )

}
