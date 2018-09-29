package com.m2iformation.parser

import scala.util.{Failure, Success, Try}

class DriverMilesParser extends Parser { // CSV

  override type Out = DriverMiles

  override def transform(line: String): DriverMiles = {
    val columns: Array[String] = line.split(",")
    DriverMiles(
        id = columns(0),
        week = columns(1).toInt,
        miles = columns(2).toInt
      )
  }

  override def isValid(line: String): Boolean = Try(transform(line)) match {
    case Failure(_) => false
    case Success(_) => true
  }

}

case class DriverMiles(id: String, week: Int, miles: Int)
