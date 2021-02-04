package me.khazaddum.cqrs

import com.typesafe.config.{ Config, ConfigFactory }

trait Environment {
  val config: Config
  val token: String
}

case class LocalEnvironment(
  config: Config,
  token:  String
) extends Environment

object Environment {

  def local(): LocalEnvironment = {
    val config: Config = ConfigFactory.load().getConfig( "me.khazad-dum.cqrs" )
    val token: String = config.getString( "token" )
    LocalEnvironment( config, token )
  }

}