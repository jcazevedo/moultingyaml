package net.jcazevedo

import scala.collection.JavaConverters._

import com.github.nscala_time.time.Imports._
import org.yaml.snakeyaml.{ Yaml, DumperOptions }
import org.yaml.snakeyaml.constructor._
import org.yaml.snakeyaml.nodes._
import org.yaml.snakeyaml.representer._
import org.yaml.snakeyaml.resolver._

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
      case YamlNode(o, t) => {
        val tag = YamlTag(t.getValue)
        o match {
          case m: java.util.Map[Object @unchecked, Object @unchecked] =>
            YamlObject(m.asScala.map {
              case (k, v) => convertToYamlValue(k) -> convertToYamlValue(v)
            }.toMap, tag)
          case l: java.util.List[Object @unchecked] =>
            YamlArray(l.asScala.map(convertToYamlValue).toVector, tag)
          case s: java.util.Set[Object @unchecked] =>
            YamlSet(s.asScala.map(convertToYamlValue).toSet, tag)
          case i: java.lang.Integer =>
            YamlNumber(i.toInt, tag)
          case i: java.lang.Long =>
            YamlNumber(i.toLong, tag)
          case i: java.math.BigInteger =>
            YamlNumber(BigDecimal(BigInt(i)), tag)
          case d: java.lang.Double =>
            YamlNumber(d.toDouble, tag)
          case s: java.lang.String =>
            YamlString(s, tag)
          case d: java.util.Date =>
            YamlDate(new DateTime(d), tag)
          case b: java.lang.Boolean =>
            YamlBoolean(b, tag)
          case ba: Array[Byte] =>
            YamlString(new String(ba), tag)
          case n if n == null =>
            YamlNull(tag)
        }
      }
      case other =>
        deserializationError("Expected YamlNode, got " + other)
    }
  }

  implicit class PimpedAny[A](val any: A) extends AnyVal {
    def toYaml(implicit writer: YamlWriter[A]): YamlValue = writer.write(any)
  }

  implicit class PimpedString(val string: String) extends AnyVal {
    private def yamlInstance = {
      val resolver = new Resolver()
      new Yaml(
        new Constructor {
          override def constructObject(node: Node) = {
            val nodeValue = node match {
              case s: ScalarNode => s.getValue
              case _ => null
            }
            val prevTag = node.getTag()
            node.setTag(resolver.resolve(node.getNodeId, nodeValue, true))
            YamlNode(super.constructObject(node), prevTag)
          }
        },
        new Representer(),
        new DumperOptions(),
        resolver)
    }

    def parseYaml: YamlValue =
      convertToYamlValue(yamlInstance.load(string))

    def parseYamls: Seq[YamlValue] =
      yamlInstance.loadAll(string).asScala.map(convertToYamlValue).toSeq
  }
}
