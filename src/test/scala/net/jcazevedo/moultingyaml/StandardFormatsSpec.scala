package net.jcazevedo.moultingyaml

import org.specs2.mutable._

class StandardFormatsSpec extends Specification with StandardFormats
    with BasicFormats {

  "The optionFormat" should {

    "convert None to YamlNull" in {
      None.asInstanceOf[Option[Int]].toYaml mustEqual YamlNull
    }

    "convert YamlNull to None" in {
      YamlNull.convertTo[Option[Int]] mustEqual None
    }

    "convert Some(Hello) to YamlString(Hello)" in {
      Some("Hello").asInstanceOf[Option[String]].toYaml mustEqual
        YamlString("Hello")
    }

    "convert YamlString(Hello) to Some(Hello)" in {
      YamlString("Hello").convertTo[Option[String]] mustEqual Some("Hello")
    }
  }

  "The eitherFormat" should {
    val a: Either[Int, String] = Left(42)
    val b: Either[Int, String] = Right("Hello")

    "convert the left side of an Either value to a YamlValue" in {
      a.toYaml mustEqual YamlNumber(42)
    }

    "convert the right side of an Either value to a YamlValue" in {
      b.toYaml mustEqual YamlString("Hello")
    }

    "convert the left side of an Either value from a YamlValue" in {
      YamlNumber(42).convertTo[Either[Int, Boolean]] mustEqual Left(42)
    }

    "convert the right side of an Either value from a YamlValue" in {
      YamlString("Hello").convertTo[Either[Int, String]] mustEqual
        Right("Hello")
    }

    "fail when a YamlValue can be parsed to both sides of an Either" in {
      YamlNumber(42).convertTo[Either[Int, Long]] must
        throwA[DeserializationException]
    }

    "fail when a YamlValue cannot be parsed to any side of an Either" in {
      YamlString("Hello").convertTo[Either[Int, Long]] must
        throwA[DeserializationException]
    }
  }

  "The tuple2Format" should {
    val yaml = YamlArray(YamlNumber(42), YamlNumber(4.2))

    "convert (42, 4.2) to a YamlArray" in {
      (42, 4.2).toYaml mustEqual yaml
    }

    "be able to convert a YamlArray to a (Int, Double)]" in {
      yaml.convertTo[(Int, Double)] mustEqual (42, 4.2)
    }
  }

  "The tuple3Format" should {
    val yaml = YamlArray(YamlNumber(42), YamlNumber(4.2), YamlNumber(3))

    "convert (42, 4.2, 3) to a YamlArray" in {
      (42, 4.2, 3).toYaml mustEqual yaml
    }

    "be able to convert a YamlArray to a (Int, Double, Int)]" in {
      yaml.convertTo[(Int, Double, Int)] mustEqual (42, 4.2, 3)
    }
  }

  "The tuple4Format" should {
    val yaml = YamlArray(YamlNumber(42), YamlNumber(4.2), YamlNumber(3),
      YamlNumber(4))

    "convert (42, 4.2, 3, 4) to a YamlArray" in {
      (42, 4.2, 3, 4).toYaml mustEqual yaml
    }

    "be able to convert a YamlArray to a (Int, Double, Int, Int)]" in {
      yaml.convertTo[(Int, Double, Int, Int)] mustEqual (42, 4.2, 3, 4)
    }
  }

  "The tuple5Format" should {
    val yaml = YamlArray(YamlNumber(42), YamlNumber(4.2), YamlNumber(3),
      YamlNumber(4), YamlNumber(5))

    "convert (42, 4.2, 3, 4, 5) to a YamlArray" in {
      (42, 4.2, 3, 4, 5).toYaml mustEqual yaml
    }

    "be able to convert a YamlArray to a (Int, Double, Int, Int, Int)]" in {
      yaml.convertTo[(Int, Double, Int, Int, Int)] mustEqual (42, 4.2, 3, 4, 5)
    }
  }

  "The tuple6Format" should {
    val yaml = YamlArray(YamlNumber(42), YamlNumber(4.2), YamlNumber(3),
      YamlNumber(4), YamlNumber(5), YamlNumber(6))

    "convert (42, 4.2, 3, 4, 5, 6) to a YamlArray" in {
      (42, 4.2, 3, 4, 5, 6).toYaml mustEqual yaml
    }

    "be able to convert a YamlArray to a (Int, Double, Int, Int, Int, Int)]" in {
      yaml.convertTo[(Int, Double, Int, Int, Int, Int)] mustEqual
        (42, 4.2, 3, 4, 5, 6)
    }
  }

  "The tuple7Format" should {
    val yaml = YamlArray(YamlNumber(42), YamlNumber(4.2), YamlNumber(3),
      YamlNumber(4), YamlNumber(5), YamlNumber(6), YamlNumber(7))

    "convert (42, 4.2, 3, 4, 5, 6, 7) to a YamlArray" in {
      (42, 4.2, 3, 4, 5, 6, 7).toYaml mustEqual yaml
    }

    "be able to convert a YamlArray to a (Int, Double, Int, Int, Int, Int, Int)]" in {
      yaml.convertTo[(Int, Double, Int, Int, Int, Int, Int)] mustEqual
        (42, 4.2, 3, 4, 5, 6, 7)
    }
  }
}
