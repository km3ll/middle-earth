package me.khazaddum.http

import HttpDtos.Status
import io.circe._
import io.circe.generic.semiauto._

trait HttpParsers {

  implicit val statusDecoder: Decoder[Status] = deriveDecoder[Status]
  implicit val statusEncoder: Encoder[Status] = deriveEncoder[Status]

}
