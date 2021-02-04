package me.khazaddum.cqrs

sealed trait CqrsDto

case class SuccessDto(
  message: String,
  events:  List[CqrsEvent] = List.empty
) extends CqrsDto

case class FailureDto(
  message:   String,
  events:    List[CqrsEvent],
  exception: Option[Exception] = None
) extends CqrsDto

case class CoffeeNames(
  names: List[String]
) extends CqrsDto