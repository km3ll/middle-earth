package me.khazaddum.cqrs.query

import me.khazaddum.Tags.UnitTest
import me.khazaddum.cqrs._
import me.khazaddum.cqrs.dto.CqrsDto
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.flatspec.AsyncFlatSpec
import org.scalatest.matchers.should.Matchers

class ListCoffeeNamesTest extends AsyncFlatSpec with ScalaFutures with Matchers {

  "ListCoffeeNames Query" should "return a list of coffees" taggedAs UnitTest in {

    // Step 1. Load
    val environment = Environment.local()
    val context = Context.transactional()

    // Step 2. Configure
    val query: QueryK[Environment, CqrsDto] = ListCoffeeNames().execute( context )

    // Step 3. Run
    val result = query.run( environment )

    // Step 4. Evaluate
    whenReady( result.value ) { response => response.isRight should be( true ) }

  }

}
