package me.khazaddum.persistence.cassandra

import me.khazaddum.persistence.cassandra.CassandraRecords.CassandraAccount
import com.outworkers.phantom.dsl._
import scala.concurrent.Future

object CassandraTables {

  abstract class CassandraAccountTable extends Table[CassandraAccountTable, CassandraAccount] with RootConnector {

    object no extends StringColumn with PartitionKey
    object dateOfOpen extends DateColumn
    object dateOfClose extends OptionalDateColumn
    object balance extends BigDecimalColumn

    def find( no: String ): Future[Option[CassandraAccount]] = {
      select.where( _.no eqs no ).one()
    }

    def upsert( account: CassandraAccount ): Future[ResultSet] = {
      insert()
        .value( _.no, account.no )
        .value( _.dateOfOpen, account.dateOfOpen )
        .value( _.dateOfClose, account.dateOfClose )
        .value( _.balance, account.balance )
        .future()
    }

  }

}
