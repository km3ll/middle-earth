package me.khazaddum.frdm.infrastructure.persistence.h2

import slick.jdbc.H2Profile.api._

class H2AccountTable( tag: Tag ) extends Table[H2AccountDao]( tag, "tbl_accounts" ) {

  def account_type = column[String]( "account_type" )
  def account_no = column[String]( "account_no", O.PrimaryKey )
  def date_of_open = column[String]( "date_of_open" )
  def date_of_close = column[Option[String]]( "date_of_close" )
  def balance = column[Double]( "balance" )
  def rate_of_interest = column[Option[Double]]( "rate_of_interest" )
  def is_active = column[Boolean]( "is_active" )

  def * = ( account_type, account_no, date_of_open, date_of_close, balance, rate_of_interest, is_active ) <> ( H2AccountDao.tupled, H2AccountDao.unapply )

}
