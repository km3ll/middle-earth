package me.khazaddum.akka.http

object HttpDto {

  case class Item(
    id:       String,
    name:     String,
    isActive: Boolean,
    price:    Double
  )

  case class GetItemResponse(
    dateTime: String,
    item:     Item
  )

  case class PostItemRequest(
    item: Item
  )

  case class PostItemResponse(
    dateTime: String,
    item:     Item
  )

  case class Status(
    dateTime: String,
    message:  String
  )

}
