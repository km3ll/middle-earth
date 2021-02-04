package me.khazaddum.cqrs.query

import cats.data.{ EitherT, Kleisli }
import me.khazaddum.cqrs.{ QueryK, Context, CqrsDto, Response }

import scala.concurrent.Future

trait Query[C <: Context, D <: CqrsDto] {

  def queryK( f: C => Response[D] ): QueryK[C, D] = Kleisli( f )

  def eitherT[A]( f: => Future[Either[String, A]] ): EitherT[Future, String, A] = EitherT( f )

  def execute( context: Context ): QueryK[C, D]

}
