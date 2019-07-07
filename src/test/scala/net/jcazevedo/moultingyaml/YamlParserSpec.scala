package net.jcazevedo.moultingyaml

import com.github.nscala_time.time.Imports._
import java.net.URLDecoder

import scala.io.Source
import scala.util.{ Failure, Success, Try }

import org.scalactic.source.Position
import org.scalatest.{ FlatSpec, Matchers }

class YamlParserSpec extends FlatSpec with Matchers {
  def getResourceURL(resource: String): String =
    URLDecoder.decode(getClass.getResource(resource).getFile, "UTF-8")

  def withYaml(filename: String)(testFun: YamlValue => Any)(implicit pos: Position): Unit = {
    withYamls(filename) { s => testFun(s(0)) }
  }

  def withYamls(filename: String)(testFun: Seq[YamlValue] => Any)(implicit pos: Position): Unit = {
    Try(Source.fromFile(getResourceURL(filename)).mkString.parseYamls) match {
      case Success(yamls) => testFun(yamls)
      case Failure(error) =>
        error.printStackTrace()
        fail("Unable to parse YAML")
    }
  }

  "The provided YAML parser" should "correctly parse sequences of scalars" in withYaml("/examples/ex1.yaml") { yaml =>
    yaml should ===(YamlArray(
      YamlString("Mark McGwire"),
      YamlString("Sammy Sosa"),
      YamlString("Ken Griffey")))
  }

  it should "correctly parse mappings of scalars to scalars" in withYaml("/examples/ex2.yaml") { yaml =>
    yaml should ===(YamlObject(
      YamlString("hr") -> YamlNumber(65),
      YamlString("avg") -> YamlNumber(0.278),
      YamlString("rbi") -> YamlNumber(147)))
  }

  it should "correctly parse mappings of scalars to sequences" in withYaml("/examples/ex3.yaml") { yaml =>
    yaml should ===(YamlObject(
      YamlString("american") -> YamlArray(
        YamlString("Boston Red Sox"),
        YamlString("Detroit Tigers"),
        YamlString("New York Yankees")),
      YamlString("national") -> YamlArray(
        YamlString("New York Mets"),
        YamlString("Chicago Cubs"),
        YamlString("Atlanta Braves"))))
  }

  it should "correctly parse sequences of mappings" in withYaml("/examples/ex4.yaml") { yaml =>
    yaml should ===(YamlArray(
      YamlObject(
        YamlString("name") -> YamlString("Mark McGwire"),
        YamlString("hr") -> YamlNumber(65),
        YamlString("avg") -> YamlNumber(0.278)),
      YamlObject(
        YamlString("name") -> YamlString("Sammy Sosa"),
        YamlString("hr") -> YamlNumber(63),
        YamlString("avg") -> YamlNumber(0.288))))
  }

  it should "correctly parse sequences of sequences" in withYaml("/examples/ex5.yaml") { yaml =>
    yaml should ===(YamlArray(
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
        YamlNumber(0.288))))
  }

  it should "correctly parse mappings of mappings" in withYaml("/examples/ex6.yaml") { yaml =>
    yaml should ===(YamlObject(
      YamlString("Mark McGwire") -> YamlObject(
        YamlString("hr") -> YamlNumber(65),
        YamlString("avg") -> YamlNumber(0.278)),
      YamlString("Sammy Sosa") -> YamlObject(
        YamlString("hr") -> YamlNumber(63),
        YamlString("avg") -> YamlNumber(0.288))))
  }

  it should "correctly parse aliased nodes" in withYaml("/examples/ex7.yaml") { yaml =>
    yaml should ===(YamlObject(
      YamlString("hr") ->
        YamlArray(
          YamlString("Mark McGwire"),
          YamlString("Sammy Sosa")),
      YamlString("rbi") ->
        YamlArray(
          YamlString("Sammy Sosa"),
          YamlString("Ken Griffey"))))
  }

  it should "correctly parse mappings between sequences" in withYaml("/examples/ex8.yaml") { yaml =>
    yaml should ===(YamlObject(
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
            DateTimeZone.getDefault())))))
  }

  it should "correctly parse in-line nested mapping" in withYaml("/examples/ex9.yaml") { yaml =>
    yaml should ===(YamlArray(
      YamlObject(
        YamlString("item") -> YamlString("Super Hoop"),
        YamlString("quantity") -> YamlNumber(1)),
      YamlObject(
        YamlString("item") -> YamlString("Basketball"),
        YamlString("quantity") -> YamlNumber(4)),
      YamlObject(
        YamlString("item") -> YamlString("Big Shoes"),
        YamlString("quantity") -> YamlNumber(1))))
  }

  it should "correctly preserve new lines in literals" in withYaml("/examples/ex10.yaml") { yaml =>
    yaml should ===(YamlString(
      """\//||\/||
            |// ||  ||__
            |""".stripMargin))
  }

  it should "correctly replace newlines by spaces in plain scalar" in withYaml("/examples/ex11.yaml") { yaml =>
    yaml should ===(YamlString("Mark McGwire's year was crippled by a knee injury."))
  }

  it should "correctly parse folded new lines" in withYaml("/examples/ex12.yaml") { yaml =>
    yaml should ===(YamlString(
      """Sammy Sosa completed another fine season with great stats.
            |
            |  63 Home Runs
            |  0.288 Batting Average
            |
            |What a year!
            |""".stripMargin))
  }

  it should "correctly determine scope by indentation" in withYaml("/examples/ex13.yaml") { yaml =>
    yaml should ===(YamlObject(
      YamlString("name") ->
        YamlString("Mark McGwire"),
      YamlString("accomplishment") ->
        YamlString("Mark set a major league home run record in 1998.\n"),
      YamlString("stats") ->
        YamlString("65 Home Runs\n0.278 Batting Average\n")))
  }

  it should "correctly parse quoted scalars" in withYaml("/examples/ex14.yaml") { yaml =>
    yaml should ===(YamlObject(
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

  it should "correctly parse multi-line flow scalars" in withYaml("/examples/ex15.yaml") { yaml =>
    yaml should ===(YamlObject(
      YamlString("plain") ->
        YamlString("This unquoted scalar spans many lines."),
      YamlString("quoted") ->
        YamlString("So does this quoted scalar.\n")))
  }

  it should "correctly parse integers" in withYaml("/examples/ex16.yaml") { yaml =>
    yaml should ===(YamlObject(
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

  it should "correctly parse large integers" in withYaml("/examples/ex16-2.yaml") { yaml =>
    yaml should ===(YamlObject(
      YamlString("long_canonical") ->
        YamlNumber(21474836470L),
      YamlString("bigint_canonical") ->
        YamlNumber(BigInt("92233720368547758070"))))
  }

  it should "correctly parse floating point numbers" in withYaml("/examples/ex17.yaml") { yaml =>
    yaml should ===(YamlObject(
      YamlString("canonical") ->
        YamlNumber(1230.15),
      YamlString("exponential") ->
        YamlNumber(1230.15),
      YamlString("sexagesimal") ->
        YamlNumber(1230.15),
      YamlString("fixed") ->
        YamlNumber(1230.15)))
  }

  it should "correctly parse miscellaneous scalars" in withYaml("/examples/ex18.yaml") { yaml =>
    yaml should ===(YamlObject(
      YamlNull ->
        YamlNull,
      YamlBoolean(true) ->
        YamlString("y"),
      YamlBoolean(false) ->
        YamlString("n"),
      YamlString("string") ->
        YamlString("12345")))
  }

  it should "correctly parse timestamps" in withYaml("/examples/ex19.yaml") { yaml =>
    yaml should ===(YamlObject(
      YamlString("canonical") ->
        YamlDate("2001-12-15T02:59:43.1Z".toDateTime),
      YamlString("iso8601") ->
        YamlDate("2001-12-14T21:59:43.10-05:00".toDateTime),
      YamlString("spaced") ->
        YamlDate("2001-12-14T21:59:43.10-05:00".toDateTime),
      YamlString("date") ->
        YamlDate(new DateTime(
          new DateTime("2002-12-14", DateTimeZone.UTC).getMillis(),
          DateTimeZone.getDefault()))))
  }

  it should "correctly parse explicit sets" in withYaml("/examples/ex20.yaml") { yaml =>
    yaml should ===(YamlSet(
      YamlString("Mark McGwire"),
      YamlString("Sammy Sosa"),
      YamlString("Ken Griff")))
  }

  it should "correctly parse explicit ordered mappings" in withYaml("/examples/ex21.yaml") { yaml =>
    yaml should ===(YamlObject(
      YamlString("Mark McGwire") -> YamlNumber(65),
      YamlString("Sammy Sosa") -> YamlNumber(63),
      YamlString("Ken Griffy") -> YamlNumber(58)))
  }

  it should "correctly parse multiple documents in a stream" in withYamls("/examples/ex22.yaml") { yamls =>
    yamls should ===(Seq(
      YamlArray(
        YamlString("Mark McGwire"),
        YamlString("Sammy Sosa"),
        YamlString("Ken Griffey")),
      YamlArray(
        YamlString("Chicago Cubs"),
        YamlString("St Louis Cardinals"))))
  }
}
