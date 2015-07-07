package net.jcazevedo.moultingyaml

import scala.reflect.runtime.universe._

trait ProductFormats {

  def yamlFormat0[T <: Product: WeakTypeTag](construct: () => T) = new YF[T] {
    def write(p: T) = YamlObject()
    def read(value: YamlValue) = value match {
      case _: YamlObject => construct()
      case x => deserializationError("YamlObject expected, found " + x)
    }
  }

  def yamlFormat1[A: YF, T <: Product: WeakTypeTag](
    construct: A => T): YF[T] = {

    val List(a1) = fieldInfo[T]
    yamlFormat(construct, a1)
  }

  def yamlFormat2[A: YF, B: YF, T <: Product: WeakTypeTag](
    construct: (A, B) => T): YF[T] = {

    val List(a1, a2) = fieldInfo[T]
    yamlFormat(construct, a1, a2)
  }

  def yamlFormat3[A: YF, B: YF, C: YF, T <: Product: WeakTypeTag](
    construct: (A, B, C) => T): YF[T] = {

    val List(a1, a2, a3) = fieldInfo[T]
    yamlFormat(construct, a1, a2, a3)
  }

  def yamlFormat4[A: YF, B: YF, C: YF, D: YF, T <: Product: WeakTypeTag](
    construct: (A, B, C, D) => T): YF[T] = {

    val List(a1, a2, a3, a4) = fieldInfo[T]
    yamlFormat(construct, a1, a2, a3, a4)
  }

  def yamlFormat5[A: YF, B: YF, C: YF, D: YF, E: YF, T <: Product: WeakTypeTag](
    construct: (A, B, C, D, E) => T): YF[T] = {

    val List(a1, a2, a3, a4, a5) = fieldInfo[T]
    yamlFormat(construct, a1, a2, a3, a4, a5)
  }

  def yamlFormat6[A: YF, B: YF, C: YF, D: YF, E: YF, F: YF, T <: Product: WeakTypeTag](
    construct: (A, B, C, D, E, F) => T): YF[T] = {

    val List(a1, a2, a3, a4, a5, a6) = fieldInfo[T]
    yamlFormat(construct, a1, a2, a3, a4, a5, a6)
  }

  def yamlFormat7[A: YF, B: YF, C: YF, D: YF, E: YF, F: YF, G: YF, T <: Product: WeakTypeTag](
    construct: (A, B, C, D, E, F, G) => T): YF[T] = {

    val List(a1, a2, a3, a4, a5, a6, a7) = fieldInfo[T]
    yamlFormat(construct, a1, a2, a3, a4, a5, a6, a7)
  }

  def yamlFormat[A: YF, T <: Product](
    construct: A => T, field1: (String, Boolean)) = new YF[T] {

    def write(p: T) = {
      val fields = Seq(
        writeField[A](p.productElement(0), field1._1, field1._2))
      YamlObject(fields.flatten: _*)
    }

    def read(value: YamlValue) = construct(
      readField[A](value, field1._1, field1._2))
  }

  def yamlFormat[A: YF, B: YF, T <: Product](
    construct: (A, B) => T, field1: (String, Boolean),
    field2: (String, Boolean)) = new YF[T] {

    def write(p: T) = {
      val fields = Seq(
        writeField[A](p.productElement(0), field1._1, field1._2),
        writeField[B](p.productElement(1), field2._1, field2._2))
      YamlObject(fields.flatten: _*)
    }

    def read(value: YamlValue) = construct(
      readField[A](value, field1._1, field1._2),
      readField[B](value, field2._1, field2._2))
  }

  def yamlFormat[A: YF, B: YF, C: YF, T <: Product](
    construct: (A, B, C) => T, field1: (String, Boolean),
    field2: (String, Boolean), field3: (String, Boolean)) = new YF[T] {

    def write(p: T) = {
      val fields = Seq(
        writeField[A](p.productElement(0), field1._1, field1._2),
        writeField[B](p.productElement(1), field2._1, field2._2),
        writeField[C](p.productElement(2), field3._1, field3._2))
      YamlObject(fields.flatten: _*)
    }

    def read(value: YamlValue) = construct(
      readField[A](value, field1._1, field1._2),
      readField[B](value, field2._1, field2._2),
      readField[C](value, field3._1, field3._2))
  }

  def yamlFormat[A: YF, B: YF, C: YF, D: YF, T <: Product](
    construct: (A, B, C, D) => T, field1: (String, Boolean),
    field2: (String, Boolean), field3: (String, Boolean),
    field4: (String, Boolean)) = new YF[T] {

    def write(p: T) = {
      val fields = Seq(
        writeField[A](p.productElement(0), field1._1, field1._2),
        writeField[B](p.productElement(1), field2._1, field2._2),
        writeField[C](p.productElement(2), field3._1, field3._2),
        writeField[D](p.productElement(3), field4._1, field4._2))
      YamlObject(fields.flatten: _*)
    }

    def read(value: YamlValue) = construct(
      readField[A](value, field1._1, field1._2),
      readField[B](value, field2._1, field2._2),
      readField[C](value, field3._1, field3._2),
      readField[D](value, field4._1, field4._2))
  }

  def yamlFormat[A: YF, B: YF, C: YF, D: YF, E: YF, T <: Product](
    construct: (A, B, C, D, E) => T, field1: (String, Boolean),
    field2: (String, Boolean), field3: (String, Boolean),
    field4: (String, Boolean), field5: (String, Boolean)) = new YF[T] {

    def write(p: T) = {
      val fields = Seq(
        writeField[A](p.productElement(0), field1._1, field1._2),
        writeField[B](p.productElement(1), field2._1, field2._2),
        writeField[C](p.productElement(2), field3._1, field3._2),
        writeField[D](p.productElement(3), field4._1, field4._2),
        writeField[E](p.productElement(4), field5._1, field5._2))
      YamlObject(fields.flatten: _*)
    }

    def read(value: YamlValue) = construct(
      readField[A](value, field1._1, field1._2),
      readField[B](value, field2._1, field2._2),
      readField[C](value, field3._1, field3._2),
      readField[D](value, field4._1, field4._2),
      readField[E](value, field5._1, field5._2))
  }

  def yamlFormat[A: YF, B: YF, C: YF, D: YF, E: YF, F: YF, T <: Product](
    construct: (A, B, C, D, E, F) => T, field1: (String, Boolean),
    field2: (String, Boolean), field3: (String, Boolean),
    field4: (String, Boolean), field5: (String, Boolean),
    field6: (String, Boolean)) = new YF[T] {

    def write(p: T) = {
      val fields = Seq(
        writeField[A](p.productElement(0), field1._1, field1._2),
        writeField[B](p.productElement(1), field2._1, field2._2),
        writeField[C](p.productElement(2), field3._1, field3._2),
        writeField[D](p.productElement(3), field4._1, field4._2),
        writeField[E](p.productElement(4), field5._1, field5._2),
        writeField[F](p.productElement(5), field6._1, field6._2))
      YamlObject(fields.flatten: _*)
    }

    def read(value: YamlValue) = construct(
      readField[A](value, field1._1, field1._2),
      readField[B](value, field2._1, field2._2),
      readField[C](value, field3._1, field3._2),
      readField[D](value, field4._1, field4._2),
      readField[E](value, field5._1, field5._2),
      readField[F](value, field6._1, field6._2))
  }

  def yamlFormat[A: YF, B: YF, C: YF, D: YF, E: YF, F: YF, G: YF, T <: Product](
    construct: (A, B, C, D, E, F, G) => T, field1: (String, Boolean),
    field2: (String, Boolean), field3: (String, Boolean),
    field4: (String, Boolean), field5: (String, Boolean),
    field6: (String, Boolean), field7: (String, Boolean)) = new YF[T] {

    def write(p: T) = {
      val fields = Seq(
        writeField[A](p.productElement(0), field1._1, field1._2),
        writeField[B](p.productElement(1), field2._1, field2._2),
        writeField[C](p.productElement(2), field3._1, field3._2),
        writeField[D](p.productElement(3), field4._1, field4._2),
        writeField[E](p.productElement(4), field5._1, field5._2),
        writeField[F](p.productElement(5), field6._1, field6._2),
        writeField[G](p.productElement(6), field7._1, field7._2))
      YamlObject(fields.flatten: _*)
    }

    def read(value: YamlValue) = construct(
      readField[A](value, field1._1, field1._2),
      readField[B](value, field2._1, field2._2),
      readField[C](value, field3._1, field3._2),
      readField[D](value, field4._1, field4._2),
      readField[E](value, field5._1, field5._2),
      readField[F](value, field6._1, field6._2),
      readField[G](value, field7._1, field7._2))
  }

  private[this] def fieldInfo[T: WeakTypeTag]: List[(String, Boolean)] =
    implicitly[WeakTypeTag[T]].tpe.members.sorted.collect {
      case m: MethodSymbol if m.isCaseAccessor =>
        (m.name.decodedName.toString, m.returnType <:< typeOf[Option[Any]])
    }

  protected[this] def writeField[A: YamlWriter](
    value: Any,
    fieldName: String,
    isOption: Boolean): Option[(YamlString, YamlValue)] = value match {

    case None => None
    case _ => Some(YamlString(fieldName) -> value.asInstanceOf[A].toYaml)
  }

  protected[this] def readField[A: YamlReader](
    value: YamlValue,
    fieldName: String,
    isOption: Boolean) = value match {

    case YamlObject(fields) if isOption &&
      !fields.contains(YamlString(fieldName)) => None.asInstanceOf[A]

    case YamlObject(fields) =>
      try fields(YamlString(fieldName)).convertTo[A]
      catch {
        case e: NoSuchElementException =>
          deserializationError("YamlObject is missing required member '" +
            fieldName + "'", e, fieldName :: Nil)

        case DeserializationException(msg, cause, fieldNames) =>
          deserializationError(msg, cause, fieldName :: fieldNames)
      }

    case _ => deserializationError("YamlObject expected in field '" + fieldName
      + "'", fieldNames = fieldName :: Nil)
  }
}

trait NullOptions extends ProductFormats {
  override protected[this] def writeField[A: YamlWriter](
    value: Any, fieldName: String, isOption: Boolean) =
    Some(YamlString(fieldName) -> value.asInstanceOf[A].toYaml)
}
