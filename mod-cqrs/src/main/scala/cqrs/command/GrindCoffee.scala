package cqrs.command

import java.util.UUID

import cats.syntax.either._
import com.typesafe.scalalogging.LazyLogging
import cqrs._
import cqrs.dto.{ CqrsDto, SuccessDto }

import scala.concurrent.Future

case class GrindCoffee( beans: String ) extends Command[Environment, CqrsDto] with LazyLogging {

  def execute( context: Context ): CommandK[Environment, CqrsDto] = {
    commandK {
      environment =>
        eitherT[CqrsDto] {

          logger.debug( s"${context.correlationId} - Grinding..." )

          if ( beans.toLowerCase == "baked" )
            Future.successful {
              logger.error( s"Error grinding '$beans'" )
              s"'$beans' cannot be ground".asLeft
            }
          else
            Future.successful {
              logger.debug( s"Beans $beans ground successfully" )
              SuccessDto( "Beans ground successfully", createEvents( beans ) ).asRight
            }

        }
    }
  }

  private def createEvents( beans: String ): List[CqrsEvent] = {
    List(
      CqrsEvent( id = UUID.randomUUID.toString, name = "CoffeeGround", body = s"""{ "type" : "$beans" }""" )
    )
  }

}