package net.jcazevedo.moultingyaml

import com.github.nscala_time.time.Imports._
import scala.collection.mutable
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
  def tag: YamlTag

  def print(flowStyle: FlowStyle = FlowStyle.DEFAULT,
            scalarStyle: ScalarStyle = ScalarStyle.DEFAULT,
            lineBreak: LineBreak = LineBreak.DEFAULT) = {
    val printer = new SnakeYamlPrinter(flowStyle, scalarStyle, lineBreak)
    printer(this)
  }

  def prettyPrint: String = print()
}

/**
 * A YAML mapping from scalars to scalars.
 */
case class YamlObject(fields: Map[YamlValue, YamlValue], tag: YamlTag = YamlTag.MAP) extends YamlValue {
  override def asYamlObject(errorMsg: String) = this

  def getFields(fieldKeys: YamlValue*): Seq[YamlValue] =
    fieldKeys.flatMap(fields.get)(collection.breakOut)

  private[moultingyaml] lazy val snakeYamlObject: Object = {
    tag match {
      case YamlTag.MAP =>
        mapAsJavaMap(fields.map {
          case (k, v) =>
            k.snakeYamlObject -> v.snakeYamlObject
        })
      case YamlTag.SET =>
        setAsJavaSet(fields.keys.map(_.snakeYamlObject).toSet)
    }
  }
}

object YamlObject {
  def apply(fields: (YamlValue, YamlValue)*) = new YamlObject(Map(fields: _*))
}

/**
 * A YAML array.
 */
case class YamlArray(elements: Vector[YamlValue], tag: YamlTag = YamlTag.SEQ) extends YamlValue {
  private[moultingyaml] lazy val snakeYamlObject: Object =
    seqAsJavaList(elements.map(_.snakeYamlObject))
}

object YamlArray {
  def apply(elements: YamlValue*) = new YamlArray(elements.toVector)
}

/**
 * A YAML set.
 */
case class YamlSet(set: Set[YamlValue], tag: YamlTag = YamlTag.SET) extends YamlValue {
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
case class YamlString(value: String, tag: YamlTag = YamlTag.STR) extends YamlValue {
  private[moultingyaml] lazy val snakeYamlObject = value
}

/**
 * A YAML number
 */
case class YamlNumber(value: BigDecimal, tag: YamlTag) extends YamlValue {
  private[moultingyaml] lazy val snakeYamlObject: Object = {
    value match {
      case v if tag == YamlTag.STR => v.toString()
      case v if v.ulp.isWhole => value.underlying.toBigInteger
      case _ => value
    }
  }
}

object YamlNumber {
  def apply(n: Int): YamlNumber = apply(n, YamlTag.INT)
  def apply(n: Long): YamlNumber = apply(n, YamlTag.INT)
  def apply(n: Double): YamlValue = apply(n, YamlTag.FLOAT)
  def apply(n: BigInt): YamlNumber = apply(n, YamlTag.INT)
  def apply(n: Int, tag: YamlTag) = new YamlNumber(BigDecimal(n), tag)
  def apply(n: Long, tag: YamlTag) = new YamlNumber(BigDecimal(n), tag)
  def apply(n: Double, tag: YamlTag) = n match {
    case n if n.isNaN => YamlNaN(tag)
    case n if n.isPosInfinity => YamlPositiveInf(tag)
    case n if n.isNegInfinity => YamlNegativeInf(tag)
    case _ => new YamlNumber(BigDecimal(n), tag)
  }
  def apply(n: BigInt, tag: YamlTag) = new YamlNumber(BigDecimal(n), tag)
}

case class YamlNaN(tag: YamlTag = YamlTag.FLOAT) extends YamlValue {
  private[moultingyaml] lazy val snakeYamlObject: Object = Double.NaN.asInstanceOf[java.lang.Double]
}

case class YamlPositiveInf(tag: YamlTag = YamlTag.FLOAT) extends YamlValue {
  private[moultingyaml] lazy val snakeYamlObject: Object = Double.PositiveInfinity.asInstanceOf[java.lang.Double]
}

case class YamlNegativeInf(tag: YamlTag = YamlTag.FLOAT) extends YamlValue {
  private[moultingyaml] lazy val snakeYamlObject: Object = Double.NegativeInfinity.asInstanceOf[java.lang.Double]
}

/**
 * A YAML date.
 */
case class YamlDate(date: DateTime, tag: YamlTag = YamlTag.TIMESTAMP) extends YamlValue {
  private[moultingyaml] lazy val snakeYamlObject: Object = date.toDate()
}

/**
 * A YAML boolean.
 */
case class YamlBoolean(boolean: Boolean, tag: YamlTag = YamlTag.BOOL) extends YamlValue {
  private[moultingyaml] lazy val snakeYamlObject: Object =
    new java.lang.Boolean(boolean)
}

/**
 * The representation for YAML null.
 */
case class YamlNull(tag: YamlTag = YamlTag.NULL) extends YamlValue {
  private[moultingyaml] lazy val snakeYamlObject: Object = null
}
