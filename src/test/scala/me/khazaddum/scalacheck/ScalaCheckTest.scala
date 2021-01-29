package me.khazaddum.scalacheck

import java.util
import java.util.UUID
import java.text.SimpleDateFormat

import me.khazaddum.UnitTest
import org.scalacheck.{ Arbitrary, Gen }
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ScalaCheckTest extends AnyFlatSpec with Matchers {

  behavior of "Generator"

  it should "be created from basic builder" taggedAs UnitTest in {

    val numberGenerator: Gen[String] = for {
      no <- Gen.choose( 1L, 99999999L )
    } yield "%08d".format( no )

    println( s"basic builder sample: ${numberGenerator.sample.get}" )

  }

  it should "be created from a custom generator trait" taggedAs UnitTest in {

    trait Generator[A] {
      def gen: Gen[A]
      def arb: Arbitrary[A] = Arbitrary.apply( gen )
      def sample: A = gen.sample.get
    }

    object numberGenerator extends Generator[String] {
      val gen: Gen[String] = for {
        no <- Gen.choose[Int]( 1, 99999999 )
      } yield "%08d".format( no )
    }

    println( s"custom generator sample: ${numberGenerator.gen.sample.get}" )

  }

  it should "be created from a case class" taggedAs UnitTest in {

    case class Account( no: String, balance: Double )

    val AccountGenerator: Gen[Account] = for {
      no <- Gen.choose( 1L, 99999999L ).map( "%08d".format( _ ) )
      balance <- Gen.choose[Double]( 1, 1000000 )
    } yield Account( no, balance )

    println( s"case class sample: ${AccountGenerator.sample.get}" )

  }

  behavior of "Misc Generators"

  it should "include 'calendar'" taggedAs UnitTest in {

    val formatter = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss.SSS" )

    val CalendarGenerator: Gen[String] = for {
      calendar <- Gen.calendar
    } yield formatter.format( calendar.getTime )

    println( s"calendar sample: ${CalendarGenerator.sample.get}" )

  }

  it should "include 'duration'" taggedAs UnitTest in {

    val DurationGenerator = for {
      duration <- Gen.duration
    } yield duration

    println( s"duration sample: ${DurationGenerator.sample.get}" )

  }

  behavior of "Number Generators"

  it should "include 'posNum'" taggedAs UnitTest in {

    val PositiveNumberGenerator = for {
      number <- Gen.posNum[Int]
    } yield number

    println( s"posNum sample: ${PositiveNumberGenerator.sample.get}" )

  }

  it should "include 'negNum'" taggedAs UnitTest in {

    val NegativeNumberGenerator = for {
      number <- Gen.negNum[Int]
    } yield number

    println( s"negNum sample: ${NegativeNumberGenerator.sample.get}" )

  }

  it should "include 'chooseNum'" taggedAs UnitTest in {

    val NumberGenerator = for {
      number <- Gen.chooseNum[Int]( 0, 100, 10, 20, 30 )
    } yield number

    println( s"chooseNum with specials sample: ${NumberGenerator.sample.get}" )

  }

  behavior of "String Generators"

  it should "include 'alphaLowerStr'" taggedAs UnitTest in {

    val StringGenerator = for {
      value <- Gen.alphaLowerStr
    } yield value

    println( s"alphaLowerStr sample: ${StringGenerator.sample.get}" )

  }

  it should "include 'alphaNumStr'" taggedAs UnitTest in {

    val StringGenerator = for {
      value <- Gen.alphaNumStr
    } yield value

    println( s"alphaNumStr sample: ${StringGenerator.sample.get}" )

  }

  it should "include 'alphaStr'" taggedAs UnitTest in {

    val StringGenerator = for {
      value <- Gen.alphaStr
    } yield value

    println( s"alphaStr sample: ${StringGenerator.sample.get}" )

  }

  it should "include 'alphaUpperStr'" taggedAs UnitTest in {

    val StringGenerator = for {
      value <- Gen.alphaUpperStr
    } yield value

    println( s"alphaUpperStr sample: ${StringGenerator.sample.get}" )

  }

  it should "include 'identifier'" taggedAs UnitTest in {

    // A string that starts with a lower-case alpha character,
    val IdentifierGenerator = for {
      value <- Gen.identifier
    } yield value

    println( s"identifier sample: ${IdentifierGenerator.sample.get}" )

  }

  it should "include 'numStr'" taggedAs UnitTest in {

    val NumericStringGenerator = for {
      value <- Gen.numStr
    } yield value

    println( s"numStr sample: ${NumericStringGenerator.sample.get}" )

  }

  behavior of "Character Generators"

  it should "include 'alphaChar'" taggedAs UnitTest in {

    val CharGenerator = for {
      value <- Gen.alphaChar
    } yield value

    println( s"alphaChar sample: ${CharGenerator.sample.get}" )

  }

  it should "include 'alphaNumChar'" taggedAs UnitTest in {

    val CharGenerator = for {
      value <- Gen.alphaNumChar
    } yield value

    println( s"alphaNumChar sample: ${CharGenerator.sample.get}" )

  }

  it should "include 'alphaLowerChar'" taggedAs UnitTest in {

    val CharGenerator = for {
      value <- Gen.alphaLowerChar
    } yield value

    println( s"alphaLowerChar sample: ${CharGenerator.sample.get}" )

  }

  it should "include 'alphaUpperChar'" taggedAs UnitTest in {

    val NameGenerator: Gen[String] = for {
      upperChar <- Gen.alphaUpperChar.map( _.toString )
      chars <- Gen.listOfN( 10, Gen.alphaLowerChar ).map( _.mkString )
    } yield s"$upperChar$chars"

    println( s"alphaUpperChar sample: ${NameGenerator.sample.get}" )

  }

  it should "include 'numChar'" taggedAs UnitTest in {

    val CharGenerator = for {
      value <- Gen.numChar
    } yield value

    println( s"numChar sample: ${CharGenerator.sample.get}" )

  }

  behavior of "List Generators"

  it should "include ''" taggedAs UnitTest in {
    /*
    val gen = Gen.buildableOfN
    val gen = Gen.buildableOf
    val gen = Gen.nonEmptyBuildableOf
    val gen = Gen.containerOfN
    val gen = Gen.containerOf
    val gen = Gen.nonEmptyContainerOf
    val gen = Gen.listOf
    val gen = Gen.nonEmptyListOf
    val gen = Gen.mapOf
    val gen = Gen.nonEmptyMap
    val gen = Gen.mapOfN
    val gen = Gen.nonEmptyMap
    val gen = Gen.mapOfN
    val gen = Gen.infiniteStream
    val gen = Gen.someOf
    val gen = Gen.atLeastOne
    val gen = Gen.resultOf
    val gen = Gen.function0
    */
  }

  it should "include 'listOf'" taggedAs UnitTest in {

    val CoffeesGenerator = for {
      coffees <- Gen.listOf( Gen.oneOf( "Cold brew", "Moka", "Espresso", "Cappuccino", "Affogato" ) )
    } yield coffees

    println( s"listOf sample: ${CoffeesGenerator.sample.get}" )

  }

  it should "include 'listOfN'" taggedAs UnitTest in {

    val EmailGenerator: Gen[String] = for {
      name <- Gen.listOfN( 10, Gen.alphaLowerChar ).map( _.mkString )
      domain <- Gen.oneOf( "gmail.com", "protonmail.com", "pm.me" )
    } yield s"$name@$domain"

    println( s"listOfN sample: ${EmailGenerator.sample.get}" )

  }

  it should "include 'pick'" taggedAs UnitTest in {

    val CurrenciesGenerator: Gen[Seq[String]] = for {
      values <- Gen.pick( 3, List( "AUD", "BRL", "CAD", "CNY", "COP", "EUR" ) )
    } yield values

    println( s"pick sample: ${CurrenciesGenerator.sample.get}" )
    CurrenciesGenerator.sample.get.length shouldBe 3

  }

  behavior of "Generator Combinators"

  it should "include 'choose'" taggedAs UnitTest in {

    val AgeGenerator: Gen[Int] = Gen.choose[Int]( 1, 100 )
    println( s"choose sample: ${AgeGenerator.sample.get}" )

  }

  it should "include 'const'" taggedAs UnitTest in {

    val UuidGenerator: Gen[UUID] = for {
      _ <- Gen.const( () )
    } yield UUID.randomUUID()

    println( s"const sample: ${UuidGenerator.sample.get}" )

  }

  it should "include 'fail'" taggedAs UnitTest in {

    val FailGenerator: Gen[String] = Gen.fail[String]
    println( s"fail sample: ${FailGenerator.sample}" )

  }

  it should "include 'frequency'" taggedAs UnitTest in {

    trait Event
    case object DebitOccurred extends Event
    case object CreditOccurred extends Event

    val EventGenerator: Gen[Event] = Gen.frequency(
      ( 3, DebitOccurred ), // 30%
      ( 7, CreditOccurred ) // 70%
    )

    println( s"frequency sample 1: ${EventGenerator.sample.get}" )
    println( s"frequency sample 2: ${EventGenerator.sample.get}" )
    println( s"frequency sample 3: ${EventGenerator.sample.get}" )

  }

  it should "include 'oneOf'" taggedAs UnitTest in {

    val CurrencyCodeGenerator: Gen[String] = for {
      value <- Gen.oneOf( "BRL", "CAD", "COP", "EUR" )
    } yield value

    println( s"oneOf sample: ${CurrencyCodeGenerator.sample.get}" )

  }

  it should "include 'option'" taggedAs UnitTest in {

    trait Status
    case object Starting extends Status
    case object Running extends Status
    case object Stopping extends Status

    val MaybeStatusGenerator: Gen[Option[Status]] = for {
      status <- Gen.option( Gen.oneOf( Starting, Running, Stopping ) )
    } yield status

    println( s"option sample 1: ${MaybeStatusGenerator.sample.get}" )
    println( s"option sample 2: ${MaybeStatusGenerator.sample.get}" )
    println( s"option sample 3: ${MaybeStatusGenerator.sample.get}" )

  }

  it should "include 'sequence'" taggedAs UnitTest in {

    case class Event( name: String, accountNo: String, amount: Double )

    val DebitEventGenerator: Gen[Event] = for {
      no <- Gen.choose( 1L, 99999999L ).map( "%08d".format( _ ) )
      amount <- Gen.choose[Double]( 100, 1000000 )
    } yield Event( "DebitOccurred", no, amount )

    val CreditEventGenerator: Gen[Event] = for {
      event <- DebitEventGenerator
    } yield event.copy( name = "CreditOccurred" )

    val EventsGenerator: Gen[util.ArrayList[Event]] = Gen.sequence( List( DebitEventGenerator, CreditEventGenerator ) )

    println( s"sequence sample: ${EventsGenerator.sample.get}" )
    EventsGenerator.sample.exists( _.size() == 2 ) shouldBe true

  }

}