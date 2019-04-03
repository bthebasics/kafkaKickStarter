name := "kafkaKickStarter"

version := "0.1"

scalaVersion := "2.11.12"

libraryDependencies += "com.typesafe" % "config" % "1.3.2"
libraryDependencies += "org.apache.kafka" % "kafka-clients" % "1.0.0"
libraryDependencies += "org.apache.logging.log4j" % "log4j-api" % "2.11.1"
libraryDependencies += "org.apache.logging.log4j" % "log4j-core" % "2.11.1"
libraryDependencies +=  "org.apache.logging.log4j" %% "log4j-api-scala" % "11.0"


libraryDependencies ++= Seq("org.slf4j" % "slf4j-api" % "1.7.5",
  "org.slf4j" % "slf4j-simple" % "1.7.5", "org.clapper" %% "grizzled-slf4j" % "1.3.3")
libraryDependencies += "com.fasterxml.jackson.core" % "jackson-core" % "2.9.2"
libraryDependencies += "com.fasterxml.jackson.core" % "jackson-databind" % "2.9.5"
// https://mvnrepository.com/artifact/org.apache.kafka/connect-json
libraryDependencies += "org.apache.kafka" % "connect-json" % "1.1.0"

libraryDependencies += "org.openweathermap.java-api" % "api-model" % "1.2"



resolvers += Resolver.url("mvnrepository Repository (ivy)", url("https://mvnrepository.com/artifact/"))(Resolver.ivyStylePatterns)
