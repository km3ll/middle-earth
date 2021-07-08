package me.khazaddum.persistence

import java.util.Calendar

import me.khazaddum.domain.model.SavingAccount
import me.khazaddum.persistence.cassandra.RepositoryInCassandra
import me.khazaddum.persistence.h2.RepositoryInH2
import me.khazaddum.persistence.map.RepositoryInMap
import org.cassandraunit.utils.EmbeddedCassandraServerHelper
import org.scalatest.{ AsyncFlatSpec, BeforeAndAfterAll, Matchers }

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits

class RepositoryTest extends AsyncFlatSpec with Matchers with BeforeAndAfterAll {

  private val repositoryInMap = RepositoryInMap( Implicits.global )
  private val repositoryInH2 = RepositoryInH2( "h2.default", Implicits.global )
  private val repositoryInCassandra = RepositoryInCassandra( Implicits.global )

  override protected def beforeAll(): Unit = {

    Await.ready( repositoryInH2.start(), 3.seconds )
    EmbeddedCassandraServerHelper.startEmbeddedCassandra( "cassandra.yaml", 60.seconds.toMillis )
    Await.result( repositoryInCassandra.start(), 3.seconds )

  }

  override protected def afterAll(): Unit = {

    Await.ready( repositoryInH2.stop(), 3.seconds )
    Await.ready( repositoryInCassandra.stop(), 3.seconds )
    EmbeddedCassandraServerHelper.cleanEmbeddedCassandra()

  }

  "InMap Repository" should "upsert and find an account" in {

    val openDate = Calendar.getInstance().getTime
    val calendar = Calendar.getInstance()
    calendar.add( Calendar.YEAR, 1 )
    val closeDate = Some( calendar.getTime )
    val account = SavingAccount( "0000000003", openDate, closeDate, BigDecimal( 120000L ) )

    val result = for {
      saved <- repositoryInMap.upsert( account )
      found <- repositoryInMap.find( account.no )
    } yield found

    result map { maybeAccount =>
      assert( maybeAccount.isDefined )
      assert( maybeAccount.get.no == account.no )
      assert( maybeAccount.get.balance == account.balance )
    }

  }

  "H2 Repository" should "upsert and find an account" in {

    val openDate = Calendar.getInstance().getTime
    val calendar = Calendar.getInstance()
    calendar.add( Calendar.YEAR, 1 )
    val closeDate = Some( calendar.getTime )
    val account = SavingAccount( "0000000004", openDate, closeDate, BigDecimal( 30000L ) )

    val result = for {
      saved <- repositoryInH2.upsert( account )
      found <- repositoryInH2.find( account.no )
    } yield found

    result map { maybeAccount =>

      assert( maybeAccount.isDefined )
      assert( maybeAccount.get.no == account.no )
      assert( maybeAccount.get.balance == account.balance )

    }

  }

  "Cassandra Repository" should "upsert and find an account" in {

    val openDate = Calendar.getInstance().getTime
    val calendar = Calendar.getInstance()
    calendar.add( Calendar.YEAR, 1 )
    val closeDate = Some( calendar.getTime )
    val account = SavingAccount( "0000000005", openDate, closeDate, BigDecimal( 76000L ) )

    val result = for {
      saved <- repositoryInCassandra.upsert( account )
      found <- repositoryInCassandra.find( account.no )
    } yield found

    result map { maybeAccount =>

      assert( maybeAccount.isDefined )
      assert( maybeAccount.get.no == account.no )
      assert( maybeAccount.get.balance == account.balance )

    }

  }

}
