package me.khazaddum.cqrs

import java.util.UUID

trait Context {
  def correlationId: String
}

case class TransactionalContext(
  correlationId: String
) extends Context

object Context {

  def transactional( correlationId: Option[String] = None ): TransactionalContext = {
    TransactionalContext( correlationId.getOrElse( UUID.randomUUID().toString ) )
  }

}