package net.jcazevedo.moultingyaml

import scala.annotation.implicitNotFound

@implicitNotFound(msg =
  "Cannot find YamlReader or YamlFormat type class for ${A}")
trait YamlReader[A] {
  def read(json: YamlValue): A
}

object YamlReader {
  implicit def func2Reader[A](f: YamlValue => A): YamlReader[A] =
    new YamlReader[A] {
      def read(json: YamlValue) = f(json)
    }
}

@implicitNotFound(msg =
  "Cannot find YamlWriter or YamlFormat type class for ${A}")
trait YamlWriter[A] {
  def write(obj: A): YamlValue
}

object YamlWriter {
  implicit def func2Writer[A](f: A => YamlValue): YamlWriter[A] =
    new YamlWriter[A] {
      def write(obj: A) = f(obj)
    }
}

trait YamlFormat[A] extends YamlReader[A] with YamlWriter[A]
