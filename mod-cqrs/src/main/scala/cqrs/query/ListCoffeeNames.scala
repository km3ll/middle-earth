package cqrs.query

import cats.syntax.either._
import com.typesafe.scalalogging.LazyLogging
import cqrs._
import cqrs.dto.{ CoffeeNames, CqrsDto }

import scala.concurrent.Future

case class ListCoffeeNames() extends Query[Environment, CqrsDto] with LazyLogging {

  def execute( context: Context ): QueryK[Environment, CqrsDto] = {
    queryK {
      environment =>
        eitherT[CqrsDto] {

          logger.debug( s"${context.correlationId} - Listing..." )

          Future.successful {
            logger.debug( "Names listed successfully" )
            CoffeeNames( names = List( "Americano", "Cappuccino", "Espresso", "Latte" ) ).asRight[String]
          }

        }
    }
  }

}