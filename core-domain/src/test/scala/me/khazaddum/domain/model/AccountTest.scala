package me.khazaddum.domain.model

import java.util.Calendar
import org.scalatest.{ FlatSpec, Matchers }
import testkit.Tags._
import Account.savingAccount

class AccountTest extends FlatSpec with Matchers {

  "Account constructor" should "report errors when invalid data" taggedAs ( Unit ) in {

    val openDate = Some( Calendar.getInstance().getTime )
    val calendar = Calendar.getInstance()
    calendar.add( Calendar.YEAR, -3 )
    val closeDate = Some( calendar.getTime )

    val result = savingAccount( "invalid", openDate, closeDate, -50000 )

    result.isLeft shouldBe true
    result.left.get.size shouldBe 3

  }

  it should "create an account when valid data" taggedAs ( Unit ) in {

    val result = savingAccount( "1", Some( Calendar.getInstance().getTime ), None, 100000 )

    result.isRight shouldBe true
    result.right.get.no shouldBe "0000000001"
    result.right.get.balance shouldBe BigDecimal( 100000 )
    result.right.get.dateOfClose shouldBe None
    println( result.right.get.toString )

  }

  it should "create an account using default open date" taggedAs ( Unit ) in {

    val result = savingAccount( "2", None, None, 20000 )

    result.isRight shouldBe true
    result.right.get.no shouldBe "0000000002"
    result.right.get.balance shouldBe BigDecimal( 20000 )
    result.right.get.dateOfClose shouldBe None
    println( result.right.get.toString )

  }

}
