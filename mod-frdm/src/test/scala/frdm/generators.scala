package frdm

import java.util.Date
import frdm.domain.model.{ Account, CheckingAccount, SavingAccount }
import org.scalacheck.Gen

object generators {

  val GenAccountNo: Gen[String] = Gen
    .choose( 1L, 9999999999L )
    .map( no => "%010d".format( no ) )

  val GenOpenCloseDates: Gen[( Date, Option[Date] )] = for {
    open <- Gen.calendar.map( _.getTime )
    close <- Gen.calendar.map( _.getTime )
  } yield {
    if ( open.before( close ) ) ( open, Some( close ) ) else ( open, None )
  }

  val GenBalance: Gen[Double] = Gen
    .choose( 0D, 1000000D )

  val GenRate: Gen[Double] = Gen
    .choose( 1D, 100D )

  val GenCheckingAccount: Gen[CheckingAccount] = for {
    no <- GenAccountNo
    ( open, close ) <- GenOpenCloseDates
    balance <- GenBalance
  } yield CheckingAccount( no, open, close, balance )

  val GenSavingAccount: Gen[SavingAccount] = for {
    no <- GenAccountNo
    rate <- GenRate
    ( open, close ) <- GenOpenCloseDates
    balance <- GenBalance
  } yield SavingAccount( no, rate, open, close, balance )

  val GenAccount: Gen[Account] = Gen
    .frequency(
      ( 5, GenCheckingAccount ),
      ( 5, GenSavingAccount )
    )

}
