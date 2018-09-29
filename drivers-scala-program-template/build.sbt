import sbt.Keys.libraryDependencies

val Slf4jVersion = "1.7.7"
val PlayVersion = "2.6.7"
val ScalatraVersion = "2.5.3"
val JettyVersion = "9.4.7.v20170914"
val Json4JVersion = "3.5.0"

resolvers += Classpaths.typesafeReleases



lazy val commonSettings = Seq(
  organization := "com.m2iformation",
  version := "0.1.0-SNAPSHOT",
  scalaVersion := "2.11.11"
)

lazy val root = (project in file("."))
  .aggregate(parser, persister, service)
  .settings(
    name := "drivers-scala-program",
    normalizedName := "drivers-scala-program",
    commonSettings
  )


lazy val parser = Project("parser", sbt.file("parser"))
  .settings(
    commonSettings,
    libraryDependencies += "org.slf4j" % "slf4j-api" % Slf4jVersion,
    libraryDependencies += "org.slf4j" % "slf4j-simple" % Slf4jVersion,
    libraryDependencies += "com.typesafe.play" %% "play-json" % PlayVersion,
    libraryDependencies += "com.jsuereth" %% "scala-arm" % "2.0",
    libraryDependencies += "org.scala-lang.modules" %% "scala-xml" % "1.0.2",
    libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.4",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.4" % "test"

  )

lazy val persister = Project("persister", sbt.file("persister"))
  .dependsOn(parser)
  .settings(
    commonSettings,
    libraryDependencies += "org.slf4j" % "slf4j-api" % Slf4jVersion,
    libraryDependencies += "org.slf4j" % "slf4j-simple" % Slf4jVersion,
    libraryDependencies += "org.scalaj" %% "scalaj-http" % "2.3.0",
    libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.4",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.4" % "test"
  )

lazy val service = Project("service", sbt.file("service"))
  .dependsOn(persister)
  .settings(
    commonSettings,
    libraryDependencies ++= Seq(
      "org.scalatra" %% "scalatra" % ScalatraVersion,
      "org.scalatra" %% "scalatra-scalate" % ScalatraVersion,
      "org.scalatra" %% "scalatra-json" % ScalatraVersion,
      "org.json4s"   %% "json4s-jackson" % Json4JVersion,
      "org.json4s"   %% "json4s-ext" % Json4JVersion,
      "org.scalatra" %% "scalatra-specs2" % ScalatraVersion % "test",
      "org.slf4j" % "slf4j-api" % Slf4jVersion,
      "org.slf4j" % "slf4j-simple" % Slf4jVersion,
      "org.eclipse.jetty" % "jetty-server" % JettyVersion,
      "org.eclipse.jetty" % "jetty-webapp" % JettyVersion,
      "javax.servlet" % "javax.servlet-api" % "3.1.0" % "provided",
      "org.scalatra" %% "scalatra-scalatest" % "2.3.2" % "test"
    )
  )


