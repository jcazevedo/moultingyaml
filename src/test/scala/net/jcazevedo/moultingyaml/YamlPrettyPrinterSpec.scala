package net.jcazevedo.moultingyaml

import org.scalatest.{ FlatSpec, Matchers }

class YamlPrettyPrinterSpec extends FlatSpec with Matchers {
  "The provided YAML prettyprinter" should "pretty print sequences of scalars" in {
    val yaml = YamlArray(
      YamlString("Mark McGwire"),
      YamlString("Sammy Sosa"),
      YamlString("Ken Griffey"))

    yaml.prettyPrint should ===(
      """- Mark McGwire
          |- Sammy Sosa
          |- Ken Griffey
          |""".stripMargin)
  }

  it should "pretty print yaml objects" in {
    val yaml = YamlObject(
      YamlString("hr") -> YamlNumber(65),
      YamlString("avg") -> YamlNumber(0.278),
      YamlString("rbi") -> YamlNumber(147))

    yaml.prettyPrint should ===(
      """hr: 65
          |avg: 0.278
          |rbi: 147
          |""".stripMargin)
  }

  it should "pretty print yaml objects with sequences on it" in {
    val yaml = YamlObject(
      YamlString("american") -> YamlArray(
        YamlString("Boston Red Sox"),
        YamlString("Detroit Tigers"),
        YamlString("New York Yankees")),
      YamlString("national") -> YamlArray(
        YamlString("New York Mets"),
        YamlString("Chicago Cubs"),
        YamlString("Atlanta Braves")))

    yaml.prettyPrint should ===(
      """american:
          |- Boston Red Sox
          |- Detroit Tigers
          |- New York Yankees
          |national:
          |- New York Mets
          |- Chicago Cubs
          |- Atlanta Braves
          |""".stripMargin)
  }

  it should "pretty print sequences of mappings" in {
    val yaml = YamlArray(
      YamlObject(
        YamlString("name") -> YamlString("Mark McGwire"),
        YamlString("hr") -> YamlNumber(65),
        YamlString("avg") -> YamlNumber(0.278)),
      YamlObject(
        YamlString("name") -> YamlString("Sammy Sosa"),
        YamlString("hr") -> YamlNumber(63),
        YamlString("avg") -> YamlNumber(0.288)))

    yaml.prettyPrint should ===(
      """- name: Mark McGwire
          |  hr: 65
          |  avg: 0.278
          |- name: Sammy Sosa
          |  hr: 63
          |  avg: 0.288
          |""".stripMargin)
  }

  it should "pretty print sequences of sequences" in {
    val yaml = YamlArray(
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

    yaml.prettyPrint should ===(
      """- - name
          |  - hr
          |  - avg
          |- - Mark McGwire
          |  - 65
          |  - 0.278
          |- - Sammy Sosa
          |  - 63
          |  - 0.288
          |""".stripMargin)
  }

  it should "pretty print mappings of mappings" in {
    val yaml = YamlObject(
      YamlString("Mark McGwire") -> YamlObject(
        YamlString("hr") -> YamlNumber(65),
        YamlString("avg") -> YamlNumber(0.278)),
      YamlString("Sammy Sosa") -> YamlObject(
        YamlString("hr") -> YamlNumber(63),
        YamlString("avg") -> YamlNumber(0.288)))

    yaml.prettyPrint should ===(
      """Mark McGwire:
          |  hr: 65
          |  avg: 0.278
          |Sammy Sosa:
          |  hr: 63
          |  avg: 0.288
          |""".stripMargin)
  }

  it should "pretty print mappings between sequences" in {
    val yaml = YamlObject(
      YamlArray(
        YamlString("Detroit Tigers"),
        YamlString("Chicago cubs")) ->
        YamlArray(
          YamlString("2001-07-23")),
      YamlArray(
        YamlString("New York Yankees"),
        YamlString("Atlanta Braves")) ->
        YamlArray(
          YamlString("2001-07-02"),
          YamlString("2001-08-12"),
          YamlString("2001-08-14")))

    yaml.prettyPrint should ===(
      """? - Detroit Tigers
          |  - Chicago cubs
          |: - '2001-07-23'
          |? - New York Yankees
          |  - Atlanta Braves
          |: - '2001-07-02'
          |  - '2001-08-12'
          |  - '2001-08-14'
          |""".stripMargin)
  }

  it should "pretty print strings with newlines" in {
    val yaml1 = YamlString(
      """\//||\/||
          |// ||  ||__
          |""".stripMargin)

    yaml1.prettyPrint should ===(
      """||
          |  \//||\/||
          |  // ||  ||__
          |""".stripMargin)

    val yaml2 = YamlString(
      """Sammy Sosa completed another fine season with great stats.
          |
          |  63 Home Runs
          |  0.288 Batting Average
          |
          |What a year!
          |""".stripMargin)

    yaml2.prettyPrint should ===(
      """||
          |  Sammy Sosa completed another fine season with great stats.
          |
          |    63 Home Runs
          |    0.288 Batting Average
          |
          |  What a year!
          |""".stripMargin)
  }

  it should "pretty print numbers" in {
    val yaml = YamlObject(
      YamlString("int") ->
        YamlNumber(42),
      YamlString("float") ->
        YamlNumber(0.4555),
      YamlString("long_canonical") ->
        YamlNumber(21474836470L),
      YamlString("bigint_canonical") ->
        YamlNumber(BigInt("92233720368547758070")))

    yaml.prettyPrint should ===(
      """int: 42
          |float: 0.4555
          |long_canonical: 21474836470
          |bigint_canonical: 92233720368547758070
          |""".stripMargin)
  }

  it should "pretty print miscellaneous scalars" in {
    val yaml = YamlObject(
      YamlNull ->
        YamlNull,
      YamlBoolean(true) ->
        YamlString("y"),
      YamlBoolean(false) ->
        YamlString("n"),
      YamlString("string") ->
        YamlString("12345"))

    yaml.prettyPrint should ===(
      """null: null
          |true: y
          |false: n
          |string: '12345'
          |""".stripMargin)
  }

  it should "pretty print explicit sets" in {
    val yaml = YamlSet(
      YamlString("Mark McGwire"),
      YamlString("Sammy Sosa"),
      YamlString("Ken Griff"))

    yaml.prettyPrint should ===(
      """!!set
          |Mark McGwire: null
          |Sammy Sosa: null
          |Ken Griff: null
          |""".stripMargin)
  }

  it should "print according to a provided configuration" in {
    val yaml = YamlObject(
      YamlString("int") ->
        YamlNumber(42),
      YamlString("float") ->
        YamlNumber(0.4555),
      YamlString("long_canonical") ->
        YamlNumber(21474836470L),
      YamlString("bigint_canonical") ->
        YamlNumber(BigInt("92233720368547758070")))

    yaml.print(new SnakeYamlPrinter(scalarStyle = DoubleQuoted)) should ===(
      """"int": !!int "42"
          |"float": !!float "0.4555"
          |"long_canonical": !!int "21474836470"
          |"bigint_canonical": !!int "92233720368547758070"
          |""".stripMargin)
  }

  it should "print with custom scalar style" in {
    val yaml = YamlObject(
      YamlString("int") ->
        YamlNumber(42),
      YamlString("float") ->
        YamlNumber(0.4555),
      YamlString("long_canonical") ->
        YamlNumber(21474836470L),
      YamlString("bigint_canonical") ->
        YamlNumber(BigInt("92233720368547758070")))

    yaml.print(new SnakeYamlPrinter(scalarStyle = DoubleQuoted)) should ===(
      yaml.print(new SnakeYamlPrinter(scalarStyle = ScalarStyle.createStyle('"'))))
  }

  it should "print with default configuration" in {
    val yaml = YamlObject(
      YamlString("int") ->
        YamlNumber(42),
      YamlString("float") ->
        YamlNumber(0.4555),
      YamlString("long_canonical") ->
        YamlNumber(21474836470L),
      YamlString("bigint_canonical") ->
        YamlNumber(BigInt("92233720368547758070")))

    yaml.print should ===(yaml.prettyPrint)
  }

  it should "print can use an implicit YamlPrinter" in {
    val yaml = YamlObject(
      YamlString("int") ->
        YamlNumber(42),
      YamlString("float") ->
        YamlNumber(0.4555),
      YamlString("long_canonical") ->
        YamlNumber(21474836470L),
      YamlString("bigint_canonical") ->
        YamlNumber(BigInt("92233720368547758070")))

    implicit val yamlPrinter = new SnakeYamlPrinter(scalarStyle = DoubleQuoted)

    yaml.print should ===(yaml.print(new SnakeYamlPrinter(scalarStyle = DoubleQuoted)))
  }
}
