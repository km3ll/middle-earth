package eventstore

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class EventTest extends AnyFlatSpec with Matchers {

  "Builder" should "create an event" in {

    val event = Event( name = "OrderPaid", content = OrderPaid( 1100L, 10.50 ) )

    println( event )

    event.at.nonEmpty shouldBe true
    event.key.nonEmpty shouldBe true
    event.name == "OrderPaid"

  }

}
