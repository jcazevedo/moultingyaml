package net.jcazevedo

import com.github.nscala_time.time.Imports._
import org.yaml.snakeyaml.Yaml
import scala.collection.JavaConverters._

package object moultingyaml {
  private[moultingyaml] def convertToYamlValue(obj: Object): YamlValue = {
    obj match {
      case m: java.util.Map[Object @unchecked, Object @unchecked] =>
        YamlObject(m.asScala.map { case (k, v) =>
          convertToYamlValue(k) -> convertToYamlValue(v)
        }.toMap)
      case l: java.util.List[Object @unchecked] =>
        YamlArray(l.asScala.map(convertToYamlValue).toVector)
      case i: java.lang.Integer =>
        YamlNumber(i.toInt)
      case d: java.lang.Double =>
        YamlNumber(d.toDouble)
      case s: java.lang.String =>
        YamlString(s)
      case d: java.util.Date =>
        YamlDate(new DateTime(d))
      case b: java.lang.Boolean =>
        YamlBoolean(b)
      case n if n == null =>
        YamlNull
    }
  }

  implicit class PimpedString(val string: String) extends AnyVal {
    def parseYaml: YamlValue = {
      convertToYamlValue(new Yaml().load(string))
    }
  }
}
