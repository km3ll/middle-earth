package me.khazaddum.persistence.cassandra

import java.sql.Timestamp
import me.khazaddum.domain.model.{ Account, SavingAccount }
import me.khazaddum.persistence.cassandra.CassandraRecords.CassandraAccount

trait CassandraMappers {

  def recordToAccount( record: CassandraAccount ): Account = {
    SavingAccount( record.no, record.dateOfOpen, record.dateOfClose, record.balance )
  }

  def accountToRecord( account: Account ): CassandraAccount = {
    CassandraAccount(
      account.no,
      new Timestamp( account.dateOfOpen.getTime ),
      account.dateOfClose.map( date => new Timestamp( date.getTime ) ),
      account.balance
    )
  }

}
