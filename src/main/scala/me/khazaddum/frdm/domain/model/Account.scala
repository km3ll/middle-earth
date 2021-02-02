package me.khazaddum.frdm.domain.model

import java.util.{ Calendar, Date }

import cats.Applicative
import cats.data.NonEmptyList
import cats.implicits._
import me.khazaddum.frdm.Validation

sealed trait Account {
  def no: String
  def dateOfOpen: Date
  def dateOfClose: Option[Date]
  def balance: Double
}

final case class CheckingAccount(
  no:          String,
  dateOfOpen:  Date,
  dateOfClose: Option[Date] = None,
  balance:     Double
) extends Account

final case class SavingAccount(
  no:             String,
  rateOfInterest: Double,
  dateOfOpen:     Date,
  dateOfClose:    Option[Date] = None,
  balance:        Double
) extends Account

object Account {

  private def validateAccountNo( no: String ): Validation[String] = {
    if ( no.nonEmpty && no.forall( Character.isDigit ) && no.toLong >= 1 && no.toLong <= 9999999999L ) "%010d".format( no.toLong ).validNel
    else s"Account number must be between 1 and 9999999999. Found '$no'".invalidNel
  }

  private def validateOpenCloseDates( openDate: Date, closeDate: Option[Date] ): Validation[( Date, Option[Date] )] = {
    closeDate.map { cd =>
      if ( cd before openDate ) s"Close date '$cd' cannot be earlier than open date '$openDate'".invalidNel
      else ( openDate, cd.some ).validNel
    }.getOrElse( ( openDate, closeDate ).validNel )
  }

  private def validateBalance( balance: Double ): Validation[Double] = {
    if ( balance >= 0 ) balance.validNel
    else s"Balance must not be negative. Found '$balance'".invalidNel
  }

  private def validateRate( rate: Double ): Validation[Double] = {
    if ( rate < 0 || rate > 100 ) rate.validNel
    else s"Rate of interest must not be between 1 and 100. Found '$rate'".invalidNel
  }

  def checkingAccount(
    no:          String,
    dateOfOpen:  Option[Date],
    dateOfClose: Option[Date],
    balance:     Double
  ): Either[NonEmptyList[String], CheckingAccount] = {

    val openDate = dateOfOpen.getOrElse( Calendar.getInstance.getTime )

    Applicative[Validation].map3( validateAccountNo( no ), validateOpenCloseDates( openDate, dateOfClose ), validateBalance( balance ) ) {
      ( validNo, validDates, validBalance ) => CheckingAccount( validNo, validDates._1, validDates._2, validBalance )
    }.toEither

  }

  def savingAccount(
    no:             String,
    rateOfInterest: Double,
    dateOfOpen:     Option[Date],
    dateOfClose:    Option[Date],
    balance:        Double
  ): Either[NonEmptyList[String], SavingAccount] = {

    val openDate = dateOfOpen.getOrElse( Calendar.getInstance.getTime )

    Applicative[Validation].map4( validateAccountNo( no ), validateRate( rateOfInterest ), validateOpenCloseDates( openDate, dateOfClose ), validateBalance( balance ) ) {
      ( validNo, validRate, validDates, validBalance ) => SavingAccount( validNo, validRate, validDates._1, validDates._2, validBalance )
    }.toEither

  }

}