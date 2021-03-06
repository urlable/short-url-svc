import java.time.Instant

import com.typesafe.sbt.packager.archetypes.JavaServerAppPackaging
import sbt.Keys._
import sbt._
import sbtassembly.AssemblyKeys._
import scoverage.ScoverageKeys._

object Build
  extends Build with LibraryDependencyVersions {

  lazy val commonSettings =
    Seq(
      test in assembly := {},
      version := sys.env.getOrElse(
        "SVC_VERSION",
        s"0.1.${Instant.now.getEpochSecond}"
      ),
      coverageEnabled in test := true,
      coverageEnabled in IntegrationTest := true,
      coverageMinimum := 90,
      coverageFailOnMinimum := true,
      updateOptions := updateOptions
        .value
        .withCachedResolution(true),
      parallelExecution in IntegrationTest := false,
      organization := "com.urlable",
      scalaVersion := "2.11.7",
      scalacOptions := Seq(
        "-unchecked",
        "-deprecation",
        "-encoding",
        "utf8"
      ),
      libraryDependencies ++=
        Seq(
          "org.testobjects" %% "test-objects-for-scala" % "0.1.4" % "test,it",
          "org.scalatest" %% "scalatest" % "2.2.5" % "test,it",
          "org.mockito" % "mockito-core" % "1.10.19" % "test,it",
          "com.iheart" %% "ficus" % ficusVersion % "test,it",
          "com.softwaremill.macwire" %% "macros" % "2.2.2" % "provided"
        )
    ) ++
      Defaults
        .itSettings

  lazy val root =
    Project(
      id = "root",
      base = file(".")
    )
      .aggregate(
        core,
        dbAdapter,
        server,
        restApi
      )
      .settings(
        commonSettings
      )
      .configs(IntegrationTest)
      .dependsOn(
        core,
        dbAdapter,
        server,
        restApi
      )
      .enablePlugins(JavaServerAppPackaging)

  lazy val core =
    Project(
      id = "core",
      base = file("core")
    )
      .settings(commonSettings: _*)
      .configs(IntegrationTest)


  lazy val dbAdapter =
    Project(
      id = "db-adapter",
      base = file("db-adapter")
    )
      .settings(
        commonSettings ++
          Seq(
            libraryDependencies ++= Seq(
              "ch.qos.logback" % "logback-classic" % logbackClassicVersion
            )
          ): _*
      )
      .configs(IntegrationTest extend Test)
      .dependsOn(
        core % "test->test;it->test;compile"
      )

  lazy val server =
    Project(
      id = "server",
      base = file("server")
    )
      .settings(commonSettings: _*)
      .configs(IntegrationTest)
      .dependsOn(
        restApi,
        core % "test->test;it->test"
      )

  lazy val restApi =
    Project(
      id = "rest-api",
      base = file("rest-api")
    )
      .settings(
        commonSettings ++
          Seq(
            libraryDependencies ++= Seq(
              "com.iheart" %% "ficus" % ficusVersion,
              "ch.qos.logback" % "logback-classic" % logbackClassicVersion
            )
          ): _*
      )
      .configs(IntegrationTest)
      .dependsOn(
        dbAdapter,
        core % "test->test;it->test;compile"
      )

}
