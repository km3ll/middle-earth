package me.khazaddum.domain.repository

import org.scalacheck.Prop.forAll
import org.scalacheck.Properties
import me.khazaddum.domain.infrastructure.RepositoryInMemory
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Await
import scala.concurrent.duration._
import testkit.Generators.GenSavingsAccount

object RepositoryTest extends Properties( "Repository" ) {

  property( "save and find the same account" ) = {

    val repository = RepositoryInMemory( global )

    forAll( GenSavingsAccount ) {
      account =>
        {

          val operation = for {
            savedAcc <- repository.upsert( account )
            foundAcc <- repository.find( account.no )
          } yield ( savedAcc, foundAcc )

          val ( saved, found ) = Await.result( operation, 3.seconds )

          found.isDefined
          found.get.no == saved.no
          found.get.balance == saved.balance
        }

    }

  }

}
