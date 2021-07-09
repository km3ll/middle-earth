package me.khazaddum.persistence.h2

import java.sql.Timestamp
import H2Records.H2Account
import me.khazaddum.domain.model.{ Account, SavingAccount }

trait H2Mappers {

  def recordToAccount( record: H2Account ): Account = {
    SavingAccount( record.no, record.dateOfOpen, record.dateOfClose, record.balance )
  }

  def accountToRecord( account: Account ): H2Account = {
    H2Account( account.no, new Timestamp( account.dateOfOpen.getTime ), account.dateOfClose.map( dt => new Timestamp( dt.getTime ) ), account.balance )
  }

}