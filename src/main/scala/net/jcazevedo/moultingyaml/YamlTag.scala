package net.jcazevedo.moultingyaml

case class YamlTag(tag: String) {
  def secondary = !tag.startsWith(YamlTag.PREFIX)
}

object YamlTag {
  val PREFIX = org.yaml.snakeyaml.nodes.Tag.PREFIX
  val YAML = YamlTag(org.yaml.snakeyaml.nodes.Tag.YAML.getValue)
  val MERGE = YamlTag(org.yaml.snakeyaml.nodes.Tag.MERGE.getValue)
  val SET = YamlTag(org.yaml.snakeyaml.nodes.Tag.SET.getValue)
  val PAIRS = YamlTag(org.yaml.snakeyaml.nodes.Tag.PAIRS.getValue)
  val OMAP = YamlTag(org.yaml.snakeyaml.nodes.Tag.OMAP.getValue)
  val BINARY = YamlTag(org.yaml.snakeyaml.nodes.Tag.BINARY.getValue)
  val INT = YamlTag(org.yaml.snakeyaml.nodes.Tag.INT.getValue)
  val FLOAT = YamlTag(org.yaml.snakeyaml.nodes.Tag.FLOAT.getValue)
  val TIMESTAMP = YamlTag(org.yaml.snakeyaml.nodes.Tag.TIMESTAMP.getValue)
  val BOOL = YamlTag(org.yaml.snakeyaml.nodes.Tag.BOOL.getValue)
  val NULL = YamlTag(org.yaml.snakeyaml.nodes.Tag.NULL.getValue)
  val STR = YamlTag(org.yaml.snakeyaml.nodes.Tag.STR.getValue)
  val SEQ = YamlTag(org.yaml.snakeyaml.nodes.Tag.SEQ.getValue)
  val MAP = YamlTag(org.yaml.snakeyaml.nodes.Tag.MAP.getValue)
}
