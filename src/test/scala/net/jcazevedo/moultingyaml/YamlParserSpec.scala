package net.jcazevedo.moultingyaml

import java.net.URLDecoder
import net.jcazevedo.moultingyaml._
import org.specs2.execute.Result
import org.specs2.matcher.MatchResult
import org.specs2.mutable._
import scala.io.Source
import scala.util.{Failure, Success, Try}

class YamlParserSpec extends Specification {
  def getResourceURL(resource: String): String =
    URLDecoder.decode(getClass.getResource(resource).getFile, "UTF-8")

  def withYaml[T](filename: String)(f: YamlValue => MatchResult[T]): Result = {
    Try(Source.fromFile(getResourceURL(filename)).mkString.parseYaml) match {
      case Success(yaml) => f(yaml)
      case Failure(error) => org.specs2.execute.Failure("Unable to parse YAML")
    }
  }

  "A YamlParser" should {
    "correctly parse sequences of scalars" !
    withYaml("/ex1.yaml") { yaml =>
      yaml mustEqual YamlArray(
        Vector(
          YamlString("Mark McGwire"),
          YamlString("Sammy Sosa"),
          YamlString("Ken Griffey")))
    }

    "correctly parse mappings of scalars to scalars" !
    withYaml("/ex2.yaml") { yaml =>
      yaml mustEqual YamlObject(
        Map(
          "hr" -> YamlNumber(65),
          "avg" -> YamlNumber(0.278),
          "rbi" -> YamlNumber(147)))
    }

    "correctly parse mappings of scalars to sequences" !
    withYaml("/ex3.yaml") { yaml =>
      yaml mustEqual YamlObject(
        Map(
          "american" -> YamlArray(
            Vector(
              YamlString("Boston Red Sox"),
              YamlString("Detroit Tigers"),
              YamlString("New York Yankees"))),
          "national" -> YamlArray(
            Vector(
              YamlString("New York Mets"),
              YamlString("Chicago Cubs"),
              YamlString("Atlanta Braves")))))
    }

    "correctly parse sequences of mappings" !
    withYaml("/ex4.yaml") { yaml =>
      yaml mustEqual YamlArray(
        Vector(
          YamlObject(
            Map(
              "name" -> YamlString("Mark McGwire"),
              "hr" -> YamlNumber(65),
              "avg" -> YamlNumber(0.278))),
          YamlObject(
            Map(
              "name" -> YamlString("Sammy Sosa"),
              "hr" -> YamlNumber(63),
              "avg" -> YamlNumber(0.288)))))
    }

    "correctly parse sequences of sequences" !
    withYaml("/ex5.yaml") { yaml =>
      yaml mustEqual YamlArray(
        Vector(
          YamlArray(
            Vector(
              YamlString("name"),
              YamlString("hr"),
              YamlString("avg"))),
          YamlArray(
            Vector(
              YamlString("Mark McGwire"),
              YamlNumber(65),
              YamlNumber(0.278))),
          YamlArray(
            Vector(
              YamlString("Sammy Sosa"),
              YamlNumber(63),
              YamlNumber(0.288)))))
    }

    "correctly parse mappings of mappings" !
    withYaml("/ex6.yaml") { yaml =>
      yaml mustEqual YamlObject(
        Map(
          "Mark McGwire" -> YamlObject(
            Map(
              "hr" -> YamlNumber(65),
              "avg" -> YamlNumber(0.278))),
          "Sammy Sosa" -> YamlObject(
            Map(
              "hr" -> YamlNumber(63),
              "avg" -> YamlNumber(0.288)))))
    }
  }
}
