package com.m2iformation.service

import org.json4s.{DefaultFormats, Formats}
import org.json4s.JsonAST.{JObject, JString}
import org.scalatra._
import org.scalatra.json.JacksonJsonSupport
import org.slf4j.LoggerFactory


trait RestServlet extends ScalatraServlet
  with UrlGeneratorSupport
  with JacksonJsonSupport
  with CorsSupport {

  private val logger = LoggerFactory.getLogger(this.getClass.getName)

  protected implicit def jsonFormats: Formats = DefaultFormats

  before() {
    contentType = formats("json")
  }

  error {
    case ex: Exception ⇒ {
      logger.error("REST service error:", ex)
      halt(500, "A service error.")
    }
  }

}
