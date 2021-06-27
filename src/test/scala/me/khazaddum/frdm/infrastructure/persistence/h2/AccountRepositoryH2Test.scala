package me.khazaddum.frdm.infrastructure.persistence.h2

import me.khazaddum.Tags.UnitTest
import me.khazaddum.frdm.domain.model.{ Account, CheckingAccount, SavingAccount }
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import me.khazaddum.frdm.generators.{ GenAccountNo, GenCheckingAccount, GenSavingAccount }
import me.khazaddum.frdm.infrastructure.persistence.h2.H2Tables.tbl_Accounts
import org.scalatest.BeforeAndAfterAll
import slick.jdbc.H2Profile.api._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{ Await, Future }

class AccountRepositoryH2Test extends AnyFlatSpec with BeforeAndAfterAll with Matchers {

  val repository = AccountRepositoryH2()

  override protected def beforeAll(): Unit = {
    Await.ready( repository.db.run( tbl_Accounts.schema.create ), 5.seconds )
    ()
  }

  override protected def afterAll(): Unit = {
    repository.db.close()
  }

  "H2 Account repository" should "return a previously persisted Checking account" taggedAs UnitTest in {

    val account: CheckingAccount = GenCheckingAccount.sample.get

    val transaction = for {
      upserted <- repository.upsert( account )
      queried <- repository.find( account.no )
    } yield queried

    val result: Option[Account] = Await.result( transaction, 5.seconds )

    result.exists { acc =>
      acc.no == account.no &&
        acc.dateOfOpen == account.dateOfOpen &&
        acc.dateOfClose == account.dateOfClose &&
        acc.balance == account.balance
    }

  }

  it should "return a previously persisted Saving account" taggedAs UnitTest in {

    val account: SavingAccount = GenSavingAccount.sample.get

    val transaction = for {
      upserted <- repository.upsert( account )
      queried <- repository.find( account.no )
    } yield queried

    val result: Option[Account] = Await.result( transaction, 5.seconds )

    result.exists { acc =>
      acc.no == account.no &&
        acc.dateOfOpen == account.dateOfOpen &&
        acc.dateOfClose == account.dateOfClose &&
        acc.balance == account.balance
    }

  }

  it should "report a non-existent account" taggedAs UnitTest in {

    val transaction = repository.find( GenAccountNo.sample.get )

    val result: Option[Account] = Await.result( transaction, 5.seconds )

    result.isEmpty should be( true )

  }

  it should "report a record with invalid account type" taggedAs UnitTest in {

    val record = H2AccountDao( "XF23", "0001", "2021-02-10 11:45:33.621", None, 50000D, None, true )

    Await.ready( repository.db.run( tbl_Accounts.+=( record ) ), 5.seconds )

    val transaction: Future[Option[Account]] = repository.find( "0001" )

    val exception: Exception = intercept[Exception] {
      Await.result( transaction, 5.seconds )
    }

    exception.getMessage.contains( "Type must be either 'C' or 'S'" )

  }

  it should "report a record with invalid dateOfOpen" taggedAs UnitTest in {

    val record = H2AccountDao( "C", "8000000001", "AABC-02-TH 11:45:33.621", None, 50000D, None, true )

    Await.ready( repository.db.run( tbl_Accounts.+=( record ) ), 5.seconds )

    val transaction: Future[Option[Account]] = repository.find( "8000000001" )

    val exception: Exception = intercept[Exception] {
      Await.result( transaction, 5.seconds )
    }

    exception.getMessage.contains( "cannot be parsed with format yyyy-MM-dd HH:mm:ss.SSS" )

  }

  it should "report a record with invalid dateOfClose" taggedAs UnitTest in {

    val record = H2AccountDao( "C", "8000000002", "2021-02-10 11:45:33.621", Some( "XYZZ-02-10 11:45:33.621" ), 50000D, None, false )

    Await.ready( repository.db.run( tbl_Accounts.+=( record ) ), 5.seconds )

    val transaction: Future[Option[Account]] = repository.find( "8000000002" )

    val exception: Exception = intercept[Exception] {
      Await.result( transaction, 5.seconds )
    }

    exception.getMessage.contains( "cannot be parsed with format yyyy-MM-dd HH:mm:ss.SSS" )

  }

  it should "read a valid DB record" taggedAs UnitTest in {

    val record1 = H2AccountDao( "C", "8000000003", "2021-02-10 11:45:33.621", None, 50000D, None, true )
    val record2 = H2AccountDao( "C", "8000000004", "2021-02-10 11:45:33.621", Some( "2021-02-15 00:00:00.000" ), 50000D, None, false )

    Await.ready( repository.db.run( tbl_Accounts.+=( record1 ) ), 5.seconds )
    Await.ready( repository.db.run( tbl_Accounts.+=( record2 ) ), 5.seconds )

    val transaction1: Future[Option[Account]] = repository.find( "8000000003" )
    val transaction2: Future[Option[Account]] = repository.find( "8000000004" )

    val result1 = Await.result( transaction1, 5.seconds )
    val result2 = Await.result( transaction2, 5.seconds )

    result1.nonEmpty should be( true )
    result2.nonEmpty should be( true )

  }

}
