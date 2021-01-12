package me.khazaddum

import com.typesafe.config.ConfigFactory
import org.scalatest.funsuite.AnyFunSuite

class MainTest extends AnyFunSuite {

  test( "khazad-dum test" ) {

    val config = ConfigFactory.load().getConfig( "domain.khazad-dum" )
    val greeting = config.getString( "greeting" )
    println( greeting )

    assert( true )

  }

}
