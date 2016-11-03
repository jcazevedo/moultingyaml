package net.jcazevedo

import com.github.nscala_time.time.Imports._
import org.yaml.snakeyaml.Yaml
import org.yaml.snakeyaml.constructor.Constructor
import org.yaml.snakeyaml.nodes.Tag
import scala.collection.JavaConverters._

package object moultingyaml {

  // format: OFF
  private[moultingyaml] type YF[A] = YamlFormat[A]
  // format: ON

  case class DeserializationException(msg: String,
                                      cause: Throwable = null,
                                      fieldNames: List[String] = Nil)
      extends RuntimeException(msg, cause)

  case class SerializationException(msg: String) extends RuntimeException(msg)

  def deserializationError(msg: String,
                           cause: Throwable = null,
                           fieldNames: List[String] = Nil) =
    throw new DeserializationException(msg, cause, fieldNames)

  def serializationError(msg: String) = throw new SerializationException(msg)

  private[moultingyaml] def convertToYamlValue(obj: Object): YamlValue = {
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

  implicit class PimpedAny[A](val any: A) extends AnyVal {
    def toYaml(implicit writer: YamlWriter[A]): YamlValue = writer.write(any)
  }

  implicit class PimpedString(val string: String) extends AnyVal {
    private def constructor(tags: Map[String, String]) = new Constructor {
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

    def parseYaml(tags: Map[String, String] = Map()): YamlValue = {
      convertToYamlValue(new Yaml(constructor(tags)).load(string))
    }

    def parseYamls(tags: Map[String, String] = Map()): Seq[YamlValue] = {
      new Yaml(constructor(tags)).loadAll(string).asScala.map(convertToYamlValue).toSeq
    }
  }
}
