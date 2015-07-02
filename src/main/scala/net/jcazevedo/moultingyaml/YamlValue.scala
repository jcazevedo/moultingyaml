package net.jcazevedo.moultingyaml

sealed trait YamlValue

case class YamlObject(fields: Map[String, YamlValue]) extends YamlValue

case class YamlArray(elements: Vector[YamlValue]) extends YamlValue

case class YamlString(value: String) extends YamlValue

case class YamlNumber(value: BigDecimal) extends YamlValue
