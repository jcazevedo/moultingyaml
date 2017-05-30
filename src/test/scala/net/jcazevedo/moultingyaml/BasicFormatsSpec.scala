package net.jcazevedo.moultingyaml

import com.github.nscala_time.time.Imports._
import org.specs2.mutable._

class BasicFormatsSpec extends Specification with BasicFormats {

  "The IntYamlFormat" should {

    "convert an Int to a YamlNumber" in {
      42.toYaml mustEqual YamlNumber(42)
    }

    "convert a YamlNumber to an Int" in {
      YamlNumber(42).convertTo[Int] mustEqual 42
    }
  }

  "The LongYamlFormat" should {

    "convert a Long to a YamlNumber" in {
      7563661897011259335L.toYaml mustEqual YamlNumber(7563661897011259335L)
    }

    "convert a YamlNumber to a Long" in {
      YamlNumber(7563661897011259335L).convertTo[Long] mustEqual
        7563661897011259335L
    }
  }

  "The FloatYamlFormat" should {

    "convert a Float to a YamlNumber" in {
      4.2f.toYaml mustEqual YamlNumber(4.2f)
    }

    "convert a Float.NaN to a YamlNumber" in {
      Float.NaN.toYaml mustEqual YamlNaN
    }

    "convert a Float.PositiveInfinity to a YamlNumber" in {
      Float.PositiveInfinity.toYaml mustEqual YamlNumber(Float.PositiveInfinity)
    }

    "convert a Float.NegativeInfinity to a YamlNumber" in {
      Float.PositiveInfinity.toYaml mustEqual YamlNumber(Float.PositiveInfinity)
    }

    "convert a YamlNumber to a Float" in {
      YamlNumber(4.2f).convertTo[Float] mustEqual 4.2f
    }

    "convert a YamlNull to a Float" in {
      YamlNull.convertTo[Float].isNaN mustEqual Float.NaN.isNaN
    }
  }

  "The DoubleYamlFormat" should {

    "convert a Double to a YamlNumber" in {
      4.2.toYaml mustEqual YamlNumber(4.2)
    }

    "convert a Double.NaN to a YamlNumber" in {
      Double.NaN.toYaml mustEqual YamlNaN
    }

    "convert a Double.PositiveInfinity to a YamlNumber" in {
      Double.PositiveInfinity.toYaml mustEqual
        YamlNumber(Double.PositiveInfinity)
    }

    "convert a Double.NegativeInfinity to a YamlNumber" in {
      Double.NegativeInfinity.toYaml mustEqual
        YamlNumber(Double.NegativeInfinity)
    }

    "convert a YamlNumber to a Double" in {
      YamlNumber(4.2).convertTo[Double] mustEqual 4.2
    }

    "convert a YamlNull to a Double" in {
      YamlNull.convertTo[Double].isNaN mustEqual Double.NaN.isNaN
    }
  }

  "The ByteYamlFormat" should {

    "convert a Byte to a YamlNumber" in {
      42.asInstanceOf[Byte].toYaml mustEqual YamlNumber(42)
    }

    "convert a YamlNumber to a Byte" in {
      YamlNumber(42).convertTo[Byte] mustEqual 42
    }
  }

  "The ShortYamlFormat" should {

    "convert a Short to a YamlNumber" in {
      42.asInstanceOf[Short].toYaml mustEqual YamlNumber(42)
    }

    "convert a YamlNumber to a Short" in {
      YamlNumber(42).convertTo[Short] mustEqual 42
    }
  }

  "The BigDecimalYamlFormat" should {

    "convert a BigDecimal to a YamlNumber" in {
      BigDecimal(42).toYaml mustEqual YamlNumber(42)
    }

    "convert a YamlNumber to a BigDecimal" in {
      YamlNumber(42).convertTo[BigDecimal] mustEqual BigDecimal(42)
    }
  }

  "The BigIntYamlFormat" should {

    "convert a BigInt to a YamlNumber" in {
      BigInt(42).toYaml mustEqual YamlNumber(42)
    }

    "convert a YamlNumber to a BigInt" in {
      YamlNumber(42).convertTo[BigInt] mustEqual BigInt(42)
    }
  }

  "The UnitYamlFormat" should {

    "convert Unit to a YamlNumber(1)" in {
      ().toYaml mustEqual YamlNumber(1)
    }

    "convert a YamlNumber to Unit" in {
      YamlNumber(1).convertTo[Unit] mustEqual { () }
    }
  }

  "The BooleanYamlFormat" should {

    "convert true to a YamlTrue" in {
      true.toYaml mustEqual YamlBoolean(true)
    }

    "convert false to a YamlFalse" in {
      false.toYaml mustEqual YamlBoolean(false)
    }

    "convert a YamlTrue to true" in {
      YamlBoolean(true).convertTo[Boolean] mustEqual true
    }

    "convert a YamlFalse to false" in {
      YamlBoolean(false).convertTo[Boolean] mustEqual false
    }
  }

  "The CharYamlFormat" should {

    "convert a Char to a YamlString" in {
      'c'.toYaml mustEqual YamlString("c")
    }

    "convert a YamlString to a Char" in {
      YamlString("c").convertTo[Char] mustEqual 'c'
    }
  }

  "The StringYamlFormat" should {

    "convert a String to a YamlString" in {
      "Hello".toYaml mustEqual YamlString("Hello")
    }

    "convert a YamlString to a String" in {
      YamlString("Hello").convertTo[String] mustEqual "Hello"
    }

    "allow converting a YamlBoolean to a String" in {
      StringYamlFormat.read(YamlBoolean(true)) mustEqual "true"
      StringYamlFormat.read(YamlBoolean(false)) mustEqual "false"
    }

    "allow converting a YamlNumber to a String" in {
      StringYamlFormat.read(YamlNumber(42)) mustEqual "42"
      StringYamlFormat.read(YamlNumber(4.2)) mustEqual "4.2"
      StringYamlFormat.read(YamlNumber(BigDecimal(4.2))) mustEqual "4.2"
      StringYamlFormat.read(YamlNumber(BigInt(42))) mustEqual "42"
    }
  }

  "The SymbolYamlFormat" should {

    "convert a Symbol to a YamlString" in {
      'Hello.toYaml mustEqual YamlString("Hello")
    }

    "convert a YamlString to a Symbol" in {
      YamlString("Hello").convertTo[Symbol] mustEqual 'Hello
    }
  }

  "The DateTimeYamlFormat" should {

    "convert a DateTime to a YamlDate" in {
      "2015-07-01".toDateTime.toYaml mustEqual YamlDate("2015-07-01".toDateTime)
    }

    "convert a YamlDate to a DateTime" in {
      YamlDate("2015-07-01".toDateTime).convertTo[DateTime] mustEqual
        "2015-07-01".toDateTime
    }
  }
}
