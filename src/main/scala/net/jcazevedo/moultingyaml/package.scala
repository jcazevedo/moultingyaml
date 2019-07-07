package net.jcazevedo

import com.github.nscala_time.time.Imports._
import org.yaml.snakeyaml.Yaml
import scala.collection.JavaConverters._

package object moultingyaml {

  // format: OFF
  private[moultingyaml] type YF[A] = YamlFormat[A]
  // format: ON

  case class DeserializationException(
      msg: String,
      cause: Throwable = null,
      fieldNames: List[String] = Nil)
    extends RuntimeException(msg, cause)

  case class SerializationException(msg: String) extends RuntimeException(msg)

  def deserializationError(
    msg: String,
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
    def parseYaml: YamlValue =
      convertToYamlValue(new Yaml().load(string))

    def parseYamls: Seq[YamlValue] =
      new Yaml().loadAll(string).asScala.map(convertToYamlValue).toSeq
  }
}
