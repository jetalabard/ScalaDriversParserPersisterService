package com.m2iformation.parser

import java.io.FileInputStream

import com.m2iformation.persister.DriverNoramlizerProcessor
import com.m2iformation.persister.support.{HttpDownloadSupport, SerializationSupport}


/**
  * Parse input data
  * Normalize data
  * Persist data into an output file
  */
object ParserBoot extends App with HttpDownloadSupport with SerializationSupport {


  //input streams
  val milesInput = new FileInputStream(getClass.getClassLoader.getResource("drivers-miles.csv").getFile)
  val hoursInput = new FileInputStream(getClass.getClassLoader.getResource("drivers-hours.xml").getFile)
  val namesInput = new FileInputStream(getClass.getClassLoader.getResource("drivers-names.json").getFile)

  //normalize data
  val normalizer = new DriverNoramlizerProcessor()
  val normalizedDrivers = normalizer.run(milesInput, hoursInput, namesInput)

  //save output data
  val outputFile = "/tmp/drivers"
  serialize(normalizedDrivers, outputFile)

  //test deserialization
  //deserialize[Driver](outputFile).foreach(println)

}

@SerialVersionUID(1L)
case class Driver(id: String, name: String, week: Int, hours: Int = 0, miles: Int = 0) extends Serializable


