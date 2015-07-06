package net.jcazevedo.moultingyaml

import net.jcazevedo.moultingyaml._
import org.specs2.mutable._

class YamlPrettyPrinterSpec extends Specification {
  "The provided YAML prettyprinter" should {
    "pretty print sequences of scalars" in {
      val yaml = YamlArray(
        Vector(
          YamlString("Mark McGwire"),
          YamlString("Sammy Sosa"),
          YamlString("Ken Griffey")))

      yaml.prettyPrint mustEqual """- Mark McGwire
- Sammy Sosa
- Ken Griffey
"""
    }

    "pretty print yaml objects" in {
      val yaml = YamlObject(
        Map(
          YamlString("hr") -> YamlNumber(65),
          YamlString("avg") -> YamlNumber(0.278),
          YamlString("rbi") -> YamlNumber(147)))

      yaml.prettyPrint mustEqual """hr: 65
avg: 0.278
rbi: 147
"""
    }

    "pretty print yaml objects with sequences on it" in {
      val yaml = YamlObject(
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

      yaml.prettyPrint mustEqual """american:
- Boston Red Sox
- Detroit Tigers
- New York Yankees
national:
- New York Mets
- Chicago Cubs
- Atlanta Braves
"""
    }

    "pretty print sequences of mappings" in {
      val yaml = YamlArray(
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

      yaml.prettyPrint mustEqual """- name: Mark McGwire
  hr: 65
  avg: 0.278
- name: Sammy Sosa
  hr: 63
  avg: 0.288
"""
    }

    "pretty print sequences of sequences" in {
      val yaml = YamlArray(
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

      yaml.prettyPrint mustEqual """- - name
  - hr
  - avg
- - Mark McGwire
  - 65
  - 0.278
- - Sammy Sosa
  - 63
  - 0.288
"""
    }

    "pretty print mappings of mappings" in {
      val yaml = YamlObject(
        Map(
          YamlString("Mark McGwire") -> YamlObject(
            Map(
              YamlString("hr") -> YamlNumber(65),
              YamlString("avg") -> YamlNumber(0.278))),
          YamlString("Sammy Sosa") -> YamlObject(
            Map(
              YamlString("hr") -> YamlNumber(63),
              YamlString("avg") -> YamlNumber(0.288)))))

      yaml.prettyPrint mustEqual """Mark McGwire:
  hr: 65
  avg: 0.278
Sammy Sosa:
  hr: 63
  avg: 0.288
"""
    }

    "pretty print mappings between sequences" in {
      val yaml = YamlObject(
        Map(
          YamlArray(
            Vector(
              YamlString("Detroit Tigers"),
              YamlString("Chicago cubs"))) ->
            YamlArray(
              Vector(
                YamlString("2001-07-23"))),
          YamlArray(
            Vector(
              YamlString("New York Yankees"),
              YamlString("Atlanta Braves"))) ->
            YamlArray(
              Vector(
                YamlString("2001-07-02"),
                YamlString("2001-08-12"),
                YamlString("2001-08-14")))))

      yaml.prettyPrint mustEqual """? - Detroit Tigers
  - Chicago cubs
: - '2001-07-23'
? - New York Yankees
  - Atlanta Braves
: - '2001-07-02'
  - '2001-08-12'
  - '2001-08-14'
"""
    }

    "pretty print strings with newlines" in {
      val yaml1 = YamlString("""\//||\/||
// ||  ||__
""")

      yaml1.prettyPrint mustEqual """|
  \//||\/||
  // ||  ||__
"""

      val yaml2 = YamlString(
        """Sammy Sosa completed another fine season with great stats.

  63 Home Runs
  0.288 Batting Average

What a year!
""")

      yaml2.prettyPrint mustEqual """|
  Sammy Sosa completed another fine season with great stats.

    63 Home Runs
    0.288 Batting Average

  What a year!
"""
    }

    "pretty print numbers" in {
      val yaml = YamlObject(Map(
        YamlString("int") ->
          YamlNumber(42),
        YamlString("float") ->
          YamlNumber(0.4555),
        YamlString("long_canonical") ->
          YamlNumber(21474836470L),
        YamlString("bigint_canonical") ->
          YamlNumber(BigInt("92233720368547758070"))))

      yaml.prettyPrint mustEqual """int: 42
float: 0.4555
long_canonical: 21474836470
bigint_canonical: 92233720368547758070
"""
    }

    "pretty print miscellaneous scalars" in {
      val yaml = YamlObject(
        Map(
          YamlNull ->
            YamlNull,
          YamlBoolean(true) ->
            YamlString("y"),
          YamlBoolean(false) ->
            YamlString("n"),
          YamlString("string") ->
            YamlString("12345")))

      yaml.prettyPrint mustEqual """null: null
true: y
false: n
string: '12345'
"""
    }

    "pretty print explicit sets" in {
      val yaml = YamlSet(
        Set(
          YamlString("Mark McGwire"),
          YamlString("Sammy Sosa"),
          YamlString("Ken Griff")))

      yaml.prettyPrint mustEqual """!!set
Mark McGwire: null
Sammy Sosa: null
Ken Griff: null
"""
    }
  }
}
