package me.khazaddum.cqrs.command

import cats.data.{ EitherT, Kleisli }
import me.khazaddum.cqrs.{ CommandK, Context, CqrsDto, Response }

import scala.concurrent.Future

trait Command[C <: Context, D <: CqrsDto] {

  def commandK( f: C => Response[D] ): CommandK[C, D] = Kleisli( f )

  def eitherT[A]( f: => Future[Either[String, A]] ): EitherT[Future, String, A] = EitherT( f )

  def execute( context: Context ): CommandK[C, D]

}
