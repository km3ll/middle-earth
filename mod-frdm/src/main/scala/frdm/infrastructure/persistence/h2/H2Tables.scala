package frdm.infrastructure.persistence.h2

import slick.lifted.TableQuery

object H2Tables {

  val tbl_Accounts = TableQuery[H2AccountTable]

}
