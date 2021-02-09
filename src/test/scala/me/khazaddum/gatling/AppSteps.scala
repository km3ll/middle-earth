package me.khazaddum.gatling

import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.Predef._

object AppSteps {

  def getStatusManyTimes: ChainBuilder = repeat( 100 ) {
    exec(
      http( "GET status" )
        .get( "/status" )
        .check( status.is( 200 ) )
    )
  }

}
