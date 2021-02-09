package me.khazaddum.akka.http

import java.time.ZonedDateTime
import java.util.UUID

import akka.http.scaladsl.model.StatusCodes.OK
import akka.http.scaladsl.server.Directives.{ complete, get }
import akka.http.scaladsl.server.Route
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
import akka.http.scaladsl.server.Directives._
import com.typesafe.scalalogging.LazyLogging
import me.khazaddum.akka.http.HttpDto._

trait HttpRoutes extends HttpJsonParser with LazyLogging {

  private def now: ZonedDateTime = ZonedDateTime.now

  val routes: Route = systemRoutes ~ itemRoutes

  def systemRoutes: Route = path( "status" ) {
    get {
      logger.info( "Processing GET /status" )
      complete( OK -> Status( now.toString, "UP!" ) )
    }
  }

  def itemRoutes: Route =
    pathPrefix( "item" ) {
      optionalHeaderValueByName( "X-TransactionId" ) { transactionId =>
        concat(
          parameter( "id" ) { itemId =>
            get {

              val trxId = transactionId.getOrElse( UUID.randomUUID().toString )
              logger.info( s"'$trxId' - Getting item with id '$itemId'" )

              complete( OK -> GetItemResponse( now.toString, Item( "1001", "Cappuccino", true, 3.30D ) ) )

            }
          },
          post {
            entity( as[PostItemRequest] ) { body =>

              val trxId = transactionId.getOrElse( UUID.randomUUID().toString )
              logger.info( s"'$trxId' - Posting item with id '${body.item.id}'" )

              complete( OK -> PostItemResponse( now.toString, body.item ) )

            }
          }
        )
      }
    }
}
