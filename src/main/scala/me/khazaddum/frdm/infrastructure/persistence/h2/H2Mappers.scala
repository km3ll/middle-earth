package me.khazaddum.frdm.infrastructure.persistence.h2

import cats.syntax.either._
import cats.syntax.option._
import java.text.SimpleDateFormat
import java.util.Date

import me.khazaddum.frdm.domain.model.{ Account, CheckingAccount, SavingAccount }

trait H2Mappers {

  private val formatter = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss.SSS" )

  private def parseDate( date: String ): Either[String, Date] = {
    try {
      formatter.parse( date ).asRight
    } catch {
      case ex: Exception =>
        s"Date '$date' cannot be parsed with format yyyy-MM-dd HH:mm:ss.SSS. ${ex.getMessage}".asLeft
    }
  }

  private def parseDateOfClose( dateOfClose: Option[String] ): Either[String, Option[Date]] = {
    dateOfClose.map( parseDate ) match {
      case Some( Left( error ) ) => error.asLeft
      case Some( Right( date ) ) => date.some.asRight
      case None                  => None.asRight
    }
  }

  private def parseType( accountType: String ): Either[String, String] = {
    if ( accountType == "C" || accountType == "S" ) accountType.asRight
    else s"Type must be either 'C' or 'S'. Found '$accountType'".asLeft
  }

  def accountToDao( account: Account ): Either[String, H2AccountDao] = account match {

    case acc: CheckingAccount =>
      H2AccountDao(
        account_type = "C",
        account_no = acc.no,
        date_of_open = formatter.format( acc.dateOfOpen ),
        date_of_close = acc.dateOfClose.map( formatter.format ),
        balance = acc.balance,
        rate_of_interest = None,
        is_active = acc.dateOfClose.isEmpty
      ).asRight[String]

    case acc: SavingAccount =>
      H2AccountDao(
        account_type = "S",
        account_no = acc.no,
        date_of_open = formatter.format( acc.dateOfOpen ),
        date_of_close = acc.dateOfClose.map( formatter.format ),
        balance = acc.balance,
        rate_of_interest = acc.rateOfInterest.some,
        is_active = acc.dateOfClose.isEmpty
      ).asRight[String]

  }

  def daoToAccount( dao: H2AccountDao ): Either[String, Account] = {
    for {
      accType <- parseType( dao.account_type )
      openDate <- parseDate( dao.date_of_open )
      closeDate <- parseDateOfClose( dao.date_of_close )
    } yield {
      if ( accType == "C" ) CheckingAccount( dao.account_no, openDate, closeDate, dao.balance )
      else SavingAccount( dao.account_no, dao.rate_of_interest.getOrElse( 0D ), openDate, closeDate, dao.balance )
    }

  }

}
