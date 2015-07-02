package net.jcazevedo

import org.yaml.snakeyaml.Yaml
import scala.collection.JavaConverters._

package object moultingyaml {
  private[moultingyaml] def convertToYamlValue(obj: Object): YamlValue = {
    obj match {
      case m: java.util.Map[String @unchecked, Object @unchecked] =>
        YamlObject(m.asScala.mapValues(convertToYamlValue).toMap)
      case l: java.util.List[Object @unchecked] =>
        YamlArray(l.asScala.map(convertToYamlValue).toVector)
      case i: java.lang.Integer =>
        YamlNumber(BigDecimal(i))
      case d: java.lang.Double =>
        YamlNumber(BigDecimal(d))
      case s: java.lang.String =>
        YamlString(s)
    }
  }

  implicit class PimpedString(val string: String) extends AnyVal {
    def parseYaml: YamlValue = {
      convertToYamlValue(new Yaml().load(string))
    }
  }
}
