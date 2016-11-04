package net.jcazevedo

package object moultingyaml {

  // format: OFF
  private[moultingyaml] type YF[A] = YamlFormat[A]
  // format: ON

  case class DeserializationException(msg: String,
                                      cause: Throwable = null,
                                      fieldNames: List[String] = Nil)
      extends RuntimeException(msg, cause)

  case class SerializationException(msg: String) extends RuntimeException(msg)

  def deserializationError(msg: String,
                           cause: Throwable = null,
                           fieldNames: List[String] = Nil) =
    throw new DeserializationException(msg, cause, fieldNames)

  def serializationError(msg: String) = throw new SerializationException(msg)

  implicit class PimpedAny[A](val any: A) extends AnyVal {
    def toYaml(implicit writer: YamlWriter[A]): YamlValue = writer.write(any)
  }
}
