package com.m2iformation.parser


import scala.util.{Failure, Success, Try}
import scala.xml.XML

class DriverHoursParser extends Parser { //XML

  override type Out = DriverHours

  override def transform(line: String): DriverHours = {
    val xml = XML.loadString(line)
    DriverHours(
      id = xml.child(0).text,
      week = xml.child(1).text.toInt,
      hours = xml.child(2).text.toInt
    )

  }

  override def isValid(line: String): Boolean = Try(transform(line)) match {
    case Failure(_) => false
    case Success(_) => true
  }

}





case class DriverHours(id: String, week: Int, hours: Int)


