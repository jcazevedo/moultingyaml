package net.jcazevedo.moultingyaml

import org.yaml.snakeyaml.{ DumperOptions, Yaml }

sealed trait FlowStyle {
  def toDumperOption: DumperOptions.FlowStyle
}

object FlowStyle {
  val DEFAULT = Block
}

case object Auto extends FlowStyle {
  def toDumperOption: DumperOptions.FlowStyle = DumperOptions.FlowStyle.AUTO
}

case object Block extends FlowStyle {
  def toDumperOption: DumperOptions.FlowStyle = DumperOptions.FlowStyle.BLOCK
}

case object Flow extends FlowStyle {
  def toDumperOption: DumperOptions.FlowStyle = DumperOptions.FlowStyle.FLOW
}

sealed trait ScalarStyle {
  def toDumperOption: DumperOptions.ScalarStyle
}

object ScalarStyle {
  val DEFAULT = Plain
  def createStyle(char: Char) = CustomScalarStyle(char)
}

private[moultingyaml] case class CustomScalarStyle(val char: Char) extends ScalarStyle {
  def toDumperOption: DumperOptions.ScalarStyle = DumperOptions.ScalarStyle.createStyle(char)
}

case object DoubleQuoted extends ScalarStyle {
  def toDumperOption: DumperOptions.ScalarStyle = DumperOptions.ScalarStyle.DOUBLE_QUOTED
}

case object SingleQuoted extends ScalarStyle {
  def toDumperOption: DumperOptions.ScalarStyle = DumperOptions.ScalarStyle.SINGLE_QUOTED
}

case object Literal extends ScalarStyle {
  def toDumperOption: DumperOptions.ScalarStyle = DumperOptions.ScalarStyle.LITERAL
}

case object Plain extends ScalarStyle {
  def toDumperOption: DumperOptions.ScalarStyle = DumperOptions.ScalarStyle.PLAIN
}

case object Folded extends ScalarStyle {
  def toDumperOption: DumperOptions.ScalarStyle = DumperOptions.ScalarStyle.FOLDED
}

sealed trait LineBreak {
  def toDumperOption: DumperOptions.LineBreak
}

object LineBreak {
  val DEFAULT = Unix
}

case object Win extends LineBreak {
  def toDumperOption: DumperOptions.LineBreak = DumperOptions.LineBreak.WIN
}

case object Mac extends LineBreak {
  def toDumperOption: DumperOptions.LineBreak = DumperOptions.LineBreak.MAC
}

case object Unix extends LineBreak {
  def toDumperOption: DumperOptions.LineBreak = DumperOptions.LineBreak.UNIX
}

abstract class YamlPrinter(
    flowStyle: FlowStyle, scalarStyle: ScalarStyle, lineBreak: LineBreak)
  extends (YamlValue => String) {
  def apply(value: YamlValue): String
}

class SnakeYamlPrinter(
    flowStyle: FlowStyle = FlowStyle.DEFAULT,
    scalarStyle: ScalarStyle = ScalarStyle.DEFAULT,
    lineBreak: LineBreak = LineBreak.DEFAULT)
  extends YamlPrinter(flowStyle, scalarStyle, lineBreak) {

  def dumperOptions: DumperOptions = {
    val dp = new DumperOptions
    dp.setDefaultScalarStyle(scalarStyle.toDumperOption)
    dp.setDefaultFlowStyle(flowStyle.toDumperOption)
    dp.setLineBreak(lineBreak.toDumperOption)
    dp
  }

  override def apply(value: YamlValue): String = {
    new Yaml(dumperOptions).dump(value.snakeYamlObject)
  }
}
