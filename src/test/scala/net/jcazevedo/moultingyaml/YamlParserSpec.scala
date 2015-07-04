package net.jcazevedo.moultingyaml

import com.github.nscala_time.time.Imports._
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

  def withYaml(filename: String)(f: YamlValue => Result): Result = {
    Try(Source.fromFile(getResourceURL(filename)).mkString.parseYaml) match {
      case Success(yaml) => f(yaml)
      case Failure(error) =>
        error.printStackTrace()
        org.specs2.execute.Failure("Unable to parse YAML")
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
          YamlString("hr") -> YamlNumber(65),
          YamlString("avg") -> YamlNumber(0.278),
          YamlString("rbi") -> YamlNumber(147)))
    }

    "correctly parse mappings of scalars to sequences" !
    withYaml("/ex3.yaml") { yaml =>
      yaml mustEqual YamlObject(
        Map(
          YamlString("american") -> YamlArray(
            Vector(
              YamlString("Boston Red Sox"),
              YamlString("Detroit Tigers"),
              YamlString("New York Yankees"))),
          YamlString("national") -> YamlArray(
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
              YamlString("name") -> YamlString("Mark McGwire"),
              YamlString("hr") -> YamlNumber(65),
              YamlString("avg") -> YamlNumber(0.278))),
          YamlObject(
            Map(
              YamlString("name") -> YamlString("Sammy Sosa"),
              YamlString("hr") -> YamlNumber(63),
              YamlString("avg") -> YamlNumber(0.288)))))
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
          YamlString("Mark McGwire") -> YamlObject(
            Map(
              YamlString("hr") -> YamlNumber(65),
              YamlString("avg") -> YamlNumber(0.278))),
          YamlString("Sammy Sosa") -> YamlObject(
            Map(
              YamlString("hr") -> YamlNumber(63),
              YamlString("avg") -> YamlNumber(0.288)))))
    }

    "correctly parse aliased nodes" !
    withYaml("/ex7.yaml") { yaml =>
      yaml mustEqual YamlObject(
        Map(
          YamlString("hr") ->
            YamlArray(
              Vector(
                YamlString("Mark McGwire"),
                YamlString("Sammy Sosa"))),
          YamlString("rbi") ->
            YamlArray(
              Vector(
                YamlString("Sammy Sosa"),
                YamlString("Ken Griffey")))))
    }

    "correctly parse mappings between sequences" !
    withYaml("/ex8.yaml") { yaml =>
      yaml mustEqual YamlObject(
        Map(
          YamlArray(
            Vector(
              YamlString("Detroit Tigers"),
              YamlString("Chicago cubs"))) ->
            YamlArray(
              Vector(
                YamlDate("2001-07-23".toLocalDate))),
          YamlArray(
            Vector(
              YamlString("New York Yankees"),
              YamlString("Atlanta Braves"))) ->
              YamlArray(
                Vector(
                  YamlDate("2001-07-02".toLocalDate),
                  YamlDate("2001-08-12".toLocalDate),
                  YamlDate("2001-08-14".toLocalDate)))))
    }

    "correctly parse in-line nested mapping" !
    withYaml("/ex9.yaml") { yaml =>
      yaml mustEqual YamlArray(
        Vector(
          YamlObject(
            Map(
              YamlString("item") -> YamlString("Super Hoop"),
              YamlString("quantity") -> YamlNumber(1))),
          YamlObject(
            Map(
              YamlString("item") -> YamlString("Basketball"),
              YamlString("quantity") -> YamlNumber(4))),
          YamlObject(
            Map(
              YamlString("item") -> YamlString("Big Shoes"),
              YamlString("quantity") -> YamlNumber(1)))))
    }

    "correctly preserve new lines in literals" !
    withYaml("/ex10.yaml") { yaml =>
      yaml mustEqual YamlString("""\//||\/||
// ||  ||__
""")
    }

    "correctly replace newlines by spaces in plain scalar" !
    withYaml("/ex11.yaml") { yaml =>
      yaml mustEqual YamlString("Mark McGwire's year was crippled by a knee injury.")
    }

    "correctly parse folded new lines" !
    withYaml("/ex12.yaml") { yaml =>
      yaml mustEqual YamlString("""Sammy Sosa completed another fine season with great stats.

  63 Home Runs
  0.288 Batting Average

What a year!
""")
    }

    "correctly determine scope by indentation" !
    withYaml("/ex13.yaml") { yaml =>
      yaml mustEqual YamlObject(
        Map(
          YamlString("name") ->
            YamlString("Mark McGwire"),
          YamlString("accomplishment") ->
            YamlString("Mark set a major league home run record in 1998.\n"),
          YamlString("stats") ->
            YamlString("65 Home Runs\n0.278 Batting Average\n")))
    }

    "correctly parse quoted scalars" !
    withYaml("/ex14.yaml") { yaml =>
      yaml mustEqual YamlObject(
        Map(
          YamlString("unicode") ->
            YamlString("Sosa did fine.\u263A"),
          YamlString("control") ->
            YamlString("\b1998\t1999\t2000\n"),
          YamlString("hexesc") ->
            YamlString("\u0013\u0010 is \r\n"),
          YamlString("single") ->
            YamlString(""""Howdy!" he cried."""),
          YamlString("quoted") ->
            YamlString(" # not a 'comment'."),
          YamlString("tie-fighter") ->
            YamlString("""|\-*-/|""")))
    }

    "correctly parse multi-line flow scalars" !
    withYaml("/ex15.yaml") { yaml =>
      yaml mustEqual YamlObject(
        Map(
          YamlString("plain") ->
            YamlString("This unquoted scalar spans many lines."),
          YamlString("quoted") ->
            YamlString("So does this quoted scalar.\n")))
    }

    "correctly parse integers" !
    withYaml("/ex16.yaml") { yaml =>
      yaml mustEqual YamlObject(
        Map(
          YamlString("canonical") ->
            YamlNumber(12345),
          YamlString("decimal") ->
            YamlNumber(12345),
          YamlString("sexagesimal") ->
            YamlNumber(12345),
          YamlString("octal") ->
            YamlNumber(12),
          YamlString("hexadecimal") ->
            YamlNumber(12)))
    }
  }
}
