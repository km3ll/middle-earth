package me.khazaddum

import com.typesafe.config.ConfigFactory

object Main extends App {

  val config = ConfigFactory.load().getConfig( "domain.khazad-dum" )
  val greeting = config.getString( "greeting" )

  println( greeting )

}
