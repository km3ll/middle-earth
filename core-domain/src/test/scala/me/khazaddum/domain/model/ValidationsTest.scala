package me.khazaddum.domain.model

import org.scalacheck.{ Gen, Properties }
import org.scalacheck.Prop.forAll
import testkit.Generators._
import Validations._

object ValidationsTest extends Properties( "Validations" ) {

  property( "accountNo with numeric" ) = forAll( GenRandomBigDecimal ) {
    no =>
      {
        val result = validateNo( no.toString )
        if ( no >= 1 && no <= 9999999999L ) {
          result.isValid && BigDecimal.valueOf( result.toEither.right.get.toLong ) == no
        } else {
          result.isInvalid && result.toEither.left.get.size == 1
        }
      }

  }

  property( "accountNo with alphanumeric" ) = forAll( Gen.alphaStr ) {
    alphaStr =>
      val result = validateNo( alphaStr )
      result.isInvalid && result.toEither.left.get.size == 1
  }

  property( "open and close dates" ) = forAll( GenRandomOpenCloseDates ) {
    dates =>
      val result = validateDates( dates._1, dates._2 )
      if ( dates._2.isDefined && dates._2.get.before( dates._1 ) ) {
        result.isInvalid && result.toEither.left.get.size == 1
      } else {
        result.isValid
      }
  }

  property( "balance" ) = forAll( GenRandomBigDecimal ) {
    balance =>
      val result = validateBalance( balance )
      if ( balance >= 0 ) {
        result.isValid
      } else {
        result.isInvalid && result.toEither.left.get.size == 1
      }
  }

}
