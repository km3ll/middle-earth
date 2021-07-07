package testkit

import java.util.Date
import me.khazaddum.domain.model.SavingAccount
import org.scalacheck.Gen

object Generators {

  val GenAccountNo: Gen[String] = Gen.choose( 1L, 9999999999L )
    .map( no => "%010d".format( no ) )

  val GenBalance: Gen[BigDecimal] = Gen.choose( 0.00D, 100000000.00D )
    .map( BigDecimal.valueOf )

  val GenBigDecimal: Gen[BigDecimal] = Gen.choose( 0.00D, 100000000.00D )
    .map( BigDecimal.valueOf )

  val GenDate: Gen[Date] = Gen.calendar
    .map( _.getTime )

  val GenException: Gen[Exception] = Gen.oneOf[Exception](
    new Exception( "Simulated" ),
    new RuntimeException( "Simulated" )
  )

  val GenRandomBigDecimal: Gen[BigDecimal] = Gen.choose[Long]( -9999999999L, 9999999999L )
    .map( BigDecimal.valueOf )

  val GenRandomOpenCloseDates: Gen[( Date, Option[Date] )] = for {
    open <- GenDate
    close <- Gen.option( GenDate )
  } yield ( open, close )

  val GenSavingsAccount: Gen[SavingAccount] = for {
    no <- GenAccountNo
    open <- GenDate
    close <- Gen.option( GenDate )
    balance <- GenBalance
  } yield {
    if ( close.isDefined && close.get.after( open ) ) SavingAccount( no, open, close, balance )
    else SavingAccount( no, open, None, balance )
  }

}
