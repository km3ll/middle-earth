package me.khazaddum.cqrs.command

import java.util.UUID

import cats.syntax.either._
import com.typesafe.scalalogging.LazyLogging
import me.khazaddum.cqrs.{ CommandK, Context, CqrsEvent, SuccessDto }

import scala.concurrent.Future

case class Grind( beans: String ) extends Command[Context, SuccessDto] with LazyLogging {

  def execute( context: Context ): CommandK[Context, SuccessDto] = {
    commandK {
      context =>
        eitherT[SuccessDto] {

          logger.debug( "Start grinding..." )

          if ( beans.toLowerCase == "baked beans" )
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