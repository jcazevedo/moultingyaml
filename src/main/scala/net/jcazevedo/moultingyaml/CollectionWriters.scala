package net.jcazevedo.moultingyaml

import scala.reflect.ClassTag

/**
 * Provides the YamlWriters for the most important Scala collections.
 */
trait CollectionWriters {

  /**
   * Supplies the YamlWriter for Lists.
   */
  implicit def listWriter[A: YW] = new YW[List[A]] {
    def write(list: List[A]) = YamlArray(list.map(_.toYaml).toVector)
  }

  /**
   * Supplies the YamlWriter for Arrays.
   */
  implicit def arrayWriter[A: YW: ClassTag] = new YW[Array[A]] {
    def write(array: Array[A]) = YamlArray(array.map(_.toYaml).toVector)
  }

  /**
   * Supplies the YamlWriter for Sets.
   */
  implicit def setWriter[A: YW] = new YW[Set[A]] {
    def write(set: Set[A]) = YamlSet(set.map(_.toYaml))
  }

  /**
   * Supplies the YamlWriter for Maps.
   */
  implicit def mapWriter[K: YW, V: YW] = new YW[Map[K, V]] {
    def write(m: Map[K, V]) =
      YamlObject(m.map { case (k, v) => k.toYaml -> v.toYaml })
  }

  import collection.{ immutable => imm }

  implicit def immIterableWriter[T: YW] =
    viaSeq[imm.Iterable[T], T](seq => imm.Iterable(seq: _*))

  implicit def immSeqWriter[T: YW] =
    viaSeq[imm.Seq[T], T](seq => imm.Seq(seq: _*))

  implicit def immIndexedSeqWriter[T: YW] =
    viaSeq[imm.IndexedSeq[T], T](seq => imm.IndexedSeq(seq: _*))

  implicit def immLinearSeqWriter[T: YW] =
    viaSeq[imm.LinearSeq[T], T](seq => imm.LinearSeq(seq: _*))

  implicit def vectorWriter[T: YW] =
    viaSeq[Vector[T], T](seq => Vector(seq: _*))

  import collection._

  implicit def iterableWriter[T: YW] =
    viaSeq[Iterable[T], T](seq => Iterable(seq: _*))

  implicit def seqWriter[T: YW] =
    viaSeq[Seq[T], T](seq => Seq(seq: _*))

  implicit def indexedSeqWriter[T: YW] =
    viaSeq[IndexedSeq[T], T](seq => IndexedSeq(seq: _*))

  implicit def linearSeqWriter[T: YW] =
    viaSeq[LinearSeq[T], T](seq => LinearSeq(seq: _*))

  /**
   * A YamlWriter construction helper that creates a YamlWriter for an Iterable
   * type I from a builder function List => I
   */
  def viaSeq[I <: Iterable[T], T: YW](f: imm.Seq[T] => I): YW[I] = new YW[I] {
    def write(iterable: I) = YamlArray(iterable.map(_.toYaml).toVector)
  }
}
