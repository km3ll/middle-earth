package me.khazaddum.cqrs

case class CqrsEvent(
  id:   String,
  name: String,
  body: String
)
