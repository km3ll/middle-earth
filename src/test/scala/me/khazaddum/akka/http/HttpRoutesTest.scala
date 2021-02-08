package me.khazaddum.akka.http

import akka.http.scaladsl.model.{ ContentTypes, HttpEntity, StatusCodes }
import akka.http.scaladsl.testkit.ScalatestRouteTest
import io.circe.Json
import me.khazaddum.Tags.UnitTest
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import me.khazaddum.akka.http.HttpDto._
import io.circe.syntax._

class HttpRoutesTest extends AnyWordSpec with Matchers with ScalatestRouteTest with HttpRoutes {

  "GET /status" should {

    "return system status" taggedAs UnitTest in {

      Get( "/status" ) ~> routes ~> check {

        status shouldEqual StatusCodes.OK
        Json.fromString( responseAs[String] ).as[Status]
          .exists { body => body.dateTime.nonEmpty && body.message == "UP!" }

      }

    }

  }

  "GET /item" should {

    "return an item" taggedAs UnitTest in {

      Get( "/item?id=100001" ) ~> routes ~> check {

        status shouldEqual StatusCodes.OK
        Json.fromString( responseAs[String] ).as[GetItemResponse]
          .exists { body => body.dateTime.nonEmpty && body.item.id.nonEmpty }

      }

    }

  }

  "POST /item" should {

    "persist an item" taggedAs UnitTest in {

      val entity = PostItemRequest( Item( "9002", "Latte", false, 0.0 ) ).asJson.noSpaces
      println( entity )

      Post( "/item" )
        .withEntity( HttpEntity( ContentTypes.`application/json`, entity ) ) ~> routes ~> check {

          status shouldEqual StatusCodes.OK
          Json.fromString( responseAs[String] ).as[PostItemResponse]
            .exists { body => body.dateTime.nonEmpty && body.item.id.nonEmpty }

        }

    }

  }

}
