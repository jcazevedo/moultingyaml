package net.jcazevedo.moultingyaml

import scala.reflect.ClassTag

/**
 * Provides the YamlReaders for the most important Scala collections.
 */
trait CollectionReaders {

  /**
   * Supplies the YamlReader for Lists.
   */
  implicit def listReader[A: YR] = new YR[List[A]] {
    def read(value: YamlValue): List[A] = value match {
      case YamlArray(elements) =>
        elements.map(_.convertTo[A])(collection.breakOut)
      case x =>
        deserializationError("Expected List as YamlArray, but got " + x)
    }
  }

  /**
   * Supplies the YamlReader for Arrays.
   */
  implicit def arrayReader[A: YR: ClassTag] = new YR[Array[A]] {
    def read(value: YamlValue) = value match {
      case YamlArray(elements) => elements.map(_.convertTo[A]).toArray
      case x =>
        deserializationError("Expected Array as YamlArray, but got " + x)
    }
  }

  /**
   * Supplies the YamlReader for Sets.
   */
  implicit def setReader[A: YR] = new YR[Set[A]] {
    def read(value: YamlValue): Set[A] = value match {
      case YamlSet(elements) => elements.map(_.convertTo[A])
      case x =>
        deserializationError("Expected Set as YamlSet, but got " + x)
    }
  }

  /**
   * Supplies the YamlReader for Maps.
   */
  implicit def mapReader[K: YR, V: YR] = new YR[Map[K, V]] {
    def read(value: YamlValue) = value match {
      case x: YamlObject =>
        x.fields.map { case (k, v) => k.convertTo[K] -> v.convertTo[V] }
      case x =>
        deserializationError("Expected Map as YamlObject, but got " + x)
    }
  }

  import collection.{ immutable => imm }

  implicit def immIterableReader[T: YR] =
    viaSeq[imm.Iterable[T], T](seq => imm.Iterable(seq: _*))

  implicit def immSeqReader[T: YR] =
    viaSeq[imm.Seq[T], T](seq => imm.Seq(seq: _*))

  implicit def immIndexedSeqReader[T: YR] =
    viaSeq[imm.IndexedSeq[T], T](seq => imm.IndexedSeq(seq: _*))

  implicit def immLinearSeqReader[T: YR] =
    viaSeq[imm.LinearSeq[T], T](seq => imm.LinearSeq(seq: _*))

  implicit def vectorReader[T: YR] =
    viaSeq[Vector[T], T](seq => Vector(seq: _*))

  import collection._

  implicit def iterableReader[T: YR] =
    viaSeq[Iterable[T], T](seq => Iterable(seq: _*))

  implicit def seqReader[T: YR] =
    viaSeq[Seq[T], T](seq => Seq(seq: _*))

  implicit def indexedSeqReader[T: YR] =
    viaSeq[IndexedSeq[T], T](seq => IndexedSeq(seq: _*))

  implicit def linearSeqReader[T: YR] =
    viaSeq[LinearSeq[T], T](seq => LinearSeq(seq: _*))

  /**
   * A YamlReader construction helper that creates a YamlReader for an Iterable
   * type I from a builder function List => I
   */
  def viaSeq[I <: Iterable[T], T: YR](f: imm.Seq[T] => I): YR[I] = new YR[I] {
    def read(value: YamlValue) = value match {
      case YamlArray(elements) => f(elements.map(_.convertTo[T]))
      case x =>
        deserializationError("Expected Collection as YamlArray, but got " + x)
    }
  }
}
