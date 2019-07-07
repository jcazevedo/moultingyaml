package net.jcazevedo.moultingyaml

import com.github.nscala_time.time.Imports._
import org.scalatest.{ FlatSpec, Matchers }

class BasicFormatsSpec extends FlatSpec with Matchers with BasicFormats {

  "The IntYamlFormat" should "convert an Int to a YamlNumber" in {
    42.toYaml should ===(YamlNumber(42))
  }

  it should "convert a YamlNumber to an Int" in {
    YamlNumber(42).convertTo[Int] should ===(42)
  }

  "The LongYamlFormat" should "convert a Long to a YamlNumber" in {
    7563661897011259335L.toYaml should ===(YamlNumber(7563661897011259335L))
  }

  it should "convert a YamlNumber to a Long" in {
    YamlNumber(7563661897011259335L).convertTo[Long] should ===(7563661897011259335L)
  }

  "The FloatYamlFormat" should "convert a Float to a YamlNumber" in {
    4.2f.toYaml should ===(YamlNumber(4.2f))
  }

  it should "convert a Float.NaN to a YamlNumber" in {
    Float.NaN.toYaml should ===(YamlNaN)
  }

  it should "convert a Float.PositiveInfinity to a YamlNumber" in {
    Float.PositiveInfinity.toYaml should ===(YamlNumber(Float.PositiveInfinity))
  }

  it should "convert a Float.NegativeInfinity to a YamlNumber" in {
    Float.PositiveInfinity.toYaml should ===(YamlNumber(Float.PositiveInfinity))
  }

  it should "convert a YamlNumber to a Float" in {
    YamlNumber(4.2f).convertTo[Float] should ===(4.2f)
  }

  it should "convert a YamlNull to a Float" in {
    YamlNull.convertTo[Float].isNaN should ===(Float.NaN.isNaN)
  }

  it should "convert a YamlPositiveInf to a Float" in {
    YamlPositiveInf.convertTo[Float] should ===(Float.PositiveInfinity)
  }

  it should "convert a YamlNegativeInf to a Float" in {
    YamlNegativeInf.convertTo[Float] should ===(Float.NegativeInfinity)
  }

  "The DoubleYamlFormat" should "convert a Double to a YamlNumber" in {
    4.2.toYaml should ===(YamlNumber(4.2))
  }

  it should "convert a Double.NaN to a YamlNumber" in {
    Double.NaN.toYaml should ===(YamlNaN)
  }

  it should "convert a Double.PositiveInfinity to a YamlNumber" in {
    Double.PositiveInfinity.toYaml should ===(YamlNumber(Double.PositiveInfinity))
  }

  it should "convert a Double.NegativeInfinity to a YamlNumber" in {
    Double.NegativeInfinity.toYaml should ===(YamlNumber(Double.NegativeInfinity))
  }

  it should "convert a YamlNumber to a Double" in {
    YamlNumber(4.2).convertTo[Double] should ===(4.2)
  }

  it should "convert a YamlNull to a Double" in {
    YamlNull.convertTo[Double].isNaN should ===(Double.NaN.isNaN)
  }

  it should "convert a YamlPositiveInf to a Double" in {
    YamlPositiveInf.convertTo[Double] should ===(Double.PositiveInfinity)
  }

  it should "convert a YamlNegativeInf to a Double" in {
    YamlNegativeInf.convertTo[Double] should ===(Double.NegativeInfinity)
  }

  "The ByteYamlFormat" should "convert a Byte to a YamlNumber" in {
    42.asInstanceOf[Byte].toYaml should ===(YamlNumber(42))
  }

  it should "convert a YamlNumber to a Byte" in {
    YamlNumber(42).convertTo[Byte] should ===(42)
  }

  "The ShortYamlFormat" should "convert a Short to a YamlNumber" in {
    42.asInstanceOf[Short].toYaml should ===(YamlNumber(42))
  }

  it should "convert a YamlNumber to a Short" in {
    YamlNumber(42).convertTo[Short] should ===(42)
  }

  "The BigDecimalYamlFormat" should "convert a BigDecimal to a YamlNumber" in {
    BigDecimal(42).toYaml should ===(YamlNumber(42))
  }

  it should "convert a YamlNumber to a BigDecimal" in {
    YamlNumber(42).convertTo[BigDecimal] should ===(BigDecimal(42))
  }

  "The BigIntYamlFormat" should "convert a BigInt to a YamlNumber" in {
    BigInt(42).toYaml should ===(YamlNumber(42))
  }

  it should "convert a YamlNumber to a BigInt" in {
    YamlNumber(42).convertTo[BigInt] should ===(BigInt(42))
  }

  "The UnitYamlFormat" should "convert Unit to a YamlNumber(1)" in {
    ().toYaml should ===(YamlNumber(1))
  }

  it should "convert a YamlNumber to Unit" in {
    YamlNumber(1).convertTo[Unit] should ===(())
  }

  "The BooleanYamlFormat" should "convert true to a YamlTrue" in {
    true.toYaml should ===(YamlBoolean(true))
  }

  it should "convert false to a YamlFalse" in {
    false.toYaml should ===(YamlBoolean(false))
  }

  it should "convert a YamlTrue to true" in {
    YamlBoolean(true).convertTo[Boolean] should ===(true)
  }

  it should "convert a YamlFalse to false" in {
    YamlBoolean(false).convertTo[Boolean] should ===(false)
  }

  "The CharYamlFormat" should "convert a Char to a YamlString" in {
    'c'.toYaml should ===(YamlString("c"))
  }

  it should "convert a YamlString to a Char" in {
    YamlString("c").convertTo[Char] should ===('c')
  }

  "The StringYamlFormat" should "convert a String to a YamlString" in {
    "Hello".toYaml should ===(YamlString("Hello"))
  }

  it should "convert a YamlString to a String" in {
    YamlString("Hello").convertTo[String] should ===("Hello")
  }

  "The SymbolYamlFormat" should "convert a Symbol to a YamlString" in {
    Symbol("Hello").toYaml should ===(YamlString("Hello"))
  }

  it should "convert a YamlString to a Symbol" in {
    println(YamlString("Hello").convertTo[Symbol])
    YamlString("Hello").convertTo[Symbol] should equal(Symbol("Hello"))
  }

  "The DateTimeYamlFormat" should "convert a DateTime to a YamlDate" in {
    "2015-07-01".toDateTime.toYaml should ===(YamlDate("2015-07-01".toDateTime))
  }

  it should "convert a YamlDate to a DateTime" in {
    YamlDate("2015-07-01".toDateTime).convertTo[DateTime] should ===("2015-07-01".toDateTime)
  }
}
