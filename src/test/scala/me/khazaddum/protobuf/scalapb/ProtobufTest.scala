package me.khazaddum.protobuf.scalapb

import me.khazaddum.Tags.UnitTest
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ProtobufTest extends AnyFlatSpec with Matchers {

  "Book" should "encoded and decoded to ProtoBuf format" taggedAs UnitTest in {

    val book = Book(
      isbn = "0395489334",
      title = "The Two Towers",
      genre = "Fiction",
      category = 110,
      author = "John Ronald Reuel Tolkien",
      publisher = "Penguin Random House",
      publicationDate = "2019-10-23",
      isActive = true
    )

    val encoded = book.toByteArray
    println(s"Protobuf encoded: ${encoded.mkString("")}" )

    val decoded = Book.parseFrom( encoded )
    println(s"Protobuf decoded: $decoded" )

    decoded.isbn shouldEqual book.isbn
    decoded.title shouldEqual book.title
    decoded.genre shouldEqual book.genre
    decoded.category shouldEqual book.category
    decoded.author shouldEqual book.author
    decoded.publisher shouldEqual book.publisher
    decoded.publicationDate shouldEqual book.publicationDate
    decoded.isActive shouldEqual book.isActive

  }

}
