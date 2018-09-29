package com.m2iformation.service

import javax.servlet.ServletContext

import org.eclipse.jetty.server.Server
import org.eclipse.jetty.servlet.DefaultServlet
import org.eclipse.jetty.webapp.WebAppContext
import org.scalatra.LifeCycle
import org.scalatra.servlet.ScalatraListener

object ServiceBoot extends App {

  val defaultPort = "8080"

  val port = Option(System.getenv("PORT")).getOrElse(defaultPort).toInt

  val server = new Server(port)
  val context = new WebAppContext()
  context setContextPath "/"
  context.setResourceBase("src/main/webapp")
  context.addEventListener(new ScalatraListener)
  context.addServlet(classOf[DefaultServlet], "/")
  context.setInitParameter(ScalatraListener.LifeCycleKey, "com.m2iformation.service.ScalatraBootstrap")

  server.setHandler(context)

  server.start
  server.join

}

class ScalatraBootstrap extends LifeCycle {
  override def init(context: ServletContext) {
    context mount (new DriversRoute, "/")
  }
}