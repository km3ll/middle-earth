package frdm.infrastructure.persistence.h2

import cats.syntax.option._
import com.typesafe.scalalogging.LazyLogging
import frdm.domain.model.Account
import frdm.domain.repository.AccountRepository
import slick.jdbc.H2Profile.api._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import frdm.infrastructure.persistence.h2.H2Tables.tbl_Accounts

case class AccountRepositoryH2() extends AccountRepository with H2Mappers with LazyLogging {

  val db = Database.forConfig( "me.khazad-dum.frdm.h2.default" )

  def find( no: String ): Future[Option[Account]] = {

    val action = tbl_Accounts.filter( _.account_no === no ).result

    db.run( action.headOption )
      .flatMap { result =>
        result.map( daoToAccount ) match {
          case Some( Left( error ) )    => Future.failed( new Exception( error ) )
          case Some( Right( account ) ) => Future.successful( account.some )
          case None                     => Future.successful( None )
        }
      }

  }

  def upsert( account: Account ): Future[Account] = {

    accountToDao( account ) match {

      case Left( error ) => {
        logger.error( s"Account no '${account.no}' cannot be parsed into DB record. $error" )
        Future.failed( new Exception( s"Account no '${account.no}' cannot be parsed into DB record. $error" ) )
      }

      case Right( record ) => {
        val action = tbl_Accounts.+=( record )

        db.run( action ).map { result =>
          logger.debug( s"Account no ${account.no} upserted: $result" )
          account
        }.recoverWith {
          case ex: Exception =>
            logger.error( s"Account no ${account.no} could not be upserted", ex )
            Future.failed( ex )
        }
      }

    }

  }

}
