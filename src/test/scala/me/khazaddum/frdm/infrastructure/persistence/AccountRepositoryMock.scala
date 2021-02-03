package me.khazaddum.frdm.infrastructure.persistence

import me.khazaddum.frdm.domain.model.Account
import me.khazaddum.frdm.domain.repository.AccountRepository

import scala.collection.mutable
import scala.concurrent.Future

case class AccountRepositoryMock() extends AccountRepository {

  private var map: mutable.Map[String, Account] = mutable.Map.empty

  def find( no: String ): Future[Option[Account]] = no match {
    case "exception" => Future.failed( new Exception( "Simulated exception finding account" ) )
    case _           => Future.successful( map.get( no ) )
  }

  def upsert( account: Account ): Future[Account] = account.no match {
    case "exception" => Future.failed( new Exception( "Simulated exception upserting account" ) )
    case _ => Future.successful {
      map = map += ( ( account.no, account ) )
      account
    }
  }

}
