package cqrs

case class CqrsEvent(
  id:   String,
  name: String,
  body: String
)
