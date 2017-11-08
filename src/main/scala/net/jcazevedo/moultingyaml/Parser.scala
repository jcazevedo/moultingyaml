package net.jcazevedo.moultingyaml

import scala.collection.JavaConverters._

import com.github.nscala_time.time.Imports._
import org.yaml.snakeyaml.Yaml
import org.yaml.snakeyaml.constructor.{ Constructor, SafeConstructor }
import org.yaml.snakeyaml.nodes.Tag

class Parser(val tags: Map[String, String]) {
  private def convertToYamlValue(obj: Object): YamlValue = obj match {
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

  // TODO: Make this a val instead. No point in creating a new Constructor object every time we
  // want to parse.
  // This fails when a val is used instead of a def. But I can't think of any reason why it would
  // do that.
  private def constructor = new Constructor {
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
      case "timestamp" => new SafeConstructor.ConstructYamlTimestamp()
      case _ => SafeConstructor.undefinedConstructor
    }
    for ((tag, tagType) <- tags) {
      yamlConstructors.put(new Tag(tag), getConstruct(tagType))
    }
  }

  // TODO: Create a val with the value new Yaml(constructor) and use it instead of creating a new
  // object every time we want to parse. As of now, almost all tests seem to fail when I do this.
  // Can't think of a reason why.
  implicit class StringParser(val string: String) {
    def parseYaml: YamlValue = {
      convertToYamlValue(new Yaml(constructor).load(string))
    }
    def parseYamls: Seq[YamlValue] = {
      new Yaml(constructor).loadAll(string).asScala.map(convertToYamlValue).toSeq
    }
  }
}

object defaultParser extends Parser(Map())
