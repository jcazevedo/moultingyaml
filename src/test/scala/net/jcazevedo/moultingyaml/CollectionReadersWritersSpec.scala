package net.jcazevedo.moultingyaml

import org.specs2.mutable._

class CollectionReadersWritersSpec extends Specification with CollectionWriters with CollectionReaders
    with BasicFormats {

  case class MyType(name: String, value: Int)

  implicit val MyTypeReader = new YamlReader[MyType] {
    def read(yaml: YamlValue) =
      yaml.asYamlObject.getFields(
        YamlString("name"), YamlString("value")) match {
          case Seq(YamlString(name), YamlNumber(value)) =>
            MyType(name, value.intValue)
          case _ => deserializationError("Expected fields: 'name' (YAML string) and 'value' (YAML number)")
        }
  }

  implicit val MyTypeWriter = new YamlWriter[MyType] {
    def write(obj: MyType) =
      YamlObject(
        YamlString("name") -> YamlString(obj.name),
        YamlString("value") -> YamlNumber(obj.value))
  }

  "The listWriter and listReader" should {
    val list = List(MyType("Bob", 3), MyType("Bill", 5))

    "convert a List of a type with a custom writer to YAML and back correctly" in {
      list.toYaml.convertTo[List[MyType]] mustEqual list
    }
  }

  "The arrayWriter and arrayReader" should {
    val array = Array(MyType("Bob", 3), MyType("Bill", 5))

    "convert an Array of a type with a custom writer to YAML and back correctly" in {
      array.toYaml.convertTo[Array[MyType]] mustEqual array
    }
  }

  "The setWriter and setReader" should {
    val set = Set(MyType("Bob", 3), MyType("Bill", 5))

    "convert a Set of a type with a custom writer to YAML and back correctly" in {
      set.toYaml.convertTo[Set[MyType]] mustEqual set
    }
  }

  "The indexedSeqWriter and indexedSeqReader" should {
    val seq = IndexedSeq(MyType("Bob", 3), MyType("Bill", 5))

    "convert an IndexedSeq of a type with a custom writer to YAML and back correctly" in {
      seq.toYaml.convertTo[IndexedSeq[MyType]] mustEqual seq
    }
  }

  "The mapWriter and mapReader" should {
    val map = Map(1.1 -> MyType("one", 1), 2.2 -> MyType("two", 2), 3.3 -> MyType("three", 3))

    "convert a Map[Double, MyType] to a YamlObject and back correctly" in {
      map.toYaml.convertTo[Map[Double, MyType]] mustEqual map
    }

  }

}
