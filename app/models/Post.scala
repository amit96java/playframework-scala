package models

import play.api.libs.json.Json

case class Post(id: Int, content: String)

object Post {
  // We're going to be serving this type as JSON, so specify a
  // default Json formatter for our Post type here
  implicit val format = Json.format[Post]
}
