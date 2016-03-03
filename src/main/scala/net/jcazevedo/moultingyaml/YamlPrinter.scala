package net.jcazevedo.moultingyaml

import org.yaml.snakeyaml.{ DumperOptions, Yaml }

sealed trait FlowStyle { def toDumperOption: DumperOptions.FlowStyle }
object FlowStyle {
  val DEFAULT = Block
}

case object Auto extends FlowStyle {
  override def toDumperOption: DumperOptions.FlowStyle = DumperOptions.FlowStyle.AUTO
}

case object Block extends FlowStyle {
  override def toDumperOption: DumperOptions.FlowStyle = DumperOptions.FlowStyle.BLOCK
}

case object Flow extends FlowStyle {
  override def toDumperOption: DumperOptions.FlowStyle = DumperOptions.FlowStyle.FLOW
}

sealed trait ScalarStyle {
  def toDumperOption: DumperOptions.ScalarStyle
}

object ScalarStyle {
  val DEFAULT = Plain
  def createStyle(char: Char) = CustomScalarStyle(char)
}

private[moultingyaml] case class CustomScalarStyle(val char: Char) extends ScalarStyle {
  override def toDumperOption: DumperOptions.ScalarStyle = DumperOptions.ScalarStyle.createStyle(char)
}

case object DoubleQuoted extends ScalarStyle {
  override def toDumperOption: DumperOptions.ScalarStyle = DumperOptions.ScalarStyle.DOUBLE_QUOTED
}

case object SingleQuoted extends ScalarStyle {
  override def toDumperOption: DumperOptions.ScalarStyle = DumperOptions.ScalarStyle.SINGLE_QUOTED
}

case object Literal extends ScalarStyle {
  override def toDumperOption: DumperOptions.ScalarStyle = DumperOptions.ScalarStyle.LITERAL
}

case object Plain extends ScalarStyle {
  override def toDumperOption: DumperOptions.ScalarStyle = DumperOptions.ScalarStyle.PLAIN
}

case object Folded extends ScalarStyle {
  override def toDumperOption: DumperOptions.ScalarStyle = DumperOptions.ScalarStyle.LITERAL
}

abstract class YamlPrinter(flowStyle: FlowStyle,
                           scalarStyle: ScalarStyle) extends (YamlValue => String) {
  def apply(value: YamlValue): String
}

class SnakeYamlPrinter(flowStyle: FlowStyle, scalarStyle: ScalarStyle) extends YamlPrinter(flowStyle, scalarStyle) {

  override def apply(value: YamlValue): String = {
    val dp = new DumperOptions
    dp.setDefaultFlowStyle(flowStyle.toDumperOption)
    dp.setDefaultScalarStyle(scalarStyle.toDumperOption)
    new Yaml(dp).dump(value.snakeYamlObject)
  }

}

