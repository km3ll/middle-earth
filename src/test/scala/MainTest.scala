import com.typesafe.config.ConfigFactory
import org.scalatest.funsuite.AnyFunSuite

class MainTest extends AnyFunSuite {

  test("Smoke test") {
    val config = ConfigFactory.load()
    val greeting = config.getString("me.khazad-dum.greeting")

    assert( greeting.contains("Moria") )
  }

}
