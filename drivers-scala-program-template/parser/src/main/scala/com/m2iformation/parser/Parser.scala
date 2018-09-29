package com.m2iformation.parser

import java.io.{BufferedReader, IOException, InputStream, InputStreamReader}

import org.slf4j.LoggerFactory

import scala.io.Source
import scala.util.{Failure, Success, Try}

/**
  * Mapping lines
  */
trait Parser {

  type Out

  private val logger = LoggerFactory.getLogger(this.getClass.getName)

  /**
    * transform a line to output data
    *
    * @param line to transform
    * @return
    */
  def transform(line: String): Out

  /**
    * check if line is a valid line
    *
    * @param line to check
    * @return
    */
  def isValid(line: String): Boolean

  /**
    * map the line in object Out using transform and isValid implementations
    *
    * @param line to parse
    */
  def mapLine(line: String): Try[Out] = {
    if(isValid(line))
    {
      Success(transform(line))
    }
    else
    {
      Failure(new Exception(s"La ligne '$line' est incorrecte"))
    }
  }

  /**
    * Parse an InputStream into a list of output objects
    *
    * @param input
    * @param skipHeader either to skip header or not (useful for csv files)
    * @return
    */
  def parseInputStream(input: InputStream, skipHeader: Boolean = false): List[Out] =
  {
    var lines:List[String] = if(skipHeader)
    {
      Source.fromInputStream(input).getLines.toList.tail
    }
    else
    {
      Source.fromInputStream(input).getLines.toList
    }

    lines
      .flatMap{ case (line: String) => mapLine(line) match {
          case Success(lineTransform) => {
            Some(lineTransform)
          }
          case Failure(exp : Exception) =>
          {
            logger.error(exp.getMessage)
            None
          }
        }
      }
  }

}

