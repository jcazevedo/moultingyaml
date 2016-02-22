package net.jcazevedo.moultingyaml

import org.yaml.snakeyaml.{Yaml, DumperOptions}

/**
  * @author Simone D'Avico (simonedavico@gmail.com)
  *
  * Created on 22/02/16.
  */
trait OptionsMapping[Flow, Scalar] {
  def corresponding(flow: FlowStyle): Flow
  def corresponding(scalar: ScalarStyle): Scalar
}

sealed trait FlowStyle
case class Auto() extends FlowStyle
case class Block() extends FlowStyle
case class Flow() extends FlowStyle

sealed trait ScalarStyle
object ScalarStyle {
  def createStyle(char: Char) = CustomScalarStyle(char)
}

private[moultingyaml] case class CustomScalarStyle(val char: Char) extends ScalarStyle
case class DoubleQuoted() extends ScalarStyle
case class SingleQuoted() extends ScalarStyle
case class Literal() extends ScalarStyle
case class Plain() extends ScalarStyle
case class Folded() extends ScalarStyle

abstract class YamlPrinter(flowStyle: FlowStyle,
                           scalarStyle: ScalarStyle) extends (YamlValue => String) {
  def apply(value: YamlValue): String
}

trait SnakeYAMLOptionsMapping extends OptionsMapping[DumperOptions.FlowStyle, DumperOptions.ScalarStyle] {

  def corresponding(flow: FlowStyle) = {
    flow match {
      case Auto() => DumperOptions.FlowStyle.AUTO
      case Block() => DumperOptions.FlowStyle.BLOCK
      case Flow() => DumperOptions.FlowStyle.AUTO
    }
  }

  def corresponding(scalar: ScalarStyle) = {
    scalar match {
      case DoubleQuoted() => DumperOptions.ScalarStyle.DOUBLE_QUOTED
      case SingleQuoted() => DumperOptions.ScalarStyle.SINGLE_QUOTED
      case Literal() => DumperOptions.ScalarStyle.LITERAL
      case Plain() => DumperOptions.ScalarStyle.PLAIN
      case Folded() => DumperOptions.ScalarStyle.FOLDED
      case CustomScalarStyle(customChar) => DumperOptions.ScalarStyle.createStyle(customChar)
    }
  }

}

class SnakeYAMLPrinter(flowStyle: FlowStyle, scalarStyle: ScalarStyle) extends YamlPrinter(flowStyle, scalarStyle)
                                                                       with SnakeYAMLOptionsMapping {
  override def apply(value: YamlValue): String = {
    val snakeflow = corresponding(flowStyle)
    val snakescalar = corresponding(scalarStyle)
    val dp = new DumperOptions
    dp.setDefaultFlowStyle(snakeflow)
    dp.setDefaultScalarStyle(snakescalar)
    new Yaml(dp).dump(value.snakeYamlObject)
  }
}




