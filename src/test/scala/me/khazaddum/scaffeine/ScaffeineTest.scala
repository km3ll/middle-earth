package me.khazaddum.scaffeine

import org.scalatest.flatspec.AsyncFlatSpec
import org.scalatest.matchers.should.Matchers
import com.github.blemale.scaffeine.{ AsyncLoadingCache, Cache, LoadingCache, Scaffeine }
import me.khazaddum.UnitTest
import org.scalatest.concurrent.ScalaFutures

import scala.concurrent.Future
import scala.concurrent.duration._

class ScaffeineTest extends AsyncFlatSpec with ScalaFutures with Matchers {

  /*
  Cache:
  Entries are manually added and are stored in the cache until either evicted or manually invalidated.
  I put and get values to/from cache.
  */
  "Cache" should "be created with basic builder" taggedAs UnitTest in {

    val cache: Cache[Int, String] =
      Scaffeine()
        .build[Int, String]()

    cache.put( 1, "foo" )
    cache.getIfPresent( 1 ) shouldBe Some( "foo" )

  }

  it should "enable build properties" taggedAs UnitTest in {

    val cache: Cache[Int, String] =
      Scaffeine()
        .recordStats()
        .expireAfterWrite( 10.minutes )
        .expireAfterAccess( 10.minutes )
        .maximumSize( 500 ) // maximum number of entries
        .build[Int, String]()

    cache.put( 1, "foo" )
    println( cache.stats() )

    cache.getIfPresent( 1 ) shouldBe Some( "foo" )
    println( cache.stats() )
    cache.getIfPresent( 2 ) shouldBe None

  }

  it should "enable operations with Maps" taggedAs UnitTest in {

    val cache: Cache[Int, String] =
      Scaffeine()
        .recordStats()
        .expireAfterWrite( 10.minutes )
        .maximumSize( 500 ) // maximum number of entries
        .build[Int, String]()

    // putAll
    val values = Map( 1 -> "foo", 2 -> "bar" )
    cache.putAll( values )

    // getAllPresent
    val keys = Set( 1, 2, 3 )
    val valuesFromCache: Map[Int, String] = cache.getAllPresent( keys )

    valuesFromCache.size shouldBe 2
    valuesFromCache.contains( 1 ) shouldBe true
    valuesFromCache.contains( 2 ) shouldBe true
    valuesFromCache.contains( 3 ) shouldBe false // not cached

  }

  it should "allow key invalidation" taggedAs UnitTest in {

    val cache: Cache[Int, String] =
      Scaffeine()
        .recordStats()
        .expireAfterWrite( 10.minutes )
        .maximumSize( 500 ) // maximum number of entries
        .build[Int, String]()

    val values = Map( 1 -> "foo", 2 -> "bar", 3 -> "lorem", 4 -> "ipsum" )
    cache.putAll( values )

    // invalidate one key
    cache.getIfPresent( 1 ) shouldBe Some( "foo" )
    cache.invalidate( 1 )
    cache.getIfPresent( 1 ) shouldBe None

    // invalidate a set of keys
    cache.getIfPresent( 2 ) shouldBe Some( "bar" )
    cache.getIfPresent( 3 ) shouldBe Some( "lorem" )
    cache.invalidateAll( Set( 2, 3 ) )
    cache.getIfPresent( 2 ) shouldBe None
    cache.getIfPresent( 3 ) shouldBe None

    // invalidate all the keys
    cache.getIfPresent( 4 ) shouldBe Some( "ipsum" )
    cache.invalidateAll()
    cache.getIfPresent( 4 ) shouldBe None

  }

  /*
  LoadingCache:
  Values are automatically loaded by the cache,
  and they are stored in the cache until either evicted or manually invalidated.
  I provide a loader function to (1) get a value and (2) automatically cache it
  */
  "LoadingCache" should "be created with basic builder" taggedAs UnitTest in {

    val repository: Map[Int, String] = Map( 1 -> "foo" )

    def query( repo: Map[Int, String] )( key: Int ): String = {
      println( s"querying '$key' from repo..." )
      repo.getOrElse( key, "undefined" )
    }

    val loaderFunction: Int => String = query( repository )

    val cache: LoadingCache[Int, String] =
      Scaffeine()
        .recordStats()
        .expireAfterWrite( 1.hour )
        .maximumSize( 500 )
        .build( loaderFunction )

    cache.get( 1 ) shouldBe "foo" // queried from repo
    cache.get( 1 ) shouldBe "foo" // cached
    cache.get( 2 ) shouldBe "undefined"
    cache.get( 2 ) shouldBe "undefined"

  }

  "AsyncLoadingCache" should "be created with Synchronous loader" taggedAs UnitTest in {

    val repository: Map[Int, String] = Map( 1 -> "foo", 2 -> "bar" )

    def query( repo: Map[Int, String] )( key: Int ): String = {
      println( s"querying async '$key' from repo..." )
      repo.getOrElse( key, "undefined" )
    }

    val syncLoaderFunction: Int => String = query( repository )

    val cache: AsyncLoadingCache[Int, String] =
      Scaffeine()
        .recordStats()
        .expireAfterWrite( 1.hour )
        .maximumSize( 500 )
        .buildAsync( syncLoaderFunction )

    whenReady( cache.get( 1 ) ) { value => value shouldBe "foo" } // queried from repo
    whenReady( cache.get( 1 ) ) { value => value shouldBe "foo" } //cached

  }

  it should "be created with Asynchronous loader" taggedAs UnitTest in {

    val repository: Map[Int, String] = Map( 1 -> "foo" )

    def query( repo: Map[Int, String] )( key: Int ): Future[String] = Future.successful {
      println( s"querying async '$key' from repo..." )
      repo.getOrElse( key, "undefined" )
    }

    val syncLoaderFunction: Int => Future[String] = query( repository )

    val cache: AsyncLoadingCache[Int, String] =
      Scaffeine()
        .recordStats()
        .expireAfterWrite( 1.hour )
        .maximumSize( 500 )
        .buildAsyncFuture( syncLoaderFunction )

    whenReady( cache.get( 1 ) ) { value => value shouldBe "foo" } // queried from repo
    whenReady( cache.get( 1 ) ) { value => value shouldBe "foo" } //cached

  }

}
