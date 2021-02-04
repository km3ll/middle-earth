package me.khazaddum.cqrs

sealed trait CqrsDto {
  def events: List[CqrsEvent]
}

case object EmptyDto extends CqrsDto {
  val events = List.empty[CqrsEvent]
}

case class NonEmptyDto(
  message:   String,
  events:    List[CqrsEvent],
  exception: Option[Exception] = None
) extends CqrsDto