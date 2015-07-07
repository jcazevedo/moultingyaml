package net.jcazevedo.moultingyaml

import org.specs2.mutable._

class CollectionFormatsSpec extends Specification with CollectionFormats
    with BasicFormats {

  "The listFormat" should {
    val list = List(1, 2, 3)
    val yaml = YamlArray(YamlNumber(1), YamlNumber(2), YamlNumber(3))

    "convert a List[Int] to a YamlArray of YamlNumbers" in {
      list.toYaml mustEqual yaml
    }

    "convert a YamlArray of YamlNumbers to a List[Int]" in {
      yaml.convertTo[List[Int]] mustEqual list
    }
  }

  "The arrayFormat" should {
    val array = Array(1, 2, 3)
    val yaml = YamlArray(YamlNumber(1), YamlNumber(2), YamlNumber(3))

    "convert an Array[Int] to a YamlArray of YamlNumbers" in {
      array.toYaml mustEqual yaml
    }

    "convert a YamlArray of YamlNumbers to an Array[Int]" in {
      yaml.convertTo[Array[Int]] mustEqual array
    }
  }

  "The setFormat" should {
    val set = Set(1, 2, 3)
    val yaml = YamlSet(YamlNumber(1), YamlNumber(2), YamlNumber(3))

    "convert a Set[Int] to a YamlSet of YamlNumbers" in {
      set.toYaml mustEqual yaml
    }

    "convert a YamlSet of YamlNumbers to a Set[Int]" in {
      yaml.convertTo[Set[Int]] mustEqual set
    }
  }

  "The mapFormat" should {
    val map = Map(1.1 -> 1, 2.2 -> 2, 3.3 -> 3)
    val yaml = YamlObject(
      YamlNumber(1.1) -> YamlNumber(1),
      YamlNumber(2.2) -> YamlNumber(2),
      YamlNumber(3.3) -> YamlNumber(3))

    "convert a Map[Double, Int] to a YamlObject" in {
      map.toYaml mustEqual yaml
    }

    "be able to convert a YamlObject to a Map[Double, Int]" in {
      yaml.convertTo[Map[Double, Int]] mustEqual map
    }
  }

  "The indexedSeqFormat" should {
    val seq = collection.IndexedSeq(1, 2, 3)
    val yaml = YamlArray(YamlNumber(1), YamlNumber(2), YamlNumber(3))

    "convert an IndexedSeq[Int] to a YamlArray of YamlNumbers" in {
      seq.toYaml mustEqual yaml
    }

    "convert a YamlArray of YamlNumbers to an IndexedSeq[Int]" in {
      yaml.convertTo[collection.IndexedSeq[Int]] mustEqual seq
    }
  }
}
