package me.khazaddum.eventstore

import scala.collection.concurrent.TrieMap
import cats.syntax.either._

trait EventStore[K] {

  def get( key: K ): List[Event]

  def put( key: K, event: Event ): Either[String, Event]

  def events( key: K ): Either[String, List[Event]]

  def allEvents: Either[String, List[Event]]

}

object EventStoreInMemory {

  def apply[K]: EventStore[K] = new EventStore[K] {

    val eventLog: TrieMap[K, List[Event]] = TrieMap[K, List[Event]]()

    def get( key: K ): List[Event] = {
      eventLog.getOrElse( key, List.empty[Event] )
    }

    def put( key: K, event: Event ): Either[String, Event] = {
      val currentList = eventLog.getOrElse( key, Nil )
      eventLog += ( key -> ( event :: currentList ) )
      event.asRight
    }

    def events( key: K ): Either[String, List[Event]] = {
      val currentList = eventLog.getOrElse( key, Nil )
      if ( currentList.isEmpty ) s"Event $key does not exist".asLeft
      else currentList.asRight
    }

    def allEvents: Either[String, List[Event]] = {
      eventLog.values.toList.flatten.asRight
    }

  }

}
