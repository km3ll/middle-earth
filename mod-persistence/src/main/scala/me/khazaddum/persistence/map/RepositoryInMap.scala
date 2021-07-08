package me.khazaddum.persistence.map

import com.typesafe.scalalogging.LazyLogging
import me.khazaddum.domain.model.Account
import me.khazaddum.domain.repository.Repository

import scala.collection.mutable
import scala.concurrent.{ ExecutionContext, Future }

case class RepositoryInMap( executionContext: ExecutionContext ) extends Repository with LazyLogging {

  implicit val ec: ExecutionContext = executionContext

  private var repository: mutable.Map[String, Account] = mutable.Map.empty

  def find( no: String ): Future[Option[Account]] = {
    Future {
      logger.debug( s"Finding account no '$no'" )
      repository.get( no )
    }
  }

  def upsert( account: Account ): Future[Account] = {
    Future {
      logger.debug( s"Upserting account '$account'" )
      repository = repository += ( ( account.no, account ) )
      account
    }
  }

}
