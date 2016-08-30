import scalariform.formatter.preferences._

name := "moultingyaml"

organization := "net.jcazevedo"

version := "1.0.0-SNAPSHOT"

scalaVersion := "2.11.8"

crossScalaVersions := Seq("2.11.8", "2.10.6")

libraryDependencies ++= Seq(
  "com.github.nscala-time" %% "nscala-time"   % "2.12.0",
  "org.scala-lang"          % "scala-reflect" % scalaVersion.value,
  "org.yaml"                % "snakeyaml"     % "1.17",
  "org.specs2"             %% "specs2-core"   % "3.8.4"  % "test")

scalacOptions ++= Seq(
  "-deprecation",
  "-unchecked",
  "-feature",
  "-language:implicitConversions") ++
  (CrossVersion.partialVersion(scalaVersion.value) match {
    case Some((2, major)) if major >= 11 => Seq("-Ywarn-unused-import")
    case _ => Seq()
  })

scalariformSettings

ScalariformKeys.preferences := ScalariformKeys.preferences.value
  .setPreference(AlignParameters, true)
  .setPreference(DoubleIndentClassDeclaration, true)

publishMavenStyle := true

publishTo <<= version { v =>
  val nexus = "https://oss.sonatype.org/"
  if (v.trim.endsWith("SNAPSHOT"))
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases"  at nexus + "service/local/staging/deploy/maven2")
}

publishArtifact in Test := false

pomIncludeRepository := { _ => false }

licenses := Seq("MIT License" ->
  url("http://www.opensource.org/licenses/mit-license.php"))

homepage := Some(url("https://github.com/jcazevedo/moultingyaml"))

pomExtra := (
  <scm>
    <url>https://github.com/jcazevedo/moultingyaml.git</url>
    <connection>scm:git:git@github.com:jcazevedo/moultingyaml.git</connection>
  </scm>
  <developers>
    <developer>
      <id>jcazevedo</id>
      <name>Joao Azevedo</name>
      <url>http://jcazevedo.net</url>
    </developer>
  </developers>)
