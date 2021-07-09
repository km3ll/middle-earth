package me.khazaddum.http

object HttpDtos {

  sealed trait HttpRequest
  sealed trait HttpResponse {
    def dateTime: String
    def message: String
  }

  case class Status( dateTime: String, message: String ) extends HttpResponse

}
