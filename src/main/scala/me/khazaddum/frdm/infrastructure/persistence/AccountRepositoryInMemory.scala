package me.khazaddum.frdm.infrastructure.persistence

import me.khazaddum.frdm.domain.model.Account
import me.khazaddum.frdm.domain.repository.AccountRepository
import scala.collection.mutable
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

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
