package net.jcazevedo.moultingyaml

import scala.collection.JavaConverters._

import com.github.nscala_time.time.Imports._
import org.yaml.snakeyaml.Yaml
import org.yaml.snakeyaml.constructor.Constructor
import org.yaml.snakeyaml.nodes.Tag

class Parser(val tags: Map[String, String]) {
  def convertToYamlValue(obj: Object): YamlValue = {
    obj match {
      case m: java.util.Map[Object @unchecked, Object @unchecked] =>
        YamlObject(m.asScala.map {
          case (k, v) => convertToYamlValue(k) -> convertToYamlValue(v)
        }.toMap)
      case l: java.util.List[Object @unchecked] =>
        YamlArray(l.asScala.map(convertToYamlValue).toVector)
      case s: java.util.Set[Object @unchecked] =>
        YamlSet(s.asScala.map(convertToYamlValue).toSet)
      case i: java.lang.Integer =>
        YamlNumber(i.toInt)
      case i: java.lang.Long =>
        YamlNumber(i.toLong)
      case i: java.math.BigInteger =>
        YamlNumber(BigInt(i))
      case d: java.lang.Double =>
        YamlNumber(d.toDouble)
      case s: java.lang.String =>
        YamlString(s)
      case d: java.util.Date =>
        YamlDate(new DateTime(d))
      case b: java.lang.Boolean =>
        YamlBoolean(b)
      case ba: Array[Byte] =>
        YamlString(new String(ba))
      case n if n == null =>
        YamlNull
    }
  }

  val constructor = new Constructor {
    def getConstruct(tagType: String) = tagType match {
      case "binary" => new ConstructYamlBinary()
      case "boolean" => new ConstructYamlBool()
      case "float" => new ConstructYamlFloat()
      case "int" => new ConstructYamlInt()
      case "map" => new ConstructYamlMap()
      case "null" => new ConstructYamlNull()
      case "omap" => new ConstructYamlOmap()
      case "pairs" => new ConstructYamlPairs()
      case "seq" => new ConstructYamlSeq()
      case "set" => new ConstructYamlSet()
      case "string" => new ConstructYamlStr()
      // Not sure why ConstructYamlTimestamp cannot be found even though it is in
      // org.yaml.snakeyaml.constructor.SafeConstructor which is extended by Constructor.
      // case "timestamp" => new ConstructYamlTimestamp()
      // I would rather have `undefinedConstructor` here but it cannot be found even after being
      // a public member of org.yaml.snakeyaml.constructor.SafeConstructor
      case _ => new ConstructYamlStr()
    }
    for ((tag, tagType) <- tags) {
      yamlConstructors.put(new Tag(tag), getConstruct(tagType))
    }
  }
  // Tests fail even when this is changed to `val yaml = new Yaml()`
  val yaml = new Yaml(constructor)
}

object defaultParser extends Parser(Map()) {
  implicit class PimpedString(val string: String) extends AnyVal {
    // All tests pass when the `yaml` here is changed to `new Yaml()`
    def parseYaml: YamlValue = convertToYamlValue(yaml.load(string))
    def parseYamls: Seq[YamlValue] = yaml.loadAll(string).asScala.map(convertToYamlValue).toSeq
  }
}
