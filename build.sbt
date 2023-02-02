lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .settings(
    name := """play-scala-hello-world-tutorial""",
    organization := "com.example",
    version := "1.0-SNAPSHOT",
    scalaVersion := "2.13.10",
    libraryDependencies ++= Seq(
      guice,
      "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test,
      // https://mvnrepository.com/artifact/com.pauldijou/jwt-play
      "com.pauldijou" %% "jwt-play" % "5.0.0",
      // https://mvnrepository.com/artifact/com.pauldijou/jwt-core
      "com.pauldijou" %% "jwt-core" % "5.0.0",
      "com.auth0" % "jwks-rsa" % "0.6.1"
    ),
    scalacOptions ++= Seq(
      "-feature",
      "-deprecation",
      "-Xfatal-warnings"
    )
  )
