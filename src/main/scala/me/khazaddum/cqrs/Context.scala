package me.khazaddum.cqrs

import com.typesafe.config.{ Config, ConfigFactory }

trait Context {

  val config: Config
  val token: String

}

case class LocalContext() extends Context {

  val config: Config = ConfigFactory.load( "me.khazad-dum.cqrs" )
  val token: String = config.getString( "token" )

}