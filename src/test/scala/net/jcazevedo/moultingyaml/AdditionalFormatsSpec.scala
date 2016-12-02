package net.jcazevedo.moultingyaml

import org.specs2.mutable._

class AdditionalFormatsSpec extends Specification {

  case class Container[A](inner: Option[A])

  object ReaderProtocol extends DefaultYamlProtocol {
    implicit def containerReader[A: YamlFormat] = lift {
      new YamlReader[Container[A]] {
        def read(value: YamlValue) = value match {
          case YamlObject(fields, YamlTag.MAP) if fields.contains(YamlString("content")) =>
            Container(Some(fields(YamlString("content")).convertTo[A]))

          case _ => deserializationError("Unexpected format: " + value.toString)
        }
      }
    }
  }

  object WriterProtocol extends DefaultYamlProtocol {
    implicit def containerWriter[A: YamlFormat] = lift {
      new YamlWriter[Container[A]] {
        def write(obj: Container[A]) =
          YamlObject(YamlString("content") -> obj.inner.toYaml)
      }
    }
  }

  "A lifted YamlReader" should {
    val obj = Container(Some(Container(Some(List(1, 2, 3)))))
    val yaml =
      """content:
        |  content:
        |  - 1
        |  - 2
        |  - 3
        |""".stripMargin

    import ReaderProtocol._

    "properly read a Container[Container[List[Int]]] from YAML" in {
      yaml.parseYaml.convertTo[Container[Container[List[Int]]]] mustEqual obj
    }

    "throw a DeserializationException if trying to write with it" in {
      obj.toYaml must throwAn[UnsupportedOperationException]
    }
  }

  "A lifted YamlWriter" should {
    val obj = Container(Some(Container(Some(List(1, 2, 3)))))
    val yaml =
      """content:
        |  content:
        |  - 1
        |  - 2
        |  - 3
        |""".stripMargin

    import WriterProtocol._

    "properly write a Container[Container[List[Int]]] to YAML" in {
      obj.toYaml.prettyPrint mustEqual yaml
    }

    "throw a DeserializationException if trying to read with it" in {
      yaml.parseYaml.convertTo[Container[Container[List[Int]]]] must
        throwAn[UnsupportedOperationException]
    }
  }

  case class Foo(id: Long, name: String, foos: Option[List[Foo]] = None)

  object FooProtocol extends DefaultYamlProtocol {
    implicit val fooProtocol: YamlFormat[Foo] = lazyFormat(yamlFormat3(Foo))
  }

  "The lazyFormat wrapper" should {

    "enable recursive format definitions" in {
      val obj =
        Foo(1, "a", Some(List(
          Foo(2, "b", Some(List(
            Foo(3, "c")))),
          Foo(4, "d"))))

      val yaml = """id: 1
                   |name: a
                   |foos:
                   |- id: 2
                   |  name: b
                   |  foos:
                   |  - id: 3
                   |    name: c
                   |- id: 4
                   |  name: d
                   |""".stripMargin

      import FooProtocol._
      obj.toYaml.prettyPrint mustEqual yaml
    }
  }
}
