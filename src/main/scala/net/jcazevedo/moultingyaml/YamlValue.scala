package net.jcazevedo.moultingyaml

import com.github.nscala_time.time.Imports._

sealed trait YamlValue

case class YamlObject(fields: Map[String, YamlValue]) extends YamlValue

case class YamlArray(elements: Vector[YamlValue]) extends YamlValue

case class YamlString(value: String) extends YamlValue

case class YamlNumber(value: BigDecimal) extends YamlValue

case class YamlDate(date: DateTime) extends YamlValue
