package me.khazaddum.frdm.infrastructure.persistence.h2

case class H2AccountDao(
  account_type:     String,
  account_no:       String,
  date_of_open:     String,
  date_of_close:    Option[String] = None,
  balance:          Double,
  rate_of_interest: Option[Double],
  is_active:        Boolean
)