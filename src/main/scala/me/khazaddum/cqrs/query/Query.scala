package me.khazaddum.cqrs.query

import cats.data.{ EitherT, Kleisli }
import me.khazaddum.cqrs.dto.CqrsDto
import me.khazaddum.cqrs.{ Context, Environment, QueryK, Response }

import scala.concurrent.Future

trait Query[E <: Environment, D <: CqrsDto] {

  def queryK( f: E => Response[D] ): QueryK[E, D] = Kleisli( f )

  def eitherT[A]( f: => Future[Either[String, A]] ): EitherT[Future, String, A] = EitherT( f )

  def execute( context: Context ): QueryK[E, D]

}
