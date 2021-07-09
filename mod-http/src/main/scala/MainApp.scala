import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import com.typesafe.config.ConfigFactory
import com.typesafe.scalalogging.LazyLogging
import me.khazaddum.http.HttpRoutes

import scala.concurrent.{ Await, ExecutionContextExecutor, Future }
import scala.util.{ Failure, Success }
import scala.concurrent.duration._
import scala.io.StdIn

object MainApp extends App with HttpRoutes with LazyLogging {

  // PreStart
  logger.info( "PreStart" )

  private val config = ConfigFactory.load()
  implicit val system: ActorSystem = ActorSystem( "MainApp" )
  implicit val dispatcher: ExecutionContextExecutor = system.dispatcher

  // Start
  logger.info( "Start" )

  val server: Future[Http.ServerBinding] =
    Http().newServerAt( config.getString( "http.host" ), config.getInt( "http.port" ) )
      .bind( routes )

  server.onComplete {
    case Success( Http.ServerBinding( localAddress ) ) =>
      logger.info( s"Server online at ${localAddress.getAddress}:${localAddress.getPort}" )
    case Failure( ex ) =>
      logger.error( s"There was an error while starting server", ex )
  }

  StdIn.readLine()

  val onceAllConnectionsTerminated: Future[Http.HttpTerminated] =
    Await.result( server, 10.seconds )
      .terminate( hardDeadline = 3.seconds )

  onceAllConnectionsTerminated.foreach { _ =>
    logger.info( "PostStop" )
    Await.result( system.terminate(), 10.seconds )
  }

}