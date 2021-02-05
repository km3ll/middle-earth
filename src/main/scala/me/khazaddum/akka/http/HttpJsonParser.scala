package me.khazaddum.akka.http

import me.khazaddum.akka.http.HttpDto._
import io.circe._
import io.circe.generic.semiauto._
import io.circe.{ Decoder, Encoder }

trait HttpJsonParser {

  implicit val itemDecoder: Decoder[Item] = deriveDecoder[Item]
  implicit val itemEncoder: Encoder[Item] = deriveEncoder[Item]

  implicit val getItemResponseDecoder: Decoder[GetItemResponse] = deriveDecoder[GetItemResponse]
  implicit val getItemResponseEncoder: Encoder[GetItemResponse] = deriveEncoder[GetItemResponse]

  implicit val postItemRequestDecoder: Decoder[PostItemRequest] = deriveDecoder[PostItemRequest]
  implicit val postItemRequestEncoder: Encoder[PostItemRequest] = deriveEncoder[PostItemRequest]

  implicit val postItemResponseDecoder: Decoder[PostItemResponse] = deriveDecoder[PostItemResponse]
  implicit val postItemResponseEncoder: Encoder[PostItemResponse] = deriveEncoder[PostItemResponse]

  implicit val statusDecoder: Decoder[Status] = deriveDecoder[Status]
  implicit val statusEncoder: Encoder[Status] = deriveEncoder[Status]

}
