package me.khazaddum.eventstore

import java.util.UUID

import me.khazaddum.UnitTest
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class EventStoreTest extends AnyFlatSpec with Matchers {

  "Event store" should "put en event and then get it" taggedAs UnitTest in {

    val store: EventStore[String] = EventStoreInMemory.apply[String]
    val event: Event = Event( name = "OrderPaid", content = OrderPaid( 1100L, 10.50 ) )

    val putStep: Either[String, Event] = store.put( event.key, event )
    putStep.isRight should be( true )

    val getStep = store.get( event.key )
    getStep.size shouldBe 1

  }

  it should "return an empty list when getting a non-existent key" taggedAs UnitTest in {

    val store: EventStore[String] = EventStoreInMemory.apply[String]

    store.get( UUID.randomUUID().toString ).isEmpty

  }

  it should "return all events" taggedAs UnitTest in {

    val store: EventStore[String] = EventStoreInMemory.apply[String]
    val event1 = Event( name = "OrderPaid", content = OrderPaid( 101L, 5.0 ) )
    val event2 = Event( name = "OrderPaid", content = OrderPaid( 101L, 5.0 ) )

    store.put( event1.key, event1 )
    store.put( event2.key, event2 )

    store.allEvents.isRight should be( true )
    store.allEvents.exists( events => events.size == 2 )

  }

  it should "return multiple events with the same key" taggedAs UnitTest in {

    val store: EventStore[String] = EventStoreInMemory.apply[String]

    store.events( UUID.randomUUID().toString ).isLeft should be( true )

    val event1 = Event( name = "OrderPaid", content = OrderPaid( 1001L, 3.55 ) )
    //Event 2 and 3 have the same key
    val event2 = Event( name = "OrderPaid", content = OrderPaid( 1002L, 5.00 ) )
    val event3 = event2.copy( content = OrderPaid( 1003L, 7.50 ) )
    val commonKey = event2.key

    store.put( event1.key, event1 )
    store.put( event2.key, event2 )
    store.put( event3.key, event3 )

    store.events( commonKey ).isRight should be( true )
    store.events( commonKey ).exists( events => events.size == 2 )

  }

}
