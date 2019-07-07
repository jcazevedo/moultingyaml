package net.jcazevedo.moultingyaml

import org.scalatest.FlatSpec
import org.scalatest.Matchers._

class StandardFormatsSpec extends FlatSpec with StandardFormats
    with BasicFormats {

  "The optionFormat" should "convert None to YamlNull" in {
    None.asInstanceOf[Option[Int]].toYaml should ===(YamlNull)
  }

  it should "convert YamlNull to None" in {
    YamlNull.convertTo[Option[Int]] should ===(None)
  }

  it should "convert Some(Hello) to YamlString(Hello)" in {
    Some("Hello").asInstanceOf[Option[String]].toYaml should ===(YamlString("Hello"))
  }

  it should "convert YamlString(Hello) to Some(Hello)" in {
    YamlString("Hello").convertTo[Option[String]] should ===(Some("Hello"))
  }

  {
    val eitherA: Either[Int, String] = Left(42)
    val eitherB: Either[Int, String] = Right("Hello")

    "The eitherFormat" should "convert the left side of an Either value to a YamlValue" in {
      eitherA.toYaml should ===(YamlNumber(42))
    }

    it should "convert the right side of an Either value to a YamlValue" in {
      eitherB.toYaml should ===(YamlString("Hello"))
    }

    it should "convert the left side of an Either value from a YamlValue" in {
      YamlNumber(42).convertTo[Either[Int, String]] should ===(Left(42))
    }

    it should "convert the right side of an Either value from a YamlValue" in {
      YamlString("Hello").convertTo[Either[Int, String]] should ===(Right("Hello"))
    }

    it should "fail when a YamlValue can be parsed to both sides of an Either" in {
      a[DeserializationException] should be thrownBy {
        YamlNumber(42).convertTo[Either[Int, Long]]
      }
    }

    it should "fail when a YamlValue cannot be parsed to any side of an Either" in {
      a[DeserializationException] should be thrownBy {
        YamlString("Hello").convertTo[Either[Int, Long]]
      }
    }
  }

  {
    val yaml = YamlArray(YamlNumber(42), YamlNumber(4.2))

    "The tuple2Format" should "convert (42, 4.2) to a YamlArray" in {
      (42, 4.2).toYaml should ===(yaml)
    }

    it should "be able to convert a YamlArray to a (Int, Double)]" in {
      yaml.convertTo[(Int, Double)] should ===((42, 4.2))
    }
  }

  {
    val yaml = YamlArray(YamlNumber(42), YamlNumber(4.2), YamlNumber(3))

    "The tuple3Format" should "convert (42, 4.2, 3) to a YamlArray" in {
      (42, 4.2, 3).toYaml should ===(yaml)
    }

    it should "be able to convert a YamlArray to a (Int, Double, Int)]" in {
      yaml.convertTo[(Int, Double, Int)] should ===((42, 4.2, 3))
    }
  }

  {
    val yaml = YamlArray(YamlNumber(42), YamlNumber(4.2), YamlNumber(3),
      YamlNumber(4))

    "The tuple4Format" should "convert (42, 4.2, 3, 4) to a YamlArray" in {
      (42, 4.2, 3, 4).toYaml should ===(yaml)
    }

    it should "be able to convert a YamlArray to a (Int, Double, Int, Int)]" in {
      yaml.convertTo[(Int, Double, Int, Int)] should ===((42, 4.2, 3, 4))
    }
  }

  {
    val yaml = YamlArray(YamlNumber(42), YamlNumber(4.2), YamlNumber(3),
      YamlNumber(4), YamlNumber(5))

    "The tuple5Format" should "convert (42, 4.2, 3, 4, 5) to a YamlArray" in {
      (42, 4.2, 3, 4, 5).toYaml should ===(yaml)
    }

    it should "be able to convert a YamlArray to a (Int, Double, Int, Int, Int)]" in {
      yaml.convertTo[(Int, Double, Int, Int, Int)] should ===((42, 4.2, 3, 4, 5))
    }
  }

  {
    val yaml = YamlArray(YamlNumber(42), YamlNumber(4.2), YamlNumber(3),
      YamlNumber(4), YamlNumber(5), YamlNumber(6))

    "The tuple6Format" should "convert (42, 4.2, 3, 4, 5, 6) to a YamlArray" in {
      (42, 4.2, 3, 4, 5, 6).toYaml should ===(yaml)
    }

    it should "be able to convert a YamlArray to a (Int, Double, Int, Int, Int, Int)]" in {
      yaml.convertTo[(Int, Double, Int, Int, Int, Int)] should ===((42, 4.2, 3, 4, 5, 6))
    }
  }

  {
    val yaml = YamlArray(YamlNumber(42), YamlNumber(4.2), YamlNumber(3),
      YamlNumber(4), YamlNumber(5), YamlNumber(6), YamlNumber(7))

    "The tuple7Format" should "convert (42, 4.2, 3, 4, 5, 6, 7) to a YamlArray" in {
      (42, 4.2, 3, 4, 5, 6, 7).toYaml should ===(yaml)
    }

    it should "be able to convert a YamlArray to a (Int, Double, Int, Int, Int, Int, Int)]" in {
      yaml.convertTo[(Int, Double, Int, Int, Int, Int, Int)] should ===((42, 4.2, 3, 4, 5, 6, 7))
    }
  }
}
