import sbt.Project.projectToRef

lazy val scalaV = "2.11.7"
lazy val clients = Seq(client)

lazy val server = (project in file("")).settings(
  scalaVersion := scalaV,
  scalaJSProjects := clients,
  pipelineStages := Seq(scalaJSProd),

  resolvers ++= Seq(Resolver.mavenLocal,
    "Sonatype snapshots repository" at "https://oss.sonatype.org/content/repositories/snapshots/",
    "Pablo repo" at "https://raw.github.com/fernandezpablo85/scribe-java/mvn-repo/",
    "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"
  ),
  libraryDependencies ++= Seq(
    "org.pac4j" %% "play-pac4j-scala" % "2.0.1-SNAPSHOT",
    "org.pac4j" % "pac4j-oauth" % "1.8.2",
    "com.vmunier" %% "play-scalajs-scripts" % "0.3.0",
    "com.typesafe.play" %% "play-slick" % "1.1.1",
    "com.typesafe.play" %% "play-slick-evolutions" % "1.1.1",
    "com.h2database" % "h2" % "1.4.187",
    cache,
    ws,
    specs2 % Test
  ),
  routesGenerator := InjectedRoutesGenerator
).enablePlugins(PlayScala).
  aggregate(clients.map(projectToRef): _*)
  //  dependsOn(sharedJvm)

lazy val client = (project in file("client")).settings(
  scalaVersion := scalaV,
  persistLauncher := true,
  persistLauncher in Test := false,
  libraryDependencies ++= Seq(
    "org.scala-js" %%% "scalajs-dom" % "0.8.0"
  )
).enablePlugins(ScalaJSPlugin, ScalaJSPlay)

onLoad in Global := (Command.process("project server", _: State)) compose (onLoad in Global).value
