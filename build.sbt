import scalariform.formatter.preferences._

name := "moultingyaml"

organization := "net.jcazevedo"

version := "0.4.1-SNAPSHOT"

scalaVersion := "2.12.0"

crossScalaVersions := Seq("2.12.0", "2.11.8", "2.10.6")

libraryDependencies ++= Seq(
  "com.github.nscala-time" %% "nscala-time"   % "2.14.0",
  "org.scala-lang"          % "scala-reflect" % scalaVersion.value,
  "org.yaml"                % "snakeyaml"     % "1.17",
  "org.specs2"             %% "specs2-core"   % "3.8.6"  % "test")

scalacOptions ++= Seq(
  "-deprecation",
  "-unchecked",
  "-feature",
  "-language:implicitConversions") ++
  (CrossVersion.partialVersion(scalaVersion.value) match {
    case Some((2, major)) if major >= 11 => Seq("-Ywarn-unused-import")
    case _ => Seq()
   })

scalacOptions in (Compile, console) ~= (_ filterNot (_ == "-Ywarn-unused-import"))
scalacOptions in (Test, console) := (scalacOptions in (Compile, console)).value

scalariformPreferences := scalariformPreferences.value
  .setPreference(AlignParameters, true)
  .setPreference(DoubleIndentConstructorArguments, true)

publishMavenStyle := true

publishTo := Some(
  if (version.value.trim.endsWith("SNAPSHOT"))
    "snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"
  else
    "releases" at "https://oss.sonatype.org/service/local/staging/deploy/maven2")

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
