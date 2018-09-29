package com.m2iformation.service

import com.m2iformation.parser.Driver
import com.m2iformation.persister.support.SerializationSupport
import org.scalatra.{NotFound, Ok}

import scala.util.{Failure, Success, Try}

class DriversRoute extends RestServlet with SerializationSupport {

  val inputFile = "/tmp/drivers"

  lazy val drivers:Seq[Driver] = deserialize[Driver](inputFile)

  val exposedDrivers: Map[String, DriverRest] = {

    drivers
      .groupBy(driver => driver.id)
      .map{ case(id,driversSorted) => {
        val listWeeks = driversSorted.map(driver => DriverWeek(driver.week,driver.hours,driver.miles)).toList
          DriverRest(id,driversSorted.head.name,listWeeks)
      }}.map{ driver : DriverRest => driver.id -> driver }.toMap
  }

  //---- Drivers routes -----

  get("/drivers/id/:driverId") {

    val id = params("driverId")
    exposedDrivers.find{case (key,driverRest) => key == id} match {
      case Some(driverRest) => Ok(driverRest)
      case _ => NotFound(s"No driver with id : $id")
    }
  }


  get("/drivers") {
   Ok(exposedDrivers.map{case (id,driver) => driver})
  }

}

case class DriverWeek(week: Int, hours: Int, miles: Int)
case class DriverRest(id: String, name: String, weeks: List[DriverWeek])
