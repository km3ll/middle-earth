package me.khazaddum.frdm.domain.model

import java.util.{ Calendar, Date }

import cats.data.NonEmptyList
import me.khazaddum.UnitTest
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class AccountTest extends AnyFlatSpec with Matchers {

  "Checking Account constructor" should "create an account" taggedAs UnitTest in {

    val currentDate: Date = Calendar.getInstance.getTime

    val result: Either[NonEmptyList[String], CheckingAccount] = Account.checkingAccount( "1001", Some( currentDate ), None, 0D )
    result.exists { account =>
      account.no == "0000001001" &&
        account.dateOfClose.isEmpty &&
        account.balance == 0
    }

  }

  it should "assign a date-of-open when not provided" taggedAs UnitTest in {

    val result: Either[NonEmptyList[String], CheckingAccount] = Account.checkingAccount( "1002", None, None, 50000D )
    result.exists { account =>
      account.no == "0000001002" &&
        account.dateOfClose.isEmpty &&
        account.balance == 50000
    }

  }

  it should "report a list of errors when provided invalid data" taggedAs UnitTest in {

    val openDate = Calendar.getInstance.getTime

    val calendar = Calendar.getInstance
    calendar.add( Calendar.MONTH, -2 )
    val closeDate = calendar.getTime

    val result: Either[NonEmptyList[String], CheckingAccount] = Account.checkingAccount( "L45G", Some( openDate ), Some( closeDate ), -320D )

    result.isLeft shouldBe true
    result.left.get.toList.foreach( println )
    result.left.get.size shouldBe 3

  }

  "Saving Account constructor" should "create an account" taggedAs UnitTest in {

    val calendar = Calendar.getInstance

    calendar.add( Calendar.MONTH, -5 )
    val openDate = calendar.getTime

    calendar.add( Calendar.MONTH, +3 )
    val closeDate = calendar.getTime

    val result: Either[NonEmptyList[String], SavingAccount] = Account.savingAccount( "2001", 2.34, Some( openDate ), Some( closeDate ), 1000000D )
    result.exists { account =>
      account.no == "0000002001" &&
        account.rateOfInterest == 2.34 &&
        account.dateOfClose.isDefined &&
        account.balance == 1000000
    }

  }

  it should "assign a date-of-open when not provided" taggedAs UnitTest in {

    val result: Either[NonEmptyList[String], SavingAccount] = Account.savingAccount( "2002", 3.77, None, None, 50000D )
    result.exists { account =>
      account.no == "0000002002" &&
        account.rateOfInterest == 3.77 &&
        account.dateOfClose.isEmpty &&
        account.balance == 50000
    }

  }

  it should "report a list of errors when provided invalid data" taggedAs UnitTest in {

    val openDate = Calendar.getInstance.getTime

    val calendar = Calendar.getInstance
    calendar.add( Calendar.MONTH, -1 )
    val closeDate = calendar.getTime

    val result: Either[NonEmptyList[String], SavingAccount] = Account.savingAccount( "9GJY", -100, Some( openDate ), Some( closeDate ), -999D )

    result.isLeft shouldBe true
    result.left.get.toList.foreach( println )
    result.left.get.size shouldBe 4

  }

}
