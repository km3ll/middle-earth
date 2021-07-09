package me.khazaddum.http

import java.time.ZonedDateTime
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model.StatusCodes.OK
import akka.http.scaladsl.server.Directives.{ complete, extractRequest, get, path }
import akka.http.scaladsl.server.Route
import HttpDtos.Status
import com.typesafe.scalalogging.LazyLogging
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._

trait HttpRoutes extends HttpParsers with LazyLogging {

  protected def now: ZonedDateTime = ZonedDateTime.now

  val routes: Route = {
    extractRequest { request =>
      logger.info( request.toString )
      status //~ otherRoutes
    }
  }

  def status: Route = path( "status" ) {
    get {
      complete( OK -> Status( now.toString, "UP!" ) )
    }
  }

}
