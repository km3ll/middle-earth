package me.khazaddum.akka.http

import io.circe.Decoder.Result
import io.circe.Json
import me.khazaddum.Tags.UnitTest
import me.khazaddum.akka.http.HttpDto._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import io.circe.syntax._

class HttpJsonParserTest extends AnyFlatSpec with Matchers with HttpJsonParser {

  "JSON parser" should "encode and decode a StatusResponse" taggedAs UnitTest in {

    val status: Status = Status( "2021-02-05T15:35:34.701-05:00[America/Bogota]", "UP!" )

    val encoded: Json = status.asJson( statusEncoder )
    println( s"encoded: $encoded" )

    val decoded: Result[Status] = encoded.as[Status]( statusDecoder )
    println( s"decoded: $decoded" )

    decoded.isRight should be( true )
    decoded.right.get should be( status )

  }

  it should "encode and decode a PostItemRequest" taggedAs UnitTest in {

    val request = PostItemRequest( Item( "88001", "Latte", true, 3.50 ) )

    val encoded: Json = request.asJson
    println( s"encoded: $encoded" )

    val decoded = encoded.as[PostItemRequest]
    println( s"decoded: $decoded" )

    decoded.isRight should be( true )
    decoded.right.get should be( request )

  }

  it should "encode and decode a PostItemResponse" taggedAs UnitTest in {

    val response = PostItemResponse(
      dateTime = "2021-02-05T15:35:34.701-05:00[America/Bogota]",
      item = Item( "88001", "Latte", true, 3.50 )
    )

    val encoded: Json = response.asJson
    println( s"encoded: $encoded" )

    val decoded = encoded.as[PostItemResponse]
    println( s"decoded: $decoded" )

    decoded.isRight should be( true )
    decoded.right.get should be( response )

  }

  it should "encode and decode a GetItemResponse" taggedAs UnitTest in {

    val response = GetItemResponse(
      dateTime = "2021-02-05T15:35:34.701-05:00[America/Bogota]",
      item = Item( "88008", "Cappuccino", true, 2.80 )
    )

    val encoded: Json = response.asJson
    println( s"encoded: $encoded" )

    val decoded = encoded.as[GetItemResponse]
    println( s"decoded: $decoded" )

    decoded.isRight should be( true )
    decoded.right.get should be( response )

  }

}
