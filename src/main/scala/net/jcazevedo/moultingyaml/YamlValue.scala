package net.jcazevedo.moultingyaml

import com.github.nscala_time.time.Imports._
import org.yaml.snakeyaml.{ DumperOptions, Yaml }
import scala.collection.JavaConversions._

sealed abstract class YamlValue {
  def convertTo[A](implicit reader: YamlReader[A]): A = reader.read(this)

  def asYamlObject(errorMsg: String = "YAML object expected"): YamlObject =
    deserializationError(errorMsg)
  def asYamlObject: YamlObject = asYamlObject()

  private[moultingyaml] def snakeYamlObject: Object

  def prettyPrint: String = {
    val options = new DumperOptions()
    options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK)
    val yaml = new Yaml(options)
    yaml.dump(snakeYamlObject)
  }
}

case class YamlObject(fields: Map[YamlValue, YamlValue]) extends YamlValue {
  override def asYamlObject(errorMsg: String) = this

  private[moultingyaml] lazy val snakeYamlObject: Object = {
    mapAsJavaMap(fields.map {
      case (k, v) =>
        k.snakeYamlObject -> v.snakeYamlObject
    })
  }
}

object YamlObject {
  def apply(fields: (YamlValue, YamlValue)*) = new YamlObject(Map(fields: _*))
}

case class YamlArray(elements: Vector[YamlValue]) extends YamlValue {
  private[moultingyaml] lazy val snakeYamlObject: Object = {
    seqAsJavaList(elements.map(_.snakeYamlObject))
  }
}

object YamlArray {
  def apply(elements: YamlValue*) = new YamlArray(elements.toVector)
}

case class YamlSet(set: Set[YamlValue]) extends YamlValue {
  private[moultingyaml] lazy val snakeYamlObject: Object = {
    setAsJavaSet(set.map(_.snakeYamlObject))
  }
}

object YamlSet {
  def apply(elements: YamlValue*) = new YamlSet(elements.toSet)
}

case class YamlString(value: String) extends YamlValue {
  private[moultingyaml] lazy val snakeYamlObject = value
}

case class YamlNumber[A](value: A)(
    implicit private[moultingyaml] val num: Numeric[A]) extends YamlValue {
  private[moultingyaml] lazy val snakeYamlObject: Object = value match {
    case i: Int => i.asInstanceOf[java.lang.Integer]
    case l: Long => l.asInstanceOf[java.lang.Long]
    case f: Float => f.asInstanceOf[java.lang.Float]
    case d: Double => d.asInstanceOf[java.lang.Double]
    case b: Byte => b.asInstanceOf[java.lang.Byte]
    case s: Short => s.asInstanceOf[java.lang.Short]
    case bi: BigInt => new java.math.BigInteger(bi.toString())
    case other => other.asInstanceOf[Object]
  }
}

case class YamlDate(date: DateTime) extends YamlValue {
  private[moultingyaml] lazy val snakeYamlObject: Object = date.toDate()
}

case class YamlBoolean(boolean: Boolean) extends YamlValue {
  private[moultingyaml] lazy val snakeYamlObject: Object =
    new java.lang.Boolean(boolean)
}

case object YamlNull extends YamlValue {
  private[moultingyaml] lazy val snakeYamlObject: Object = null
}
