package me.khazaddum.cqrs.query

import cats.syntax.either._
import com.typesafe.scalalogging.LazyLogging
import me.khazaddum.cqrs._

import scala.concurrent.Future

case class ListCoffeeNames() extends Query[Context, CqrsDto] with LazyLogging {

  def execute( context: Context ): CommandK[Context, CqrsDto] = {
    queryK {
      context =>
        eitherT[CqrsDto] {

          logger.debug( "Listing coffee names..." )

          Future.successful {
            logger.debug( "Names listed successfully" )
            CoffeeNames( names = List( "Americano", "Cappuccino", "Espresso", "Latte" ) ).asRight[String]
          }

        }
    }
  }

}