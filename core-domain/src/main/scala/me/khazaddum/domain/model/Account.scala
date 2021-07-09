package me.khazaddum.domain.model

import java.util.{ Calendar, Date }

import cats.Applicative
import cats.data.NonEmptyList
import me.khazaddum.domain.Validation
import me.khazaddum.domain.model.Validations._

sealed trait Account {
  def no: String
  def dateOfOpen: Date
  def dateOfClose: Option[Date]
  def balance: BigDecimal
}

case class SavingAccount(
  no:          String,
  dateOfOpen:  Date,
  dateOfClose: Option[Date] = None,
  balance:     BigDecimal
) extends Account

object Account {

  def savingAccount(
    no:          String,
    dateOfOpen:  Option[Date],
    dateOfClose: Option[Date],
    balance:     BigDecimal
  ): Either[NonEmptyList[String], Account] = {

    val openDate = dateOfOpen.getOrElse( Calendar.getInstance.getTime )

    Applicative[Validation].map3(
      validateNo( no ),
      validateDates( openDate, dateOfClose ),
      validateBalance( balance )
    ) {
        ( validNo, validDates, validBalance ) => SavingAccount( validNo, validDates._1, validDates._2, validBalance )
      }.toEither

  }

}
