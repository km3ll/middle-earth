package me.khazaddum.eventstore

import java.text.SimpleDateFormat
import java.util.{ Calendar, UUID }

case class Event(
  at:      String,
  key:     String,
  name:    String,
  content: Content
)

sealed trait Content
case class OrderPaid( no: Long, amount: Double ) extends Content

object Event {

  private val calendar = Calendar.getInstance
  private val dateFormat: SimpleDateFormat = new SimpleDateFormat( "yyyy-MM-dd'T'HH:mm:ss.SSS" )

  private def currentDateTime: String = dateFormat.format( calendar.getTime )

  def apply( name: String, content: OrderPaid ): Event = {
    Event(
      at = currentDateTime,
      key = UUID.randomUUID().toString,
      name = name,
      content = content
    )
  }

}