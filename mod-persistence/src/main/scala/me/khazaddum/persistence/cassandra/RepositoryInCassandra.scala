package me.khazaddum.persistence.cassandra

import me.khazaddum.domain.model.Account
import me.khazaddum.domain.repository.Repository
import com.typesafe.scalalogging.LazyLogging
import com.outworkers.phantom.dsl._
import scala.concurrent.{ ExecutionContext, Future }
import scala.concurrent.duration._

case class RepositoryInCassandra( executionContext: ExecutionContext ) extends Repository with CassandraMappers with LazyLogging {

  implicit val ec: ExecutionContext = executionContext

  object db extends CassandraDB( CassandraConnector.default )

  def find( no: String ): Future[Option[Account]] = {

    logger.debug( s"Finding account no '$no'" )
    db.accounts.find( no ).map { optionRecord =>
      optionRecord.map( recordToAccount )
    }

  }

  def upsert( account: Account ): Future[Account] = {

    logger.debug( s"Upserting account '$account'" )
    val record = accountToRecord( account )
    val result: Future[ResultSet] = for {
      res1 <- db.accounts.upsert( record )
    } yield res1

    result.map { result =>
      logger.debug( s"Account no '${account.no}' upsert result: $result" )
      account
    }.recoverWith {
      case ex: Exception =>
        logger.error( s"Account no '${account.no}' could not be upserted", ex )
        Future.failed( ex )
    }

  }

  def start(): Future[Unit] = Future {

    logger.info( s"Creating DB connection" )
    db.create( 30.seconds )
    ()

  }

  def stop(): Future[Unit] = Future {
    logger.info( s"Closing DB connection" )
    db.shutdown()
  }

}