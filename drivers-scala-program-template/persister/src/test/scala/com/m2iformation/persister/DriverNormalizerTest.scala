package com.m2iformation.persister

import com.m2iformation.parser.{Driver, DriverHours, DriverMiles, DriverName}
import org.scalatest.{BeforeAndAfterEach, FlatSpec, GivenWhenThen, Matchers}


class DriverNormalizerTest extends FlatSpec
  with BeforeAndAfterEach
  with Matchers
  with GivenWhenThen
{
  val driverNormalizer = new DriverNoramlizerProcessor


  "run" should  "return collection of driver from driverName and DriverMiles and driverHours" in  {


   val driverName = List(DriverName("1","toto"),DriverName("2","titi"))

    val driverMiles = List(DriverMiles("1",1,5),DriverMiles("1",2,5),DriverMiles("2",4,5))

    val driverHours = List(DriverHours("1",1,35),DriverHours("1",2,35),DriverHours("2",4,35))

    val driver = driverNormalizer.run(driverMiles,driverHours,driverName)

    val expected = List(
      Driver("1","toto",1,35,5),
      Driver("1","toto",2,35,5),
      Driver("2","titi",4,35,5)
    )

    driver should contain theSameElementsAs(expected)

  }


  "runEmpty" should  "return collection empty of driver" in  {


    val driverName = List(DriverName("1","toto"),DriverName("2","titi"))

    val driverMiles = List(DriverMiles("6",1,5),DriverMiles("6",2,5),DriverMiles("7",4,5))

    val driverHours = List(DriverHours("8",1,35),DriverHours("8",2,35),DriverHours("9",4,35))

    val driver = driverNormalizer.run(driverMiles,driverHours,driverName)

    driver should contain theSameElementsAs(Nil)

  }
}