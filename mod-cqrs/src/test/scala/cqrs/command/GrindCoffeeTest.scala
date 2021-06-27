package cqrs.command

import cqrs.dto.CqrsDto
import cqrs.{ CommandK, Context, Environment }
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.flatspec.AsyncFlatSpec
import org.scalatest.matchers.should.Matchers

class GrindCoffeeTest extends AsyncFlatSpec with ScalaFutures with Matchers {

  "GrindCoffee Command" should "grind beans" in {

    // Step 1. Load
    val environment = Environment.local()
    val context = Context.transactional()

    // Step 2. Configure
    val command: CommandK[Environment, CqrsDto] = GrindCoffee( "arabica" ).execute( context )

    // Step 3. Run
    val result = command.run( environment )

    // Step 4. Evaluate
    whenReady( result.value ) { response => response.isRight should be( true ) }

  }

  it should "not grind baked beans " in {

    // Step 1. Load
    val environment = Environment.local()
    val context = Context.transactional()

    // Step 2. Configure
    val command: CommandK[Environment, CqrsDto] = GrindCoffee( "baked" ).execute( context )

    // Step 3. Run
    val result = command.run( environment )

    // Step 4. Evaluate
    whenReady( result.value ) { response => response.isLeft should be( true ) }

  }

}
