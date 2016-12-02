package net.jcazevedo.moultingyaml

import org.specs2.mutable.Specification

class CustomFormatSpec extends Specification with DefaultYamlProtocol {
  case class MyType(name: String, value: Int)

  implicit val MyTypeProtocol = new YamlFormat[MyType] {
    def read(yaml: YamlValue) =
      yaml.asYamlObject.getFields(
        YamlString("name"), YamlString("value")) match {
          case Seq(YamlString(name, YamlTag.STR), YamlNumber(value, YamlTag.INT)) =>
            MyType(name, value.intValue)
          case _ => deserializationError("Expected fields: 'name' (YAML string) and 'value' (YAML number)")
        }

    def write(obj: MyType) =
      YamlObject(
        YamlString("name") -> YamlString(obj.name),
        YamlString("value") -> YamlNumber(obj.value))
  }

  "A custom YamlFormat built with 'asYamlObject'" should {
    val value = MyType("bob", 42)

    "correctly deserialize valid YAML content" in {
      """name: bob
        |value: 42""".stripMargin.parseYaml.convertTo[MyType] mustEqual value
    }

    "support full round-trip (de)serialization" in {
      value.toYaml.convertTo[MyType] mustEqual value
    }
  }
}
