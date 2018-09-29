package com.m2iformation.parser

import play.api.libs.json.Json

import scala.util.{Failure, Success, Try}

class DriverNamesParser extends Parser { // JSON

  override type Out = DriverName

  override def transform(line: String): DriverName = {
    val json = Json.parse(line)
    DriverName(
      id = (json \ "id").as[String],
      name = (json \ "name").as[String]
    )


  }

  override def isValid(line: String): Boolean = Try(transform(line)) match {
    case Failure(_) => false
    case Success(_) => true
  }

}

case class DriverName(id: String, name: String)



