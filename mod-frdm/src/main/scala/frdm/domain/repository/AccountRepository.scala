package frdm.domain.repository

import frdm.domain.model.Account
import scala.concurrent.Future

trait AccountRepository {

  def find( no: String ): Future[Option[Account]]

  def upsert( account: Account ): Future[Account]

}