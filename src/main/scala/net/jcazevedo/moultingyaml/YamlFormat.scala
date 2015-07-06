package net.jcazevedo.moultingyaml

import scala.annotation.implicitNotFound

@implicitNotFound(msg =
  "Cannot find YamlReader or YamlFormat type class for ${T}")
trait YamlReader[T] {
  def read(json: YamlValue): T
}

object YamlReader {
  implicit def func2Reader[T](f: YamlValue => T): YamlReader[T] =
    new YamlReader[T] {
      def read(json: YamlValue) = f(json)
    }
}

@implicitNotFound(msg =
  "Cannot find YamlWriter or YamlFormat type class for ${T}")
trait YamlWriter[T] {
  def write(obj: T): YamlValue
}

object YamlWriter {
  implicit def func2Writer[T](f: T => YamlValue): YamlWriter[T] =
    new YamlWriter[T] {
      def write(obj: T) = f(obj)
    }
}

trait YamlFormat[T] extends YamlReader[T] with YamlWriter[T]
