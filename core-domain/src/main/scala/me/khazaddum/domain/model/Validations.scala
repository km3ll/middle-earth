package me.khazaddum.domain.model

import java.util.Date

import cats.implicits._
import me.khazaddum.domain.Validation

object Validations {

  val validateNo: String => Validation[String] =
    no => {
      if ( no.nonEmpty && no.forall( Character.isDigit ) && no.toLong > 0 && no.toLong < 9999999999L ) {
        "%010d".format( no.toLong ).validNel
      } else {
        s"Account number '$no' must be between 1 and 9999999999".invalidNel
      }
    }

  val validateDates: ( Date, Option[Date] ) => Validation[( Date, Option[Date] )] =
    ( openDate, closeDate ) => {
      closeDate.map { cd =>
        if ( cd before openDate ) {
          s"Close date '$cd' cannot be earlier than open date '$openDate'".invalidNel
        } else {
          ( openDate, cd.some ).validNel
        }
      }.getOrElse( ( openDate, closeDate ).validNel )
    }

  val validateBalance: BigDecimal => Validation[BigDecimal] =
    balance => {
      if ( balance >= 0 ) balance.validNel
      else s"Balance '$balance' must not be negative".invalidNel
    }

}
