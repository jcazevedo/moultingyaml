package net.jcazevedo.moultingyaml

import org.yaml.snakeyaml.nodes.Tag

/**
 * Helper class to enclose a YAML node from SnakeYAML with its respective tag.
 */
case class YamlNode private[moultingyaml] (obj: Object, tag: Tag)
