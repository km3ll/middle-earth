package me.khazaddum.frdm.infrastructure.persistence.inmemory

import me.khazaddum.frdm.domain.model.Account
import me.khazaddum.frdm.domain.repository.AccountRepository
import scala.concurrent.ExecutionContext.Implicits.global
import scala.collection.mutable
import scala.concurrent.Future

case class AccountRepositoryInMemory() extends AccountRepository {

  private var map: mutable.Map[String, Account] = mutable.Map.empty

  def find( no: String ): Future[Option[Account]] = Future {
    map.get( no )
  }

  def upsert( account: Account ): Future[Account] = Future {
    map = map += ( ( account.no, account ) )
    account
  }

}
