package com.m2iformation.persister.support

import java.io.ByteArrayInputStream

import org.slf4j.LoggerFactory

import scala.util.{Failure, Success, Try}
import scalaj.http.HttpConstants.utf8
import scalaj.http.{Http, HttpOptions}

trait HttpDownloadSupport {

  private val logger = LoggerFactory.getLogger(this.getClass.getName)

  def httpConnectTimeout: Int = 5000 //ms
  def httpReadTimeout: Int = 10000 //ms

  /**
    * Download a referential from web
    * @param url
    * @return referential content
    */
  def download(url: String): Try[ByteArrayInputStream] = {
    val request = Http(url)

    val response = request
      .headers(Seq(("Content-Type", "application/json"), ("Charset", utf8)))
      .options(Seq(HttpOptions.connTimeout(httpConnectTimeout), HttpOptions.readTimeout(httpReadTimeout)))
      .asString

    if (response.isNotError && response.body.isEmpty) logger.warn(s"Empty response with http code ${response.code}")

    if (response.isNotError) {
      val body = new ByteArrayInputStream(response.body.getBytes)
      Success(body)
    } else {
      Failure(new HttpResponseError(response.code, response.body))
    }
  }

}

class HttpResponseError(code: Int, body: String) extends RuntimeException(s"Error while downloading: response with http code $code\nbody: $body")
