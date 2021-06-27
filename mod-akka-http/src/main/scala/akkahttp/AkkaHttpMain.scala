package akkahttp

import akka.Done
import akka.actor.{ ActorSystem, Terminated }
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route
import com.typesafe.config.{ Config, ConfigFactory }
import com.typesafe.scalalogging.LazyLogging

import scala.concurrent.duration._
import scala.concurrent.{ Await, ExecutionContextExecutor, Future }
import scala.io.StdIn
import scala.util.{ Failure, Success }

object AkkaHttpMain extends HttpRoutes with LazyLogging {

  def main( args: Array[String] ): Unit = {

    logger.info( "Loading environment" )

    val config = ConfigFactory.load().getConfig( "me.khazad-dum.akka.http" )

    implicit val system: ActorSystem = ActorSystem( name = "AkkaHttpMain" )
    implicit val dispatcher: ExecutionContextExecutor = system.dispatcher

    logger.info( "Starting server" )
    val server: Future[Http.ServerBinding] = startServer( config, routes )

    StdIn.readLine()
    logger.info( "Stopping server" )

    val gracefulShutdown: Future[Terminated] =
      server
        .flatMap( unbind )
        .flatMap( _ => terminate( system ) )

    Await.ready( gracefulShutdown, 20.seconds )
    logger.info( "Server offline" )

    ()

  }

  def startServer( config: Config, routes: Route )( implicit system: ActorSystem, dispatcher: ExecutionContextExecutor ): Future[Http.ServerBinding] = {

    val server: Future[Http.ServerBinding] =
      Http().newServerAt( config.getString( "host" ), config.getInt( "port" ) ).bind( routes )

    server.onComplete {
      case Success( Http.ServerBinding( local ) ) =>
        logger.info( s"Server online at ${local.getHostName}:${local.getPort} \nPress [Enter] to stop" )
      case Failure( ex ) =>
        logger.error( "There was an error while starting server", ex )
    }

    server
  }

  // Stop receiving HTTP connections
  def unbind( server: Http.ServerBinding ): Future[Done] = {

    logger.info( "Unbinding http connections" )
    server.unbind()

  }

  def terminate( system: ActorSystem ): Future[Terminated] = {

    logger.info( "Terminating actor system" )
    system.terminate()

  }

}
