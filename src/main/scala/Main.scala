import com.typesafe.config.ConfigFactory

class Main extends App {

  val config = ConfigFactory.load()
  println(config.getString("me.khazad-dum.greeting"))

}
