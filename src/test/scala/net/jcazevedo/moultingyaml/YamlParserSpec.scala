package net.jcazevedo.moultingyaml

import com.github.nscala_time.time.Imports._
import java.net.URLDecoder
import net.jcazevedo.moultingyaml._
import org.specs2.execute.Result
import org.specs2.matcher.MatchResult
import org.specs2.mutable._
import scala.io.Source
import scala.util.{ Failure, Success, Try }

class YamlParserSpec extends Specification {
  def getResourceURL(resource: String): String =
    URLDecoder.decode(getClass.getResource(resource).getFile, "UTF-8")

  def withYaml(filename: String)(f: YamlValue => Result): Result = {
    withYamls(filename) { s => f(s(0)) }
  }

  def withYamls(filename: String)(f: Seq[YamlValue] => Result): Result = {
    Try(Source.fromFile(getResourceURL(filename)).mkString.parseYamls) match {
      case Success(yamls) => f(yamls)
      case Failure(error) =>
        error.printStackTrace()
        org.specs2.execute.Failure("Unable to parse YAML")
    }
  }

  "The provided YAML parser" should {
    "correctly parse sequences of scalars" !
      withYaml("/examples/ex1.yaml") { yaml =>
        yaml mustEqual YamlArray(
          YamlString("Mark McGwire"),
          YamlString("Sammy Sosa"),
          YamlString("Ken Griffey"))
      }

    "correctly parse mappings of scalars to scalars" !
      withYaml("/examples/ex2.yaml") { yaml =>
        yaml mustEqual YamlObject(
          YamlString("hr") -> YamlNumber(65),
          YamlString("avg") -> YamlNumber(0.278),
          YamlString("rbi") -> YamlNumber(147))
      }

    "correctly parse mappings of scalars to sequences" !
      withYaml("/examples/ex3.yaml") { yaml =>
        yaml mustEqual YamlObject(
          YamlString("american") -> YamlArray(
            YamlString("Boston Red Sox"),
            YamlString("Detroit Tigers"),
            YamlString("New York Yankees")),
          YamlString("national") -> YamlArray(
            YamlString("New York Mets"),
            YamlString("Chicago Cubs"),
            YamlString("Atlanta Braves")))
      }

    "correctly parse sequences of mappings" !
      withYaml("/examples/ex4.yaml") { yaml =>
        yaml mustEqual YamlArray(
          YamlObject(
            YamlString("name") -> YamlString("Mark McGwire"),
            YamlString("hr") -> YamlNumber(65),
            YamlString("avg") -> YamlNumber(0.278)),
          YamlObject(
            YamlString("name") -> YamlString("Sammy Sosa"),
            YamlString("hr") -> YamlNumber(63),
            YamlString("avg") -> YamlNumber(0.288)))
      }

    "correctly parse sequences of sequences" !
      withYaml("/examples/ex5.yaml") { yaml =>
        yaml mustEqual YamlArray(
          YamlArray(
            YamlString("name"),
            YamlString("hr"),
            YamlString("avg")),
          YamlArray(
            YamlString("Mark McGwire"),
            YamlNumber(65),
            YamlNumber(0.278)),
          YamlArray(
            YamlString("Sammy Sosa"),
            YamlNumber(63),
            YamlNumber(0.288)))
      }

    "correctly parse mappings of mappings" !
      withYaml("/examples/ex6.yaml") { yaml =>
        yaml mustEqual YamlObject(
          YamlString("Mark McGwire") -> YamlObject(
            YamlString("hr") -> YamlNumber(65),
            YamlString("avg") -> YamlNumber(0.278)),
          YamlString("Sammy Sosa") -> YamlObject(
            YamlString("hr") -> YamlNumber(63),
            YamlString("avg") -> YamlNumber(0.288)))
      }

    "correctly parse aliased nodes" !
      withYaml("/examples/ex7.yaml") { yaml =>
        yaml mustEqual YamlObject(
          YamlString("hr") ->
            YamlArray(
              YamlString("Mark McGwire"),
              YamlString("Sammy Sosa")),
          YamlString("rbi") ->
            YamlArray(
              YamlString("Sammy Sosa"),
              YamlString("Ken Griffey")))
      }

    "correctly parse mappings between sequences" !
      withYaml("/examples/ex8.yaml") { yaml =>
        yaml mustEqual YamlObject(
          YamlArray(
            YamlString("Detroit Tigers"),
            YamlString("Chicago cubs")) ->
            YamlArray(
              YamlDate(new DateTime(
                new DateTime("2001-07-23", DateTimeZone.UTC).getMillis(),
                DateTimeZone.getDefault()))),
          YamlArray(
            YamlString("New York Yankees"),
            YamlString("Atlanta Braves")) ->
            YamlArray(
              YamlDate(new DateTime(
                new DateTime("2001-07-02", DateTimeZone.UTC).getMillis(),
                DateTimeZone.getDefault())),
              YamlDate(new DateTime(
                new DateTime("2001-08-12", DateTimeZone.UTC).getMillis(),
                DateTimeZone.getDefault())),
              YamlDate(new DateTime(
                new DateTime("2001-08-14", DateTimeZone.UTC).getMillis(),
                DateTimeZone.getDefault()))))
      }

    "correctly parse in-line nested mapping" !
      withYaml("/examples/ex9.yaml") { yaml =>
        yaml mustEqual YamlArray(
          YamlObject(
            YamlString("item") -> YamlString("Super Hoop"),
            YamlString("quantity") -> YamlNumber(1)),
          YamlObject(
            YamlString("item") -> YamlString("Basketball"),
            YamlString("quantity") -> YamlNumber(4)),
          YamlObject(
            YamlString("item") -> YamlString("Big Shoes"),
            YamlString("quantity") -> YamlNumber(1)))
      }

    "correctly preserve new lines in literals" !
      withYaml("/examples/ex10.yaml") { yaml =>
        yaml mustEqual YamlString(
          """\//||\/||
            |// ||  ||__
            |""".stripMargin)
      }

    "correctly replace newlines by spaces in plain scalar" !
      withYaml("/examples/ex11.yaml") { yaml =>
        yaml mustEqual YamlString(
          "Mark McGwire's year was crippled by a knee injury.")
      }

    "correctly parse folded new lines" !
      withYaml("/examples/ex12.yaml") { yaml =>
        yaml mustEqual YamlString(
          """Sammy Sosa completed another fine season with great stats.
            |
            |  63 Home Runs
            |  0.288 Batting Average
            |
            |What a year!
            |""".stripMargin)
      }

    "correctly determine scope by indentation" !
      withYaml("/examples/ex13.yaml") { yaml =>
        yaml mustEqual YamlObject(
          YamlString("name") ->
            YamlString("Mark McGwire"),
          YamlString("accomplishment") ->
            YamlString("Mark set a major league home run record in 1998.\n"),
          YamlString("stats") ->
            YamlString("65 Home Runs\n0.278 Batting Average\n"))
      }

    "correctly parse quoted scalars" !
      withYaml("/examples/ex14.yaml") { yaml =>
        yaml mustEqual YamlObject(
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
            YamlString("""|\-*-/|"""))
      }

    "correctly parse multi-line flow scalars" !
      withYaml("/examples/ex15.yaml") { yaml =>
        yaml mustEqual YamlObject(
          YamlString("plain") ->
            YamlString("This unquoted scalar spans many lines."),
          YamlString("quoted") ->
            YamlString("So does this quoted scalar.\n"))
      }

    "correctly parse integers" !
      withYaml("/examples/ex16.yaml") { yaml =>
        yaml mustEqual YamlObject(
          YamlString("canonical") ->
            YamlNumber(12345),
          YamlString("decimal") ->
            YamlNumber(12345),
          YamlString("sexagesimal") ->
            YamlNumber(12345),
          YamlString("octal") ->
            YamlNumber(12),
          YamlString("hexadecimal") ->
            YamlNumber(12))
      }

    "correctly parse large integers" !
      withYaml("/examples/ex16-2.yaml") { yaml =>
        yaml mustEqual YamlObject(
          YamlString("long_canonical") ->
            YamlNumber(21474836470L),
          YamlString("bigint_canonical") ->
            YamlNumber(BigInt("92233720368547758070")))
      }

    "correctly parse floating point numbers" !
      withYaml("/examples/ex17.yaml") { yaml =>
        yaml mustEqual YamlObject(
          YamlString("canonical") ->
            YamlNumber(1230.15),
          YamlString("exponential") ->
            YamlNumber(1230.15),
          YamlString("sexagesimal") ->
            YamlNumber(1230.15),
          YamlString("fixed") ->
            YamlNumber(1230.15))
      }

    "correctly parse miscellaneous scalars" !
      withYaml("/examples/ex18.yaml") { yaml =>
        yaml mustEqual YamlObject(
          YamlNull ->
            YamlNull,
          YamlBoolean(true) ->
            YamlString("y"),
          YamlBoolean(false) ->
            YamlString("n"),
          YamlString("string") ->
            YamlString("12345"))
      }

    "correctly parse timestamps" !
      withYaml("/examples/ex19.yaml") { yaml =>
        yaml mustEqual YamlObject(
          YamlString("canonical") ->
            YamlDate("2001-12-15T02:59:43.1Z".toDateTime),
          YamlString("iso8601") ->
            YamlDate("2001-12-14T21:59:43.10-05:00".toDateTime),
          YamlString("spaced") ->
            YamlDate("2001-12-14T21:59:43.10-05:00".toDateTime),
          YamlString("date") ->
            YamlDate(new DateTime(
              new DateTime("2002-12-14", DateTimeZone.UTC).getMillis(),
              DateTimeZone.getDefault())))
      }

    "correctly parse explicit sets" !
      withYaml("/examples/ex20.yaml") { yaml =>
        yaml mustEqual YamlSet(
          YamlString("Mark McGwire"),
          YamlString("Sammy Sosa"),
          YamlString("Ken Griff"))
      }

    "correctly parse explicit ordered mappings" !
      withYaml("/examples/ex21.yaml") { yaml =>
        yaml mustEqual YamlObject(
          YamlString("Mark McGwire") -> YamlNumber(65),
          YamlString("Sammy Sosa") -> YamlNumber(63),
          YamlString("Ken Griffy") -> YamlNumber(58))
      }

    "correctly parse multiple documents in a stream" !
      withYamls("/examples/ex22.yaml") { yamls =>
        yamls mustEqual Seq(
          YamlArray(
            YamlString("Mark McGwire"),
            YamlString("Sammy Sosa"),
            YamlString("Ken Griffey")),
          YamlArray(
            YamlString("Chicago Cubs"),
            YamlString("St Louis Cardinals")))
      }
  }
}
