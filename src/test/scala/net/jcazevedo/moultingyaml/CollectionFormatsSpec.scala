package net.jcazevedo.moultingyaml

import org.scalatest.{ FlatSpec, Matchers }

class CollectionFormatsSpec extends FlatSpec with Matchers with CollectionFormats
    with BasicFormats {

  {
    val list = List(1, 2, 3)
    val yaml = YamlArray(YamlNumber(1), YamlNumber(2), YamlNumber(3))

    "The listFormat" should "convert a List[Int] to a YamlArray of YamlNumbers" in {
      list.toYaml should ===(yaml)
    }

    it should "convert a YamlArray of YamlNumbers to a List[Int]" in {
      yaml.convertTo[List[Int]] should ===(list)
    }
  }

  {
    val array = Array(1, 2, 3)
    val yaml = YamlArray(YamlNumber(1), YamlNumber(2), YamlNumber(3))

    "The arrayFormat" should "convert an Array[Int] to a YamlArray of YamlNumbers" in {
      array.toYaml should ===(yaml)
    }

    it should "convert a YamlArray of YamlNumbers to an Array[Int]" in {
      yaml.convertTo[Array[Int]] should ===(array)
    }
  }

  {
    val set = Set(1, 2, 3)
    val yaml = YamlSet(YamlNumber(1), YamlNumber(2), YamlNumber(3))

    "The setFormat" should "convert a Set[Int] to a YamlSet of YamlNumbers" in {
      set.toYaml should ===(yaml)
    }

    it should "convert a YamlSet of YamlNumbers to a Set[Int]" in {
      yaml.convertTo[Set[Int]] should ===(set)
    }
  }

  {
    val map = Map(1.1 -> 1, 2.2 -> 2, 3.3 -> 3)
    val yaml = YamlObject(
      YamlNumber(1.1) -> YamlNumber(1),
      YamlNumber(2.2) -> YamlNumber(2),
      YamlNumber(3.3) -> YamlNumber(3))

    "The mapFormat" should "convert a Map[Double, Int] to a YamlObject" in {
      map.toYaml should ===(yaml)
    }

    it should "be able to convert a YamlObject to a Map[Double, Int]" in {
      yaml.convertTo[Map[Double, Int]] should ===(map)
    }
  }

  {
    val seq = collection.IndexedSeq(1, 2, 3)
    val yaml = YamlArray(YamlNumber(1), YamlNumber(2), YamlNumber(3))

    "The indexedSeqFormat" should "convert an IndexedSeq[Int] to a YamlArray of YamlNumbers" in {
      seq.toYaml should ===(yaml)
    }

    it should "convert a YamlArray of YamlNumbers to an IndexedSeq[Int]" in {
      yaml.convertTo[collection.IndexedSeq[Int]] should ===(seq)
    }
  }
}
