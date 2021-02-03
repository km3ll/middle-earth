package me.khazaddum.frdm.domain.service

import java.util.Date
import cats.implicits._
import cats.data.EitherT
import me.khazaddum.frdm.domain.model.{ Account, CheckingAccount, SavingAccount }
import me.khazaddum.frdm.domain.repository.AccountRepository

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object AccountService {

  def openCheckingAccount( no: String, openDate: Option[Date], closeDate: Option[Date], balance: Double )( repository: AccountRepository ): EitherT[Future, String, CheckingAccount] = {

    for {
      _ <- validateNewAccount( no )( repository )
      account <- createCheckingAccount( no, openDate, closeDate, balance )
      _ <- upsertAccount( account )( repository )
    } yield account

  }

  def openSavingAccount( no: String, rate: Double, openDate: Option[Date], closeDate: Option[Date], balance: Double )( repository: AccountRepository ): EitherT[Future, String, SavingAccount] = {

    for {
      _ <- validateNewAccount( no )( repository )
      account <- createSavingAccount( no, rate, openDate, closeDate, balance )
      _ <- upsertAccount( account )( repository )
    } yield account

  }

  def findAccountByNumber( no: String )( repository: AccountRepository ): EitherT[Future, String, Option[Account]] = EitherT {

    repository.find( no )
      .map( _.asRight )
      .recoverWith {
        case ex: Exception =>
          println( ex.getMessage )
          Future.successful( s"Error finding account number '$no'. ${ex.getMessage}".asLeft )
      }

  }

  def createCheckingAccount( no: String, openDate: Option[Date], closeDate: Option[Date], balance: Double ) = EitherT {

    Future {
      Account.checkingAccount( no, openDate, closeDate, balance )
        .leftMap { errors => s"Checking account cannot be created. ${errors.toList.mkString( "," )}" }
    }

  }

  def createSavingAccount( no: String, rate: Double, openDate: Option[Date], closeDate: Option[Date], balance: Double ) = EitherT {

    Future {
      Account.savingAccount( no, rate, openDate, closeDate, balance )
        .leftMap { errors => s"Saving account cannot be created. ${errors.toList.mkString( "," )}" }
    }

  }

  def upsertAccount( account: Account )( repository: AccountRepository ): EitherT[Future, String, Account] = EitherT {

    repository.upsert( account )
      .map( _.asRight )
      .recoverWith {
        case ex: Exception =>
          println( ex.getMessage )
          Future.successful( s"Error upserting account number '${account.no}'. ${ex.getMessage}".asLeft )
      }

  }

  def validateNewAccount( no: String )( repository: AccountRepository ): EitherT[Future, String, String] = EitherT {
    repository.find( no )
      .map {
        case None      => s"No account found with number '$no'".asRight
        case Some( _ ) => s"An account with number '$no' already exists".asLeft
      }
      .recoverWith {
        case ex: Exception =>
          println( ex.getMessage )
          Future.successful( s"Error validating account number '$no'. ${ex.getMessage}".asLeft )
      }
  }

}
