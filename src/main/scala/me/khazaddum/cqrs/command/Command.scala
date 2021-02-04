package me.khazaddum.cqrs.command

import cats.data.{ EitherT, Kleisli }
import me.khazaddum.cqrs.dto.CqrsDto
import me.khazaddum.cqrs.{ CommandK, Context, Environment, Response }

import scala.concurrent.Future

trait Command[E <: Environment, D <: CqrsDto] {

  def commandK( f: E => Response[D] ): CommandK[E, D] = Kleisli( f )

  def eitherT[A]( f: => Future[Either[String, A]] ): EitherT[Future, String, A] = EitherT( f )

  def execute( context: Context ): CommandK[E, D]

}
