package com.m2iformation.parser

import java.io.ByteArrayInputStream

import org.scalatest.{BeforeAndAfterEach, FlatSpec, GivenWhenThen, Matchers}

class DriverHoursParserTest extends FlatSpec
  with BeforeAndAfterEach
  with Matchers
  with GivenWhenThen
{

  val hoursParsers = new DriverHoursParser

  "isValid" should  "return false for invalid lines " in  {
    val lineEmpty = ""
    val line= "<driver-hoursurs>"


    val inputs = List(line,lineEmpty)

    inputs.foreach{ input =>
      hoursParsers.isValid(input) shouldBe false
    }



  }

  it should  "return true for valid lines " in {
    val line = "<driver-hours><id>10</id><week>1</week><hours>70</hours></driver-hours>"
    hoursParsers.isValid(line) shouldBe true
  }


  "parser" should  "return true only valid lines " in {
    Given("Input data with valid lines")
    val input =
      new ByteArrayInputStream(
        """
         | <driver-hours><id>10</id><week>1</week><hours>70</hours></driver-hours>
         | <driver-hours><id>10</id><week>2</week><hours>70</hours></driver-hours>
         | <driver-hours><id>11</id><week>1</week><hours>50</hours></driver-hours>
        """.stripMargin.getBytes()
      )

    When("We parse our input data")
    val parsedLines = hoursParsers.parseInputStream(input)

    Then("We got all parsed lines")
    val expected = List(
      DriverHours("10",1,70),
      DriverHours("10",2,70),
      DriverHours("11",1,50)
    )

    parsedLines should contain theSameElementsAs(expected)
  }


  "ParsonValid" should  "return true only valid lines " in {
    Given("Input data with valid lines")
    val input =
      new ByteArrayInputStream(
        """
          | sdvsdvsdv
          | sdvs
          | dvsdvsdv
        """.stripMargin.getBytes()
      )

    When("We parse our input data")
    val parsedLines = hoursParsers.parseInputStream(input)

    Then("We got an empty list")
    parsedLines should contain theSameElementsAs(Nil)
  }


  "parserTest" should  "return true only valid lines " in {
    Given("Input data with valid lines")
    val input =
      new ByteArrayInputStream(
        """
          | <driver-hours><id>10</id><week>1</week><hours>70</hours></driver-hours>
          | sdvsdvsdv
          | sdvs
          | dvsdvsdv
          | <driver-hours><id>11</id><week>1</week><hours>50</hours></driver-hours>
        """.stripMargin.getBytes()
      )

    When("We parse our input data")
    val parsedLines = hoursParsers.parseInputStream(input)

    Then("We got only valid elements in list")
    val expected = List(
      DriverHours("10",1,70),
      DriverHours("11",1,50)
    )

    parsedLines should contain theSameElementsAs(expected)
  }


}
