package me.khazaddum.persistence.cassandra

import me.khazaddum.persistence.cassandra.CassandraTables.CassandraAccountTable
import com.outworkers.phantom.dsl._

class CassandraDB( override val connector: CassandraConnection ) extends Database[CassandraDB]( connector ) {

  object accounts extends CassandraAccountTable with connector.Connector

}