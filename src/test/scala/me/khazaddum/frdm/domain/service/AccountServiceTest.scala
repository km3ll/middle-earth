package me.khazaddum.frdm.domain.service

import java.util.Calendar

import me.khazaddum.Tags.ComponentTest
import me.khazaddum.frdm.generators.{ GenAccount, GenCheckingAccount, GenSavingAccount }
import me.khazaddum.frdm.domain.model.{ CheckingAccount, SavingAccount }
import me.khazaddum.frdm.infrastructure.persistence.AccountRepositoryMock
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.flatspec.AsyncFlatSpec
import org.scalatest.matchers.should.Matchers

class AccountServiceTest extends AsyncFlatSpec with ScalaFutures with Matchers {

  "openCheckingAccount" should "create and persist a new account" taggedAs ComponentTest in {

    val repository = AccountRepositoryMock()
    val now = Calendar.getInstance.getTime

    val openAccountStep = AccountService.openCheckingAccount( "8001", Some( now ), None, 10 )( repository )

    whenReady( openAccountStep.value ) { result => result.isRight shouldBe true }

    val findAccountStep = AccountService.findAccountByNumber( "0000008001" )( repository )

    whenReady( findAccountStep.value ) { result =>
      result.isRight shouldBe true
      result.right.get.exists { account => account.no == "0000008001" && account.balance == 10 } shouldBe true
    }

  }

  it should "reject an account if it is already persisted" taggedAs ComponentTest in {

    val repository = AccountRepositoryMock()
    val account: CheckingAccount = GenCheckingAccount.sample.get

    val persistAccountStep = AccountService.upsertAccount( account )( repository )

    whenReady( persistAccountStep.value ) { result => result.isRight shouldBe true }

    val openAccountStep = AccountService.openCheckingAccount( account.no, Some( account.dateOfOpen ), account.dateOfClose, account.balance )( repository )

    whenReady( openAccountStep.value ) { result => result.isLeft shouldBe true }

  }

  it should "reject an account with invalid data" taggedAs ComponentTest in {

    val repository = AccountRepositoryMock()

    val openAccountStep = AccountService.openCheckingAccount( "Tdh6", None, None, -78000 )( repository )

    whenReady( openAccountStep.value ) { result => result.isLeft shouldBe true }

  }

  "openSavingAccount" should "create and persist a new account" taggedAs ComponentTest in {

    val repository = AccountRepositoryMock()
    val now = Calendar.getInstance.getTime

    val openAccountStep = AccountService.openSavingAccount( "9001", 2.02, Some( now ), None, 230 )( repository )

    whenReady( openAccountStep.value ) { result => result.isRight shouldBe true }

    val findAccountStep = AccountService.findAccountByNumber( "0000009001" )( repository )

    whenReady( findAccountStep.value ) { result =>
      result.isRight shouldBe true
      result.right.get.exists { account => account.no == "0000009001" && account.balance == 230 } shouldBe true
    }

  }

  it should "reject an account if it is already persisted" taggedAs ComponentTest in {

    val repository = AccountRepositoryMock()
    val account: SavingAccount = GenSavingAccount.sample.get

    val persistAccountStep = AccountService.upsertAccount( account )( repository )

    whenReady( persistAccountStep.value ) { result => result.isRight shouldBe true }

    val openAccountStep = AccountService.openSavingAccount( account.no, account.rateOfInterest, Some( account.dateOfOpen ), account.dateOfClose, account.balance )( repository )

    whenReady( openAccountStep.value ) { result => result.isLeft shouldBe true }

  }

  it should "reject an account with invalid data" taggedAs ComponentTest in {

    val repository = AccountRepositoryMock()

    val openAccountStep = AccountService.openSavingAccount( "Tdh6", -4.22, None, None, -100 )( repository )

    whenReady( openAccountStep.value ) { result => result.isLeft shouldBe true }

  }

  "findAccountByNumber" should "return an existing account" taggedAs ComponentTest in {

    val repository = AccountRepositoryMock()
    val account = GenAccount.sample.get

    val persistAccountStep = AccountService.upsertAccount( account )( repository )

    whenReady( persistAccountStep.value ) { result => result.isRight shouldBe true }

    val findAccountStep = AccountService.findAccountByNumber( account.no )( repository )

    whenReady( findAccountStep.value ) { result =>
      result.isRight shouldBe true
      result.right.get.isDefined shouldBe true
    }

  }

  it should "report a non existing account" taggedAs ComponentTest in {

    val repository = AccountRepositoryMock()

    val findAccountStep = AccountService.findAccountByNumber( "0000000000" )( repository )

    whenReady( findAccountStep.value ) { result =>
      result.isRight shouldBe true
      result.right.get.isDefined shouldBe false
    }

  }

  it should "report exceptions" taggedAs ComponentTest in {

    val repository = AccountRepositoryMock()

    val findAccountStep = AccountService.findAccountByNumber( "exception" )( repository )

    whenReady( findAccountStep.value ) { result => result.isLeft shouldBe true }

  }

  "upsertAccount" should "persist an account" taggedAs ComponentTest in {

    val repository = AccountRepositoryMock()
    val account = GenAccount.sample.get

    val persistAccountStep = AccountService.upsertAccount( account )( repository )

    whenReady( persistAccountStep.value ) { result => result.isRight shouldBe true }

    val findAccountStep = AccountService.findAccountByNumber( account.no )( repository )

    whenReady( findAccountStep.value ) { result =>
      result.isRight shouldBe true
      result.right.get.isDefined shouldBe true
    }

  }

  it should "report exceptions" taggedAs ComponentTest in {

    val repository = AccountRepositoryMock()
    val account = GenCheckingAccount.sample.get.copy( no = "exception" )

    val persistAccountStep = AccountService.upsertAccount( account )( repository )

    whenReady( persistAccountStep.value ) { result => result.isLeft shouldBe true }

  }

  "validateNewAccount" should "report a non existing account as success" taggedAs ComponentTest in {

    val repository = AccountRepositoryMock()

    val validateAccountStep = AccountService.validateNewAccount( "0000000000" )( repository )

    whenReady( validateAccountStep.value ) { result => result.isRight shouldBe true }

  }

  it should "report an existing account as error" taggedAs ComponentTest in {

    val repository = AccountRepositoryMock()
    val account = GenAccount.sample.get

    val persistAccountStep = AccountService.upsertAccount( account )( repository )

    whenReady( persistAccountStep.value ) { result => result.isRight shouldBe true }

    val validateAccountStep = AccountService.validateNewAccount( account.no )( repository )

    whenReady( validateAccountStep.value ) { result => result.isLeft shouldBe true }

  }

  it should "report exceptions" taggedAs ComponentTest in {

    val repository = AccountRepositoryMock()

    val validateAccountStep = AccountService.validateNewAccount( "exception" )( repository )

    whenReady( validateAccountStep.value ) { result => result.isLeft shouldBe true }

  }

}
