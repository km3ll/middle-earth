package me.khazaddum.domain.repository

import me.khazaddum.domain.model.Account
import scala.concurrent.Future

trait Repository {

  def find( no: String ): Future[Option[Account]]

  def upsert( account: Account ): Future[Account]

}