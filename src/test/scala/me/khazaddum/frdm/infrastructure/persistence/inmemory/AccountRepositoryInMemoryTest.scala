package me.khazaddum.frdm.infrastructure.persistence.inmemory

import me.khazaddum.frdm.domain.model.Account
import me.khazaddum.frdm.generators.GenAccount
import org.scalacheck.Prop.forAll
import org.scalacheck.Properties
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{ Await, Future }
import scala.concurrent.duration._

object AccountRepositoryInMemoryTest extends Properties( "In-Memory Account repository" ) {

  val repository = AccountRepositoryInMemory()

  property( "a previously persisted account must be queried back" ) = forAll( GenAccount ) {
    account =>
      {
        val actions: Future[Option[Account]] = for {
          persisted <- repository.upsert( account )
          queried <- repository.find( account.no )
        } yield queried

        val result: Option[Account] = Await.result( actions, 5.seconds )

        result.exists { acc =>
          acc.no == account.no &&
            acc.dateOfOpen == account.dateOfOpen &&
            acc.dateOfClose == account.dateOfClose &&
            acc.balance == account.balance
        }
      }
  }

}
