package net.jcazevedo.moultingyaml

sealed trait YAMLValue

case class YAMLObject(fields: Map[String, YAMLValue]) extends YAMLValue

case class YAMLArray(elements: Vector[YAMLValue]) extends YAMLValue

case class YAMLString(value: String) extends YAMLValue

case class YAMLNumber(value: BigDecimal) extends YAMLValue
