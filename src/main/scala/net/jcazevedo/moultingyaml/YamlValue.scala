package net.jcazevedo.moultingyaml

import com.github.nscala_time.time.Imports._
import scala.collection.JavaConversions._

/**
 * The general type of a YAML AST node.
 */
sealed abstract class YamlValue {
  def convertTo[A](implicit reader: YamlReader[A]): A = reader.read(this)

  def asYamlObject(errorMsg: String = "YAML object expected"): YamlObject =
    deserializationError(errorMsg)
  def asYamlObject: YamlObject = asYamlObject()

  private[moultingyaml] def snakeYamlObject: Object

  def print(flowStyle: FlowStyle = FlowStyle.DEFAULT,
            scalarStyle: ScalarStyle = ScalarStyle.DEFAULT) = {
    val printer = new SnakeYamlPrinter(flowStyle, scalarStyle)
    printer(this)
  }

  def prettyPrint: String = print()
}

/**
 * A YAML mapping from scalars to scalars.
 */
case class YamlObject(fields: Map[YamlValue, YamlValue]) extends YamlValue {
  override def asYamlObject(errorMsg: String) = this

  def getFields(fieldKeys: YamlValue*): Seq[YamlValue] =
    fieldKeys.flatMap(fields.get)(collection.breakOut)

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

/**
 * A YAML array.
 */
case class YamlArray(elements: Vector[YamlValue]) extends YamlValue {
  private[moultingyaml] lazy val snakeYamlObject: Object = {
    seqAsJavaList(elements.map(_.snakeYamlObject))
  }
}

object YamlArray {
  def apply(elements: YamlValue*) = new YamlArray(elements.toVector)
}

/**
 * A YAML set.
 */
case class YamlSet(set: Set[YamlValue]) extends YamlValue {
  private[moultingyaml] lazy val snakeYamlObject: Object = {
    setAsJavaSet(set.map(_.snakeYamlObject))
  }
}

object YamlSet {
  def apply(elements: YamlValue*) = new YamlSet(elements.toSet)
}

/**
 * A YAML string.
 */
case class YamlString(value: String) extends YamlValue {
  private[moultingyaml] lazy val snakeYamlObject = value
}

/**
 * A YAML number
 */
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

/**
 * A YAML date.
 */
case class YamlDate(date: DateTime) extends YamlValue {
  private[moultingyaml] lazy val snakeYamlObject: Object = date.toDate()
}

/**
 * A YAML boolean.
 */
case class YamlBoolean(boolean: Boolean) extends YamlValue {
  private[moultingyaml] lazy val snakeYamlObject: Object =
    new java.lang.Boolean(boolean)
}

/**
 * The representation for YAML null.
 */
case object YamlNull extends YamlValue {
  private[moultingyaml] lazy val snakeYamlObject: Object = null
}
