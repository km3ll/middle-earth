package me.khazaddum.persistence.cassandra

import com.outworkers.phantom.dsl._
import com.typesafe.config.{ Config, ConfigFactory }

object CassandraConnector {

  private val config: Config = ConfigFactory.load()
  private val keyspace: String = config.getString( "cassandra.keyspace" )
  private val host: String = config.getString( "cassandra.host" )
  private val port: Int = config.getInt( "cassandra.port" )

  val default: CassandraConnection = ContactPoint( host, port )
    .noHeartbeat()
    .keySpace( keyspace )

}
