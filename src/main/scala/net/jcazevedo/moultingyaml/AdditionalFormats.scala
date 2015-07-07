package net.jcazevedo.moultingyaml

trait AdditionalFormats {

  implicit object YamlValueFormat extends YF[YamlValue] {
    def write(value: YamlValue) = value
    def read(value: YamlValue) = value
  }

  def yamlFormat[A](reader: YamlReader[A], writer: YamlWriter[A]) = new YF[A] {
    def write(obj: A) = writer.write(obj)
    def read(value: YamlValue) = reader.read(value)
  }

  def lift[A](writer: YamlWriter[A]) = new YF[A] {
    def write(obj: A) = writer.write(obj)
    def read(value: YamlValue) = throw new UnsupportedOperationException(
      "YamlReader implementation missing")
  }

  def lift[A](reader: YamlReader[A]) = new YF[A] {
    def write(obj: A) = throw new UnsupportedOperationException(
      "YamlWriter implementation missing")
    def read(value: YamlValue) = reader.read(value)
  }

  def lazyFormat[A](format: => YF[A]) = new YF[A] {
    lazy val delegate = format
    def write(x: A) = delegate.write(x)
    def read(value: YamlValue) = delegate.read(value)
  }
}
