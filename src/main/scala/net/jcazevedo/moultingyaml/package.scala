package net.jcazevedo

package object moultingyaml {
  implicit class PimpedString(val string: String) extends AnyVal {
    def parseYaml: YamlValue = ???
  }
}
