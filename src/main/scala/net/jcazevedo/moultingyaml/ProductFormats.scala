package net.jcazevedo.moultingyaml

import scala.reflect.runtime.universe._

/**
 * Provides the helpers for constructing custom YamlFormat implementations for
 * types implementing the Product trait (especially case classes).
 */
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

  def yamlFormat8[A: YF, B: YF, C: YF, D: YF, E: YF, F: YF, G: YF, H: YF, T <: Product: WeakTypeTag](
    construct: (A, B, C, D, E, F, G, H) => T): YF[T] = {

    val List(a1, a2, a3, a4, a5, a6, a7, a8) = fieldInfo[T]
    yamlFormat(construct, a1, a2, a3, a4, a5, a6, a7, a8)
  }

  def yamlFormat9[A: YF, B: YF, C: YF, D: YF, E: YF, F: YF, G: YF, H: YF, I: YF, T <: Product: WeakTypeTag](
    construct: (A, B, C, D, E, F, G, H, I) => T): YF[T] = {

    val List(a1, a2, a3, a4, a5, a6, a7, a8, a9) = fieldInfo[T]
    yamlFormat(construct, a1, a2, a3, a4, a5, a6, a7, a8, a9)
  }

  def yamlFormat10[A: YF, B: YF, C: YF, D: YF, E: YF, F: YF, G: YF, H: YF, I: YF, J: YF, T <: Product: WeakTypeTag](
    construct: (A, B, C, D, E, F, G, H, I, J) => T): YF[T] = {

    val List(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10) = fieldInfo[T]
    yamlFormat(construct, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10)
  }

  def yamlFormat11[A: YF, B: YF, C: YF, D: YF, E: YF, F: YF, G: YF, H: YF, I: YF, J: YF, K: YF, T <: Product: WeakTypeTag](
    construct: (A, B, C, D, E, F, G, H, I, J, K) => T): YF[T] = {

    val List(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11) = fieldInfo[T]
    yamlFormat(construct, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11)
  }

  def yamlFormat12[A: YF, B: YF, C: YF, D: YF, E: YF, F: YF, G: YF, H: YF, I: YF, J: YF, K: YF, L: YF, T <: Product: WeakTypeTag](
    construct: (A, B, C, D, E, F, G, H, I, J, K, L) => T): YF[T] = {

    val List(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12) = fieldInfo[T]
    yamlFormat(construct, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12)
  }

  def yamlFormat13[A: YF, B: YF, C: YF, D: YF, E: YF, F: YF, G: YF, H: YF, I: YF, J: YF, K: YF, L: YF, M: YF, T <: Product: WeakTypeTag](
    construct: (A, B, C, D, E, F, G, H, I, J, K, L, M) => T): YF[T] = {

    val List(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13) = fieldInfo[T]
    yamlFormat(construct, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13)
  }

  def yamlFormat14[A: YF, B: YF, C: YF, D: YF, E: YF, F: YF, G: YF, H: YF, I: YF, J: YF, K: YF, L: YF, M: YF, N: YF, T <: Product: WeakTypeTag](
    construct: (A, B, C, D, E, F, G, H, I, J, K, L, M, N) => T): YF[T] = {

    val List(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14) = fieldInfo[T]
    yamlFormat(construct, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14)
  }

  def yamlFormat15[A: YF, B: YF, C: YF, D: YF, E: YF, F: YF, G: YF, H: YF, I: YF, J: YF, K: YF, L: YF, M: YF, N: YF, O: YF, T <: Product: WeakTypeTag](
    construct: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O) => T): YF[T] = {

    val List(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15) = fieldInfo[T]
    yamlFormat(construct, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15)
  }

  def yamlFormat16[A: YF, B: YF, C: YF, D: YF, E: YF, F: YF, G: YF, H: YF, I: YF, J: YF, K: YF, L: YF, M: YF, N: YF, O: YF, P: YF, T <: Product: WeakTypeTag](
    construct: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P) => T): YF[T] = {

    val List(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16) = fieldInfo[T]
    yamlFormat(construct, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16)
  }

  def yamlFormat17[A: YF, B: YF, C: YF, D: YF, E: YF, F: YF, G: YF, H: YF, I: YF, J: YF, K: YF, L: YF, M: YF, N: YF, O: YF, P: YF, Q: YF, T <: Product: WeakTypeTag](
    construct: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q) => T): YF[T] = {

    val List(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17) = fieldInfo[T]
    yamlFormat(construct, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17)
  }

  def yamlFormat18[A: YF, B: YF, C: YF, D: YF, E: YF, F: YF, G: YF, H: YF, I: YF, J: YF, K: YF, L: YF, M: YF, N: YF, O: YF, P: YF, Q: YF, R: YF, T <: Product: WeakTypeTag](
    construct: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R) => T): YF[T] = {

    val List(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18) = fieldInfo[T]
    yamlFormat(construct, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18)
  }

  def yamlFormat19[A: YF, B: YF, C: YF, D: YF, E: YF, F: YF, G: YF, H: YF, I: YF, J: YF, K: YF, L: YF, M: YF, N: YF, O: YF, P: YF, Q: YF, R: YF, S: YF, T <: Product: WeakTypeTag](
    construct: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S) => T): YF[T] = {

    val List(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19) = fieldInfo[T]
    yamlFormat(construct, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19)
  }

  def yamlFormat20[A: YF, B: YF, C: YF, D: YF, E: YF, F: YF, G: YF, H: YF, I: YF, J: YF, K: YF, L: YF, M: YF, N: YF, O: YF, P: YF, Q: YF, R: YF, S: YF, U: YF, T <: Product: WeakTypeTag](
    construct: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, U) => T): YF[T] = {

    val List(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19, a20) = fieldInfo[T]
    yamlFormat(construct, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19, a20)
  }

  def yamlFormat21[A: YF, B: YF, C: YF, D: YF, E: YF, F: YF, G: YF, H: YF, I: YF, J: YF, K: YF, L: YF, M: YF, N: YF, O: YF, P: YF, Q: YF, R: YF, S: YF, U: YF, V: YF, T <: Product: WeakTypeTag](
    construct: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, U, V) => T): YF[T] = {

    val List(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19, a20, a21) = fieldInfo[T]
    yamlFormat(construct, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19, a20, a21)
  }

  def yamlFormat22[A: YF, B: YF, C: YF, D: YF, E: YF, F: YF, G: YF, H: YF, I: YF, J: YF, K: YF, L: YF, M: YF, N: YF, O: YF, P: YF, Q: YF, R: YF, S: YF, U: YF, V: YF, X: YF, T <: Product: WeakTypeTag](
    construct: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, U, V, X) => T): YF[T] = {

    val List(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19, a20, a21, a22) = fieldInfo[T]
    yamlFormat(construct, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19, a20, a21, a22)
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

  def yamlFormat[A: YF, B: YF, C: YF, D: YF, E: YF, F: YF, G: YF, H: YF, T <: Product](
    construct: (A, B, C, D, E, F, G, H) => T, field1: (String, Boolean),
    field2: (String, Boolean), field3: (String, Boolean),
    field4: (String, Boolean), field5: (String, Boolean),
    field6: (String, Boolean), field7: (String, Boolean),
    field8: (String, Boolean)) = new YF[T] {

    def write(p: T) = {
      val fields = Seq(
        writeField[A](p.productElement(0), field1._1, field1._2),
        writeField[B](p.productElement(1), field2._1, field2._2),
        writeField[C](p.productElement(2), field3._1, field3._2),
        writeField[D](p.productElement(3), field4._1, field4._2),
        writeField[E](p.productElement(4), field5._1, field5._2),
        writeField[F](p.productElement(5), field6._1, field6._2),
        writeField[G](p.productElement(6), field7._1, field7._2),
        writeField[H](p.productElement(7), field8._1, field8._2))
      YamlObject(fields.flatten: _*)
    }

    def read(value: YamlValue) = construct(
      readField[A](value, field1._1, field1._2),
      readField[B](value, field2._1, field2._2),
      readField[C](value, field3._1, field3._2),
      readField[D](value, field4._1, field4._2),
      readField[E](value, field5._1, field5._2),
      readField[F](value, field6._1, field6._2),
      readField[G](value, field7._1, field7._2),
      readField[H](value, field8._1, field8._2))
  }

  def yamlFormat[A: YF, B: YF, C: YF, D: YF, E: YF, F: YF, G: YF, H: YF, I: YF, T <: Product](
    construct: (A, B, C, D, E, F, G, H, I) => T, field1: (String, Boolean),
    field2: (String, Boolean), field3: (String, Boolean),
    field4: (String, Boolean), field5: (String, Boolean),
    field6: (String, Boolean), field7: (String, Boolean),
    field8: (String, Boolean), field9: (String, Boolean)) = new YF[T] {

    def write(p: T) = {
      val fields = Seq(
        writeField[A](p.productElement(0), field1._1, field1._2),
        writeField[B](p.productElement(1), field2._1, field2._2),
        writeField[C](p.productElement(2), field3._1, field3._2),
        writeField[D](p.productElement(3), field4._1, field4._2),
        writeField[E](p.productElement(4), field5._1, field5._2),
        writeField[F](p.productElement(5), field6._1, field6._2),
        writeField[G](p.productElement(6), field7._1, field7._2),
        writeField[H](p.productElement(7), field8._1, field8._2),
        writeField[I](p.productElement(8), field9._1, field9._2))
      YamlObject(fields.flatten: _*)
    }

    def read(value: YamlValue) = construct(
      readField[A](value, field1._1, field1._2),
      readField[B](value, field2._1, field2._2),
      readField[C](value, field3._1, field3._2),
      readField[D](value, field4._1, field4._2),
      readField[E](value, field5._1, field5._2),
      readField[F](value, field6._1, field6._2),
      readField[G](value, field7._1, field7._2),
      readField[H](value, field8._1, field8._2),
      readField[I](value, field9._1, field9._2))
  }

  def yamlFormat[A: YF, B: YF, C: YF, D: YF, E: YF, F: YF, G: YF, H: YF, I: YF, J: YF, T <: Product](
    construct: (A, B, C, D, E, F, G, H, I, J) => T, field1: (String, Boolean),
    field2: (String, Boolean), field3: (String, Boolean),
    field4: (String, Boolean), field5: (String, Boolean),
    field6: (String, Boolean), field7: (String, Boolean),
    field8: (String, Boolean), field9: (String, Boolean),
    field10: (String, Boolean)) = new YF[T] {

    def write(p: T) = {
      val fields = Seq(
        writeField[A](p.productElement(0), field1._1, field1._2),
        writeField[B](p.productElement(1), field2._1, field2._2),
        writeField[C](p.productElement(2), field3._1, field3._2),
        writeField[D](p.productElement(3), field4._1, field4._2),
        writeField[E](p.productElement(4), field5._1, field5._2),
        writeField[F](p.productElement(5), field6._1, field6._2),
        writeField[G](p.productElement(6), field7._1, field7._2),
        writeField[H](p.productElement(7), field8._1, field8._2),
        writeField[I](p.productElement(8), field9._1, field9._2),
        writeField[J](p.productElement(9), field10._1, field10._2))
      YamlObject(fields.flatten: _*)
    }

    def read(value: YamlValue) = construct(
      readField[A](value, field1._1, field1._2),
      readField[B](value, field2._1, field2._2),
      readField[C](value, field3._1, field3._2),
      readField[D](value, field4._1, field4._2),
      readField[E](value, field5._1, field5._2),
      readField[F](value, field6._1, field6._2),
      readField[G](value, field7._1, field7._2),
      readField[H](value, field8._1, field8._2),
      readField[I](value, field9._1, field9._2),
      readField[J](value, field10._1, field10._2))
  }

  def yamlFormat[A: YF, B: YF, C: YF, D: YF, E: YF, F: YF, G: YF, H: YF, I: YF, J: YF, K: YF, T <: Product](
    construct: (A, B, C, D, E, F, G, H, I, J, K) => T, field1: (String, Boolean),
    field2: (String, Boolean), field3: (String, Boolean),
    field4: (String, Boolean), field5: (String, Boolean),
    field6: (String, Boolean), field7: (String, Boolean),
    field8: (String, Boolean), field9: (String, Boolean),
    field10: (String, Boolean), field11: (String, Boolean)) = new YF[T] {

    def write(p: T) = {
      val fields = Seq(
        writeField[A](p.productElement(0), field1._1, field1._2),
        writeField[B](p.productElement(1), field2._1, field2._2),
        writeField[C](p.productElement(2), field3._1, field3._2),
        writeField[D](p.productElement(3), field4._1, field4._2),
        writeField[E](p.productElement(4), field5._1, field5._2),
        writeField[F](p.productElement(5), field6._1, field6._2),
        writeField[G](p.productElement(6), field7._1, field7._2),
        writeField[H](p.productElement(7), field8._1, field8._2),
        writeField[I](p.productElement(8), field9._1, field9._2),
        writeField[J](p.productElement(9), field10._1, field10._2),
        writeField[K](p.productElement(10), field11._1, field11._2))
      YamlObject(fields.flatten: _*)
    }

    def read(value: YamlValue) = construct(
      readField[A](value, field1._1, field1._2),
      readField[B](value, field2._1, field2._2),
      readField[C](value, field3._1, field3._2),
      readField[D](value, field4._1, field4._2),
      readField[E](value, field5._1, field5._2),
      readField[F](value, field6._1, field6._2),
      readField[G](value, field7._1, field7._2),
      readField[H](value, field8._1, field8._2),
      readField[I](value, field9._1, field9._2),
      readField[J](value, field10._1, field10._2),
      readField[K](value, field11._1, field11._2))
  }

  def yamlFormat[A: YF, B: YF, C: YF, D: YF, E: YF, F: YF, G: YF, H: YF, I: YF, J: YF, K: YF, L: YF, T <: Product](
    construct: (A, B, C, D, E, F, G, H, I, J, K, L) => T, field1: (String, Boolean),
    field2: (String, Boolean), field3: (String, Boolean),
    field4: (String, Boolean), field5: (String, Boolean),
    field6: (String, Boolean), field7: (String, Boolean),
    field8: (String, Boolean), field9: (String, Boolean),
    field10: (String, Boolean), field11: (String, Boolean),
    field12: (String, Boolean)) = new YF[T] {

    def write(p: T) = {
      val fields = Seq(
        writeField[A](p.productElement(0), field1._1, field1._2),
        writeField[B](p.productElement(1), field2._1, field2._2),
        writeField[C](p.productElement(2), field3._1, field3._2),
        writeField[D](p.productElement(3), field4._1, field4._2),
        writeField[E](p.productElement(4), field5._1, field5._2),
        writeField[F](p.productElement(5), field6._1, field6._2),
        writeField[G](p.productElement(6), field7._1, field7._2),
        writeField[H](p.productElement(7), field8._1, field8._2),
        writeField[I](p.productElement(8), field9._1, field9._2),
        writeField[J](p.productElement(9), field10._1, field10._2),
        writeField[K](p.productElement(10), field11._1, field11._2),
        writeField[L](p.productElement(11), field12._1, field12._2))
      YamlObject(fields.flatten: _*)
    }

    def read(value: YamlValue) = construct(
      readField[A](value, field1._1, field1._2),
      readField[B](value, field2._1, field2._2),
      readField[C](value, field3._1, field3._2),
      readField[D](value, field4._1, field4._2),
      readField[E](value, field5._1, field5._2),
      readField[F](value, field6._1, field6._2),
      readField[G](value, field7._1, field7._2),
      readField[H](value, field8._1, field8._2),
      readField[I](value, field9._1, field9._2),
      readField[J](value, field10._1, field10._2),
      readField[K](value, field11._1, field11._2),
      readField[L](value, field12._1, field12._2))
  }

  def yamlFormat[A: YF, B: YF, C: YF, D: YF, E: YF, F: YF, G: YF, H: YF, I: YF, J: YF, K: YF, L: YF, M: YF, T <: Product](
    construct: (A, B, C, D, E, F, G, H, I, J, K, L, M) => T, field1: (String, Boolean),
    field2: (String, Boolean), field3: (String, Boolean),
    field4: (String, Boolean), field5: (String, Boolean),
    field6: (String, Boolean), field7: (String, Boolean),
    field8: (String, Boolean), field9: (String, Boolean),
    field10: (String, Boolean), field11: (String, Boolean),
    field12: (String, Boolean), field13: (String, Boolean)) = new YF[T] {

    def write(p: T) = {
      val fields = Seq(
        writeField[A](p.productElement(0), field1._1, field1._2),
        writeField[B](p.productElement(1), field2._1, field2._2),
        writeField[C](p.productElement(2), field3._1, field3._2),
        writeField[D](p.productElement(3), field4._1, field4._2),
        writeField[E](p.productElement(4), field5._1, field5._2),
        writeField[F](p.productElement(5), field6._1, field6._2),
        writeField[G](p.productElement(6), field7._1, field7._2),
        writeField[H](p.productElement(7), field8._1, field8._2),
        writeField[I](p.productElement(8), field9._1, field9._2),
        writeField[J](p.productElement(9), field10._1, field10._2),
        writeField[K](p.productElement(10), field11._1, field11._2),
        writeField[L](p.productElement(11), field12._1, field12._2),
        writeField[M](p.productElement(12), field13._1, field13._2))
      YamlObject(fields.flatten: _*)
    }

    def read(value: YamlValue) = construct(
      readField[A](value, field1._1, field1._2),
      readField[B](value, field2._1, field2._2),
      readField[C](value, field3._1, field3._2),
      readField[D](value, field4._1, field4._2),
      readField[E](value, field5._1, field5._2),
      readField[F](value, field6._1, field6._2),
      readField[G](value, field7._1, field7._2),
      readField[H](value, field8._1, field8._2),
      readField[I](value, field9._1, field9._2),
      readField[J](value, field10._1, field10._2),
      readField[K](value, field11._1, field11._2),
      readField[L](value, field12._1, field12._2),
      readField[M](value, field13._1, field13._2))
  }

  def yamlFormat[A: YF, B: YF, C: YF, D: YF, E: YF, F: YF, G: YF, H: YF, I: YF, J: YF, K: YF, L: YF, M: YF, N: YF, T <: Product](
    construct: (A, B, C, D, E, F, G, H, I, J, K, L, M, N) => T, field1: (String, Boolean),
    field2: (String, Boolean), field3: (String, Boolean),
    field4: (String, Boolean), field5: (String, Boolean),
    field6: (String, Boolean), field7: (String, Boolean),
    field8: (String, Boolean), field9: (String, Boolean),
    field10: (String, Boolean), field11: (String, Boolean),
    field12: (String, Boolean), field13: (String, Boolean),
    field14: (String, Boolean)) = new YF[T] {

    def write(p: T) = {
      val fields = Seq(
        writeField[A](p.productElement(0), field1._1, field1._2),
        writeField[B](p.productElement(1), field2._1, field2._2),
        writeField[C](p.productElement(2), field3._1, field3._2),
        writeField[D](p.productElement(3), field4._1, field4._2),
        writeField[E](p.productElement(4), field5._1, field5._2),
        writeField[F](p.productElement(5), field6._1, field6._2),
        writeField[G](p.productElement(6), field7._1, field7._2),
        writeField[H](p.productElement(7), field8._1, field8._2),
        writeField[I](p.productElement(8), field9._1, field9._2),
        writeField[J](p.productElement(9), field10._1, field10._2),
        writeField[K](p.productElement(10), field11._1, field11._2),
        writeField[L](p.productElement(11), field12._1, field12._2),
        writeField[M](p.productElement(12), field13._1, field13._2),
        writeField[N](p.productElement(13), field14._1, field14._2))
      YamlObject(fields.flatten: _*)
    }

    def read(value: YamlValue) = construct(
      readField[A](value, field1._1, field1._2),
      readField[B](value, field2._1, field2._2),
      readField[C](value, field3._1, field3._2),
      readField[D](value, field4._1, field4._2),
      readField[E](value, field5._1, field5._2),
      readField[F](value, field6._1, field6._2),
      readField[G](value, field7._1, field7._2),
      readField[H](value, field8._1, field8._2),
      readField[I](value, field9._1, field9._2),
      readField[J](value, field10._1, field10._2),
      readField[K](value, field11._1, field11._2),
      readField[L](value, field12._1, field12._2),
      readField[M](value, field13._1, field13._2),
      readField[N](value, field14._1, field14._2))
  }

  def yamlFormat[A: YF, B: YF, C: YF, D: YF, E: YF, F: YF, G: YF, H: YF, I: YF, J: YF, K: YF, L: YF, M: YF, N: YF, O: YF, T <: Product](
    construct: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O) => T, field1: (String, Boolean),
    field2: (String, Boolean), field3: (String, Boolean),
    field4: (String, Boolean), field5: (String, Boolean),
    field6: (String, Boolean), field7: (String, Boolean),
    field8: (String, Boolean), field9: (String, Boolean),
    field10: (String, Boolean), field11: (String, Boolean),
    field12: (String, Boolean), field13: (String, Boolean),
    field14: (String, Boolean), field15: (String, Boolean)) = new YF[T] {

    def write(p: T) = {
      val fields = Seq(
        writeField[A](p.productElement(0), field1._1, field1._2),
        writeField[B](p.productElement(1), field2._1, field2._2),
        writeField[C](p.productElement(2), field3._1, field3._2),
        writeField[D](p.productElement(3), field4._1, field4._2),
        writeField[E](p.productElement(4), field5._1, field5._2),
        writeField[F](p.productElement(5), field6._1, field6._2),
        writeField[G](p.productElement(6), field7._1, field7._2),
        writeField[H](p.productElement(7), field8._1, field8._2),
        writeField[I](p.productElement(8), field9._1, field9._2),
        writeField[J](p.productElement(9), field10._1, field10._2),
        writeField[K](p.productElement(10), field11._1, field11._2),
        writeField[L](p.productElement(11), field12._1, field12._2),
        writeField[M](p.productElement(12), field13._1, field13._2),
        writeField[N](p.productElement(13), field14._1, field14._2),
        writeField[O](p.productElement(14), field15._1, field15._2))
      YamlObject(fields.flatten: _*)
    }

    def read(value: YamlValue) = construct(
      readField[A](value, field1._1, field1._2),
      readField[B](value, field2._1, field2._2),
      readField[C](value, field3._1, field3._2),
      readField[D](value, field4._1, field4._2),
      readField[E](value, field5._1, field5._2),
      readField[F](value, field6._1, field6._2),
      readField[G](value, field7._1, field7._2),
      readField[H](value, field8._1, field8._2),
      readField[I](value, field9._1, field9._2),
      readField[J](value, field10._1, field10._2),
      readField[K](value, field11._1, field11._2),
      readField[L](value, field12._1, field12._2),
      readField[M](value, field13._1, field13._2),
      readField[N](value, field14._1, field14._2),
      readField[O](value, field15._1, field15._2))
  }

  def yamlFormat[A: YF, B: YF, C: YF, D: YF, E: YF, F: YF, G: YF, H: YF, I: YF, J: YF, K: YF, L: YF, M: YF, N: YF, O: YF, P: YF, T <: Product](
    construct: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P) => T, field1: (String, Boolean),
    field2: (String, Boolean), field3: (String, Boolean),
    field4: (String, Boolean), field5: (String, Boolean),
    field6: (String, Boolean), field7: (String, Boolean),
    field8: (String, Boolean), field9: (String, Boolean),
    field10: (String, Boolean), field11: (String, Boolean),
    field12: (String, Boolean), field13: (String, Boolean),
    field14: (String, Boolean), field15: (String, Boolean),
    field16: (String, Boolean)) = new YF[T] {

    def write(p: T) = {
      val fields = Seq(
        writeField[A](p.productElement(0), field1._1, field1._2),
        writeField[B](p.productElement(1), field2._1, field2._2),
        writeField[C](p.productElement(2), field3._1, field3._2),
        writeField[D](p.productElement(3), field4._1, field4._2),
        writeField[E](p.productElement(4), field5._1, field5._2),
        writeField[F](p.productElement(5), field6._1, field6._2),
        writeField[G](p.productElement(6), field7._1, field7._2),
        writeField[H](p.productElement(7), field8._1, field8._2),
        writeField[I](p.productElement(8), field9._1, field9._2),
        writeField[J](p.productElement(9), field10._1, field10._2),
        writeField[K](p.productElement(10), field11._1, field11._2),
        writeField[L](p.productElement(11), field12._1, field12._2),
        writeField[M](p.productElement(12), field13._1, field13._2),
        writeField[N](p.productElement(13), field14._1, field14._2),
        writeField[O](p.productElement(14), field15._1, field15._2),
        writeField[P](p.productElement(15), field16._1, field16._2))
      YamlObject(fields.flatten: _*)
    }

    def read(value: YamlValue) = construct(
      readField[A](value, field1._1, field1._2),
      readField[B](value, field2._1, field2._2),
      readField[C](value, field3._1, field3._2),
      readField[D](value, field4._1, field4._2),
      readField[E](value, field5._1, field5._2),
      readField[F](value, field6._1, field6._2),
      readField[G](value, field7._1, field7._2),
      readField[H](value, field8._1, field8._2),
      readField[I](value, field9._1, field9._2),
      readField[J](value, field10._1, field10._2),
      readField[K](value, field11._1, field11._2),
      readField[L](value, field12._1, field12._2),
      readField[M](value, field13._1, field13._2),
      readField[N](value, field14._1, field14._2),
      readField[O](value, field15._1, field15._2),
      readField[P](value, field16._1, field16._2))
  }

  def yamlFormat[A: YF, B: YF, C: YF, D: YF, E: YF, F: YF, G: YF, H: YF, I: YF, J: YF, K: YF, L: YF, M: YF, N: YF, O: YF, P: YF, Q: YF, T <: Product](
    construct: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q) => T, field1: (String, Boolean),
    field2: (String, Boolean), field3: (String, Boolean),
    field4: (String, Boolean), field5: (String, Boolean),
    field6: (String, Boolean), field7: (String, Boolean),
    field8: (String, Boolean), field9: (String, Boolean),
    field10: (String, Boolean), field11: (String, Boolean),
    field12: (String, Boolean), field13: (String, Boolean),
    field14: (String, Boolean), field15: (String, Boolean),
    field16: (String, Boolean), field17: (String, Boolean)) = new YF[T] {

    def write(p: T) = {
      val fields = Seq(
        writeField[A](p.productElement(0), field1._1, field1._2),
        writeField[B](p.productElement(1), field2._1, field2._2),
        writeField[C](p.productElement(2), field3._1, field3._2),
        writeField[D](p.productElement(3), field4._1, field4._2),
        writeField[E](p.productElement(4), field5._1, field5._2),
        writeField[F](p.productElement(5), field6._1, field6._2),
        writeField[G](p.productElement(6), field7._1, field7._2),
        writeField[H](p.productElement(7), field8._1, field8._2),
        writeField[I](p.productElement(8), field9._1, field9._2),
        writeField[J](p.productElement(9), field10._1, field10._2),
        writeField[K](p.productElement(10), field11._1, field11._2),
        writeField[L](p.productElement(11), field12._1, field12._2),
        writeField[M](p.productElement(12), field13._1, field13._2),
        writeField[N](p.productElement(13), field14._1, field14._2),
        writeField[O](p.productElement(14), field15._1, field15._2),
        writeField[P](p.productElement(15), field16._1, field16._2),
        writeField[Q](p.productElement(16), field17._1, field17._2))
      YamlObject(fields.flatten: _*)
    }

    def read(value: YamlValue) = construct(
      readField[A](value, field1._1, field1._2),
      readField[B](value, field2._1, field2._2),
      readField[C](value, field3._1, field3._2),
      readField[D](value, field4._1, field4._2),
      readField[E](value, field5._1, field5._2),
      readField[F](value, field6._1, field6._2),
      readField[G](value, field7._1, field7._2),
      readField[H](value, field8._1, field8._2),
      readField[I](value, field9._1, field9._2),
      readField[J](value, field10._1, field10._2),
      readField[K](value, field11._1, field11._2),
      readField[L](value, field12._1, field12._2),
      readField[M](value, field13._1, field13._2),
      readField[N](value, field14._1, field14._2),
      readField[O](value, field15._1, field15._2),
      readField[P](value, field16._1, field16._2),
      readField[Q](value, field17._1, field17._2))
  }

  def yamlFormat[A: YF, B: YF, C: YF, D: YF, E: YF, F: YF, G: YF, H: YF, I: YF, J: YF, K: YF, L: YF, M: YF, N: YF, O: YF, P: YF, Q: YF, R: YF, T <: Product](
    construct: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R) => T, field1: (String, Boolean),
    field2: (String, Boolean), field3: (String, Boolean),
    field4: (String, Boolean), field5: (String, Boolean),
    field6: (String, Boolean), field7: (String, Boolean),
    field8: (String, Boolean), field9: (String, Boolean),
    field10: (String, Boolean), field11: (String, Boolean),
    field12: (String, Boolean), field13: (String, Boolean),
    field14: (String, Boolean), field15: (String, Boolean),
    field16: (String, Boolean), field17: (String, Boolean),
    field18: (String, Boolean)) = new YF[T] {

    def write(p: T) = {
      val fields = Seq(
        writeField[A](p.productElement(0), field1._1, field1._2),
        writeField[B](p.productElement(1), field2._1, field2._2),
        writeField[C](p.productElement(2), field3._1, field3._2),
        writeField[D](p.productElement(3), field4._1, field4._2),
        writeField[E](p.productElement(4), field5._1, field5._2),
        writeField[F](p.productElement(5), field6._1, field6._2),
        writeField[G](p.productElement(6), field7._1, field7._2),
        writeField[H](p.productElement(7), field8._1, field8._2),
        writeField[I](p.productElement(8), field9._1, field9._2),
        writeField[J](p.productElement(9), field10._1, field10._2),
        writeField[K](p.productElement(10), field11._1, field11._2),
        writeField[L](p.productElement(11), field12._1, field12._2),
        writeField[M](p.productElement(12), field13._1, field13._2),
        writeField[N](p.productElement(13), field14._1, field14._2),
        writeField[O](p.productElement(14), field15._1, field15._2),
        writeField[P](p.productElement(15), field16._1, field16._2),
        writeField[Q](p.productElement(16), field17._1, field17._2),
        writeField[R](p.productElement(17), field18._1, field18._2))
      YamlObject(fields.flatten: _*)
    }

    def read(value: YamlValue) = construct(
      readField[A](value, field1._1, field1._2),
      readField[B](value, field2._1, field2._2),
      readField[C](value, field3._1, field3._2),
      readField[D](value, field4._1, field4._2),
      readField[E](value, field5._1, field5._2),
      readField[F](value, field6._1, field6._2),
      readField[G](value, field7._1, field7._2),
      readField[H](value, field8._1, field8._2),
      readField[I](value, field9._1, field9._2),
      readField[J](value, field10._1, field10._2),
      readField[K](value, field11._1, field11._2),
      readField[L](value, field12._1, field12._2),
      readField[M](value, field13._1, field13._2),
      readField[N](value, field14._1, field14._2),
      readField[O](value, field15._1, field15._2),
      readField[P](value, field16._1, field16._2),
      readField[Q](value, field17._1, field17._2),
      readField[R](value, field18._1, field18._2))
  }

  def yamlFormat[A: YF, B: YF, C: YF, D: YF, E: YF, F: YF, G: YF, H: YF, I: YF, J: YF, K: YF, L: YF, M: YF, N: YF, O: YF, P: YF, Q: YF, R: YF, S: YF, T <: Product](
    construct: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S) => T, field1: (String, Boolean),
    field2: (String, Boolean), field3: (String, Boolean),
    field4: (String, Boolean), field5: (String, Boolean),
    field6: (String, Boolean), field7: (String, Boolean),
    field8: (String, Boolean), field9: (String, Boolean),
    field10: (String, Boolean), field11: (String, Boolean),
    field12: (String, Boolean), field13: (String, Boolean),
    field14: (String, Boolean), field15: (String, Boolean),
    field16: (String, Boolean), field17: (String, Boolean),
    field18: (String, Boolean), field19: (String, Boolean)) = new YF[T] {

    def write(p: T) = {
      val fields = Seq(
        writeField[A](p.productElement(0), field1._1, field1._2),
        writeField[B](p.productElement(1), field2._1, field2._2),
        writeField[C](p.productElement(2), field3._1, field3._2),
        writeField[D](p.productElement(3), field4._1, field4._2),
        writeField[E](p.productElement(4), field5._1, field5._2),
        writeField[F](p.productElement(5), field6._1, field6._2),
        writeField[G](p.productElement(6), field7._1, field7._2),
        writeField[H](p.productElement(7), field8._1, field8._2),
        writeField[I](p.productElement(8), field9._1, field9._2),
        writeField[J](p.productElement(9), field10._1, field10._2),
        writeField[K](p.productElement(10), field11._1, field11._2),
        writeField[L](p.productElement(11), field12._1, field12._2),
        writeField[M](p.productElement(12), field13._1, field13._2),
        writeField[N](p.productElement(13), field14._1, field14._2),
        writeField[O](p.productElement(14), field15._1, field15._2),
        writeField[P](p.productElement(15), field16._1, field16._2),
        writeField[Q](p.productElement(16), field17._1, field17._2),
        writeField[R](p.productElement(17), field18._1, field18._2),
        writeField[S](p.productElement(18), field19._1, field19._2))
      YamlObject(fields.flatten: _*)
    }

    def read(value: YamlValue) = construct(
      readField[A](value, field1._1, field1._2),
      readField[B](value, field2._1, field2._2),
      readField[C](value, field3._1, field3._2),
      readField[D](value, field4._1, field4._2),
      readField[E](value, field5._1, field5._2),
      readField[F](value, field6._1, field6._2),
      readField[G](value, field7._1, field7._2),
      readField[H](value, field8._1, field8._2),
      readField[I](value, field9._1, field9._2),
      readField[J](value, field10._1, field10._2),
      readField[K](value, field11._1, field11._2),
      readField[L](value, field12._1, field12._2),
      readField[M](value, field13._1, field13._2),
      readField[N](value, field14._1, field14._2),
      readField[O](value, field15._1, field15._2),
      readField[P](value, field16._1, field16._2),
      readField[Q](value, field17._1, field17._2),
      readField[R](value, field18._1, field18._2),
      readField[S](value, field19._1, field19._2))
  }

  def yamlFormat[A: YF, B: YF, C: YF, D: YF, E: YF, F: YF, G: YF, H: YF, I: YF, J: YF, K: YF, L: YF, M: YF, N: YF, O: YF, P: YF, Q: YF, R: YF, S: YF, U: YF, T <: Product](
    construct: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, U) => T, field1: (String, Boolean),
    field2: (String, Boolean), field3: (String, Boolean),
    field4: (String, Boolean), field5: (String, Boolean),
    field6: (String, Boolean), field7: (String, Boolean),
    field8: (String, Boolean), field9: (String, Boolean),
    field10: (String, Boolean), field11: (String, Boolean),
    field12: (String, Boolean), field13: (String, Boolean),
    field14: (String, Boolean), field15: (String, Boolean),
    field16: (String, Boolean), field17: (String, Boolean),
    field18: (String, Boolean), field19: (String, Boolean),
    field20: (String, Boolean)) = new YF[T] {

    def write(p: T) = {
      val fields = Seq(
        writeField[A](p.productElement(0), field1._1, field1._2),
        writeField[B](p.productElement(1), field2._1, field2._2),
        writeField[C](p.productElement(2), field3._1, field3._2),
        writeField[D](p.productElement(3), field4._1, field4._2),
        writeField[E](p.productElement(4), field5._1, field5._2),
        writeField[F](p.productElement(5), field6._1, field6._2),
        writeField[G](p.productElement(6), field7._1, field7._2),
        writeField[H](p.productElement(7), field8._1, field8._2),
        writeField[I](p.productElement(8), field9._1, field9._2),
        writeField[J](p.productElement(9), field10._1, field10._2),
        writeField[K](p.productElement(10), field11._1, field11._2),
        writeField[L](p.productElement(11), field12._1, field12._2),
        writeField[M](p.productElement(12), field13._1, field13._2),
        writeField[N](p.productElement(13), field14._1, field14._2),
        writeField[O](p.productElement(14), field15._1, field15._2),
        writeField[P](p.productElement(15), field16._1, field16._2),
        writeField[Q](p.productElement(16), field17._1, field17._2),
        writeField[R](p.productElement(17), field18._1, field18._2),
        writeField[S](p.productElement(18), field19._1, field19._2),
        writeField[U](p.productElement(19), field20._1, field20._2))
      YamlObject(fields.flatten: _*)
    }

    def read(value: YamlValue) = construct(
      readField[A](value, field1._1, field1._2),
      readField[B](value, field2._1, field2._2),
      readField[C](value, field3._1, field3._2),
      readField[D](value, field4._1, field4._2),
      readField[E](value, field5._1, field5._2),
      readField[F](value, field6._1, field6._2),
      readField[G](value, field7._1, field7._2),
      readField[H](value, field8._1, field8._2),
      readField[I](value, field9._1, field9._2),
      readField[J](value, field10._1, field10._2),
      readField[K](value, field11._1, field11._2),
      readField[L](value, field12._1, field12._2),
      readField[M](value, field13._1, field13._2),
      readField[N](value, field14._1, field14._2),
      readField[O](value, field15._1, field15._2),
      readField[P](value, field16._1, field16._2),
      readField[Q](value, field17._1, field17._2),
      readField[R](value, field18._1, field18._2),
      readField[S](value, field19._1, field19._2),
      readField[U](value, field20._1, field20._2))
  }

  def yamlFormat[A: YF, B: YF, C: YF, D: YF, E: YF, F: YF, G: YF, H: YF, I: YF, J: YF, K: YF, L: YF, M: YF, N: YF, O: YF, P: YF, Q: YF, R: YF, S: YF, U: YF, V: YF, T <: Product](
    construct: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, U, V) => T, field1: (String, Boolean),
    field2: (String, Boolean), field3: (String, Boolean),
    field4: (String, Boolean), field5: (String, Boolean),
    field6: (String, Boolean), field7: (String, Boolean),
    field8: (String, Boolean), field9: (String, Boolean),
    field10: (String, Boolean), field11: (String, Boolean),
    field12: (String, Boolean), field13: (String, Boolean),
    field14: (String, Boolean), field15: (String, Boolean),
    field16: (String, Boolean), field17: (String, Boolean),
    field18: (String, Boolean), field19: (String, Boolean),
    field20: (String, Boolean), field21: (String, Boolean)) = new YF[T] {

    def write(p: T) = {
      val fields = Seq(
        writeField[A](p.productElement(0), field1._1, field1._2),
        writeField[B](p.productElement(1), field2._1, field2._2),
        writeField[C](p.productElement(2), field3._1, field3._2),
        writeField[D](p.productElement(3), field4._1, field4._2),
        writeField[E](p.productElement(4), field5._1, field5._2),
        writeField[F](p.productElement(5), field6._1, field6._2),
        writeField[G](p.productElement(6), field7._1, field7._2),
        writeField[H](p.productElement(7), field8._1, field8._2),
        writeField[I](p.productElement(8), field9._1, field9._2),
        writeField[J](p.productElement(9), field10._1, field10._2),
        writeField[K](p.productElement(10), field11._1, field11._2),
        writeField[L](p.productElement(11), field12._1, field12._2),
        writeField[M](p.productElement(12), field13._1, field13._2),
        writeField[N](p.productElement(13), field14._1, field14._2),
        writeField[O](p.productElement(14), field15._1, field15._2),
        writeField[P](p.productElement(15), field16._1, field16._2),
        writeField[Q](p.productElement(16), field17._1, field17._2),
        writeField[R](p.productElement(17), field18._1, field18._2),
        writeField[S](p.productElement(18), field19._1, field19._2),
        writeField[U](p.productElement(19), field20._1, field20._2),
        writeField[V](p.productElement(20), field21._1, field21._2))
      YamlObject(fields.flatten: _*)
    }

    def read(value: YamlValue) = construct(
      readField[A](value, field1._1, field1._2),
      readField[B](value, field2._1, field2._2),
      readField[C](value, field3._1, field3._2),
      readField[D](value, field4._1, field4._2),
      readField[E](value, field5._1, field5._2),
      readField[F](value, field6._1, field6._2),
      readField[G](value, field7._1, field7._2),
      readField[H](value, field8._1, field8._2),
      readField[I](value, field9._1, field9._2),
      readField[J](value, field10._1, field10._2),
      readField[K](value, field11._1, field11._2),
      readField[L](value, field12._1, field12._2),
      readField[M](value, field13._1, field13._2),
      readField[N](value, field14._1, field14._2),
      readField[O](value, field15._1, field15._2),
      readField[P](value, field16._1, field16._2),
      readField[Q](value, field17._1, field17._2),
      readField[R](value, field18._1, field18._2),
      readField[S](value, field19._1, field19._2),
      readField[U](value, field20._1, field20._2),
      readField[V](value, field21._1, field21._2))
  }

  def yamlFormat[A: YF, B: YF, C: YF, D: YF, E: YF, F: YF, G: YF, H: YF, I: YF, J: YF, K: YF, L: YF, M: YF, N: YF, O: YF, P: YF, Q: YF, R: YF, S: YF, U: YF, V: YF, X: YF, T <: Product](
    construct: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, U, V, X) => T, field1: (String, Boolean),
    field2: (String, Boolean), field3: (String, Boolean),
    field4: (String, Boolean), field5: (String, Boolean),
    field6: (String, Boolean), field7: (String, Boolean),
    field8: (String, Boolean), field9: (String, Boolean),
    field10: (String, Boolean), field11: (String, Boolean),
    field12: (String, Boolean), field13: (String, Boolean),
    field14: (String, Boolean), field15: (String, Boolean),
    field16: (String, Boolean), field17: (String, Boolean),
    field18: (String, Boolean), field19: (String, Boolean),
    field20: (String, Boolean), field21: (String, Boolean),
    field22: (String, Boolean)) = new YF[T] {

    def write(p: T) = {
      val fields = Seq(
        writeField[A](p.productElement(0), field1._1, field1._2),
        writeField[B](p.productElement(1), field2._1, field2._2),
        writeField[C](p.productElement(2), field3._1, field3._2),
        writeField[D](p.productElement(3), field4._1, field4._2),
        writeField[E](p.productElement(4), field5._1, field5._2),
        writeField[F](p.productElement(5), field6._1, field6._2),
        writeField[G](p.productElement(6), field7._1, field7._2),
        writeField[H](p.productElement(7), field8._1, field8._2),
        writeField[I](p.productElement(8), field9._1, field9._2),
        writeField[J](p.productElement(9), field10._1, field10._2),
        writeField[K](p.productElement(10), field11._1, field11._2),
        writeField[L](p.productElement(11), field12._1, field12._2),
        writeField[M](p.productElement(12), field13._1, field13._2),
        writeField[N](p.productElement(13), field14._1, field14._2),
        writeField[O](p.productElement(14), field15._1, field15._2),
        writeField[P](p.productElement(15), field16._1, field16._2),
        writeField[Q](p.productElement(16), field17._1, field17._2),
        writeField[R](p.productElement(17), field18._1, field18._2),
        writeField[S](p.productElement(18), field19._1, field19._2),
        writeField[U](p.productElement(19), field20._1, field20._2),
        writeField[V](p.productElement(20), field21._1, field21._2),
        writeField[X](p.productElement(21), field22._1, field22._2))
      YamlObject(fields.flatten: _*)
    }

    def read(value: YamlValue) = construct(
      readField[A](value, field1._1, field1._2),
      readField[B](value, field2._1, field2._2),
      readField[C](value, field3._1, field3._2),
      readField[D](value, field4._1, field4._2),
      readField[E](value, field5._1, field5._2),
      readField[F](value, field6._1, field6._2),
      readField[G](value, field7._1, field7._2),
      readField[H](value, field8._1, field8._2),
      readField[I](value, field9._1, field9._2),
      readField[J](value, field10._1, field10._2),
      readField[K](value, field11._1, field11._2),
      readField[L](value, field12._1, field12._2),
      readField[M](value, field13._1, field13._2),
      readField[N](value, field14._1, field14._2),
      readField[O](value, field15._1, field15._2),
      readField[P](value, field16._1, field16._2),
      readField[Q](value, field17._1, field17._2),
      readField[R](value, field18._1, field18._2),
      readField[S](value, field19._1, field19._2),
      readField[U](value, field20._1, field20._2),
      readField[V](value, field21._1, field21._2),
      readField[X](value, field22._1, field22._2))
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

    case other =>
      deserializationError("YamlObject expected, but got " + other,
        fieldNames = fieldName :: Nil)
  }
}

/**
 * Supplies an alternative rendering mode for optional case class
 * members. Normally optional members that are undefined (`None`) are not
 * rendered at all. By mixing in this trait into your custom YamlProtocol, you
 * enforce the rendering of undefined members as `null`.
 */
trait NullOptions extends ProductFormats {
  override protected[this] def writeField[A: YamlWriter](
    value: Any, fieldName: String, isOption: Boolean) =
    Some(YamlString(fieldName) -> value.asInstanceOf[A].toYaml)
}
