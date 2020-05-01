package net.jcazevedo.moultingyaml

import com.github.nscala_time.time.Imports._

/**
 * Provides the YamlFormats for the most important Scala types.
 */
trait BasicFormats {

  implicit def IntYamlFormat: YamlFormat[Int] = new YamlFormat[Int] {
    def write(x: Int) = YamlNumber(x)
    def read(value: YamlValue) = value match {
      case YamlNumber(x) => x.intValue
      case x =>
        deserializationError("Expected Int as YamlNumber, but got " + x)
    }
  }

  implicit def LongYamlFormat: YamlFormat[Long] = new YamlFormat[Long] {
    def write(x: Long) = YamlNumber(x)
    def read(value: YamlValue) = value match {
      case YamlNumber(x) => x.longValue
      case x =>
        deserializationError("Expected Long as YamlNumber, but got " + x)
    }
  }

  implicit def FloatYamlFormat: YamlFormat[Float] = new YamlFormat[Float] {
    def write(x: Float) = YamlNumber(x)
    def read(value: YamlValue) = value match {
      case YamlNumber(x) => x.floatValue
      case YamlNull => Float.NaN
      case YamlPositiveInf => Float.PositiveInfinity
      case YamlNegativeInf => Float.NegativeInfinity
      case x =>
        deserializationError("Expected Float as YamlNumber, but got " + x)
    }
  }

  implicit def DoubleYamlFormat: YamlFormat[Double] = new YamlFormat[Double] {
    def write(x: Double) = YamlNumber(x)
    def read(value: YamlValue) = value match {
      case YamlNumber(x) => x.doubleValue
      case YamlNull => Double.NaN
      case YamlPositiveInf => Double.PositiveInfinity
      case YamlNegativeInf => Double.NegativeInfinity
      case x =>
        deserializationError("Expected Double as YamlNumber, but got " + x)
    }
  }

  implicit def ByteYamlFormat: YamlFormat[Byte] = new YamlFormat[Byte] {
    def write(x: Byte) = YamlNumber(x)
    def read(value: YamlValue) = value match {
      case YamlNumber(x) => x.byteValue
      case x =>
        deserializationError("Expected Byte as YamlNumber, but got " + x)
    }
  }

  implicit def ShortYamlFormat: YamlFormat[Short] = new YamlFormat[Short] {
    def write(x: Short) = YamlNumber(x)
    def read(value: YamlValue) = value match {
      case YamlNumber(x) => x.shortValue
      case x =>
        deserializationError("Expected Short as YamlNumber, but got " + x)
    }
  }

  implicit def BigDecimalYamlFormat: YamlFormat[BigDecimal] = new YamlFormat[BigDecimal] {
    def write(x: BigDecimal) = {
      require(x ne null)
      YamlNumber(x)
    }
    def read(value: YamlValue) = value match {
      case YamlNumber(x) => x
      case x =>
        deserializationError("Expected BigDecimal as YamlNumber, but got " + x)
    }
  }

  implicit def BigIntYamlFormat: YamlFormat[BigInt] = new YamlFormat[BigInt] {
    def write(x: BigInt) = {
      require(x ne null)
      YamlNumber(BigDecimal(x))
    }
    def read(value: YamlValue) = value match {
      case YamlNumber(x) => x.toBigInt
      case x =>
        deserializationError("Expected BigInt as YamlNumber, but got " + x)
    }
  }

  implicit def UnitYamlFormat: YamlFormat[Unit] = new YamlFormat[Unit] {
    def write(x: Unit) = YamlNumber(1)
    def read(value: YamlValue) {}
  }

  implicit def BooleanYamlFormat: YamlFormat[Boolean] = new YamlFormat[Boolean] {
    def write(x: Boolean) = YamlBoolean(x)
    def read(value: YamlValue) = value match {
      case YamlBoolean(x) => x
      case x =>
        deserializationError("Expected YamlBoolean, but got " + x)
    }
  }

  implicit def CharYamlFormat: YamlFormat[Char] = new YamlFormat[Char] {
    def write(x: Char) = YamlString(String.valueOf(x))
    def read(value: YamlValue) = value match {
      case YamlString(x) if x.length == 1 => x.charAt(0)
      case x =>
        deserializationError("Expected Char as single-character YamlString, " +
          "but got " + x)
    }
  }

  implicit def StringYamlFormat: YamlFormat[String] = new YamlFormat[String] {
    def write(x: String) = {
      require(x ne null)
      YamlString(x)
    }
    def read(value: YamlValue) = value match {
      case YamlString(x) => x
      case x =>
        deserializationError("Expected String as YamlString, but got " + x)
    }
  }

  implicit def SymbolYamlFormat: YamlFormat[Symbol] = new YamlFormat[Symbol] {
    def write(x: Symbol) = YamlString(x.name)
    def read(value: YamlValue) = value match {
      case YamlString(x) => Symbol(x)
      case x =>
        deserializationError("Expected Symbol as YamlString, but got " + x)
    }
  }

  implicit def DateTimeYamlFormat: YamlFormat[DateTime] = new YamlFormat[DateTime] {
    def write(x: DateTime) = YamlDate(x)
    def read(value: YamlValue) = value match {
      case YamlDate(x) => x
      case x =>
        deserializationError("Expected Date as YamlDate, but got " + x)
    }
  }
}
