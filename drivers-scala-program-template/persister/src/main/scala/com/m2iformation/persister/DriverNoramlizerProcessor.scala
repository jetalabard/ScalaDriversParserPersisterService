package com.m2iformation.persister

import java.io.InputStream

import com.m2iformation.parser.ParserBoot.serialize
import com.m2iformation.parser._
import com.m2iformation.persister.support.SerializationSupport


class DriverNoramlizerProcessor extends SerializationSupport {

  //parsers
  private val milesParser = new DriverMilesParser
  private val hoursParser = new DriverHoursParser
  private val namesParser = new DriverNamesParser


  def run(driverMilesList: List[DriverMiles], driverHoursList: List[DriverHours], driverNameList: List[DriverName]): List[Driver] = {
    //parse input streams

    val driversWithMiles: List[Driver] = enrichDriverWithMiles(driverNameList, driverMilesList)
    val drivers: List[Driver] = enrichDriverWithHours(driversWithMiles, driverHoursList)

    drivers
  }

  def run(milesInput: InputStream, hoursInput: InputStream, namesInput: InputStream): List[Driver] = {
    //parse input streams
    val driverMilesList: List[DriverMiles] = milesParser.parseInputStream(milesInput, skipHeader = true)
    val driverHoursList: List[DriverHours] = hoursParser.parseInputStream(hoursInput, skipHeader = false)
    val driverNameList: List[DriverName] = namesParser.parseInputStream(namesInput, skipHeader = false)

    val driversWithMiles: List[Driver] = enrichDriverWithMiles(driverNameList, driverMilesList)
    val drivers: List[Driver] = enrichDriverWithHours(driversWithMiles, driverHoursList)

    drivers
  }

  private def enrichDriverWithMiles(driverNameList: List[DriverName], driverMilesList: List[DriverMiles]): List[Driver] =
  {
    driverNameList
     .flatMap{ driverName =>
     {
       driverMilesList.filter(d => d.id == driverName.id)
         .map( driverMiles =>
         {
           Driver(
             driverName.id,
             driverName.name,
             driverMiles.week,
             0,
             driverMiles.miles)
         })
     }}
  }

  private def enrichDriverWithHours(drivers: List[Driver], driverHoursList: List[DriverHours]): List[Driver] =
  {
    drivers
      .flatMap{ driver => {
        driverHoursList.filter(d => d.id == driver.id && driver.week == d.week)
          .map( driverHours =>
          {
            Driver(
              driver.id,
              driver.name,
              driver.week,
              driverHours.hours,
              driver.miles
              )
          })
      }}

  }

}
